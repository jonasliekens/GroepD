package be.kdg.backend.services.impl;

import be.kdg.backend.dao.interfaces.TripDao;
import be.kdg.backend.entities.Trip;
import be.kdg.backend.services.interfaces.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Bart Verhavert
 * Date: 21/02/13 14:08
 */
@Service("tripService")
public class TripServiceImpl implements TripService {

    @Qualifier("tripDaoImpl")
    @Autowired(required = true)
    private TripDao tripDao;

    @Override
    public void add(Trip trip) {
        tripDao.add(trip);
    }

    @Override
    public void remove(Trip trip) {
        tripDao.remove(trip);
    }

    @Override
    public Trip get(Integer key) {
        return tripDao.findById(key);
    }

    @Override
    public void update(Trip entity) {
        tripDao.update(entity);
    }

    @Override
    public List<Trip> getTrips() {
        return tripDao.findAll();
    }
}
