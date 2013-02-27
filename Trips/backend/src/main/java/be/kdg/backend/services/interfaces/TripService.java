package be.kdg.backend.services.interfaces;

import be.kdg.backend.entities.Trip;

import java.util.List;

/**
 * User: Bart Verhavert
 * Date: 21/02/13 14:07
 */
public interface TripService {
    public void addTrip(Trip trip);
    public void deleteTrip(Trip trip);
    public void editTrip(Trip trip);
    public Trip getTrip(Integer key);
    public List<Trip> getTrips();
}
