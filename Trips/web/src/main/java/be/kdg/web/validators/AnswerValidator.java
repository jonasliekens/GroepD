package be.kdg.web.validators;

import be.kdg.web.forms.AnswerForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created with IntelliJ IDEA.
 * User: Nick
 * Date: 1/03/13
 * Time: 0:27
 * To change this template use File | Settings | File Templates.
 */
@Component("answerValidator")
public class AnswerValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return AnswerForm.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
