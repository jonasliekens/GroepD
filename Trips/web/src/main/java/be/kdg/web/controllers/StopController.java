package be.kdg.web.controllers;

import be.kdg.backend.entities.Stop;
import be.kdg.backend.services.interfaces.StopService;
import be.kdg.backend.services.interfaces.TripService;
import be.kdg.web.forms.StopForm;
import be.kdg.web.validators.StopValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    List stops;

    @Autowired
    StopValidator stopValidator;

    @RequestMapping(value = "/trips/{id}/stops", method = RequestMethod.GET)
    public String list(@PathVariable Integer id, ModelMap model) {
        model.addAttribute("tripId", id);
        model.addAttribute("stops", stopService.getStopsByTripId(id));
        stops = setToList(tripService.get(id).getStops());
        return "/stops/list";
    }

    @RequestMapping(value = "/trips/{id}/stops/add", method = RequestMethod.GET)
    public String add(@PathVariable Integer id, ModelMap model) {
        model.addAttribute("stopForm", new StopForm());
        return "/stops/add";
    }

    @RequestMapping(value = "/trips/{id}/stops/add", method = RequestMethod.POST)
    public String addStop(@PathVariable Integer id, @ModelAttribute("stopForm") StopForm stopForm, BindingResult result, SessionStatus status) {

        stopValidator.validate(stopForm, result);

        if (result.hasErrors()) {
            return "/stops/add";
        } else {
            status.setComplete();

            Stop stop = new Stop();

            stop.setName(stopForm.getName());
            stop.setDescription(stopForm.getDescription());
            stop.setLatitude(stopForm.getLatitude());
            stop.setLongitude(stopForm.getLongitude());
            stop.setAccuracy(stopForm.getAccuracy());

            if (stopForm.getOrderString().equals("first")) {
                stop.setOrderNumber(1);
            } else if (stopForm.getOrderString().equals("last")) {
                stop.setOrderNumber(tripService.get(id).getStops().size()+1);
            } else if (stopForm.getOrderString().equals("after")) {
                stop.setOrderNumber(stopForm.getOrderNumber()+1);
            }

            stopService.add(stop, id);

            return "redirect:/trips/" + id + "/details";
        }
    }

    @RequestMapping(value = "trips/{id}/stops/edit/{stopid}", method = RequestMethod.GET)
    public String edit(@PathVariable Integer id, @PathVariable Integer stopid, ModelMap model) {
        Stop stop = stopService.get(stopid);
        StopForm stopForm = new StopForm();
        stopForm.setName(stop.getName());
        stopForm.setDescription(stop.getDescription());
        stopForm.setAccuracy(stop.getAccuracy());
        stopForm.setLatitude(stop.getLatitude());
        stopForm.setLongitude(stop.getLongitude());
        stopForm.setOrderNumber(stop.getOrderNumber());
        model.addAttribute("stopForm", stopForm);
        model.addAttribute("stop", stop);
        return "/stops/edit";
    }

    @RequestMapping(value = "/trips/{id}/stops/edit/{stopid}", method = RequestMethod.POST)
    public String editTrip(@PathVariable Integer id, @PathVariable Integer stopid, @ModelAttribute("stopForm") StopForm stopForm, BindingResult result, SessionStatus status) {
        stopValidator.validate(stopForm, result);
        if (result.hasErrors()) {
            return "stops";
        } else {
            status.setComplete();

            Stop stop = stopService.get(stopid);

            stop.setName(stopForm.getName());
            stop.setDescription(stopForm.getDescription());
            // On creation, a trip shouldn't be published
            stop.setLongitude(stopForm.getLongitude());
            stop.setLatitude(stopForm.getLatitude());
            stop.setAccuracy(stopForm.getAccuracy());
            stop.setOrderNumber(stopForm.getOrderNumber());
            stopService.update(stop, id);

            return "redirect:/trips/" + id + "/stops";
        }
    }

    @RequestMapping(value = "trips/{id}/stops/delete/{stopid}", method = RequestMethod.GET)
    public String deleteStopConfirm(@PathVariable Integer id, @PathVariable Integer stopid, ModelMap model) {
        model.addAttribute("trip", tripService.get(id));
        model.addAttribute("stop", stopService.get(stopid));
        return "/stops/delete";
    }

    @RequestMapping(value = "trips/{id}/stops/delete/{stopid}", method = RequestMethod.POST)
    public String deleteStop(@PathVariable Integer id, @PathVariable Integer stopid, ModelMap model) {

        stopService.remove(stopService.get(stopid));

        return "redirect:/trips/"+id+"/stops/";
    }

    private List<String> setToList(Set<Stop> stops){
        List<String> list = new ArrayList<String>();
        for(Stop stop : stops){
            list.add(stop.getName());
        }
        return list;
    }
}
