package org.lpw.ranch.ad;

import org.lpw.tephra.dao.orm.PageList;

/**
 * @author lpw
 */
interface AdDao {
    PageList<AdModel> query(String type, int state, int pageSize, int pageNum);

    PageList<AdModel> query(String type, int state);

    AdModel findById(String id);

    void save(AdModel ad);

    void delete(String id);
}
