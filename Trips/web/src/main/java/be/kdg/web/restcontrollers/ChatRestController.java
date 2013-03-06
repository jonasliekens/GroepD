package be.kdg.web.restcontrollers;

import be.kdg.backend.services.interfaces.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * User: Bart Verhavert
 * Date: 5/03/13 22:10
 */
@Controller
@RequestMapping("/chats")
public class ChatRestController {
    @Autowired
    @Qualifier("chatService")
    private ChatService chatService;
}
