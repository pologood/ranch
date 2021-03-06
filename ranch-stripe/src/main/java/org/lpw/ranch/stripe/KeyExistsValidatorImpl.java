package org.lpw.ranch.stripe;

import org.lpw.tephra.ctrl.validate.ValidateWrapper;
import org.lpw.tephra.ctrl.validate.ValidatorSupport;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;

/**
 * @author lpw
 */
@Controller(StripeService.VALIDATOR_KEY_EXISTS)
public class KeyExistsValidatorImpl extends ValidatorSupport {
    @Inject
    private StripeService stripeService;

    @Override
    public boolean validate(ValidateWrapper validate, String parameter) {
        return stripeService.findByKey(parameter) != null;
    }

    @Override
    protected String getDefaultFailureMessageKey() {
        return StripeModel.NAME + ".key.not-exists";
    }
}
