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
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.AssertTrue;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * User: Bart Verhavert
 * Date: 5/03/13 18:03
 */
@ContextConfiguration(locations = "classpath*:/META-INF/applicationContext.xml")
//TODO: extends AbstractTransactionalJUnit4SpringContextTests
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
    @Transactional
    public void testGetOrCreate() {
        User user1 = new User("testGetOrCreate1@test.com", "", "", "", Utilities.makeDate("04/06/1992"));
        User user2 = new User("testGetOrCreate2@test.com", "", "", "", Utilities.makeDate("04/06/1992"));
        User user3 = new User("testGetOrCreate3@test.com", "", "", "", Utilities.makeDate("04/06/1992"));

        userService.addUser(user1);
        userService.addUser(user2);
        userService.addUser(user3);

        List<User> list1 = new ArrayList<User>();
        List<User> list2 = new ArrayList<User>();
        List<User> list3 = new ArrayList<User>();

        list1.add(user1);
        list1.add(user2);

        list2.add(user1);
        list2.add(user3);

        list3.add(user2);
        list3.add(user3);

        Chat chat1 = chatService.get(list1);
        Chat chat2 = chatService.get(list2);
        Chat chat3 = chatService.get(list3);

        user1 = userService.get(user1.getId());
        user2 = userService.get(user2.getId());
        user3 = userService.get(user3.getId());

        List<User> testList = new ArrayList<User>();
        testList.add(user3);
        testList.add(user1);
        Chat testChat = chatService.get(testList);

        assertEquals(chat2.getId(), testChat.getId());
    }

    @Test
    public void testSendMessage() {
        User user = new User("test.message.1@user.com", "blah", "blah", "blah", Utilities.makeDate("04/06/1992"));
        Message message = new Message("Test message", user, Utilities.makeDate("01/01/2013"));
        Chat chat = new Chat();

        chatService.add(chat);

        this.chatService.sendMessage(chat.getId(), message);

        assertTrue(this.chatService.get(chat.getId()).getMessages().size() == 1);
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

//    @After
//    public void removeAllChats(){
//        for (Chat ch: chatService.getAllChats()){
//            chatService.remove(ch);
//        }
//    }
}
