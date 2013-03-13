package be.kdg.backend.services.impl;

import be.kdg.backend.dao.interfaces.ParticipatedTripDao;
import be.kdg.backend.entities.ParticipatedTrip;
import be.kdg.backend.services.interfaces.ParticipatedTripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Nick
 * Date: 28/02/13
 * Time: 23:26
 * To change this template use File | Settings | File Templates.
 */
@Service("participatedTripService")
public class ParticipatedTripServiceImpl implements ParticipatedTripService {

    @Qualifier("participatedTripDaoImpl")
    @Autowired(required = true)
    private ParticipatedTripDao participatedTripDao;
    @Override
    public void add(ParticipatedTrip entity) {
        participatedTripDao.add(entity);
    }

    @Override
    public void remove(ParticipatedTrip entity) {
        participatedTripDao.remove(entity);
    }

    @Override
    public void update(ParticipatedTrip entity) {
        participatedTripDao.update(entity);
    }

    @Override
    public ParticipatedTrip get(Integer integer) {
        return participatedTripDao.findById(integer);
    }

    @Override
    public List<ParticipatedTrip> getParticipatedTripsByTripId(Integer tripId) {
        return participatedTripDao.findAllByTripId(tripId);
    }

    @Override
    public List<ParticipatedTrip> getParticipatedTripsByUserId(Integer userId) {
        return participatedTripDao.findAllByUserId(userId);
    }

    @Override
    public List<ParticipatedTrip> getAllParticipatedTrips() {
        return participatedTripDao.findAll();
    }

    @Override
    public List<ParticipatedTrip> getInvitations(Integer userId) {
        return participatedTripDao.getInvitations(userId);
    }

    @Override
    public List<ParticipatedTrip> getConfirmedParticipatedTripsByTripId(Integer tripId) {
        return participatedTripDao.findAllConfirmedByTripId(tripId);
    }
}
