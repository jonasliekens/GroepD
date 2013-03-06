package be.kdg.backend.services.interfaces;

import be.kdg.backend.entities.ParticipatedTrip;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Nick
 * Date: 28/02/13
 * Time: 23:26
 * To change this template use File | Settings | File Templates.
 */
public interface ParticipatedTripService extends GenericService<ParticipatedTrip, Integer> {
    @Transactional
    public List<ParticipatedTrip> getParticipatedTrips();
    @Transactional
    public List<ParticipatedTrip> getParticipatedTripsByTripId(Integer tripId);
    @Transactional
    public List<ParticipatedTrip> getAllParticipatedTrips();
}
