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

    void removeAnnouncementFromTrip(Integer announcementId);

    List<Announcement> getAnnouncementsByTripId(Integer tripId);

    void removeEquipmentFromTrip(Integer equipmentId);

    List<Equipment> getEquipmentByTrip(Integer tripId);

    List<Trip> findOwnTripsByUserId(Integer userId);
}
