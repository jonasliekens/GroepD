package be.kdg.web.forms;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created with IntelliJ IDEA.
 * User: Nick
 * Date: 1/03/13
 * Time: 0:09
 * To change this template use File | Settings | File Templates.
 */
public class PhotoForm {

    private MultipartFile photo;

    public PhotoForm() {
    }

    public MultipartFile getPhoto() {
        return photo;
    }

    public void setPhoto(MultipartFile photo) {
        this.photo = photo;
    }
}
