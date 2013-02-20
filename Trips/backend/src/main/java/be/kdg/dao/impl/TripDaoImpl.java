package be.kdg.dao.impl;

import be.kdg.entities.Trip;
import be.kdg.dao.interfaces.TripDao;
import org.hibernate.Hibernate;
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
    @Override
    public boolean removeTrip(Trip trip) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Trip findTripWithStops(Integer id){
        Trip trip = super.find(id);
        Hibernate.initialize(trip.getStops());
        return trip;
    }
}