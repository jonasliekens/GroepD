package be.kdg.services.dao;

import be.kdg.entities.Trip;
import be.kdg.services.GenericDao;

/**
 * Created with IntelliJ IDEA.
 * User: Nick
 * Date: 9/02/13
 * Time: 19:34
 * To change this template use File | Settings | File Templates.
 */
public interface TripDao  extends GenericDao<Trip,Integer> {
    boolean removeTrip(Trip trip);
}
