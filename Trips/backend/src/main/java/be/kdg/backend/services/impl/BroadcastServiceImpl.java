package be.kdg.backend.services.impl;

import be.kdg.backend.dao.interfaces.BroadcastDao;
import be.kdg.backend.entities.BroadcastMessage;
import be.kdg.backend.services.interfaces.BroadcastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 8/03/13
 * Time: 11:26
 * Copyright @ Soulware.be
 */
@Service("broadcastService")
public class BroadcastServiceImpl implements BroadcastService {
    @Qualifier("broadCastDaoImpl")
    @Autowired(required = true)
    private BroadcastDao broadcastDao;

    @Override
    public List<BroadcastMessage> getUserBroadcastMessages(Integer userId) throws NoResultException{
        return broadcastDao.findMessagesByUserId(userId);
    }

    @Override
    public void confirmMessage(Integer userId, Integer messageId) {
        broadcastDao.confirmMessage(userId, messageId);
    }

    @Override
    public List<BroadcastMessage> getAllBroadcastMessages() {
        return broadcastDao.findAll();
    }

    @Override
    public void add(BroadcastMessage entity) {
        broadcastDao.add(entity);
    }

    @Override
    public void remove(BroadcastMessage entity) {
        broadcastDao.remove(entity);
    }

    @Override
    public void update(BroadcastMessage entity) {
        broadcastDao.update(entity);
    }

    @Override
    public BroadcastMessage get(Integer integer) throws NoResultException{
        return broadcastDao.findById(integer);
    }
}
