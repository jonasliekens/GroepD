package be.kdg.backend.services.interfaces;

import be.kdg.backend.entities.BroadcastMessage;

import java.util.List;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 8/03/13
 * Time: 11:16
 * Copyright @ Soulware.be
 */
public interface BroadcastService extends GenericService<BroadcastMessage, Integer>{
    public List<BroadcastMessage> getUserBroadcastMessages(Integer userId);
    public void confirmMessage(Integer userId, Integer messageId);
    public List<BroadcastMessage> getAllBroadcastMessages();
}
