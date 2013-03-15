package be.kdg.web.restcontrollers;

import be.kdg.backend.entities.ParticipatedTrip;
import be.kdg.backend.entities.Trip;
import be.kdg.backend.entities.User;
import be.kdg.backend.services.interfaces.ParticipatedTripService;
import be.kdg.backend.services.interfaces.StopService;
import be.kdg.backend.services.interfaces.TripService;
import be.kdg.backend.services.interfaces.UserService;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * User: Bart Verhavert
 * Date: 27/02/13 10:18
 */
@Controller
@RequestMapping("/rest/trips")
public class TripRestController {
    @Autowired
    @Qualifier("tripService")
    private TripService tripService;

    @Autowired
    @Qualifier("stopService")
    private StopService stopService;

    @Autowired
    @Qualifier("participatedTripService")
    private ParticipatedTripService participatedTripService;

    @RequestMapping(value = "/alltrips", method = RequestMethod.GET)
    @ResponseBody
    public List<Trip> getAllTrips() {
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

    @RequestMapping(value = "/mytrips/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<Trip> getMyTrips(@PathVariable Integer id) {
        return tripService.findOwnTripsByUserId(id);
    }

    @RequestMapping(value = "/registeredtrips/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<Trip> getRegisteredTrips(@PathVariable Integer id) {
        List<ParticipatedTrip> participatedTrips = participatedTripService.getConfirmedParticipatedTripsByUserId(id);
        return getTripsFromParticipatedTrips(participatedTrips);
    }

    @RequestMapping(value = "/invitations/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<Trip> getInvitations(@PathVariable Integer id) {
        List<ParticipatedTrip> participatedTrips = participatedTripService.getInvitations(id);
        return getTripsFromParticipatedTrips(participatedTrips);
    }

    @RequestMapping(value = "/registeredtrips/start", method = RequestMethod.POST)
    public String startTrip(@RequestParam Integer tripId, @RequestParam Integer userId){
        ParticipatedTrip pt = participatedTripService.getParticipatedTrip(tripId, userId);
        pt.setStarted(true);
        participatedTripService.update(pt);
        return "true";
    }

    private List<Trip> getTripsFromParticipatedTrips(List<ParticipatedTrip> participatedTrips) {
        List<Trip> trips = new ArrayList<Trip>();

        for(ParticipatedTrip pt : participatedTrips) {
            trips.add(pt.getTrip());
        }

        return trips;
    }

    @RequestMapping(value = "/participant/location", method = RequestMethod.POST)
    public String sendLocation(@RequestParam Integer tripId, @RequestParam Integer userId, @RequestParam Double latitude, @RequestParam Double longitude){
        ParticipatedTrip pt = participatedTripService.getParticipatedTrip(tripId, userId);
        pt.setLatitude(latitude);
        pt.setLongitude(longitude);
        participatedTripService.update(pt);
        return "true";
    }

    @RequestMapping(value = "/participants/started", method = RequestMethod.POST)
    @ResponseBody
    public List<ParticipatedTrip> getStartedParticipants(@RequestParam Integer tripId){
        return participatedTripService.getStartedParticipatedTripsByTripId(tripId);
    }
}
