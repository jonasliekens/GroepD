package be.kdg.backend.dao.interfaces;

import be.kdg.backend.entities.Trip;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Nick
 * Date: 9/02/13
 * Time: 19:34
 * To change this template use File | Settings | File Templates.
 */
public interface TripDao  extends GenericDao<Trip,Integer> {
    @Transactional
    List<Trip> getPublicTrips();
}
