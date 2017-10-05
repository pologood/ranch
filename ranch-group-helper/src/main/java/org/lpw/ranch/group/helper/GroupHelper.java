package org.lpw.ranch.group.helper;

import com.alibaba.fastjson.JSONObject;

/**
 * 群组服务支持。
 *
 * @author lpw
 */
public interface GroupHelper {
    /**
     * 获取群组信息。
     *
     * @param id ID值。
     * @return JSON数据，如果未找到则返回仅包含id属性的JSON数据。
     */
    JSONObject get(String id);
}
