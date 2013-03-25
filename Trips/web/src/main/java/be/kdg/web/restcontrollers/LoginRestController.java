package be.kdg.web.restcontrollers;

import be.kdg.backend.entities.User;
import be.kdg.backend.exceptions.DataNotFoundException;
import be.kdg.backend.exceptions.LoginInvalidException;
import be.kdg.backend.services.interfaces.UserService;
import be.kdg.backend.utilities.Utilities;
import be.kdg.web.controllers.LoginController;
import be.kdg.web.security.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * User: Bart Verhavert
 * Date: 27/02/13 09:25
 */
@Controller
@RequestMapping("/rest/login")
public class LoginRestController {
    static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Autowired
    @Qualifier("userDetailsService")
    private CustomUserDetailsService userDetailsService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public User doLogin(@RequestParam String username, @RequestParam String password) {
        try {
            User user = userService.checkLogin(username, password);
            return user;
        } catch (LoginInvalidException e) {
            logger.warn("Failed login attempt detected for user: " + username, e);
            return null;
        }
    }

    @RequestMapping(value = "/facebook", method = RequestMethod.POST)
    @ResponseBody
    public String doLoginFacebook(@RequestParam String id, @RequestParam String first_name, @RequestParam String last_name, @RequestParam String birthday, @RequestParam String email, HttpSession session) {
        //TODO: Rewrite, for example only 1 call in the try
        User user;

        try {
            user = userService.get(userService.checkLoginWithFacebook(id));
        }

        catch (LoginInvalidException e) {
            try {
                user = userService.findUserByEMail(email);
                userService.mergeUserWithFacebook(user.getId(), id);
                userService.update(user);
            }

            catch (DataNotFoundException e1) {
                String americanDate[] = birthday.split("/");
                //TODO: Try catch verbeteren?
                try {
                    user = new User(email, Utilities.getEncryptPassword(Utilities.newPass(10)), first_name, last_name, Utilities.makeDate(americanDate[1] + "/" + americanDate[0] + "/" + americanDate[2]), id);
                } catch (NoSuchAlgorithmException e2) {
                    return "false";
                } catch (UnsupportedEncodingException e2) {
                    return "false";
                }
                boolean userAdded = !userService.addUser(user);
                System.out.println("userAdded="+userAdded);
            }
        }

        // Log the user in into Spring Security
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, " ", userDetails.getAuthorities()));

        // Log the user in into the session
        session.setAttribute("userId", user.getId());

        return "true";
    }
}