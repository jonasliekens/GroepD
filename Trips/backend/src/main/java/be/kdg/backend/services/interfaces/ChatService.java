package be.kdg.backend.services.interfaces;

import be.kdg.backend.entities.Chat;
import be.kdg.backend.entities.Message;
import be.kdg.backend.entities.User;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: Bart Verhavert
 * Date: 5/03/13 18:00
 */
public interface ChatService extends GenericService<Chat, Integer> {
    @Transactional
    public void sendMessage(Integer receiverId, Message message);

    @Transactional
    public void findAllChatsByUserId(Integer id);

    @Transactional
    public void findAllMessagesByChatId(Integer id);
}
