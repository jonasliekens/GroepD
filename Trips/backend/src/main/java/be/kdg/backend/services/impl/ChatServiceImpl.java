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

import java.util.List;

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
        chatDao.add(entity);
    }

    @Override
    public void remove(Chat entity) {

    }

    @Override
    public void update(Chat entity) {

    }

    @Override
    public Chat get(Integer integer) {
        return chatDao.findById(integer);
    }

    @Override
    public void sendMessage(Integer chatId, Message message) {
        Chat chat = this.get(chatId);

        chat.addMessage(message);

        chatDao.update(chat);
    }

    @Override
    public List<Chat> findAllChatsByUserId(Integer id) {
        return chatDao.findAllChatsByUserId(id);
    }

    @Override
    public List<Message> findAllMessagesByChatId(Integer id) {
        return chatDao.findAllMessagesByChatId(id);
    }
}
