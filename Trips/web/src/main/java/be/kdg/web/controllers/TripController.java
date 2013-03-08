package be.kdg.web.controllers;

import be.kdg.backend.entities.*;
import be.kdg.backend.enums.TravelType;
import be.kdg.backend.enums.TripType;
import be.kdg.backend.services.interfaces.ParticipatedTripService;
import be.kdg.backend.services.interfaces.StopService;
import be.kdg.backend.services.interfaces.TripService;
import be.kdg.backend.services.interfaces.UserService;
import be.kdg.web.forms.AnnouncementForm;
import be.kdg.web.forms.EquipmentForm;
import be.kdg.web.forms.TripForm;
import be.kdg.web.validators.AnnouncementValidator;
import be.kdg.web.validators.EquipmentValidator;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * User: Bart Verhavert
 * Date: 21/02/13 14:02
 */
@Controller
@RequestMapping("/trips")
public class TripController {

    //SERVICES
    @Autowired
    @Qualifier("tripService")
    private TripService tripService;
    @Autowired
    @Qualifier("userService")
    private UserService userService;
    @Autowired
    @Qualifier("participatedTripService")
    private ParticipatedTripService participatedTripService;
    @Autowired
    private StopService stopService;
    //VALIDATORS
    @Autowired
    @Qualifier("tripValidator")
    private TripValidator tripValidator;
    @Autowired
    @Qualifier("announcementValidator")
    private AnnouncementValidator announcementValidator;
    @Autowired
    @Qualifier("equipmentValidator")
    private EquipmentValidator equipmentValidator;

