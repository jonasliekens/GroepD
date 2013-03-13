package be.kdg.web.controllers;

import be.kdg.backend.entities.Photo;
import be.kdg.backend.entities.Stop;
import be.kdg.backend.services.interfaces.StopService;
import be.kdg.web.forms.PhotoForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * Author: Nick De Waele
 * Date: 13/03/13
 */
@Controller
public class FileUploadController extends SimpleFormController {

    @Autowired
    @Qualifier("stopService")
    private StopService stopService;

    public FileUploadController() {
        setCommandClass(Photo.class);
        setCommandName("fileUploadForm");
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        Photo photo = (Photo) command;
        byte[] file = photo.getFile();
        return new ModelAndView("FileUploadSuccess");
    }

    @RequestMapping(value = "trips/{id}/stops/addphoto/{stopid}", method = RequestMethod.GET)
    public String showPage(@PathVariable Integer id, @PathVariable Integer stopid, ModelMap model) {


        model.addAttribute("fileuploadForm", new PhotoForm());

        return "photo/fileUpload";
    }

    @RequestMapping(value = "trips/{id}/stops/addphoto/{stopid}", method = RequestMethod.POST)
    public String create(@PathVariable Integer id, @PathVariable Integer stopid, HttpServletRequest request,@ModelAttribute("fileuploadForm") PhotoForm photoForm, BindingResult result) {
        Stop stop = stopService.get(stopid);
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                System.err.println("Error: " + error.getCode() + " - " + error.getDefaultMessage());
            }
            return "upload/uploadForm";
        } else {
            try {
                MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

                MultipartFile multipartFile = multipartRequest.getFile("file");

                Photo photo = new Photo();
                photo.setFile(multipartFile.getBytes());
                photo.setStop(stop);
                stop.addPhoto(photo);
                stopService.update(stop, id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "redirect:/trips/" + id + "/stops/details/" + stopid;
    }
}
