package be.kdg.entities;

import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 7/02/13
 * Time: 13:54
 * Copyright @ Soulware.be
 */
@Entity
@Table(name = "T_USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Email
    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private Integer facebookID;

    @NotNull
    private Date birthday;

    @ManyToMany(mappedBy="admins")
    private List<Trip> ownTrips;

    @ManyToMany(mappedBy="invitedUsers")
    private List<Trip> participatedTrips;

    public User() {
    }

    public User(String email, String password, String firstName, String lastName, Date birthday) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getFacebookID() {
        return facebookID;
    }

    public Date getBirthday() {
        return birthday;
    }

    private void setId(Integer id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFacebookID(Integer facebookID) {
        this.facebookID = facebookID;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
