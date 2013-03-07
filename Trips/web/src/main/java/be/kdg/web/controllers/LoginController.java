package be.kdg.web.controllers;

import be.kdg.backend.entities.User;
import be.kdg.backend.exceptions.LoginInvalidException;
import be.kdg.backend.services.interfaces.UserService;
import be.kdg.web.forms.EditProfileForm;
import be.kdg.web.forms.LoginForm;
import be.kdg.web.forms.RegisterForm;
import be.kdg.web.validators.EditProfileValidator;
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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


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

    @RequestMapping(method = RequestMethod.POST)
    public String checkLogin(ModelMap model, @ModelAttribute("loginForm") @Valid LoginForm loginForm, BindingResult result, SessionStatus status, HttpSession session) {
        try {
            if (!result.hasErrors()) {
                // Throws LoginInvalidException when user not found
                User user = userService.checkLogin(loginForm.getEmail(), loginForm.getPassword());
                session.setAttribute("userId", user.getId());

                return "redirect:/trips/";
            } else {
                return returnToLoginIndex(model, result);
            }
        } catch (LoginInvalidException e) {
            return returnToLoginIndex(model, result);
        }
    }

    private String returnToLoginIndex(ModelMap model, BindingResult result) {
        model.addAttribute("registerForm", new RegisterForm());
        result.addError(new ObjectError("email", "Email or password invalid."));

        return "login/index";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(ModelMap model, @ModelAttribute("registerForm") @Valid RegisterForm registerForm, BindingResult result, SessionStatus status, HttpSession session) {
        if (result.hasErrors()) {
            model.addAttribute("loginForm", new LoginForm());

            return "login/index";
        } else {
            User user = new User(registerForm.getEmail(), registerForm.getPassword(), registerForm.getFirstname(), registerForm.getLastname(), registerForm.getBirthday());
            boolean userExisted = userService.addUser(user);

            if (userExisted) {
                model.addAttribute("msg", "User already existed. You can login with your email and password.");
            } else {
                model.addAttribute("msg", "User has been succesfully registered. You can now login with your provided email and password.");
                session.setAttribute("userId", user.getId());
            }

            return "login/registercomplete";
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String goToLogin() {
        return "redirect:/login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logOut() {
        return "login/logout";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logOut(HttpSession session) {
        session.removeAttribute("userId");

        return "redirect:/";
    }

    @RequestMapping(value = "/editprofile", method = RequestMethod.GET)
    public String editProfileGet(HttpSession session, ModelMap model) {


        User user = userService.get((Integer) session.getAttribute("userId"));
        EditProfileForm editProfileForm = new EditProfileForm();
        editProfileForm.setBirthday(user.getBirthday());
        editProfileForm.setFirstname(user.getFirstName());
        editProfileForm.setLastname(user.getLastName());
        model.addAttribute("editprofileform", editProfileForm);
        return "users/profile";
    }

    @RequestMapping(value = "/editprofile", method = RequestMethod.POST)
    public String editProfilePost(HttpSession session, @ModelAttribute("editprofileform") @Valid EditProfileForm editProfileForm, BindingResult result) {
        EditProfileValidator editProfileValidator = new EditProfileValidator();
        editProfileValidator.validate(editProfileForm, result);
        if (result.hasErrors()) {
            return "users/profile";
        } else {
            User user = userService.get((Integer) session.getAttribute("userId"));
            Calendar cal = new GregorianCalendar();
            cal.setTime(editProfileForm.getBirthday());
            user.setBirthday(new Date(cal.getTimeInMillis()));
            user.setFirstName(editProfileForm.getFirstname());
            user.setLastName(editProfileForm.getLastname());
            user.setReceiveMails(editProfileForm.isReceiveMails());
            user.setShareLocation(editProfileForm.isShareLocation());
            userService.update(user);
            return "users/profile";
        }
    }
}
