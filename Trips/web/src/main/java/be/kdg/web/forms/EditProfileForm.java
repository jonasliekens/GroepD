package be.kdg.web.forms;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Author: Nick De Waele
 * Date: 6/03/13
 */
public class EditProfileForm {

    private String firstname;
    private String lastname;
    private Date birthday;
    private boolean receiveMails;
    private boolean shareLocation;


    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public boolean isReceiveMails() {
        return receiveMails;
    }

    public void setReceiveMails(boolean receiveMails) {
        this.receiveMails = receiveMails;
    }

    public boolean isShareLocation() {
        return shareLocation;
    }

    public void setShareLocation(boolean shareLocation) {
        this.shareLocation = shareLocation;
    }
}
