package be.kdg.backend.services.impl;

import be.kdg.backend.dao.interfaces.RequestDao;
import be.kdg.backend.entities.Request;
import be.kdg.backend.services.interfaces.RequestService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 26/03/13
 * Time: 14:49
 * Copyright @ Soulware.be
 */
@Service("requestService")
public class RequestServiceImpl implements RequestService {
//    static final Logger logger = LoggerFactory.getLogger(RequestServiceImpl.class);
    @Qualifier("requestDaoImpl")
    @Autowired(required = true)
    private RequestDao requestDao;

    @Override
    public void removeUserFromList(String fbRequestId, String userId) {
        try{
            Request req = findRequestByFBRequestId(fbRequestId);
            req.removeUserFromList(userId);
            update(req);
        }catch(NoResultException nre){
//            logger.debug("NoResultException in removeUserFromList: No Request object with facebookrequestid " + fbRequestId);
        }
    }

    @Override
    public Request findRequestByFBRequestId(String id) throws NoResultException{
        return requestDao.findRequestByFBRequestId(id);
    }

    @Override
    public List<Request> getRequests() {
        return requestDao.findAll();
    }

    @Override
    public void add(Request entity) {
        requestDao.add(entity);
    }

    @Override
    public void remove(Request entity) {
        requestDao.remove(entity);
    }

    @Override
    public void update(Request entity) {
        requestDao.update(entity);
    }

    @Override
    public Request get(Integer integer) {
        return requestDao.findById(integer);
    }
}
