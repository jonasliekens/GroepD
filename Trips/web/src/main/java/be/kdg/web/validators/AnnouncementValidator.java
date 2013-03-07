package be.kdg.web.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by IntelliJ IDEA.
 * Author: Nick De Waele
 * Date: 7/03/13
 */
@Component("announcementValidator")
public class AnnouncementValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return AnnouncementValidator.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
