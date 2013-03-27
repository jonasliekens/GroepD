package be.kdg.backend.services.interfaces;

import be.kdg.backend.entities.Request;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 26/03/13
 * Time: 14:45
 * Copyright @ Soulware.be
 */
public interface RequestService extends GenericService<Request, Integer>{
    @Transactional
    public void removeUserFromList(String fbRequestId, String userId);
    @Transactional
    public Request findRequestByFBRequestId(String id);
    @Transactional
    public List<Request> getRequests();
}
