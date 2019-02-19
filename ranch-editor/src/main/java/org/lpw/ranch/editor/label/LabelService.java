package org.lpw.ranch.editor.label;

import java.util.List;
import java.util.Set;

/**
 * @author lpw
 */
public interface LabelService {
    /**
     * 根据标签名称集检索编辑器。
     *
     * @param names 标签名称集。
     * @return 编辑器ID集。
     */
    Set<String> query(List<String> names);

    /**
     * 保存标签名称集。
     *
     * @param editor    编辑器ID。
     * @param names     名称集。
     * @param autoClose 是否自动提交保存。
     */
    void save(String editor, String names, boolean autoClose);
}