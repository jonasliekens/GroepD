package be.kdg.web.restcontrollers;

import be.kdg.backend.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String doLogin(@RequestParam String username, @RequestParam String password) {
        userService.checkLogin(username, password);

        return "true";
    }
}
