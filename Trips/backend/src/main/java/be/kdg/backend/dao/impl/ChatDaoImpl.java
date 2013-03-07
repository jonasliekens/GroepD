package be.kdg.backend.dao.impl;

import be.kdg.backend.dao.interfaces.ChatDao;
import be.kdg.backend.entities.Chat;
import be.kdg.backend.entities.Message;
import be.kdg.backend.entities.User;
import be.kdg.backend.utilities.MessageComparator;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * User: Bart Verhavert
 * Date: 5/03/13 14:22
 */
@Repository
public class ChatDaoImpl implements ChatDao {
    protected EntityManager entityManager;

    public ChatDaoImpl() {
        this.entityManager =  emf.createEntityManager();;
    }

    @Override
    public void add(Chat entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public void remove(Chat entity) {
        entityManager.getTransaction().begin();
        entity=entityManager.find(Chat.class, entity.getId());
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public void update(Chat entity) {
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public Chat findById(Integer id) {
        return entityManager.find(Chat.class, id);
    }

    @Override
    @Deprecated
    public List<Chat> findAll() {
        Query query = entityManager.createQuery("select c from Chat c");
        return query.getResultList();
    }

    @Override
    public List<Message> findAllMessagesByChatId(Integer id) {
        Query query = entityManager.createQuery("SELECT c.messages FROM Chat c WHERE c.id = :id");
        query.setParameter("id", id);

        List<Message> messages = query.getResultList();
        Collections.sort(messages, new MessageComparator());

        return messages;
    }

    @Override
    public List<Chat> findAllChatsByUserId(Integer id) {
        Query query = entityManager.createQuery("SELECT u.chats FROM User u WHERE u.id = :id");
        query.setParameter("id", id);
        return query.getResultList();
    }
}
