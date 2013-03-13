package services;

import be.kdg.backend.entities.Expense;
import be.kdg.backend.entities.Trip;
import be.kdg.backend.entities.User;
import be.kdg.backend.services.interfaces.ExpenseService;
import be.kdg.backend.services.interfaces.TripService;
import be.kdg.backend.services.interfaces.UserService;
import be.kdg.backend.utilities.Utilities;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * User: Bart Verhavert
 * Date: 12/03/13 14:19
 */
@ContextConfiguration(locations = "classpath*:/META-INF/applicationContext.xml")
public class ExpenseServiceTest extends AbstractJUnit4SpringContextTests {
    @Autowired(required = true)
    ExpenseService expenseService;

    @Autowired(required = true)
    TripService tripService;

    @Autowired(required = true)
    UserService userService;

    private Trip trip;
    private User user;

    @Before
    public void beforeTest() {
        trip = newTrip("beforeTest");

        user = new User("expense@test.com", "", "", "", Utilities.makeDate("04/06/1992"));
        userService.addUser(user);
    }

    @After
    public void afterTest() {
        tripService.remove(trip);
        userService.remove(user);
    }

    @Test
    public void addFindAndRemoveExpenseTest() {
        Expense expense = new Expense(trip, user, 1.5, "addFindAndRemoveExpenseTest");

        expenseService.add(expense);

        assertTrue(expenseService.get(expense.getId()) != null);

        expenseService.remove(expenseService.get(expense.getId()));

        assertTrue(expenseService.get(expense.getId()) == null);
    }

    @Test
    public void updateExpenseTest() {
        Expense expense = new Expense(trip, user, 1.5, "updateExpenseTest");

        expenseService.add(expense);

        expense = expenseService.get(expense.getId());
        expense.setPrice(2.5);

        expenseService.update(expense);

        assertTrue(expenseService.get(expense.getId()).getPrice() == 2.5);
    }

    @Test
    public void findAllByTripIdAndUserIdTest() {
        User user2 = new User("findAllByTripIdAndUserIdTest@test.com", "", "", "", Utilities.makeDate("04/06/1992"));
        userService.addUser(user2);

        Expense expense1 = new Expense(trip, user, 1.5, "findAllByTripIdAndUserIdTest1");
        Expense expense2 = new Expense(trip, user, 1.5, "findAllByTripIdAndUserIdTest2");
        Expense expense3 = new Expense(trip, user2, 1.5, "findAllByTripIdAndUserIdTest3");

        expenseService.add(expense1);
        expenseService.add(expense2);
        expenseService.add(expense3);

        assertTrue(expenseService.findAllByTripIdAndUserId(trip.getId(), user.getId()).size() == 2);

        expenseService.remove(expense1);
        expenseService.remove(expense2);
        expenseService.remove(expense3);

        userService.remove(user2);
    }

    @Test
    public void findAllByTripIdTest() {
        Trip trip2 = newTrip("findAllByTripId");

        Expense expense1 = new Expense(trip, user, 1.5, "findAllByTripId1");
        Expense expense2 = new Expense(trip, user, 1.5, "findAllByTripId2");
        Expense expense3 = new Expense(trip2, user, 1.5, "findAllByTripId3");

        expenseService.add(expense1);
        expenseService.add(expense2);
        expenseService.add(expense3);

        List<Expense> expenses = expenseService.findAllByTripId(trip.getId());

        assertTrue(expenses.size() == 2);

        expenseService.remove(expense1);
        expenseService.remove(expense2);
        expenseService.remove(expense3);

        tripService.remove(trip2);
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

        tripService.add(trip);

        return trip;
    }
}
