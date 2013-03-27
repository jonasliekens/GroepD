package be.kdg.backend.services.interfaces;

import be.kdg.backend.entities.ParticipatedTrip;
import be.kdg.backend.entities.Trip;
import be.kdg.backend.entities.User;
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
    public List<ParticipatedTrip> getParticipatedTripsByTripId(Integer tripId);
    @Transactional
    public List<ParticipatedTrip> getAllParticipatedTrips();
    @Transactional
    public List<ParticipatedTrip>getInvitations(Integer userId);
    @Transactional
    public List<ParticipatedTrip> getConfirmedParticipatedTripsByTripId(Integer tripId);
    @Transactional
    public List<ParticipatedTrip> getConfirmedParticipatedTripsByUserId(Integer userId);
    @Transactional
    public ParticipatedTrip getParticipatedTrip(Integer tripId, Integer userId);
    @Transactional
    public List<ParticipatedTrip> getAllParticipatedTripsStartedWithLocationByTripId(Integer tripId);
    @Transactional
    public ParticipatedTrip getParticipatedTripNotStarted(Integer tripId, Integer userId);
}
