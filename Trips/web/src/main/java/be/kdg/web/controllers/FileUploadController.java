package be.kdg.web.controllers;

import be.kdg.backend.entities.Photo;
import be.kdg.backend.entities.Stop;
import be.kdg.backend.services.interfaces.StopService;
import be.kdg.web.forms.PhotoForm;
import be.kdg.web.vuforia.SignatureBuilder;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.DateUtils;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
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
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

//import org.apache.http.client.ClientProtocolException;
//import org.json.JSONException;

/**
 * Created by IntelliJ IDEA.
 * Author: Nick De Waele
 * Date: 13/03/13
 */
@Controller
public class FileUploadController extends SimpleFormController {
    private String accessKey = "e5eed588864ed2fdf23908899806267d2620d97c";
    private String secretKey = "06894d75a3781f60193e428b814103f823a8753b";

    private String url = "https://vws.vuforia.com";
    private Stop stop;
    private String tempName="";

    MultipartFile multipartFile;
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
        //byte[] file = photo.getFile();
        return new ModelAndView("FileUploadSuccess");
    }

    @RequestMapping(value = "trips/{id}/stops/addphoto/{stopid}", method = RequestMethod.GET)
    public String showPage(@PathVariable Integer id, @PathVariable Integer stopid, ModelMap model) {


        model.addAttribute("fileuploadForm", new PhotoForm());

        return "photo/fileUpload";
    }

    @RequestMapping(value = "trips/{id}/stops/addphoto/{stopid}", method = RequestMethod.POST)
    public String create(@PathVariable Integer id, @PathVariable Integer stopid, HttpServletRequest request, @ModelAttribute("fileuploadForm") PhotoForm photoForm, BindingResult result) {
        stop = stopService.get(stopid);
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                System.err.println("Error: " + error.getCode() + " - " + error.getDefaultMessage());
            }
            return "upload/uploadForm";
        } else {
            try {
                MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

                multipartFile = multipartRequest.getFile("file");

                Photo photo = new Photo();
                //photo.setFile(multipartFile.getBytes());
                photo.setTargetId(postTarget());
                photo.setTargetName(tempName);
                photo.setStop(stop);
                stop.addPhoto(photo);
                stopService.update(stop, id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "redirect:/trips/details/" + id;
    }

    private String postTarget() throws URISyntaxException, ClientProtocolException, IOException, JSONException {
        HttpPost postRequest = new HttpPost();
        HttpClient client = new DefaultHttpClient();
        postRequest.setURI(new URI(url + "/targets"));
        JSONObject requestBody = new JSONObject();

        setRequestBody(requestBody);
        postRequest.setEntity(new StringEntity(requestBody.toString()));
        setHeaders(postRequest); // Must be done after setting the body

        HttpResponse response = client.execute(postRequest);
        String responseBody = EntityUtils.toString(response.getEntity());
        System.out.println(responseBody);

        JSONObject jobj = new JSONObject(responseBody);

        String uniqueTargetId = jobj.has("target_id") ? jobj.getString("target_id") : "";
        System.out.println("\nCreated target with id: " + uniqueTargetId);

        return uniqueTargetId;
    }

    private void setRequestBody(JSONObject requestBody) throws IOException, JSONException {
        if (multipartFile.isEmpty()) {
            System.out.println("File location does not exist!");
            System.exit(1);
        }
        byte[] image = multipartFile.getBytes();
        requestBody.put("name", multipartFile.getOriginalFilename()); // Mandatory
        tempName=multipartFile.getOriginalFilename();
        requestBody.put("width", 320.0); // Mandatory
        requestBody.put("image", Base64.encodeBase64String(image)); // Mandatory
        requestBody.put("active_flag", 1); // Optional
        requestBody.put("application_metadata", Base64.encodeBase64String((stop.getId()+"").getBytes())); // Optional

    }

    private void setHeaders(HttpUriRequest request) {
        SignatureBuilder sb = new SignatureBuilder();
        System.out.println("Setting date");
        request.setHeader(new BasicHeader("Date", DateUtils.formatDate(new Date()).replaceFirst("[+]00:00$", "")));
        System.out.println("Setting Content Type");
        request.setHeader(new BasicHeader("Content-Type", "application/json"));
        System.out.println("Setting Authorization");
        request.setHeader("Authorization", "VWS " + accessKey + ":" + sb.tmsSignature(request, secretKey));
    }

}
