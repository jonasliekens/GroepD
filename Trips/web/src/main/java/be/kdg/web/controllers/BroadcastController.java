package be.kdg.web.controllers;

import be.kdg.backend.services.interfaces.BroadcastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 12/03/13
 * Time: 14:19
 * Copyright @ Soulware.be
 */
@Controller
@RequestMapping("/broadcast")
public class BroadcastController {
    @Autowired
    @Qualifier("broadcastService")
    private BroadcastService broadcastService;

    @RequestMapping(method = RequestMethod.GET)
    public String getAllMessages(ModelMap model, HttpSession session){
        model.addAttribute("messages", broadcastService.getUserBroadcastMessages((Integer)session.getAttribute("userId")));
        return "broadcast/index";
    }
}
