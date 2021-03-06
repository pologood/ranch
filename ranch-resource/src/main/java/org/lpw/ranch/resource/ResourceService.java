package org.lpw.ranch.resource;

import com.alibaba.fastjson.JSONObject;

/**
 * @author lpw
 */
public interface ResourceService {
    /**
     * 资源是否存在验证器Bean名称。
     */
    String VALIDATOR_EXISTS = ResourceModel.NAME + ".exists";
    /**
     * 资源状态是否可编辑验证器Bean名称。
     */
    String VALIDATOR_EDITABLE = ResourceModel.NAME + ".validator.editable";

    /**
     * 检索资源集。
     *
     * @param type  类型，为空则不限制。
     * @param name  名称，为空则不限制。
     * @param label 标签，为空则不限制。
     * @param state 状态，为空则不限制。
     * @param uid   用户，为空则不限制。
     * @return 资源集。
     */
    JSONObject query(String type, String name, String label, int state, String uid);

    /**
     * 获取上架资源集。
     *
     * @param type  类型。
     * @param label 标签，为空则不限制。
     * @return 资源集。
     */
    JSONObject onsale(String type, String label);

    /**
     * 查找资源信息。
     *
     * @param id ID值。
     * @return 资源信息；如果不存在则返回null。
     */
    ResourceModel findById(String id);

    /**
     * 验证是否为标准的SVG文档。
     *
     * @param string SVG文档。
     * @param base64 Base64编码后的SVG文档。
     * @return 如果是则返回true；否则返回false。
     */
    boolean svg(String string, String base64);

    /**
     * 保存资源信息。
     *
     * @param resource 资源信息。
     * @return 资源信息。
     */
    JSONObject save(ResourceModel resource);

    /**
     * 设置状态。
     *
     * @param id    ID值。
     * @param state 状态。
     * @return 资源信息。
     */
    JSONObject state(String id, int state);

    /**
     * 删除。
     *
     * @param id ID值。
     */
    void delete(String id);
}
