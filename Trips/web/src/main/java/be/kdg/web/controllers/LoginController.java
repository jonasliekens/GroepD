package be.kdg.web.controllers;

import be.kdg.web.forms.RegisterForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

/**
 * User: Sander
 * Date: 19/02/13 14:08
 */
@Controller
@SessionAttributes
@RequestMapping("/login")
//@ContextConfiguration(locations = "/persistence-beans.xml")
public class LoginController {
    /*@Autowired
    private UserService userService;*/

    @RequestMapping(method = RequestMethod.GET)
    public String showPage(ModelMap model) {
        RegisterForm registerForm = new RegisterForm();

        model.addAttribute("registerForm", registerForm);

        return "login/index";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@ModelAttribute("registerForm") RegisterForm registerForm, BindingResult result, SessionStatus status) {
        //TODO: Validate the RegisterForm object and register the user

        return "login/registercomplete";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String goToLogin() {
        return "redirect:/login";
    }
}
