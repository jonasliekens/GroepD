package be.kdg.web.validators;

import be.kdg.web.forms.EquipmentForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by IntelliJ IDEA.
 * Author: Nick De Waele
 * Date: 8/03/13
 */
@Component("equipmentValidator")
public class EquipmentValidator   implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return EquipmentForm.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
