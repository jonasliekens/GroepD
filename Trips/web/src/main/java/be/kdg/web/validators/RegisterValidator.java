package be.kdg.web.validators;

import be.kdg.web.forms.RegisterForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created with IntelliJ IDEA.
 * User: Bart
 * Date: 27/02/13
 * Time: 13:15
 * To change this template use File | Settings | File Templates.
 */

@Component("registerValidator")
public class RegisterValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return RegisterForm.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
    }
}
