package be.kdg.backend.services.impl;

import be.kdg.backend.dao.interfaces.StopDao;
import be.kdg.backend.entities.Stop;
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

    @Override
    public void add(Stop entity, Integer tripId) {
        fixOrderNumbers(entity, tripId);
        stopDao.add(entity);
    }

    @Override
    public void update(Stop entity, Integer tripId) {
        fixOrderNumbers(entity, tripId);
        stopDao.update(entity);
    }

    @Override
    public void remove(Stop entity) {
        stopDao.remove(entity);
    }

    @Override
    public Stop get(Integer integer) {
        return stopDao.findById(integer);
    }

    private void fixOrderNumbers(Stop entity, Integer tripId) {
        List<Stop> orderedStops = stopDao.findAllByTripId(tripId);
        boolean isDouble = false;
        for(Stop stop : orderedStops){
            if(stop.getOrderNumber() == entity.getOrderNumber()){
                isDouble = true;
            }
            if(isDouble){
                stop.setOrderNumber(stop.getOrderNumber()+1);
                stopDao.update(stop);
            }
        }
    }

    @Override
    public List<Stop> getStopsByTripId(Integer tripId) {
        return stopDao.findAllByTripId(tripId);
    }
}
