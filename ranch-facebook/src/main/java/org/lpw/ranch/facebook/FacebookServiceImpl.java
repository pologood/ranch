package org.lpw.ranch.facebook;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.lpw.tephra.cache.Cache;
import org.lpw.tephra.dao.model.ModelHelper;
import org.lpw.tephra.util.Http;
import org.lpw.tephra.util.Json;
import org.lpw.tephra.util.Logger;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lpw
 */
@Service(FacebookModel.NAME + ".service")
public class FacebookServiceImpl implements FacebookService {
    private static final String CACHE_KEY = FacebookModel.NAME + ".key:";

    @Inject
    private Cache cache;
    @Inject
    private Http http;
    @Inject
    private Json json;
    @Inject
    private Logger logger;
    @Inject
    private ModelHelper modelHelper;
    @Inject
    private FacebookDao facebookDao;

    @Override
    public JSONArray query() {
        return modelHelper.toJson(facebookDao.query().getList());
    }

    @Override
    public FacebookModel findByKey(String key) {
        String cacheKey = CACHE_KEY + key;
        FacebookModel facebook = cache.get(cacheKey);
        if (facebook == null)
            cache.put(cacheKey, facebook = facebookDao.findByKey(key), false);

        return facebook;
    }

    @Override
    public void save(FacebookModel facebook) {
        FacebookModel model = findByKey(facebook.getKey());
        facebook.setId(model == null ? null : model.getId());
        facebookDao.save(facebook);
        cache.remove(CACHE_KEY + facebook.getKey());
    }

    @Override
    public void delete(String id) {
        FacebookModel facebook = facebookDao.findById(id);
        if (facebook == null)
            return;

        facebookDao.delete(id);
        cache.remove(CACHE_KEY + facebook.getKey());
    }

    @Override
    public JSONObject auth(String key, String code) {
        FacebookModel facebook = findByKey(key);
        Map<String, String> map = new HashMap<>();
        map.put("fields", "id,name,email,picture");
        map.put("access_token", code);
        String string = http.get("https://graph.facebook.com/" + facebook.getVersion() + "/me", null, map);
        JSONObject object = json.toObject(string);
        if (object == null) {
            logger.warn(null, "获取Facebook用户信息[{}:{}]失败！", map, string);

            return new JSONObject();
        }

        return object;
    }
}
