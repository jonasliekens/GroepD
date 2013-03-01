package be.kdg.web.controllers;

import be.kdg.backend.entities.Answer;
import be.kdg.backend.entities.Question;
import be.kdg.backend.entities.Stop;
import be.kdg.backend.services.interfaces.StopService;
import be.kdg.backend.services.interfaces.TripService;
import be.kdg.web.forms.AnswerForm;
import be.kdg.web.forms.QuestionForm;
import be.kdg.web.forms.StopForm;
import be.kdg.web.validators.AnswerValidator;
import be.kdg.web.validators.QuestionValidator;
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
import java.util.HashMap;
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

    @Autowired
    StopValidator stopValidator;

    @Autowired
    AnswerValidator answerValidator;

    @Autowired
    QuestionValidator questionValidator;

    @RequestMapping(value = "/trips/{id}/stops", method = RequestMethod.GET)
    public String list(@PathVariable Integer id, ModelMap model) {
        model.addAttribute("tripId", id);
        return "/stops/list";
    }

    @RequestMapping(value = "/trips/{id}/stops/add", method = RequestMethod.GET)
    public String add(@PathVariable Integer id, ModelMap model) {
        model.addAttribute("stopForm", new StopForm());

        HashMap<Integer, String> stops = new HashMap<Integer, String>();
        for(Stop stop : tripService.get(id).getStops()){
            stops.put(stop.getOrderNumber(), stop.getName());
        }
        model.addAttribute("stops", stops);
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

            if (stopForm.getOrderOption().equals("first")) {
                stop.setOrderNumber(1);
            } else if (stopForm.getOrderOption().equals("last")) {
                stop.setOrderNumber(tripService.get(id).getStops().size()+1);
            } else if (stopForm.getOrderOption().equals("after")) {

                //stop.setOrderNumber(stopForm.getSelectedStop().getKey()+1);
                stop.setOrderNumber(stopForm.getOrderNumber()+1);
            }

            stopService.add(stop, id);

            return "redirect:/trips/details/"+id;
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

        HashMap<Integer, String> stops = new HashMap<Integer, String>();
        for(Stop stop2 : tripService.get(id).getStops()){
            stops.put(stop2.getOrderNumber(), stop2.getName());
        }
        model.addAttribute("stops", stops);

        if(stopService.get(stopid).getOrderNumber() == stopService.getStopsByTripId(id).get(0).getOrderNumber()){
            stopForm.setOrderOption("first");
        } else if(stopService.get(stopid).getOrderNumber() == stopService.getStopsByTripId(id).get(stopService.getStopsByTripId(id).size()-1).getOrderNumber()){
            stopForm.setOrderOption("last");
        } else {
            int index = stopService.getStopsByTripId(id).indexOf(stopService.get(stopid));
            stopForm.setOrderOption("after");
            stopForm.setOrderNumber(stopService.getStopsByTripId(id).get(index-1).getOrderNumber());
        }

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

            return "redirect:/trips/details/"+id;
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

        return "redirect:/trips/details/" + id;
    }

    @RequestMapping(value = "trips/{id}/stops/details/{stopid}", method = RequestMethod.GET)
    public String detailsStop(@PathVariable Integer id, @PathVariable Integer stopid, ModelMap model) {
        model.addAttribute("trip", tripService.get(id));
        model.addAttribute("stop", stopService.get(stopid));
        return "/stops/details";
    }

    @RequestMapping(value = "trips/{id}/stops/addquestion/{stopid}", method = RequestMethod.GET)
    public String addInfoGet(@PathVariable Integer id, @PathVariable Integer stopid, ModelMap model) {
        model.addAttribute("questionForm", new QuestionForm());
        return "/stops/addquestion";
    }

    @RequestMapping(value = "trips/{id}/stops/addquestion/{stopid}", method = RequestMethod.POST)
    public String addInfoPost(@PathVariable Integer id, @PathVariable Integer stopid,@ModelAttribute("questionForm") QuestionForm questionForm, BindingResult result, ModelMap model) {
        questionValidator.validate(questionForm, result);
        Stop stop = stopService.get(stopid);
        Question question = new Question();
        question.setQuestion(questionForm.getQuestion());
        stop.addQuestion(question);
        stopService.update(stop,stop.getTrip().getId());
        return "redirect:/trips/"+id+"/stops/details/" + stopid;
    }

    @RequestMapping(value = "trips/{id}/stops/addanswer/{stopid}/{questionid}", method = RequestMethod.GET)
    public String addAnswerGet(@PathVariable Integer id, @PathVariable Integer stopid,@PathVariable Integer questionid, ModelMap model) {
        model.addAttribute("answerForm", new AnswerForm());
        return "/stops/addanswer";
    }
    @RequestMapping(value = "trips/{id}/stops/addanswer/{stopid}/{questionid}", method = RequestMethod.POST)
    public String addAnswerPost(@PathVariable Integer id, @PathVariable Integer stopid,@PathVariable Integer questionid,@ModelAttribute("answerForm") AnswerForm answerForm, BindingResult result, ModelMap model) {
        answerValidator.validate(answerForm, result);
        Stop stop = stopService.get(stopid);
        Set<Question> questions = stop.getQuestions();
        Question question = new Question();
        for(Question q:questions){
            if (q.getId().equals(questionid)){
                question = q;
                break;
            }
        }
        Answer answer = new Answer();
        answer.setAnswer(answerForm.getAnswer());
        answer.setCorrect(answerForm.isIscorrect());
        question.addAnswer(answer);
        stopService.update(stop,stop.getTrip().getId());
        return "redirect:/trips/"+id+"/stops/details/" + stopid;
    }
    private List<String> setToList(Set<Stop> stops) {
        List<String> list = new ArrayList<String>();
        for(Stop stop : stops){
            list.add(stop.getName());
        }
        return list;
    }
}
