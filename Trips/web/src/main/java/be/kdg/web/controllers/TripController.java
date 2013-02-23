package be.kdg.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * User: Bart Verhavert
 * Date: 21/02/13 14:02
 */
@Controller
@RequestMapping("/trips")
public class TripController {
    /*@Autowired
    private TripService tripService;*/

    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap model) {

        //model.addAttribute("trips", tripService.getTrips());
        return "trips";

    }

}
