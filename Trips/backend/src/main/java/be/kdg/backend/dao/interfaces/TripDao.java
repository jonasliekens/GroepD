package be.kdg.backend.dao.interfaces;

import be.kdg.backend.entities.Announcement;
import be.kdg.backend.entities.Equipment;
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
    @Transactional
    void removeAnnouncementFromTrip(Integer announcementId);
    @Transactional
    List<Announcement> getAnnouncementsByTripId(Integer tripId);
    @Transactional
    void removeEquipmentFromTrip(Integer equipmentId);
    @Transactional
    List<Equipment> getEquipmentByTrip(Integer tripId);
    @Transactional
    List<Trip> findOwnTripsByUserId(Integer userId);
    @Transactional
    List<Trip> findRegisteredTripsByUserId(Integer userId);
    @Transactional
    List<Trip> findTripsByNamePattern(String query);
}
