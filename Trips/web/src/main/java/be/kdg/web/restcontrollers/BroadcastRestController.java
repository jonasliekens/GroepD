package be.kdg.web.restcontrollers;

import be.kdg.backend.services.interfaces.BroadcastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 13/03/13
 * Time: 9:58
 * Copyright @ Soulware.be
 */
@Controller
@RequestMapping("/rest/broadcast")
public class BroadcastRestController {
    @Autowired
    @Qualifier("broadcastService")
    private BroadcastService broadcastService;

    @RequestMapping(value="/confirm", method = RequestMethod.POST)
    @ResponseBody
    public String confirmMessage(@RequestParam String id, HttpSession session){
        Integer userId = (Integer)session.getAttribute("userId");
        if(id != null && userId != null){
            broadcastService.confirmMessage(userId, Integer.parseInt(id));
            return "true";
        }
        return "false";
    }
}
