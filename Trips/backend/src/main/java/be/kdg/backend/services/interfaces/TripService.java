package be.kdg.backend.services.interfaces;

import be.kdg.backend.entities.Announcement;
import be.kdg.backend.entities.Equipment;
import be.kdg.backend.entities.Trip;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Bart Verhavert
 * Date: 21/02/13 14:07
 */
public interface TripService extends GenericService<Trip, Integer>{
    @Transactional
    public List<Trip> getTrips();
    @Transactional
    public List<Trip> getPublicTrips();

    void removeAnnouncementFromTrip(Integer announcementId);

    List<Announcement> getAnnouncementsByTripId(Integer tripId);

    List<Equipment> getEquipmentByTripId(Integer tripId);

    void removeEquipmentFromTrip(Integer equipmentId);
}
