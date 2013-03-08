package be.kdg.backend.dao.interfaces;

import be.kdg.backend.entities.BroadcastMessage;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 6/03/13
 * Time: 10:48
 * Copyright @ Soulware.be
 */
public interface BroadcastDao extends GenericDao<BroadcastMessage, Integer> {
    @Transactional
    public List<BroadcastMessage> findMessagesByUserId(Integer userId);
    @Transactional
    public void confirmMessage(Integer userId, Integer messageId);
}
