package be.kdg.web.restcontrollers;

import be.kdg.backend.entities.User;
import be.kdg.backend.exceptions.DataNotFoundException;
import be.kdg.backend.exceptions.LoginInvalidException;
import be.kdg.backend.services.interfaces.UserService;
import be.kdg.backend.utilities.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * User: Bart Verhavert
 * Date: 27/02/13 09:25
 */
@Controller
@RequestMapping("/rest/login")
public class LoginRestController {
    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String doLogin(@RequestParam String username, @RequestParam String password) {
        try {
            userService.checkLogin(username, password);
            return "true";
        } catch (LoginInvalidException e) {
            return "false";
        }
    }

    @RequestMapping(value = "/facebook", method = RequestMethod.POST)
    @ResponseBody
    public String doLoginFacebook(@RequestParam String id, @RequestParam String first_name, @RequestParam String last_name, @RequestParam String birthday, @RequestParam String email, HttpSession session) {
        Integer userId;
        System.out.println("FacebookREST INIT.");
        try {
            System.out.println("Checking login.");
            userId = userService.checkLoginWithFacebook(id);
        } catch (LoginInvalidException e) {
            try {
                System.out.println("Updating user.");
                User user = userService.findUserByEMail(email);
                userService.mergeUserWithFacebook(user.getId(), id);
                userService.update(user);
                userId = user.getId();
            } catch (DataNotFoundException e1) {
                System.out.println("Adding User.");
                String americanDate[] = birthday.split("/");
                User user = new User(email, Utilities.newPass(10), first_name, last_name, Utilities.makeDate(americanDate[1] + "/" + americanDate[0] + "/" + americanDate[2]), id);
                boolean userAdded = !userService.addUser(user);
                System.out.println("userAdded="+userAdded);
                userId = user.getId();
            }
        }
        System.out.println("setting userId SessionAtt to " + userId);
        session.setAttribute("userId", userId);
        return "true";
    }
}