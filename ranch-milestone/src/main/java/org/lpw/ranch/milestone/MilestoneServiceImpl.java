package org.lpw.ranch.milestone;

import com.alibaba.fastjson.JSONObject;
import org.lpw.ranch.user.helper.UserHelper;
import org.lpw.ranch.util.Pagination;
import org.lpw.tephra.dao.model.ModelHelper;
import org.lpw.tephra.util.DateTime;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * @author lpw
 */
@Service(MilestoneModel.NAME + ".service")
public class MilestoneServiceImpl implements MilestoneService {
    @Inject
    private DateTime dateTime;
    @Inject
    private ModelHelper modelHelper;
    @Inject
    private Pagination pagination;
    @Inject
    private UserHelper userHelper;
    @Inject
    private MilestoneDao milestoneDao;

    @Override
    public JSONObject user() {
        return milestoneDao.query(userHelper.id(), pagination.getPageSize(20), pagination.getPageNum()).toJson();
    }

    @Override
    public JSONObject find(String type) {
        MilestoneModel milestone = milestoneDao.find(userHelper.id(), type);

        return milestone == null ? new JSONObject() : modelHelper.toJson(milestone);
    }

    @Override
    public JSONObject create(MilestoneModel milestone) {
        MilestoneModel model = milestoneDao.find(milestone.getUser(), milestone.getType());
        if (model == null) {
            model = new MilestoneModel();
            model.setUser(milestone.getUser());
            model.setType(milestone.getType());
            model.setJson(milestone.getJson());
            model.setTime(dateTime.now());
            milestoneDao.save(model);
        }

        return modelHelper.toJson(model);
    }
}
