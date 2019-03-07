package org.lpw.ranch.editor.role;

import org.lpw.tephra.ctrl.validate.ValidateWrapper;
import org.lpw.tephra.ctrl.validate.ValidatorSupport;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;

/**
 * @author lpw
 */
@Controller(RoleService.VALIDATOR_PASSWORD)
public class PasswordValidatorImpl extends ValidatorSupport {
    @Inject
    private RoleService roleService;

    @Override
    public boolean validate(ValidateWrapper validate, String[] parameters) {
        return roleService.password(parameters[0], parameters[1], parameters[2]);
    }

    @Override
    protected String getDefaultFailureMessageKey() {
        return RoleModel.NAME + ".password.illegal";
    }
}
