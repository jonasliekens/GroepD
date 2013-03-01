package be.kdg.web.controllers;

import be.kdg.backend.entities.ParticipatedTrip;
import be.kdg.backend.entities.Trip;
import be.kdg.backend.entities.User;
import be.kdg.backend.enums.TravelType;
import be.kdg.backend.enums.TripType;
import be.kdg.backend.services.interfaces.ParticipatedTripService;
import be.kdg.backend.services.interfaces.StopService;
import be.kdg.backend.services.interfaces.TripService;
import be.kdg.backend.services.interfaces.UserService;
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

import javax.servlet.http.HttpSession;

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

    @Autowired
    @Qualifier("userService")
    UserService userService;
    @Autowired
    @Qualifier("participatedTripService")
    ParticipatedTripService participatedTripService;

    @Autowired
    StopService stopService;

    @RequestMapping(method = RequestMethod.GET)
    public String list(ModelMap model) {
        model.addAttribute("trips", tripService.getTrips());

        return "trips/list";
    }

    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable Integer id, ModelMap model) {
        model.addAttribute("trip", tripService.get(id));
        model.addAttribute("stops", stopService.getStopsByTripId(id));

        return "trips/details";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addTripForm(ModelMap model) {
        model.addAttribute("tripForm", new TripForm());
        model.addAttribute("tripTypes", TripType.values());
        model.addAttribute("travelTypes", TravelType.values());
        return "trips/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addTrip(@ModelAttribute("tripForm") TripForm tripForm, BindingResult result, SessionStatus status, HttpSession session) {
        tripValidator.validate(tripForm, result);

        if (result.hasErrors()) {
            return "trips/add";
        } else {
            status.setComplete();

            Trip trip = new Trip();
            User user = userService.get((Integer) session.getAttribute("userId"));

            trip.setName(tripForm.getName());
            trip.setPrivateTrip(tripForm.getPrivateTrip());
            trip.setCommunicationByLocation(tripForm.getCommunicationByLocation());
            trip.setCommunicationByChat(tripForm.getCommunicationByChat());
            // On creation, a trip shouldn't be published
            trip.setPublished(false);
            trip.setTripType(tripForm.getTripType());
            trip.setTravelType(tripForm.getTravelType());


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


            tripService.add(trip);
            trip.addAdmin(user);
            tripService.update(trip);

            return "redirect:/trips/";
        }
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Integer id, ModelMap model) {
        Trip trip = tripService.get(id);
        TripForm tripForm = new TripForm();
        tripForm.setName(trip.getName());
        tripForm.setNrDays(trip.getNrDays());
        tripForm.setNrHours(trip.getNrHours());
        tripForm.setPrivateTrip(trip.getPrivateTrip());
        tripForm.setCommunicationByChat(trip.getCommunicationByChat());
        tripForm.setCommunicationByLocation(trip.getCommunicationByLocation());
        tripForm.setTravelType(trip.getTravelType());
        tripForm.setTripType(trip.getTripType());
        model.addAttribute("tripForm", tripForm);
        model.addAttribute("trip", trip);
        model.addAttribute("tripTypes", TripType.values());
        model.addAttribute("travelTypes", TravelType.values());
        return "trips/edit";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String editTrip(@PathVariable Integer id, @ModelAttribute("tripForm") TripForm tripForm, BindingResult result, SessionStatus status) {
        tripValidator.validate(tripForm, result);
        if (result.hasErrors()) {
            return "trips";
        } else {
            status.setComplete();

            Trip trip = tripService.get(id);

            trip.setName(tripForm.getName());
            trip.setPrivateTrip(tripForm.getPrivateTrip());
            trip.setCommunicationByLocation(tripForm.getCommunicationByLocation());
            trip.setCommunicationByChat(tripForm.getCommunicationByChat());
            trip.setTripType(tripForm.getTripType());
            trip.setTravelType(tripForm.getTravelType());

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


            tripService.update(trip);

            return "redirect:/trips/";
        }
    }


    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteTripConfirm(@PathVariable Integer id, ModelMap model) {

        model.addAttribute("trip", tripService.get(id));

        return "trips/delete";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String deleteTip(@PathVariable Integer id, ModelMap model) {

        tripService.remove(tripService.get(id));

        return "redirect:/trips/";
    }

    @RequestMapping(value = "/register/{id}", method = RequestMethod.GET)
    public String registerForTrip(@PathVariable Integer id) {
        return "trips/register";
    }
    @RequestMapping(value = "/register/{id}", method = RequestMethod.POST)
    public String register(@PathVariable Integer id, HttpSession session) {
        Trip trip = tripService.get(id);
        User user = userService.get((Integer)session.getAttribute("userId"));
        ParticipatedTrip participatedTrip = new ParticipatedTrip();
        //participatedTripService.add(participatedTrip);
        participatedTrip.setUser(user);
        participatedTrip.setTrip(trip);
        participatedTripService.add(participatedTrip);
        return "redirect:/trips/details/"+id;
    }

    @RequestMapping(value = "/registered", method = RequestMethod.GET)
    public String registeredTrips(ModelMap model, HttpSession session) {
        model.addAttribute("user", userService.get((Integer)session.getAttribute("userId")));
        return "trips/registered";
    }
}
