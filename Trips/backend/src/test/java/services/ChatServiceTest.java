package services;

import be.kdg.backend.entities.Chat;
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

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
    public void testAddAndGet() {
        Chat chat = new Chat();

        chatService.add(chat);

        Chat newChat = chatService.get(chat.getId());

        assertEquals(chat, newChat);
    }

    @Test
    public void testSendMessage() {
        User user = new User("test.message.1@user.com", "blah", "blah", "blah", Utilities.makeDate("04/06/1992"));
        Message message = new Message("Test message", user, Utilities.makeDate("01/01/2013"));
        Chat chat = new Chat();

        chat.addParticipant(user);

        chatService.add(chat);

        this.chatService.sendMessage(chat.getId(), message);
    }

    @Test
    public void testFindAllChatsByUserId() {
        User user1 = new User("testFindAllChatsByUserId1@test.com", "", "", "", Utilities.makeDate("04/06/1992"));
        User user2 = new User("testFindAllChatsByUserId2@test.com", "", "", "", Utilities.makeDate("04/06/1992"));

        Chat chat1 = new Chat();
        Chat chat2 = new Chat();
        Chat chat3 = new Chat();

        chat1.addParticipant(user1);
        chat2.addParticipant(user1);
        chat3.addParticipant(user2);

        this.chatService.add(chat1);
        this.chatService.add(chat2);
        this.chatService.add(chat3);

        assertTrue(this.chatService.findAllChatsByUserId(user1.getId()).size() == 2);
    }

    @Test
    public void testFindAllMessagesByChatId() {
        User user = new User("testFindAllMessagesByChatId@test.com", "", "", "", Utilities.makeDate("04/06/1992"));
        Chat chat1 = new Chat();
        Chat chat2 = new Chat();

        Message message1 = new Message("Blah 1", user, new Date());
        Message message2 = new Message("Blah 2", user, new Date());
        Message message3 = new Message("Blah 3", user, new Date());

        chat1.addMessage(message1);
        chat1.addMessage(message2);
        chat2.addMessage(message3);

        this.chatService.add(chat1);
        this.chatService.add(chat2);

        assertTrue(this.chatService.findAllMessagesByChatId(chat1.getId()).size() == 2);
    }

    @After
    public void removeAllChats(){
        for (Chat ch: chatService.getAllChats()){
            chatService.remove(ch);
        }
    }
}
