package be.kdg.backend.services.impl;

import be.kdg.backend.dao.interfaces.StopDao;
import be.kdg.backend.dao.interfaces.TripDao;
import be.kdg.backend.entities.Stop;
import be.kdg.backend.entities.Trip;
import be.kdg.backend.services.interfaces.StopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Bart
 * Date: 26/02/13
 * Time: 14:29
 * To change this template use File | Settings | File Templates.
 */
@Service("stopService")
public class StopServiceImpl implements StopService {

    @Qualifier("stopDaoImpl")
    @Autowired(required = true)
    private StopDao stopDao;

    @Qualifier("tripDaoImpl")
    @Autowired(required = true)
    private TripDao tripDao;

    @Override
    public void add(Stop entity, Integer tripId) {
        fixOrderNumbers(entity, tripId);
        Trip trip = tripDao.findById(tripId);
        entity.setTrip(trip);
        trip.addStop(entity);
        tripDao.update(trip);
    }

    @Override
    public void update(Stop entity, Integer tripId) {
        fixOrderNumbers(entity, tripId);
        stopDao.update(entity);
    }

    @Override
    public void remove(Stop entity) {
        entity.getTrip().getStops().remove(entity);
        tripDao.update(entity.getTrip());
        stopDao.remove(entity);
    }

    @Override
    public Stop get(Integer integer) {
        return stopDao.findById(integer);
    }

    private void fixOrderNumbers(Stop entity, Integer tripId) {
        int position = entity.getOrderNumber(),
            i = 1;

        // Get all the stops for this trip in the right order and loop
        for(Stop stop : stopDao.findAllByTripId(tripId)) {
            // If the position of the counter is the same as the new position of the given entity, skip the spot to keep it free
            if(i == position) {
                i++;
            }

            // Ignore the stop that is being edited because it will be put in the empty spot and otherwise the position generated here will be empty
            if(stop.getId() != entity.getId()) {
                stop.setOrderNumber(i++);
                stopDao.update(stop);
            }
        }
    }

    @Override
    public List<Stop> getStopsByTripId(Integer tripId) {
        return stopDao.findAllByTripId(tripId);
    }
}
