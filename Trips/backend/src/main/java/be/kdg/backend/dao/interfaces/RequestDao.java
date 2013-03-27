package be.kdg.backend.dao.interfaces;

import be.kdg.backend.entities.Request;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 26/03/13
 * Time: 13:02
 * Copyright @ Soulware.be
 */
public interface RequestDao extends GenericDao<Request,Integer>{
    @Transactional
    public Request findRequestByFBRequestId(String id);
}
