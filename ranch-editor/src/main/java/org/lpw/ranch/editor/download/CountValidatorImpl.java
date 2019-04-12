package org.lpw.ranch.editor.download;

import org.lpw.ranch.editor.buy.BuyService;
import org.lpw.ranch.user.helper.UserHelper;
import org.lpw.tephra.ctrl.validate.ValidateWrapper;
import org.lpw.tephra.ctrl.validate.ValidatorSupport;
import org.lpw.tephra.util.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
import java.util.Set;

/**
 * @author lpw
 */
@Controller(DownloadService.VALIDATOR_COUNT)
public class CountValidatorImpl extends ValidatorSupport {
    @Inject
    private DateTime dateTime;
    @Inject
    private BuyService buyService;
    @Inject
    private UserHelper userHelper;
    @Inject
    private DownloadDao downloadDao;
    @Value("${" + DownloadModel.NAME + ".date-max:2}")
    private int dateMax;
    @Value("${" + DownloadModel.NAME + ".vip-date-max:10}")
    private int vipDateMax;

    @Override
    public boolean validate(ValidateWrapper validate, String parameter) {
        String user = userHelper.id();
        if (buyService.find(user, parameter) != null)
            return true;

        Set<String> set = downloadDao.editors(user, dateTime.getStart(dateTime.now()));
        set.remove(parameter);
        int count = set.size();

        return count < dateMax || (count < vipDateMax && userHelper.isVip());
    }

    @Override
    protected String getDefaultFailureMessageKey() {
        return DownloadModel.NAME + ".over-date-max";
    }
}
