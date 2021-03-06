package org.lpw.ranch.message;

import org.lpw.tephra.dao.orm.PageList;
import org.lpw.tephra.dao.orm.lite.LiteOrm;
import org.lpw.tephra.dao.orm.lite.LiteQuery;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author lpw
 */
@Repository(MessageModel.NAME + ".dao")
class MessageDaoImpl implements MessageDao {
    @Inject
    private LiteOrm liteOrm;

    @Override
    public void save(MessageModel message) {
        liteOrm.save(message);
    }

    @Override
    public PageList<MessageModel> query(Timestamp time,Timestamp deadline,  String sender, Set<String> receivers) {
        StringBuilder where = new StringBuilder("c_time>=? and c_deadline>=? and (c_sender=? or c_receiver in(?");
        List<Object> args = new ArrayList<>();
        args.add(time);
        args.add(deadline);
        args.add(sender);
        args.add(sender);
        receivers.forEach(receiver -> {
            where.append(",?");
            args.add(receiver);
        });
        where.append("))");

        return liteOrm.query(new LiteQuery(MessageModel.class).where(where.toString()).order("c_time desc"), args.toArray());
    }

    @Override
    public MessageModel findByCode(String code) {
        return liteOrm.findOne(new LiteQuery(MessageModel.class).where("c_code=?"), new Object[]{code});
    }
}
