package be.kdg.web.controllers;

import be.kdg.backend.entities.Stop;
import be.kdg.backend.services.interfaces.StopService;
import be.kdg.backend.services.interfaces.TripService;
import be.kdg.web.forms.StopForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

/**
 * Created by IntelliJ IDEA.
 * Author: Nick De Waele
 * Date: 27/02/13
 */
@Controller
public class StopController {
    @Autowired
    StopService stopService;
    @Autowired
    TripService tripService;

    @RequestMapping(value = "/trips/{id}/stops", method = RequestMethod.GET)
    public String list(@PathVariable Integer id, ModelMap model) {
        model.addAttribute("stops", stopService.getStopsByTripId(id));
        model.addAttribute("trip", tripService.getTrip(id));
        return "/stops/list";
    }

    @RequestMapping(value = "/trips/{id}/stops/add", method = RequestMethod.GET)
    public String add(@PathVariable Integer id, ModelMap model) {
        model.addAttribute("addForm", new StopForm());
        return "/stops/add";
    }

    @RequestMapping(value = "/trips/{id}/stops/add", method = RequestMethod.POST)
        public String addStop(@PathVariable Integer id, @ModelAttribute("stopForm") StopForm stopForm, BindingResult result, SessionStatus status) {

        //stopValidator.validate(stopForm, result);

            if (result.hasErrors()) {
                return "trips/add";
            } else {
                status.setComplete();

                Stop stop = new Stop();

                stop.setName(stopForm.getName());
                //SETTERS

                /*if (tripForm.getNrDays() == null) {
                    trip.setNrDays(0);
                } else {
                    trip.setNrDays(tripForm.getNrDays());
                }
                if (tripForm.getNrHours() == null) {
                    trip.setNrHours(0);
                } else {
                    trip.setNrHours(tripForm.getNrHours());
                }       */


                //stopService.add(stop);

                return "redirect:/trips/" + id+"/stops";
            }
        }
}
