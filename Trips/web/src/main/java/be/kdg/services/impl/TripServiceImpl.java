package be.kdg.services.impl;

import be.kdg.dao.interfaces.TripDao;
import be.kdg.entities.Trip;
import be.kdg.services.interfaces.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Bart Verhavert
 * Date: 21/02/13 14:08
 */
@Service("tripService")
@Component
@ContextConfiguration(locations = "/persistence-beans.xml")
public class TripServiceImpl implements TripService {
    @Autowired(required = true)
    private TripDao tripDao;

    @Override
    @Transactional
    public void addTrip(Trip trip) {
        tripDao.add(trip);
    }

    @Override
    @Transactional
    public void deleteTrip(Trip trip) {
        tripDao.remove(trip);
    }

    @Override
    @Transactional
    public Trip getTrip(Integer key) {
        return tripDao.find(key);
    }

    @Override
    @Transactional
    public List<Trip> getTrips() {
        return tripDao.list();
    }
}
