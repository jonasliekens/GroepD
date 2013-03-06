package services;

import be.kdg.backend.entities.Message;
import be.kdg.backend.entities.User;
import be.kdg.backend.services.interfaces.ChatService;
import be.kdg.backend.services.interfaces.UserService;
import be.kdg.backend.utilities.Utilities;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * User: Bart Verhavert
 * Date: 5/03/13 18:03
 */
@ContextConfiguration(locations = "classpath*:/META-INF/applicationContext.xml")
public class ChatServiceTest extends AbstractJUnit4SpringContextTests {
    @Autowired(required = true)
    ChatService chatService;

    @Autowired(required = true)
    UserService userService;

    @Test
    public void testSendMessage() {
        User sender = new User("test.message.1@user.com", "blah", "blah", "blah", Utilities.makeDate("04/06/1992"));
        User receiver = new User("test.message.2@user.com", "blah", "blah", "blah", Utilities.makeDate("04/06/1992"));

        userService.addUser(sender);
        userService.addUser(receiver);

        Message message = new Message("Test message", sender, Utilities.makeDate("01/01/2013"));

        this.chatService.sendMessage(receiver.getId(), message);
    }

    @Test
    public void testFindAllChatsByUserId() {
        this.chatService.findAllChatsByUserId(5);
    }

    @Test
    public void testFindAllMessagesByChatId() {
        this.chatService.findAllMessagesByChatId(5);
    }

    @After
    public void after() {

    }
}
