package org.lpw.ranch.async;

import com.alibaba.fastjson.JSONObject;
import org.lpw.tephra.bean.BeanFactory;
import org.lpw.tephra.bean.ContextRefreshedListener;
import org.lpw.tephra.cache.Cache;
import org.lpw.tephra.scheduler.MinuteJob;
import org.lpw.tephra.scheduler.SecondsJob;
import org.lpw.tephra.util.Context;
import org.lpw.tephra.util.DateTime;
import org.lpw.tephra.util.Generator;
import org.lpw.tephra.util.Io;
import org.lpw.tephra.util.Logger;
import org.lpw.tephra.util.Numeric;
import org.lpw.tephra.util.Thread;
import org.lpw.tephra.util.TimeUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author lpw
 */
@Service(AsyncModel.NAME + ".service")
public class AsyncServiceImpl implements AsyncService, SecondsJob, MinuteJob, ContextRefreshedListener {
    private static final String CACHE_STATE = AsyncModel.NAME + ".service.state:";
    @Inject
    private DateTime dateTime;
    @Inject
    private Numeric numeric;
    @Inject
    private Generator generator;
    @Inject
    private Context context;
    @Inject
    private Io io;
    @Inject
    private Logger logger;
    @Inject
    private Cache cache;
    @Inject
    private Thread thread;
    @Inject
    private AsyncDao asyncDao;
    @Value("${" + AsyncModel.NAME + ".size:64}")
    private int size;
    private ExecutorService executorService;
    private Map<String, Future<String>> map = new ConcurrentHashMap<>();

    @Override
    public String submit(String key, String parameter, int timeout, Callable<String> callable) {
        return submit(key, parameter, timeout, callable, null);
    }

    @Override
    public String submit(String key, String parameter, int timeout, Callable<String> callable, AsyncNotifier asyncNotifier) {
        AsyncModel async = new AsyncModel();
        async.setKey(key);
        async.setParameter(parameter);
        async.setBegin(dateTime.now());
        async.setTimeout(new Timestamp(System.currentTimeMillis() + timeout * TimeUnit.Second.getTime()));
        asyncDao.save(async);
        putCache(async);
        map.put(async.getId(), executorService.submit(BeanFactory.getBean(Callabler.class).set(callable, asyncNotifier)));

        return async.getId();
    }

    @Override
    public JSONObject find(String id) {
        String cacheKey = CACHE_STATE + id;
        JSONObject object = cache.get(cacheKey);
        if (object == null) {
            object = new JSONObject();
            object.put("state", 0);
            cache.put(cacheKey, object, false);
        }

        return object;
    }

    @Override
    public String wait(String id, int time) {
        for (int i = 0; i < time; i++) {
            thread.sleep(1, TimeUnit.Second);
            JSONObject object = find(id);
            int state = object.getIntValue("state");
            if (state > 0)
                return state == 1 ? object.getString("result") : null;
        }

        return null;
    }

    @Override
    public void executeSecondsJob() {
        Set<String> set = new HashSet<>();
        map.forEach((id, future) -> {
            boolean done = future.isDone();
            String result = null;
            boolean success = false;
            if (done) {
                try {
                    result = future.get();
                    success = true;
                } catch (Throwable throwable) {
                    logger.warn(throwable, "获取异步任务[{}]数据时发生异常！", find(id));
                }
            }
            if (done || future.isCancelled()) {
                set.add(id);
                AsyncModel async = asyncDao.findById(id);
                if (async == null)
                    return;

                if (async.getState() != 0) {
                    putCache(async);

                    return;
                }

                if (done && success) {
                    async.setState(1);
                    async.setResult(result);
                    async.setFinish(dateTime.now());
                } else
                    async.setState(2);
                asyncDao.save(async);
                putCache(async);
            }
        });
        set.forEach(map::remove);
    }

    @Override
    public void executeMinuteJob() {
        asyncDao.query(0, dateTime.now()).getList().forEach(async -> {
            async.setState(3);
            asyncDao.save(async);
            putCache(async);
        });
    }

    private void putCache(AsyncModel async) {
        JSONObject object = new JSONObject();
        object.put("state", async.getState());
        if (async.getState() == 1)
            object.put("result", async.getResult());
        cache.put(CACHE_STATE + async.getId(), object, false);
    }

    @Override
    public int getContextRefreshedSort() {
        return 11;
    }

    @Override
    public void onContextRefreshed() {
        executorService = Executors.newFixedThreadPool(size);
    }
}
