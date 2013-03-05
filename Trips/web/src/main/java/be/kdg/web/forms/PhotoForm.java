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

    private MultipartFile file;

    public PhotoForm() {
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
