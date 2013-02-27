package be.kdg.web.controllers;

import be.kdg.backend.services.interfaces.StopService;
import be.kdg.backend.services.interfaces.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
        return "/stops";
    }
}
