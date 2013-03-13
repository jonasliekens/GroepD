package be.kdg.web.controllers;

import be.kdg.backend.entities.Expense;
import be.kdg.backend.entities.Trip;
import be.kdg.backend.entities.User;
import be.kdg.backend.services.interfaces.ExpenseService;
import be.kdg.backend.services.interfaces.TripService;
import be.kdg.backend.services.interfaces.UserService;
import be.kdg.web.forms.ExpenseForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;

/**
 * User: Bart Verhavert
 * Date: 12/03/13 14:36
 */
@Controller
public class ExpenseController {
    @Autowired
    @Qualifier("expenseService")
    private ExpenseService expenseService;

    @Autowired
    @Qualifier("tripService")
    private TripService tripService;

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @RequestMapping(value = "/trip/{id}/expenses", method = RequestMethod.GET)
    public String all(ModelMap model, @PathVariable Integer id, HttpSession session) {
        Trip trip = tripService.get(id);
        User user = userService.get((Integer) session.getAttribute("userId"));

        Boolean isAdmin = trip.getAdmins().contains(user);

        // If the user is an admin of the trip, show all the expenses
        if(isAdmin) {
            model.addAttribute("expenses", expenseService.findAllByTripId(id));
        }

        // Otherwise only show the expenses of the user
        else {
            model.addAttribute("expenses", expenseService.findAllByTripIdAndUserId(trip.getId(), user.getId()));
        }

        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("tripId", trip.getId());

        return "expenses/all";
    }

    @RequestMapping(value = "/trip/{id}/expenses/add", method = RequestMethod.GET)
    public String add(ModelMap model) {
        model.addAttribute("expenseForm", new ExpenseForm());

        return "expenses/add";
    }

    @RequestMapping(value = "/trip/{id}/expenses/add", method = RequestMethod.POST)
    public String add(ModelMap model, @PathVariable Integer id, @ModelAttribute("expenseForm") ExpenseForm expenseForm, BindingResult result, SessionStatus status, HttpSession session) {
        if(result.hasErrors()) {
            return "expenses/add";
        }

        Trip trip = tripService.get(id);
        User user = userService.get((Integer) session.getAttribute("userId"));

        Expense expense = new Expense(trip, user, expenseForm.getPrice(), expenseForm.getDescription());
        expenseService.add(expense);

        status.setComplete();

        return "redirect:/trip/" + id + "/expenses";
    }
}
