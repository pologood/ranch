package org.lpw.ranch.alipay;

import org.lpw.tephra.ctrl.validate.ValidateWrapper;
import org.lpw.tephra.ctrl.validate.ValidatorSupport;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;

/**
 * @author lpw
 */
@Controller(AlipayService.VALIDATOR_NOT_EXISTS)
public class NotExistsValidatorImpl extends ValidatorSupport {
    @Inject
    private AlipayService alipayService;

    @Override
    public boolean validate(ValidateWrapper validate, String[] parameters) {
        AlipayModel alipay = alipayService.findByAppId(parameters[1]);

        return alipay == null || alipay.getKey().equals(parameters[0]);
    }

    @Override
    protected String getDefaultFailureMessageKey() {
        return AlipayModel.NAME + ".exists";
    }
}
