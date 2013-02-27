package be.kdg.backend.dao.interfaces;

import be.kdg.backend.entities.Stop;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Bart
 * Date: 21/02/13
 * Time: 14:27
 * To change this template use File | Settings | File Templates.
 */
public interface StopDao extends GenericDao<Stop,Integer>  {
    @Transactional
    public List<Stop> findAllByTripId(Integer id);
}
