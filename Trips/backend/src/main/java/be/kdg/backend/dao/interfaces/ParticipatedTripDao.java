package be.kdg.backend.dao.interfaces;

import be.kdg.backend.entities.ParticipatedTrip;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Nick
 * Date: 28/02/13
 * Time: 23:23
 * To change this template use File | Settings | File Templates.
 */
public interface ParticipatedTripDao extends GenericDao<ParticipatedTrip,Integer>  {
    public List<ParticipatedTrip> findAllByTripId(Integer id);
}
