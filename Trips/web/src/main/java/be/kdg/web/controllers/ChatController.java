package be.kdg.web.controllers;

import be.kdg.backend.entities.Message;
import be.kdg.backend.services.interfaces.ChatService;
import be.kdg.backend.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * User: Bart Verhavert
 * Date: 5/03/13 22:08
 */
@Controller
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    @Qualifier("chatService")
    private ChatService chatService;

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String allChats(ModelMap model, HttpSession session) {
        model.addAttribute("chats", chatService.findAllChatsByUserId((Integer) session.getAttribute("userId")));

        return "chat/list";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String showChat(@PathVariable Integer id, ModelMap model, HttpSession session) {
        model.addAttribute("chats", chatService.findAllChatsByUserId((Integer) session.getAttribute("userId")));
        model.addAttribute("messages", chatService.findAllMessagesByChatId(id));

        return "chat/chat";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String addMessageToChat(@PathVariable Integer id, @RequestParam String message, ModelMap model, HttpSession session) {
        Message m = new Message(message, userService.get((Integer) session.getAttribute("userId")), new Date());

        chatService.sendMessage(id, m);

        model.addAttribute("chats", chatService.findAllChatsByUserId((Integer) session.getAttribute("userId")));
        model.addAttribute("messages", chatService.findAllMessagesByChatId(id));

        return "chat/chat";
    }
}
