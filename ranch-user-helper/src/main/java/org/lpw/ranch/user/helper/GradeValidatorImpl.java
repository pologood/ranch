package org.lpw.ranch.user.helper;

import org.lpw.tephra.ctrl.validate.ValidateWrapper;
import org.lpw.tephra.ctrl.validate.ValidatorSupport;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;

/**
 * @author lpw
 */
@Controller(UserHelper.VALIDATOR_GRADE)
public class GradeValidatorImpl extends ValidatorSupport {
    @Inject
    private UserHelper userHelper;

    @Override
    public boolean validate(ValidateWrapper validate, String parameter) {
        int grade = userHelper.grade();

        return grade >= 0 && (validate.getNumber()[0] < 0 || validate.getNumber()[0] <= grade)
                && (validate.getNumber()[1] < 0 || validate.getNumber()[1] >= grade);
    }

    @Override
    protected String getDefaultFailureMessageKey() {
        return "ranch.user.helper.grade.illegal";
    }
}
