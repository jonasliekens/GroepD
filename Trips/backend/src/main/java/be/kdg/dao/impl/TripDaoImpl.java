package be.kdg.dao.impl;

import be.kdg.dao.interfaces.TripDao;
import be.kdg.entities.Trip;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: Nick
 * Date: 9/02/13
 * Time: 19:36
 * To change this template use File | Settings | File Templates.
 */
@Repository("tripDao")
public class TripDaoImpl extends HibernateDao<Trip,Integer> implements TripDao {

}
