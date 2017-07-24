package org.lpw.ranch.console;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * @author lpw
 */
public interface ConsoleService {
    /**
     * 获取菜单集。
     *
     * @return 菜单集。
     */
    JSONArray menus();

    /**
     * 获取页面元数据。
     *
     * @param service 服务key。
     * @return 页面元数据；如果不存在则返回空JSON。
     */
    JSONObject meta(String service);

    /**
     * 执行服务。
     *
     * @param service   服务key。
     * @param parameter 参数集。
     * @return 服务结果。
     */
    JSONObject service(String service, Map<String, String> parameter);
}
