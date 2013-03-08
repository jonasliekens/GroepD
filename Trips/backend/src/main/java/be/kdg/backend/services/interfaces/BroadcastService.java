package be.kdg.backend.services.interfaces;

import be.kdg.backend.entities.BroadcastMessage;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 8/03/13
 * Time: 11:16
 * Copyright @ Soulware.be
 */
public interface BroadcastService extends GenericService<BroadcastMessage, Integer>{
    @Transactional
    public List<BroadcastMessage> getUserBroadcastMessages(Integer userId);
    @Transactional
    public void confirmMessage(Integer userId, Integer messageId);
    @Transactional
    public List<BroadcastMessage> getAllBroadcastMessages();
}
