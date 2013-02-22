package be.kdg.controllers;

import be.kdg.forms.RegisterForm;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView showPage() {
        ModelAndView model = new ModelAndView("/login/index");
        model.addObject("registerForm", new RegisterForm());
        return model;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView register(@ModelAttribute("registerForm") RegisterForm registerForm, BindingResult result, SessionStatus status) {
        ModelAndView model = new ModelAndView("/login/registercomplete");
        return model;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String goToLogin() {
          return "redirect:/login";
    }
}
