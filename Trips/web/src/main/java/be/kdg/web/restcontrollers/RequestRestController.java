package be.kdg.web.restcontrollers;

import be.kdg.backend.entities.ParticipatedTrip;
import be.kdg.backend.entities.Request;
import be.kdg.backend.entities.Trip;
import be.kdg.backend.entities.User;
import be.kdg.backend.services.interfaces.ParticipatedTripService;
import be.kdg.backend.services.interfaces.RequestService;
import be.kdg.backend.services.interfaces.TripService;
import be.kdg.backend.services.interfaces.UserService;
import be.kdg.web.controllers.LoginController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 26/03/13
 * Time: 15:38
 * Copyright @ Soulware.be
 */
@Controller
@RequestMapping("/rest/request")
public class RequestRestController {
    static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    @Qualifier("requestService")
    private RequestService requestService;

    @Autowired
    @Qualifier("tripService")
    private TripService tripService;

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Autowired
    @Qualifier("participatedTripService")
    private ParticipatedTripService participatedTripService;


    @RequestMapping(value = "/handle", method = RequestMethod.POST)
    @ResponseBody
    public String storeRequest(@RequestParam String fbRequestIds, @RequestParam String tripId) {
        try {
            Trip trip = tripService.get(Integer.parseInt(tripId));
            Request request = new Request();
            requestService.add(request);
            request.setTrip(trip);
            String[] fbRequestData = fbRequestIds.split(",");
            Set<String> userIds = new HashSet<String>();
            for (int requestDataCount = 1; requestDataCount < fbRequestData.length; requestDataCount++) {
                userIds.add(fbRequestData[requestDataCount]);
            }
            request.setUserIds(userIds);
            request.setRequestId(fbRequestData[0]);
            requestService.update(request);
            return "true";
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return "false";
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public String doRegisterOnTrip(@RequestParam String fbRequestIds, HttpSession session) {

        String[] requestIds = fbRequestIds.split("%2C");
        boolean tripFound = false;
        Request request = null;

        for (int requestCount = 0; requestCount < requestIds.length && !tripFound; requestCount++) {
            try {
                request = requestService.findRequestByFBRequestId(requestIds[requestCount]);
                tripFound = true;
            } catch (NoResultException nre) {
                logger.debug("NoResultException in doRegisterOnTrip, no Trip object for requestId " + requestIds[requestCount]);
            }
        }

        if (tripFound) {
            User user = userService.get((Integer) session.getAttribute("userId"));
            if (request.getUserIds().contains(user.getFacebookID())) {
                ParticipatedTrip participatedTrip = new ParticipatedTrip();
                participatedTrip.setUser(user);
                participatedTrip.setTrip(request.getTrip());
                participatedTrip.setConfirmed(true);
                participatedTripService.add(participatedTrip);
                request.removeUserFromList(user.getFacebookID());
                requestService.update(request);
                return request.getTrip().getId().toString();
            } else {
                return "false";
            }
        } else {
            return "false";
        }
    }
}