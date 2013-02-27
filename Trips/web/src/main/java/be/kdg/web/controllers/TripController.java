package be.kdg.web.controllers;

import be.kdg.backend.entities.Trip;
import be.kdg.backend.services.interfaces.TripService;
import be.kdg.web.forms.TripForm;
import be.kdg.web.validators.TripValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

/**
 * User: Bart Verhavert
 * Date: 21/02/13 14:02
 */
@Controller
@RequestMapping("/trips")
public class TripController {
    @Autowired
    @Qualifier("tripService")
    TripService tripService;

    @Autowired
    @Qualifier("tripValidator")
    TripValidator tripValidator;

    @RequestMapping(method = RequestMethod.GET)
    public String list(ModelMap model) {
        model.addAttribute("trips", tripService.getTrips());

        return "trips/list";
    }

    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable Integer id, ModelMap model) {
        model.addAttribute("trip", tripService.getTrip(id));

        return "trips/detail";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addTripForm(ModelMap model) {
        model.addAttribute("tripForm", new TripForm());

        return "trips/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addTrip(@ModelAttribute("tripForm") TripForm tripForm, BindingResult result, SessionStatus status) {
        tripValidator.validate(tripForm, result);

        if (result.hasErrors()) {
            return "trips/add";
        } else {
            status.setComplete();

            Trip trip = new Trip();

            trip.setName(tripForm.getName());
            trip.setPrivateTrip(tripForm.getPrivateTrip());
            // On creation, a trip shouldn't be published
            trip.setPublished(false);
            if (tripForm.getNrDays() == null) {
                trip.setNrDays(0);
            } else {
                trip.setNrDays(tripForm.getNrDays());
            }
            if (tripForm.getNrHours() == null) {
                trip.setNrHours(0);
            } else {
                trip.setNrHours(tripForm.getNrHours());
            }


            tripService.addTrip(trip);

            return "redirect:/trips/edit/" + trip.getId();
        }
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Integer id, ModelMap model) {
        Trip trip = tripService.getTrip(id);
        TripForm tripForm = new TripForm();
        tripForm.setName(trip.getName());
        tripForm.setNrDays(trip.getNrDays());
        tripForm.setNrHours(trip.getNrHours());
        tripForm.setPrivateTrip(trip.getPrivateTrip());
        model.addAttribute("tripForm", tripForm);
        model.addAttribute("trip", trip);
        return "trips/edit";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String editTrip(@PathVariable Integer id, @ModelAttribute("tripForm") TripForm tripForm, BindingResult result, SessionStatus status) {
        tripValidator.validate(tripForm, result);
        if (result.hasErrors()) {
            return "trips";
        } else {
            status.setComplete();

            Trip trip = tripService.getTrip(id);

            trip.setName(tripForm.getName());
            trip.setPrivateTrip(tripForm.getPrivateTrip());
            // On creation, a trip shouldn't be published
            trip.setPublished(false);
            if (tripForm.getNrDays() == null) {
                trip.setNrDays(0);
            } else {
                trip.setNrDays(tripForm.getNrDays());
            }
            if (tripForm.getNrHours() == null) {
                trip.setNrHours(0);
            } else {
                trip.setNrHours(tripForm.getNrHours());
            }


            tripService.editTrip(trip);

            return "redirect:/trips/";
        }
    }


    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteTripConfirm(@PathVariable Integer id, ModelMap model) {
        model.addAttribute("trip", tripService.getTrip(id));

        return "trips/delete";
    }
}
