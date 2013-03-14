package dao;

import be.kdg.backend.dao.interfaces.ExpenseDao;
import be.kdg.backend.dao.interfaces.TripDao;
import be.kdg.backend.dao.interfaces.UserDao;
import be.kdg.backend.entities.Expense;
import be.kdg.backend.entities.Trip;
import be.kdg.backend.entities.User;
import be.kdg.backend.utilities.Utilities;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.util.List;

import static org.junit.Assert.*;

/**
 * User: Bart Verhavert
 * Date: 12/03/13 00:55
 */
@ContextConfiguration(locations = "classpath*:/META-INF/applicationContext.xml")
public class ExpenseTest extends AbstractJUnit4SpringContextTests {
    @Qualifier("expenseDaoImpl")
    @Autowired(required = true)
    private ExpenseDao expenseDao;

    @Qualifier("userDaoImpl")
    @Autowired(required = true)
    private UserDao userDao;

    @Qualifier("tripDaoImpl")
    @Autowired(required = true)
    private TripDao tripDao;

    private Trip trip;
    private User user;

    @Before
    public void beforeTest() {
        trip = newTrip("beforeTest");

        user = new User("expense@test.com", "", "", "", Utilities.makeDate("04/06/1992"));
        userDao.add(user);
    }

    @After
    public void afterTest() {
        userDao.remove(user);
        tripDao.remove(trip);
    }

    @Test
    public void addFindAndRemoveExpenseTest() {
        Expense expense = new Expense(trip, user, 1.5, "addFindAndRemoveExpenseTest");

        expenseDao.add(expense);

        assertTrue(expenseDao.findById(expense.getId()) != null);

        expenseDao.remove(expenseDao.findById(expense.getId()));

        assertTrue(expenseDao.findById(expense.getId()) == null);
    }

    @Test
    public void updateExpenseTest() {
        Expense expense = new Expense(trip, user, 1.5, "updateExpenseTest");

        expenseDao.add(expense);

        expense = expenseDao.findById(expense.getId());
        expense.setPrice(2.5);

        expenseDao.update(expense);

        assertTrue(expenseDao.findById(expense.getId()).getPrice() == 2.5);
    }

    @Test
    public void findAllByTripIdAndUserIdTest() {
        User user2 = new User("findAllByTripIdAndUserIdTest@test.com", "", "", "", Utilities.makeDate("04/06/1992"));
        userDao.add(user2);

        Expense expense1 = new Expense(trip, user, 1.5, "findAllByTripIdAndUserIdTest1");
        Expense expense2 = new Expense(trip, user, 1.5, "findAllByTripIdAndUserIdTest2");
        Expense expense3 = new Expense(trip, user2, 1.5, "findAllByTripIdAndUserIdTest3");

        expenseDao.add(expense1);
        expenseDao.add(expense2);
        expenseDao.add(expense3);

        assertTrue(expenseDao.findAllByTripIdAndUserId(trip.getId(), user.getId()).size() == 2);

        expenseDao.remove(expense1);
        expenseDao.remove(expense2);
        expenseDao.remove(expense3);

        userDao.remove(user2);
    }

    @Test
    public void findAllByTripId() {
        Trip trip2 = newTrip("findAllByTripId");

        Expense expense1 = new Expense(trip, user, 1.5, "findAllByTripId1");
        Expense expense2 = new Expense(trip, user, 1.5, "findAllByTripId2");
        Expense expense3 = new Expense(trip2, user, 1.5, "findAllByTripId3");

        expenseDao.add(expense1);
        expenseDao.add(expense2);
        expenseDao.add(expense3);

        List<Expense> expenses = expenseDao.findAllByTripId(trip.getId());

        assertTrue(expenses.size() == 2);

        expenseDao.remove(expense1);
        expenseDao.remove(expense2);
        expenseDao.remove(expense3);

        tripDao.remove(trip2);
    }

    private Trip newTrip(String name) {
        Trip trip = new Trip();

        trip.setName(name);
        trip.setPrivateTrip(false);
        trip.setPublished(false);
        trip.setNrDays(10);
        trip.setNrHours(12);
        trip.setCommunicationByChat(true);
        trip.setCommunicationByLocation(true);

        tripDao.add(trip);

        return trip;
    }
}
