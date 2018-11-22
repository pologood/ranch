package org.lpw.ranch.editor;

import org.lpw.tephra.dao.orm.PageList;

import java.sql.Timestamp;
import java.util.Set;

/**
 * @author lpw
 */
interface EditorDao {
    PageList<EditorModel> query(Set<String> ids, int template, String type, String name, String label, int modified, Set<Integer> states,
                                Timestamp createStart, Timestamp createEnd, Timestamp modifyStart, Timestamp modifyEnd,
                                Order order, int pageSize, int pageNum);

    PageList<EditorModel> query(Timestamp[] modify);

    EditorModel findById(String id);

    void save(EditorModel editor);
}
