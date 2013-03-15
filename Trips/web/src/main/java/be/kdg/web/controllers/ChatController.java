package be.kdg.web.controllers;

import be.kdg.backend.entities.Chat;
import be.kdg.backend.entities.Message;
import be.kdg.backend.entities.User;
import be.kdg.backend.services.interfaces.ChatService;
import be.kdg.backend.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: Bart Verhavert
 * Date: 5/03/13 22:08
 */
@Controller
@RequestMapping("/chat")
@Secured("ROLE_USER")
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
        Integer userId = (Integer) session.getAttribute("userId");

        model.addAttribute("chats", chatService.findAllChatsByUserId(userId));
        model.addAttribute("participants", chatService.get(id).getParticipants());
        model.addAttribute("messages", chatService.findAllMessagesByChatId(id));

        return "chat/chat";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String addMessageToChat(@PathVariable Integer id, @RequestParam String message, ModelMap model, HttpSession session) {
        User user = userService.get((Integer) session.getAttribute("userId"));
        Message m = new Message(message, user, new Date());
        Chat chat = chatService.get(id);
        //chatService.sendMessage(id, m);
        m.setChat(chat);
        user.addMessage(m);
        userService.update(user);
        model.addAttribute("chats", chatService.findAllChatsByUserId((Integer) session.getAttribute("userId")));
        model.addAttribute("participants", chatService.get(id).getParticipants());
        model.addAttribute("messages", chatService.findAllMessagesByChatId(id));

        return "chat/chat";
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public String redirectToChatWithUser(@PathVariable Integer id, HttpSession session) {
        List<User> users = new ArrayList<User>();
        users.add(userService.get(id));
        users.add(userService.get((Integer) session.getAttribute("userId")));

        return "redirect:/chat/" + chatService.get(users).getId();
    }
}
