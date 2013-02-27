package be.kdg.web.restcontrollers;

import be.kdg.backend.entities.Trip;
import be.kdg.backend.services.interfaces.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Bart Verhavert
 * Date: 27/02/13 10:18
 */
@Controller
@RequestMapping("/rest/trips")
public class TripRestController {
    @Autowired
    @Qualifier("tripService")
    TripService tripService;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<Trip> list() {
        //return tripService.getTrips();
        return new ArrayList<Trip>();
    }
}
