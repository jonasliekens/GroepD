package dao;

import be.kdg.backend.dao.interfaces.ParticipatedTripDao;
import be.kdg.backend.dao.interfaces.TripDao;
import be.kdg.backend.dao.interfaces.UserDao;
import be.kdg.backend.entities.*;
import be.kdg.backend.enums.TripType;
import be.kdg.backend.utilities.StopComparator;
import be.kdg.backend.utilities.Utilities;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 7/02/13
 * Time: 14:09
 * Copyright @ Soulware.be
 */
@ContextConfiguration(locations = "classpath*:/META-INF/applicationContext.xml")
public class TripTest extends AbstractJUnit4SpringContextTests {

    @Qualifier("tripDaoImpl")
    @Autowired(required = true)
    private TripDao tripDao;

    @Qualifier("userDaoImpl")
    @Autowired(required = true)
    private UserDao userDao;

    @Qualifier("participatedTripDaoImpl")
    @Autowired(required = true)
    private ParticipatedTripDao participatedTripDao;
    @Test
    public void testAddTrip() {
        Trip temp = newTrip();
        tripDao.add(temp);
        assertTrue(tripDao.findById(temp.getId()) != null);
    }

    @Test
    public void testUpdateTrip() {
        Trip temp = newTrip();
        tripDao.add(temp);
        temp = tripDao.findById(temp.getId());
        temp.setPublished(true);
        tripDao.update(temp);
        assertTrue(temp.getPublished());
    }

    @Test
    public void testRemoveTrip() {
        Trip temp = newTrip();
        tripDao.add(temp);
        tripDao.remove(temp);
        assertTrue(tripDao.findById(temp.getId()) == null);
    }

    @Test
    public void testAddStops() {
        Trip temp = newTrip();
        tripDao.add(temp);
        Stop stop = newStop(1);
        Stop stop1 = newStop(2);
        temp.addStop(stop);
        temp.addStop(stop1);
        tripDao.update(temp);
        temp = tripDao.findById(temp.getId());
        assertTrue(temp.getStops().size() > 0);
    }

    @Test
    public void testSetTripType() {
        Trip temp = newTrip();
        temp.setTripType(TripType.LOOSE);
        tripDao.add(temp);
        temp = tripDao.findById(temp.getId());
        assertTrue(temp.getTripType().equals(TripType.LOOSE));
    }

    @Test
    public void testGetPublicTrips(){
        Trip privateTrip = newTrip();
        privateTrip.setPrivateTrip(true);
        Trip publicTrip = newTrip();

        tripDao.add(privateTrip);
        tripDao.add(publicTrip);

        List<Trip> publicTrips = tripDao.getPublicTrips();
        assertTrue(publicTrips.size() == 1);
        assertFalse(publicTrips.get(0).getPrivateTrip());
    }

    @Test
    public void testAddAdminToTrip() {
        Trip temp = newTrip();
        User user = new User("Admin@test.be", "lala", "test", "test", Utilities.makeDate("03/02/1992"));
        userDao.add(user);
        temp.addAdmin(user);
        tripDao.add(temp);
        temp = tripDao.findById(temp.getId());
        assertTrue(temp.getAdmins().size() > 0);
    }

    @Test
    public void testAddParticipantToTrip(){
        Trip trip = newTrip();
        tripDao.add(trip);
        User user = new User("Admin@test.be", "lala", "test", "test", Utilities.makeDate("03/02/1992"));
        userDao.add(user);
        trip.addAdmin(user);
        tripDao.update(trip);
        ParticipatedTrip participatedTrip = new ParticipatedTrip();
        participatedTrip.setUser(user);
        participatedTrip.setTrip(trip);
        participatedTripDao.add(participatedTrip);
        trip.addParticipatedTrip(participatedTrip);
        trip = tripDao.findById(participatedTrip.getTrip().getId());
        User user1 = userDao.findById(participatedTrip.getUser().getId());
        //TODO: Lijst update niet. Blijft altijd 0
        assertEquals(1, trip.getParticipatedTrips().size());
        assertTrue(user1.getEmail().equals(user.getEmail()));
    }

    @Test
    public void testOrderStops(){
        boolean correct = true;
        Stop stop1 = newStop(1);
        Stop stop2 = newStop(2);
        Stop stop3 = newStop(3);
        Trip trip = newTrip();
        trip.addStop(stop1);
        trip.addStop(stop2);
        trip.addStop(stop3);
        tripDao.add(trip);
        trip = tripDao.findById(trip.getId());
        Set<Stop> stops = new TreeSet<Stop>(new StopComparator());
        stops.addAll(trip.getStops());
        for(int i = 1; i <= stops.size(); i++){
            if(i != ((Stop)stops.toArray()[i-1]).getOrderNumber()){
                correct = false;
            }
        }
        assertTrue(correct);
    }

    @Test
    public void testAddEquipmentToTrip() {
        Trip trip = newTrip();
        tripDao.add(trip);
        Equipment equipment = new Equipment();
        equipment.setDescription("Shovel");
        equipment.setTrip(trip);
        trip.addEquipment(equipment);
        tripDao.update(trip);
        assertTrue(tripDao.findById(trip.getId()).getEquipmentSet().size() > 0);
    }

    @Test
    public void testRemoveEquipmentFromTrip() {
        Trip trip = newTrip();
        tripDao.add(trip);
        Equipment equipment = new Equipment();
        equipment.setDescription("Shovel");
        equipment.setTrip(trip);
        trip.addEquipment(equipment);
        tripDao.update(trip);
        tripDao.removeEquipmentFromTrip(((Equipment)trip.getEquipmentSet().toArray()[0]).getId());
        assertTrue(tripDao.getEquipmentByTrip(trip.getId()).isEmpty());
    }


    @After
    public void testRemoveTrips() {
        for (Trip trip : tripDao.findAll()) {
            tripDao.remove(trip);
        }
        assertFalse(tripDao.findAll().size() > 0);
    }

    private Trip newTrip() {
        Trip trip = new Trip();
        trip.setName("test");
        trip.setPrivateTrip(false);
        trip.setPublished(false);
        trip.setNrDays(10);
        trip.setNrHours(12);
        trip.setCommunicationByChat(true);
        trip.setCommunicationByLocation(true);
        return trip;
    }

    private Stop newStop(Integer order) {
        Stop stop = new Stop("Test", 12354.21, 125884.65, order);
        return stop;
    }
}
