package be.kdg.backend.dao.interfaces;

import be.kdg.backend.entities.Chat;
import be.kdg.backend.entities.Message;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Bart Verhavert
 * Date: 5/03/13 14:25
 */
public interface ChatDao extends GenericDao<Chat,Integer> {
    @Transactional
    public List<Message> findAllMessagesByChatId(Integer id);
    @Transactional
    public List<Chat> findAllChatsByUserId(Integer id);
}
