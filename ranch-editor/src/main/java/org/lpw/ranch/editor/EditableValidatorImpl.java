package org.lpw.ranch.editor;

import org.lpw.tephra.ctrl.validate.ValidateWrapper;
import org.lpw.tephra.ctrl.validate.ValidatorSupport;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;

/**
 * @author lpw
 */
@Controller(EditorService.VALIDATOR_EDITABLE)
public class EditableValidatorImpl extends ValidatorSupport {
    @Inject
    private EditorService editorService;

    @Override
    public boolean validate(ValidateWrapper validate, String parameter) {
        return editorService.findById(parameter).getState() != 3;
    }

    @Override
    protected String getDefaultFailureMessageKey() {
        return EditorModel.NAME + ".edit.disable";
    }
}
