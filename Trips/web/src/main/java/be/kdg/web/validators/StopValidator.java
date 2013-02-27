package be.kdg.web.validators;

import be.kdg.web.forms.StopForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by IntelliJ IDEA.
 * Author: Nick De Waele
 * Date: 27/02/13
 */
@Component("stopValidator")
public class StopValidator implements Validator {
    @Override
       public boolean supports(Class<?> aClass) {
           return StopForm.class.isAssignableFrom(aClass);
       }

       @Override
       public void validate(Object o, Errors errors) {
           ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required.name", "Required");
       }
}
