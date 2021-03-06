package org.lpw.ranch.editor.price;

import org.lpw.tephra.dao.orm.PageList;

/**
 * @author lpw
 */
interface PriceDao {
    PageList<PriceModel> query(String type, String group, int pageSize, int pageNum);

    PriceModel findById(String id);

    void save(PriceModel price);

    void delete(String id);
}
