package be.kdg.backend.services.impl;

import be.kdg.backend.dao.interfaces.ChatDao;
import be.kdg.backend.dao.interfaces.UserDao;
import be.kdg.backend.entities.Chat;
import be.kdg.backend.entities.Message;
import be.kdg.backend.entities.User;
import be.kdg.backend.services.interfaces.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * User: Bart Verhavert
 * Date: 5/03/13 18:01
 */
@Service("chatService")
public class ChatServiceImpl implements ChatService {
    @Qualifier("userDaoImpl")
    @Autowired(required = true)
    private UserDao userDao;

    @Qualifier("chatDaoImpl")
    @Autowired(required = true)
    private ChatDao chatDao;

    @Override
    public void add(Chat entity) {

    }

    @Override
    public void remove(Chat entity) {

    }

    @Override
    public void update(Chat entity) {

    }

    @Override
    public Chat get(Integer integer) {
        return null;
    }

    @Override
    public void sendMessage(Integer receiverId, Message message) {
        User receiver = userDao.findById(receiverId);

        Chat chat = null; //TODO: Search for a chat between these users

        // If there is no chat yet for these users, create a new one
        if(chat == null) {
            chat = new Chat();
            chat.addParticipant(message.getSender());
            chat.addParticipant(receiver);
            chatDao.add(chat);
        }

        chat.addMessage(message);
        chatDao.update(chat);
    }

    @Override
    public void findAllChatsByUserId(Integer id) {

    }

    @Override
    public void findAllMessagesByChatId(Integer id) {

    }
}
