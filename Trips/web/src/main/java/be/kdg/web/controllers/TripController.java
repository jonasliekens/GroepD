package be.kdg.web.controllers;

import be.kdg.backend.entities.Trip;
import be.kdg.web.forms.TripForm;
import be.kdg.web.validators.TripValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Bart Verhavert
 * Date: 21/02/13 14:02
 */
@Controller
@RequestMapping("/trips")
public class TripController {
    TripValidator tripValidator;

    @Autowired
    public TripController(TripValidator tripValidator){
        this.tripValidator = tripValidator;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap model) {
        List<Trip> trips = new ArrayList<Trip>();

        model.addAttribute("trips", trips);

        return "trips/list";
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable Integer id, ModelMap model) {
        model.addAttribute("id", id);

        return "trips/detail";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addTripForm(ModelMap model) {
        TripForm tripForm = new TripForm();

        model.addAttribute("tripForm", tripForm);

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

}
