package be.kdg.web.controllers;

import be.kdg.backend.entities.User;
import be.kdg.backend.services.interfaces.UserService;
import be.kdg.web.forms.LoginForm;
import be.kdg.web.forms.RegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;


/**
 * User: Sander
 * Date: 19/02/13 14:08
 */
@Controller
@SessionAttributes
@RequestMapping("/login")
public class LoginController {
    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String showPage(ModelMap model) {
        RegisterForm registerForm = new RegisterForm();
        LoginForm loginForm = new LoginForm();
        model.addAttribute("registerForm", registerForm);
        model.addAttribute("loginForm", loginForm);
        return "login/index";
    }

    @RequestMapping(value="/checkLogin", method=RequestMethod.POST)
    public String checkLogin(ModelMap model, @ModelAttribute("loginForm") @Valid LoginForm loginForm, BindingResult result, SessionStatus status){
        model.addAttribute("registerForm", new RegisterForm());
        if(!userService.checkLogin(loginForm.getEmail(), loginForm.getPassword()) || result.hasErrors()){
            result.addError(new ObjectError("email", "Email or password not correct."));
            return "login/index";
        }else{
            return "login/loginwin";
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(ModelMap model, @ModelAttribute("registerForm") @Valid RegisterForm registerForm, BindingResult result, SessionStatus status) {
        model.addAttribute("loginForm", new LoginForm());
        if (result.hasErrors()) {
            return "login/index";
        } else {
            User user = new User(registerForm.getEmail(), registerForm.getPassword(), registerForm.getFirstname(), registerForm.getLastname(), registerForm.getBirthday());
            userService.addUser(user);
            return "login/registercomplete";
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String goToLogin() {
        return doRedirect();
    }

    @RequestMapping(value = "/checklogin", method = RequestMethod.GET)
    public String redirectFromCheckLogin(){
        return doRedirect();
    }

    private String doRedirect() {
        return "redirect:/login";
    }
}
