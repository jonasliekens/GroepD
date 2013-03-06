package be.kdg.web.controllers;

import be.kdg.backend.entities.Chat;
import be.kdg.backend.services.interfaces.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Bart Verhavert
 * Date: 5/03/13 22:08
 */
@Controller
@RequestMapping("/chats")
public class ChatController {
    @Autowired
    @Qualifier("chatService")
    private ChatService chatService;

    @RequestMapping(method = RequestMethod.GET)
    public String allChats(ModelMap model) {
        List<Chat> chats = new ArrayList<Chat>();

        model.addAttribute("chats", chats);

        return "chats/chats";
    }

}
