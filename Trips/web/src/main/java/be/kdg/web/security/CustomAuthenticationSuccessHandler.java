package be.kdg.web.security;

import be.kdg.backend.entities.User;
import be.kdg.backend.exceptions.DataNotFoundException;
import be.kdg.backend.services.interfaces.BroadcastService;
import be.kdg.backend.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * User: Bart Verhavert
 * Date: 14/03/13 13:18
 */
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Qualifier("userService")
    @Autowired(required = true)
    private UserService userService;

    @Qualifier("broadcastService")
    @Autowired(required = true)
    private BroadcastService broadcastService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        //TODO: Improve catch, log user out from Spring Security?
        try {
            User user = userService.findUserByEMail(authentication.getName());
            HttpSession session = httpServletRequest.getSession();

            session.setAttribute("userId", user.getId());
            session.setAttribute("messageCount", broadcastService.getUserBroadcastMessages(user.getId()).size());
        } catch (DataNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        //TODO: Is this correct?
        httpServletResponse.sendRedirect("");

        //TODO: If the user was redirected to the login page from a page where he has to be logged in to access it, redirect to that page again (if possible)
    }
}
