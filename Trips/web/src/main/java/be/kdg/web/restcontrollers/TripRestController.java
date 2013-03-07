package be.kdg.web.restcontrollers;

import be.kdg.backend.entities.Trip;
import be.kdg.backend.services.interfaces.StopService;
import be.kdg.backend.services.interfaces.TripService;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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

    @Autowired
    @Qualifier("stopService")
    StopService stopService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Trip> list() {
        return tripService.getPublicTrips();
    }

    @RequestMapping(value = "/{id}/stops", method = RequestMethod.GET)
    @ResponseBody
    public String getStops(@PathVariable Integer id) {

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.writeValueAsString(stopService.getStopsByTripId(id));
        } catch (IOException e) {
            return "[]";
        }
    }
}
