package be.kdg.web.controllers;

import be.kdg.backend.entities.User;
import be.kdg.backend.exceptions.LoginInvalidException;
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
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


/**
 * User: Sander
 * Date: 19/02/13 14:08
 */
@Controller
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

    @RequestMapping(value = "/checkLogin", method = RequestMethod.POST)
    public String checkLogin(ModelMap model, @ModelAttribute("loginForm") @Valid LoginForm loginForm, BindingResult result, SessionStatus status, HttpSession session) {
        model.addAttribute("registerForm", new RegisterForm());
        try {
            if (!result.hasErrors()) {
                User user = userService.checkLogin(loginForm.getEmail(), loginForm.getPassword());
                session.setAttribute("userId", user.getId());
                //Redirect to loginWin? Why not /trips/list?
                //return "login/loginWin";
                return "redirect:/trips/";
            } else {
                return returnToLoginIndex(result);
            }
        } catch (LoginInvalidException e) {
            return returnToLoginIndex(result);
        }
    }

    private String returnToLoginIndex(BindingResult result) {
        result.addError(new ObjectError("email", "Email or password invalid."));
        return "login/index";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(ModelMap model, @ModelAttribute("registerForm") @Valid RegisterForm registerForm, BindingResult result, SessionStatus status) {
        model.addAttribute("loginForm", new LoginForm());
        if (result.hasErrors()) {
            return "login/index";
        } else {
            User user = new User(registerForm.getEmail(), registerForm.getPassword(), registerForm.getFirstname(), registerForm.getLastname(), registerForm.getBirthday());
            boolean userExisted = userService.addUser(user);
            if (userExisted) {
                model.addAttribute("msg", "User already existed. You can login with your email and password.");
            } else {
                model.addAttribute("msg", "User has been succesfully registered. You can now login with your provided email and password.");
            }
            return "login/registercomplete";
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String goToLogin() {
        return doRedirect();
    }

    @RequestMapping(value = "/checklogin", method = RequestMethod.GET)
    public String redirectFromCheckLogin() {
        return doRedirect();
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logOut() {

        return "login/logout";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logOut(HttpSession session) {
        session.removeAttribute("userId");
        return doRedirect();
    }

    private String doRedirect() {
        return "redirect:/login";
    }
}
