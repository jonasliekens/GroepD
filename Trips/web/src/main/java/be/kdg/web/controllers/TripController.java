package be.kdg.web.controllers;

import be.kdg.backend.entities.*;
import be.kdg.backend.enums.TravelType;
import be.kdg.backend.enums.TripType;
import be.kdg.backend.services.interfaces.*;
import be.kdg.web.forms.AnnouncementForm;
import be.kdg.web.forms.BroadcastForm;
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

import javax.mail.*;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

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
    @Autowired
    @Qualifier("broadcastService")
    private BroadcastService broadcastService;
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

    private Properties m_properties = new Properties();

    private Session m_Session = Session.getInstance(m_properties, new Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication("noreply.trips@gmail.com", "tripsadmin123"); // username and the password
        }
    });

    public TripController() {
        m_properties.put("mail.smtp.host", "smtp.gmail.com");
        m_properties.put("mail.smtp.socketFactory.port", "465");
        m_properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        m_properties.put("mail.smtp.auth", "true");
        m_properties.put("mail.smtp.port", "465");
        m_properties.put("mail.debug", "false");
        m_properties.put("mail.smtp.ssl.enable", "true");
    }

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
            if(trip.getAdmins().contains(userService.get(userId))) {
                isAdmin = true;
            }

            model.addAttribute("isAdmin", isAdmin);
            model.addAttribute("announcements", tripService.getAnnouncementsByTripId(trip.getId()));
            model.addAttribute("equipment", tripService.getEquipmentByTripId(trip.getId()));
            BroadcastForm form = new BroadcastForm();
            form.setTripId(id);
            model.addAttribute("broadcastForm", form);
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

        List<String> tripValues = new ArrayList<String>();
        for(TripType tt : TripType.values()){
            tripValues.add(tt.toString().toLowerCase());
        }
        model.addAttribute("tripTypes", tripValues);

        List<String> travelTypes = new ArrayList<String>();
        for(TravelType tt : TravelType.values()){
            travelTypes.add(tt.toString().toLowerCase());
        }
        model.addAttribute("travelTypes", travelTypes);
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
        model.addAttribute("myTrips", tripService.findOwnTripsByUserId((Integer) session.getAttribute("userId")));
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

        try {
            MimeMessage m_simpleMessage = new MimeMessage(m_Session);

            InternetAddress m_fromAddress = new InternetAddress("noreply.trips@gmail.com");
            m_simpleMessage.setSubject("Trip Invitation");
            m_simpleMessage.setFrom(m_fromAddress);
            User sender = userService.get((Integer) session.getAttribute("userId"));
            Trip trip = tripService.get(id);

            for (Integer userId : userIdList) {
                User receiver = userService.get(userId);
                if (receiver.isReceiveMails()) {
                    InternetAddress m_toAddress = new InternetAddress(receiver.getEmail());
                    m_simpleMessage.setRecipient(Message.RecipientType.TO, m_toAddress);

                    StringBuilder msgBody = new StringBuilder();
                    msgBody.append(String.format("Dear %s %s\n\nYou are invited by %s to participate the trip: %s.\n" +
                            "To confirm your invitation go to: http://localhost:8080/web/trips/invitations\n" +
                            "Make sure you are logged in before going to the link above.\n\n" +
                            "This is an automated email. You can not reply to this email.", receiver.getFirstName(), receiver.getLastName(), sender.getFirstName() + " " + sender.getLastName(), trip.getName()));

                    m_simpleMessage.setContent(msgBody.toString(), "text/plain");

                    Transport.send(m_simpleMessage);
                }
            }

        } catch (MessagingException ex) {
            ex.printStackTrace();
        }

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
    public String invitationsAcceptPost(@PathVariable Integer id, ModelMap model) {
        ParticipatedTrip pt = participatedTripService.get(id);
        pt.setConfirmed(true);
        participatedTripService.update(pt);
        return "redirect:/trips/invitations";
    }

    @RequestMapping(value = "/invitations/deny/{id}", method = RequestMethod.GET)
    public String invitationsDenyPost(@PathVariable Integer id, ModelMap model) {
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

            Trip trip = tripService.get(id);
            User sender = userService.get((Integer) session.getAttribute("userId"));

            try {
                MimeMessage m_simpleMessage = new MimeMessage(m_Session);
                InternetAddress m_fromAddress = new InternetAddress("noreply.trips@gmail.com");
                m_simpleMessage.setSubject("Trip Announcement");
                m_simpleMessage.setFrom(m_fromAddress);

                for (ParticipatedTrip pt : participatedTripService.getConfirmedParticipatedTripsByTripId(id)) {
                    if (pt.getUser().isReceiveMails()) {
                        User receiver = pt.getUser();

                        InternetAddress m_toAddress = new InternetAddress(receiver.getEmail());
                        m_simpleMessage.setRecipient(Message.RecipientType.TO, m_toAddress);

                        StringBuilder msgBody = new StringBuilder();
                        msgBody.append(String.format("Dear %s %s\n\n%s %s placed an announcement for trip: %s.\n\n" +
                                "%s\n\n" +
                                "To view the announcement go to: http://localhost:8080/web/trips/details/%d\n" +
                                "Make sure you are logged in before going to the link above.\n\n" +
                                "This is an automated email. You can not reply to this email.", receiver.getFirstName(), receiver.getLastName(), sender.getFirstName(), sender.getLastName(), trip.getName(), announcementForm.getMessage(), id));

                        m_simpleMessage.setContent(msgBody.toString(), "text/plain");

                        Transport.send(m_simpleMessage);
                    }
                }

            } catch (MessagingException ex) {
                ex.printStackTrace();
            }

            Announcement announcement = new Announcement();
            announcement.setMessage(announcementForm.getMessage());
            announcement.setTrip(trip);
            trip.addAnnouncement(announcement);
            tripService.update(trip);
            return "redirect:/trips/details/" + id;
        }

    }

    @RequestMapping(value = "/{id}/announcements/delete/{announcementid}", method = RequestMethod.GET)
    public String deleteAnnouncement(@PathVariable Integer id, @PathVariable Integer announcementid, ModelMap model) {
        tripService.removeAnnouncementFromTrip(announcementid);
        return "redirect:/trips/details/" + id;
    }

    @RequestMapping(value = "/{id}/equipment/add", method = RequestMethod.GET)
    public String addEquipmentGet(@PathVariable Integer id, ModelMap model) {
        model.addAttribute("equipmentform", new EquipmentForm());
        return "trips/addequipment";
    }

    @RequestMapping(value = "/{id}/equipment/add", method = RequestMethod.POST)
    public String addEquipmentPost(@PathVariable Integer id, @ModelAttribute("equipmentform") EquipmentForm equipmentForm, BindingResult result, HttpSession session) {
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
    public String deleteEquipment(@PathVariable Integer id, @PathVariable Integer equipmentid, ModelMap model) {
        tripService.removeEquipmentFromTrip(equipmentid);
        return "redirect:/trips/details/" + id;
    }

    @RequestMapping(value="/addBroadcast", method = RequestMethod.POST)
    public String addBroadcastMessage(@ModelAttribute("broadcastForm") @Valid BroadcastForm broadcastForm, ModelMap model, HttpSession session){
        System.out.println("tripId=" + broadcastForm.getTripId().toString());
        BroadcastMessage message = new BroadcastMessage(broadcastForm.getMessage(), tripService.get(broadcastForm.getTripId()), new Date());
        broadcastService.add(message);
        return "redirect:/trips/details/" + broadcastForm.getTripId();
    }
}
