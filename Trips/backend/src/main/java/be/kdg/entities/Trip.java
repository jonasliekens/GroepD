package be.kdg.entities;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 7/02/13
 * Time: 16:09
 * Copyright @ Soulware.be
 */
@Entity
@Table(name = "T_TRIP")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.ALL})
    @JoinTable(name="T_TRIP_ADMINS",
            joinColumns={@JoinColumn(name="tripId")},
            inverseJoinColumns={@JoinColumn(name="userId")})
    private List<User> admins;

    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.ALL})
    @JoinTable(name="T_TRIP_PARTICIPANT",
            joinColumns={@JoinColumn(name="tripId")},
            inverseJoinColumns={@JoinColumn(name="userId")})
    private List<User> invitedUsers;

    @NotNull
    private boolean privateTrip;

    @NotNull
    private boolean published;

    @Enumerated(EnumType.STRING)
    private TripType type;

    @NotNull
    private Integer nrDays;

    @NotNull
    private Integer nrHours;

    @OneToMany(fetch = FetchType.EAGER,orphanRemoval=true)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "tripId")
    private List<Stop> stops;

    public Trip(){
        initLists();
    }

    public Trip(User admin, boolean privateTrip) {
        initLists();
        addAdmin(admin);
        setPublished(false);
        setType(TripType.LOOSE);
        setPrivateTrip(privateTrip);
    }

    public Trip(User admin, boolean privateTrip, Integer nrDays, Integer nrHours, TripType type) {
        initLists();
        addAdmin(admin);
        setType(type);
        setPublished(false);
        setPrivateTrip(privateTrip);
        setNrDays(nrDays);
        setNrHours(nrHours);
    }

    private void initLists() {
        admins = new ArrayList<User>();
        invitedUsers = new ArrayList<User>();
        stops = new ArrayList<Stop>();
    }

    public void addStop(Stop stop) {
        stops.add(stop);
    }

    public boolean removeStop(Stop stop) {
        if (stops.size() > 1 && stops.contains(stop)) {
            stops.remove(stop);
            return true;
        } else {
            return false;
        }
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }

    public void addAdmin(User admin) {
        this.admins.add(admin);
    }

    public boolean removeAdmin(User admin) {
        if (admins.size() > 1 && admins.contains(admin)) {
            admins.remove(admin);
            return true;
        } else {
            return false;
        }
    }

    public void setAdmins(List<User> admins) {
        this.admins = admins;
    }

    public void addInviteduser(User user) {
        this.invitedUsers.add(user);
    }

    public boolean removeInvitedUser(User user) {
        if (invitedUsers.contains(user)) {
            invitedUsers.remove(user);
            return true;
        } else {
            return false;
        }
    }

    public void setInvitedUsers(List<User> invitedUsers) {
        this.invitedUsers = invitedUsers;
    }

    public void setPrivateTrip(boolean privateTrip) {
        this.privateTrip = privateTrip;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public void setType(TripType type) {
        this.type = type;
    }

    public void setNrDays(Integer nrDays) {
        this.nrDays = nrDays;
    }

    public void setNrHours(Integer nrHours) {
        this.nrHours = nrHours;
    }

    public List<User> getAdmins() {
        return admins;
    }

    public List<User> getInvitedUsers() {
        return invitedUsers;
    }

    public boolean isPrivateTrip() {
        return privateTrip;
    }

    public boolean isPublished() {
        return published;
    }

    public TripType getType() {
        return type;
    }

    public Integer getNrDays() {
        return nrDays;
    }

    public Integer getNrHours() {
        return nrHours;
    }

    public List<Stop> getStops() {
        return stops;
    }

    public Integer getId() {
        return id;
    }

    private void setId(Integer id) {
        this.id = id;
    }
}
