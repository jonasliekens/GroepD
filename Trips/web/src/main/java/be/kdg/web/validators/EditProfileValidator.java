package be.kdg.web.validators;

import be.kdg.web.forms.EditProfileForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by IntelliJ IDEA.
 * Author: Nick De Waele
 * Date: 6/03/13
 */
@Component("editProfileValidator")
public class EditProfileValidator  implements Validator {
    @Override
        public boolean supports(Class<?> aClass) {
            return EditProfileForm.class.isAssignableFrom(aClass);
        }

        @Override
        public void validate(Object o, Errors errors) {
            //To change body of implemented methods use File | Settings | File Templates.
        }
}
