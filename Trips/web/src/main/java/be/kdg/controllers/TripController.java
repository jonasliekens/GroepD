package be.kdg.controllers;

import be.kdg.entities.Trip;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Bart Verhavert
 * Date: 21/02/13 14:02
 */
@Controller
@RequestMapping("/trips")
public class TripController {
    /*
    @Autowired
    TripService tripService;
    */

    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap model) {
        List<Trip> trips = new ArrayList<Trip>();

        model.addAttribute("trips", trips);

        return "trips/index";
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable Integer id, ModelMap model) {
        model.addAttribute("id", id);

        return "trips/detail";
    }

}
