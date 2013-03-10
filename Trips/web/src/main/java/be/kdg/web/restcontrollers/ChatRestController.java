package be.kdg.web.restcontrollers;

import be.kdg.backend.entities.Chat;
import be.kdg.backend.entities.Message;
import be.kdg.backend.services.interfaces.ChatService;
import be.kdg.backend.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * User: Bart Verhavert
 * Date: 5/03/13 22:10
 */
@Controller
@RequestMapping("/rest/chat")
public class ChatRestController {
    @Autowired
    @Qualifier("chatService")
    private ChatService chatService;

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Chat> allChats(HttpSession session) {
        return chatService.findAllChatsByUserId((Integer) session.getAttribute("userId"));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Chat showChat(@PathVariable Integer id) {
        return chatService.get(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public void addMessageToChat(@PathVariable Integer id, @RequestParam String text, HttpSession session) {
        Message message = new Message(text, userService.get((Integer) session.getAttribute("userId")), new Date());

        chatService.sendMessage(id, message);
    }
}
