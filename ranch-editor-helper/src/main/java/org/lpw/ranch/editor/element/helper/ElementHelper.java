package org.lpw.ranch.editor.element.helper;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * @author lpw
 */
public interface ElementHelper {
    /**
     * 检索元素集。
     *
     * @param editor    编辑器ID值。
     * @param parent    父元素ID值。
     * @param recursive 是否递归获取子元素集。
     * @return 元素集。
     */
    JSONArray query(String editor, String parent, boolean recursive);

    /**
     * 检索元素集。
     *
     * @param header    请求Header信息集。
     * @param editor    编辑器ID值。
     * @param parent    父元素ID值。
     * @param recursive 是否递归获取子元素集。
     * @return 元素集。
     */
    JSONArray query(Map<String, String> header, String editor, String parent, boolean recursive);

    /**
     * 保存元素信息。
     *
     * @param id     ID值。
     * @param editor 编辑器ID值。
     * @param parent 父元素ID值。
     * @param sort   顺序。
     * @param map    扩展属性集。
     * @return 元素信息；保存失败则返回空JSON{}。
     */
    JSONObject save(String id, String editor, String parent, int sort, Map<String, String> map);

    /**
     * 保存元素信息。
     *
     * @param header 请求Header信息集。
     * @param id     ID值。
     * @param editor 编辑器ID值。
     * @param parent 父元素ID值。
     * @param sort   顺序。
     * @param map    扩展属性集。
     * @return 元素信息；保存失败则返回空JSON{}。
     */
    JSONObject save(Map<String, String> header, String id, String editor, String parent, int sort, Map<String, String> map);
}
