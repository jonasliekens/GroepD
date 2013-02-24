package be.kdg.web.validators;

import be.kdg.web.forms.TripForm;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * User: Bart Verhavert
 * Date: 24/02/13 12:30
 */

@Component("tripValidator")
public class TripValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return TripForm.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required.name", "Required");
    }
}
