package be.kdg.backend.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

    @Pattern(regexp = "\\b[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}\\b")
    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private String facebookID;

    @NotNull
    private Date birthday;

    @ManyToMany(cascade = CascadeType.ALL,mappedBy="admins")
    private Set<Trip> ownTrips;

    @OneToMany(cascade = CascadeType.ALL, mappedBy= "user")
    private Set<ParticipatedTrip> participatedTrips;

    public User() {
        initLists();
    }

    public User(String email, String password, String firstName, String lastName, Date birthday) {
        initLists();
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
    }

    public User(String email, String password, String firstName, String lastName, Date birthday, String facebookID) {
        initLists();
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.facebookID = facebookID;
    }

    private void initLists(){
        this.ownTrips = new HashSet<Trip>();
        this.participatedTrips = new HashSet<ParticipatedTrip>();
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

    public String getFacebookID() {
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

    public void setFacebookID(String facebookID) {
        this.facebookID = facebookID;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Set<Trip> getOwnTrips() {
        return ownTrips;
    }

    public void setOwnTrips(Set<Trip> ownTrips) {
        this.ownTrips = ownTrips;
    }

    public Set<ParticipatedTrip> getParticipatedTrips() {
        return participatedTrips;
    }

    public void setParticipatedTrips(Set<ParticipatedTrip> participatedTrips) {
        this.participatedTrips = participatedTrips;
    }
    public void addParticipatedTrips(ParticipatedTrip participatedTrip) {
        this.participatedTrips.add(participatedTrip);
    }
}
