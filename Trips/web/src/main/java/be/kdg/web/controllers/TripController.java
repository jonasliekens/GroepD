package be.kdg.web.controllers;

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

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable Integer id, ModelMap model) {
//        model.addAttribute("trip", tripService.getTrip(id));

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
            return "redirect:/trips";
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteTripConfirm(ModelMap model) {
//        model.addAttribute("trip", tripService.getTrip(id));

        return "trips/delete";
    }
}
