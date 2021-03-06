package be.kdg.web.controllers;

import be.kdg.backend.entities.User;
import be.kdg.backend.services.interfaces.UserService;
import be.kdg.backend.utilities.Utilities;
import be.kdg.web.forms.EditProfileForm;
import be.kdg.web.forms.LoginForm;
import be.kdg.web.forms.RegisterForm;
import be.kdg.web.security.CustomUserDetailsService;
import be.kdg.web.validators.EditProfileValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * User: Sander
 * Date: 19/02/13 14:08
 */
@Controller
public class LoginController {
    static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Autowired
    @Qualifier("userDetailsService")
    private CustomUserDetailsService userDetailsService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showPage(ModelMap model) {
        //TODO: If the user is already logged in, redirect to another page or to logout
 
        model.addAttribute("registerForm", new RegisterForm());
        model.addAttribute("loginForm", new LoginForm());

        return "login/index";
    }

    @RequestMapping(value = "/login/failed", method = RequestMethod.GET)
    public String loginFailed(ModelMap model) {
        model.addAttribute("registerForm", new RegisterForm());
        model.addAttribute("loginForm", new LoginForm());
        model.addAttribute("error", "FAILED");

        return "login/index";
    }

    @RequestMapping(value = "/login/required", method = RequestMethod.GET)
    public String loginRequired(ModelMap model) {
        model.addAttribute("registerForm", new RegisterForm());
        model.addAttribute("loginForm", new LoginForm());
        model.addAttribute("error", "REQUIRED");

        return "login/index";
    }

//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public String checkLogin(ModelMap model, @ModelAttribute("loginForm") @Valid LoginForm loginForm, BindingResult result, SessionStatus status, HttpSession session) {
//        try {
//            if (!result.hasErrors()) {
//                // Throws LoginInvalidException when user not found
//                User user = userService.checkLogin(loginForm.getEmail(), loginForm.getPassword());
//                session.setAttribute("userId", user.getId());
//
//                return "redirect:/trips/";
//            } else {
//                return returnToLoginIndex(model, result);
//            }
//        } catch (LoginInvalidException e) {
//            return returnToLoginIndex(model, result);
//        }
//    }
//
//    private String returnToLoginIndex(ModelMap model, BindingResult result) {
//        model.addAttribute("registerForm", new RegisterForm());
//        result.addError(new ObjectError("email", "Email or password invalid."));
//
//        return "login/index";
//    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(ModelMap model, @ModelAttribute("registerForm") @Valid RegisterForm registerForm, BindingResult result, SessionStatus status, HttpSession session) {
        if (result.hasErrors()) {
            model.addAttribute("loginForm", new LoginForm());

            return "login/index";
        } else {
            User user;
            try {
                user = new User(registerForm.getEmail(), Utilities.getEncryptPassword(registerForm.getPassword()), registerForm.getFirstname(), registerForm.getLastname(), registerForm.getBirthday());
            } catch (NoSuchAlgorithmException e) {
                logger.debug("A NoSuchAlgorithmException occured in register. Message: " + e.getMessage(), e);
                return "exceptions/GenericException";
            } catch (UnsupportedEncodingException e) {
                logger.debug("A UnsupportedEncodingException occured in register. Message: " + e.getMessage(), e);
                return "exceptions/GenericException";
            }
            boolean userExisted = userService.addUser(user);

            if (userExisted) {
                //TODO: Show a message the user already existed?
            } else {
                //TODO: Show a message the user is registered?
                session.setAttribute("userId", user.getId());

                // Log the user in into Spring Security
                UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, " ", userDetails.getAuthorities()));
            }

            return "redirect:/";
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

    @RequestMapping(value = "/profile/edit", method = RequestMethod.GET)
    public String editProfileGet(HttpSession session, ModelMap model) {


        User user = userService.get((Integer) session.getAttribute("userId"));
        EditProfileForm editProfileForm = new EditProfileForm();

        editProfileForm.setBirthday(user.getBirthday());
        editProfileForm.setFirstname(user.getFirstName());
        editProfileForm.setLastname(user.getLastName());
        editProfileForm.setReceiveMails(user.isReceiveMails());
        editProfileForm.setShareLocation(user.isShareLocation());

        model.addAttribute("editprofileform", editProfileForm);

        return "users/profile";
    }

    @RequestMapping(value = "/profile/edit", method = RequestMethod.POST)
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
