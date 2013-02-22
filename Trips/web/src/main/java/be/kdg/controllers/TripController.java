package be.kdg.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * User: Bart Verhavert
 * Date: 21/02/13 14:02
 */
@Controller
@RequestMapping("/trips")
public class TripController {
    /*@Autowired
    TripService tripService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap model) {

        model.addAttribute("trips", tripService.getTrips());
        return "trips";

    }   */

}
