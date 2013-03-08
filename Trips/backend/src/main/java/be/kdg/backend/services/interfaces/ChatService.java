package be.kdg.backend.services.interfaces;

import be.kdg.backend.entities.Chat;
import be.kdg.backend.entities.Message;
import be.kdg.backend.entities.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Bart Verhavert
 * Date: 5/03/13 18:00
 */
public interface ChatService extends GenericService<Chat, Integer> {
    @Transactional
    public Chat get(List<User> users);

    @Transactional
    public void sendMessage(Integer chatId, Message message);

    @Transactional
    public List<Chat> findAllChatsByUserId(Integer id);

    @Transactional
    public List<Message> findAllMessagesByChatId(Integer id);

    @Transactional
    public List<Chat> getAllChats();
}
