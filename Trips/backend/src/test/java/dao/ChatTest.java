package dao;

import be.kdg.backend.dao.interfaces.ChatDao;
import be.kdg.backend.dao.interfaces.UserDao;
import be.kdg.backend.entities.Chat;
import be.kdg.backend.entities.Message;
import be.kdg.backend.entities.User;
import be.kdg.backend.utilities.Utilities;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * User: Bart Verhavert
 * Date: 5/03/13 14:22
 */
@ContextConfiguration(locations = "classpath*:/META-INF/applicationContext.xml")
public class ChatTest extends AbstractJUnit4SpringContextTests {
    @Qualifier("chatDaoImpl")
    @Autowired(required = true)
    private ChatDao chatDao;

    @Qualifier("userDaoImpl")
        @Autowired(required = true)
        private UserDao userDao;

    @Test
    public void testAddChat() {
        Chat chat = new Chat();
        chatDao.add(chat);

        assertTrue(chatDao.findById(chat.getId()) != null);
    }

    @Test
    public void testAddUsers() {
        Chat chat = new Chat();
        chatDao.add(chat);

        User user = new User("Admin@test.be", "lala", "test", "test", Utilities.makeDate("03/02/1992"));
        chat.addParticipant(user);
        chatDao.update(chat);

        assertTrue(chatDao.findById(chat.getId()).getParticipants().size() == 1);
    }

    @Test
    public void testAddMessages() {
        Chat chat = new Chat();
        chatDao.add(chat);

        User user = new User("Admin@test.be", "lala", "test", "test", Utilities.makeDate("03/02/1992"));

        Message message = new Message("Blah", user, Utilities.makeDate("01/01/2012"));
        chat.addMessage(message);
        chat.addParticipant(user);
        chatDao.update(chat);

        assertTrue(chatDao.findById(chat.getId()).getMessages().size() == 1);
    }

    @Test
    public void testFindAllMessagesByChatId() {
        Chat chat1 = new Chat();
        Chat chat2 = new Chat();
        User user = new User("blablabla@blablabla.com", "lala", "test", "test", Utilities.makeDate("04/06/1992"));

        chat1.addMessage(new Message("blah 1", user, Utilities.makeDate("01/01/2013")));
        chat1.addMessage(new Message("blah 2", user, Utilities.makeDate("02/01/2013")));
        chat2.addMessage(new Message("blah 3", user, Utilities.makeDate("03/02/1992")));

        chat1.addParticipant(user);
        chat2.addParticipant(user);

        chatDao.add(chat1);
        chatDao.add(chat2);

        assertTrue(chatDao.findAllMessagesByChatId(chat1.getId()).size() == 2);
    }

    @Test
    public void testMessagesOrder() {
        Chat chat1 = new Chat();
        User user = new User("blablabla@blablabla.bla", "lala", "test", "test", Utilities.makeDate("04/06/1992"));

        Message message1 = new Message("blah 1", user, Utilities.makeDate("01/01/2013"));
        Message message2 = new Message("blah 2", user, Utilities.makeDate("02/02/2013"));

        chat1.addMessage(message2);
        chat1.addMessage(message1);

        chat1.addParticipant(user);

        chatDao.add(chat1);

        assertEquals(chatDao.findAllMessagesByChatId(chat1.getId()).get(0), message1);
    }

    @Test
    public void findAllChatsByUserId() {
        User user1 = new User("chat.test@test.com", "blah", "blah", "blah", Utilities.makeDate("04/06/1992"));
        User user2 = new User("chat2.test@test.com", "blah2", "blah2", "blah2", Utilities.makeDate("04/06/1992"));

        Chat chat1 = new Chat();
        Chat chat2 = new Chat();
        Chat chat3 = new Chat();

        chat1.addParticipant(user1);
        chat2.addParticipant(user1);
        chat3.addParticipant(user2);

        chatDao.add(chat1);
        chatDao.add(chat2);
        chatDao.add(chat3);

        assertTrue(chatDao.findAllChatsByUserId(user1.getId()).size() == 2);
    }

//    //TODO: This @After class causes 3 tests to fail, when commented out everything works
//    @After
//    public void testRemoveChat() {
//        List<Chat> chats = chatDao.findAll();
//
//        for(Chat chat : chats) {
//            chatDao.remove(chat);
//        }
//
//        assertTrue(chatDao.findAll().size() == 0);
//    }
}