    @RequestMapping(method = RequestMethod.GET)
    public String list(ModelMap model) {
        model.addAttribute("trips", tripService.getPublicTrips());
        return "trips/list";
    }

    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable Integer id, ModelMap model, HttpSession session) {
        Trip trip = tripService.get(id);
        model.addAttribute("trip", trip);
        boolean isAdmin = false;
        User user;
        if (!(trip.getStops().isEmpty())) {
            model.addAttribute("stops", stopService.getStopsByTripId(id));
        }
        int userId = 0;
        if (session.getAttribute("userId") != null) {
            userId = (Integer) session.getAttribute("userId");
        }
        if (userId > 0) {
            for (User u : trip.getAdmins()) {
                if (u.getId() == userId) {
                    isAdmin = true;
                }
            }
            model.addAttribute("isAdmin", isAdmin);
            model.addAttribute("announcements", tripService.getAnnouncementsByTripId(trip.getId()));
            model.addAttribute("equipment", tripService.getEquipmentByTripId(trip.getId()));
        }
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
    public String deleteTrip(@PathVariable Integer id, ModelMap model) {

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

        User user = userService.get((Integer) session.getAttribute("userId"));
        ParticipatedTrip participatedTrip = new ParticipatedTrip();
        //participatedTripService.add(participatedTrip);
        participatedTrip.setUser(user);
        participatedTrip.setTrip(trip);
        participatedTrip.setConfirmed(true);
        participatedTripService.add(participatedTrip);
        return "redirect:/trips/details/" + id;
    }

    @RequestMapping(value = "/registered", method = RequestMethod.GET)
    public String registeredTrips(ModelMap model, HttpSession session) {
        model.addAttribute("user", userService.get((Integer) session.getAttribute("userId")));
        return "trips/registered";
    }

    @RequestMapping(value = "/start/{participatedTripId}", method = RequestMethod.GET)
    public String startTrip(@PathVariable Integer participatedTripId, ModelMap model, HttpSession session) {
        ParticipatedTrip pTrip = participatedTripService.get(participatedTripId);
        pTrip.setFinished(false);
        pTrip.setStarted(true);
        User user = userService.get((Integer) session.getAttribute("userId"));

        participatedTripService.update(pTrip);
        return "redirect:/trips/registered/";
    }

    @RequestMapping(value = "/stop/{participatedTripId}", method = RequestMethod.GET)
    public String stopTrip(@PathVariable Integer participatedTripId, ModelMap model, HttpSession session) {
        ParticipatedTrip pTrip = participatedTripService.get(participatedTripId);
        pTrip.setStarted(false);
        pTrip.setFinished(true);
        participatedTripService.update(pTrip);
        return "redirect:/trips/registered/";
    }

    @RequestMapping(value = "/participants/{tripId}", method = RequestMethod.GET)
    public String participantsGet(@PathVariable Integer tripId, ModelMap model) {
        model.addAttribute("participatedTrips", participatedTripService.getConfirmedParticipatedTripsByTripId(tripId));
        return "trips/participants";
    }

    @RequestMapping(value = "/own", method = RequestMethod.GET)
    public String myTripsGet(ModelMap model, HttpSession session) {
        User user = userService.get((Integer) session.getAttribute("userId"));
        model.addAttribute("myTrips", user.getOwnTrips());
        return "trips/own";
    }

    @RequestMapping(value = "/invite/{id}", method = RequestMethod.GET)
    public String inviteGet(@PathVariable Integer id, ModelMap model, HttpSession session) {

        model.addAttribute("users", userService.getUninvitedUsers(id, (Integer) session.getAttribute("userId")));
        return "trips/invite";
    }
    @RequestMapping(value = "/invite/{id}", method = RequestMethod.POST)
       public String invitePost(@PathVariable Integer id, ModelMap model, HttpSession session, HttpServletRequest request) {
           List<Integer> userIdList = new ArrayList<Integer>();
   
           for (Enumeration e = request.getParameterNames(); e.hasMoreElements(); )
               userIdList.add(Integer.parseInt((String) e.nextElement()));
   
           userService.createUserInvitations(userIdList, id);
           return "redirect:/trips/own";
       }
   
       @RequestMapping(value = "/invitations", method = RequestMethod.GET)
       public String invitationsGet(ModelMap model, HttpSession session) {
           Integer userId = (Integer) session.getAttribute("userId");
           model.addAttribute("invitations", participatedTripService.getInvitations(userId));
           return "trips/invitations";
       }
   
       @RequestMapping(value = "/invitations/accept/{id}", method = RequestMethod.GET)
       public String invitationsAcceptPost(@PathVariable Integer id, ModelMap model){
           ParticipatedTrip pt = participatedTripService.get(id);
           pt.setConfirmed(true);
           participatedTripService.update(pt);
           return "redirect:/trips/invitations";
       }
   
       @RequestMapping(value = "/invitations/deny/{id}", method = RequestMethod.GET)
       public String invitationsDenyPost(@PathVariable Integer id, ModelMap model){
           ParticipatedTrip pt = participatedTripService.get(id);
           participatedTripService.remove(pt);
           return "redirect:/trips/invitations";
       }
    @RequestMapping(value = "{id}/announcements/add", method = RequestMethod.GET)
    public String addAnnouncementsGet(@PathVariable Integer id, ModelMap model) {
        model.addAttribute("announcementform", new AnnouncementForm());
        return "trips/addannouncement";
    }

    @RequestMapping(value = "/{id}/announcements/add", method = RequestMethod.POST)
    public String addAnnouncementsPost(@PathVariable Integer id, @ModelAttribute("announcementform") AnnouncementForm announcementForm, BindingResult result, HttpSession session) {
        announcementValidator.validate(announcementForm, result);
        if (result.hasErrors()) {
            return "trips";
        } else {
            Announcement announcement = new Announcement();
            announcement.setMessage(announcementForm.getMessage());
            Trip trip = tripService.get(id);
            announcement.setTrip(trip);
            trip.addAnnouncement(announcement);
            tripService.update(trip);
            return "redirect:/trips/details/" + id;
        }

    }

    @RequestMapping(value = "/{id}/announcements/delete/{announcementid}", method = RequestMethod.GET)
    public String deleteAnnouncement(@PathVariable Integer id,@PathVariable Integer announcementid, ModelMap model) {
        tripService.removeAnnouncementFromTrip(announcementid);
        return "redirect:/trips/details/" + id;
    }

    @RequestMapping(value = "/{id}/equipment/add", method = RequestMethod.GET)
    public String addEquipmentGet(@PathVariable Integer id, ModelMap model) {
        model.addAttribute("equipmentform", new EquipmentForm());
        return "trips/addequipment";
    }

    @RequestMapping(value = "/{id}/equipment/add", method = RequestMethod.POST)
    public String addEquipmentPost(@PathVariable Integer id,@ModelAttribute("equipmentform") EquipmentForm equipmentForm, BindingResult result, HttpSession session) {
        equipmentValidator.validate(equipmentForm, result);
        if (result.hasErrors()) {
            return "trips";
        } else {
            Equipment equipment = new Equipment();
            equipment.setDescription(equipmentForm.getDescription());
            Trip trip = tripService.get(id);
            equipment.setTrip(trip);
            trip.addEquipment(equipment);
            tripService.update(trip);
            return "redirect:/trips/details/" + id;
        }

    }
    @RequestMapping(value = "/{id}/equipment/delete/{equipmentid}", method = RequestMethod.GET)
       public String deleteEquipment(@PathVariable Integer id,@PathVariable Integer equipmentid, ModelMap model) {
           tripService.removeEquipmentFromTrip(equipmentid);
           return "redirect:/trips/details/" + id;
       }
}
