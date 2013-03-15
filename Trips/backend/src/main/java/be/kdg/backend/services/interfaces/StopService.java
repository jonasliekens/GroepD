package be.kdg.backend.services.interfaces;

import be.kdg.backend.entities.Photo;
import be.kdg.backend.entities.Stop;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Bart
 * Date: 26/02/13
 * Time: 14:21
 * To change this template use File | Settings | File Templates.
 */
public interface StopService {

    @Transactional
    void add(Stop entity, Integer tripId);

    @Transactional
    void update(Stop entity, Integer tripId);

    @Transactional
    void remove(Stop entity);

    @Transactional
    Stop get(Integer stopId);

    @Transactional
    List<Stop> getStopsByTripId(Integer tripId);

    @Transactional
    List<Photo> findPhotosByStopId(Integer stopId);

    void removePhotoByPhotoId(Integer photoId);
    @Transactional
    void update(Stop stop);
}
