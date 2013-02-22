package be.kdg.backend.entities;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

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

    @NotNull
    private String name;

    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.ALL})
    @JoinTable(name="T_TRIP_ADMINS",
            joinColumns={@JoinColumn(name="tripId")},
            inverseJoinColumns={@JoinColumn(name="userId")})
    private Set<User> admins;

    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.ALL})
    @JoinTable(name="T_TRIP_PARTICIPANT",
            joinColumns={@JoinColumn(name="tripId")},
            inverseJoinColumns={@JoinColumn(name="userId")})
    private Set<User> invitedUsers;

    @NotNull
    private Boolean privateTrip;

    @NotNull
    private Boolean published;

    @Enumerated(EnumType.STRING)
    private TripType type;

    @NotNull
    private Integer nrDays;

    @NotNull
    private Integer nrHours;

    @OneToMany(fetch = FetchType.EAGER,orphanRemoval=true)
    @JoinColumn(name = "tripId")
    private Set<Stop> stops;

    public Trip(){
        initLists();
    }

    public Trip(String name, User admin, boolean privateTrip) {
        initLists();
        setName(name);
        addAdmin(admin);
        setPublished(false);
        setType(TripType.LOOSE);
        setPrivateTrip(privateTrip);
    }

    public Trip(String name, User admin, boolean privateTrip, Integer nrDays, Integer nrHours, TripType type) {
        initLists();
        setName(name);
        addAdmin(admin);
        setType(type);
        setPublished(false);
        setPrivateTrip(privateTrip);
        setNrDays(nrDays);
        setNrHours(nrHours);
    }

    private void initLists() {
        admins = new HashSet<User>();
        invitedUsers = new HashSet<User>();
        stops = new HashSet<Stop>();
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

    public void setStops(Set<Stop> stops) {
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

    public void setAdmins(Set<User> admins) {
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

    public void setName(String name) {
        this.name = name;
    }

    public void setInvitedUsers(Set<User> invitedUsers) {
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

    public Set<User> getAdmins() {
        return admins;
    }

    public Set<User> getInvitedUsers() {
        return invitedUsers;
    }

    public boolean isPrivateTrip() {
        return privateTrip;
    }

    public boolean isPublished() {
        return published;
    }

    public String getName() {
        return name;
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

    public Set<Stop> getStops() {
        return stops;
    }

    public Integer getId() {
        return id;
    }

    private void setId(Integer id) {
        this.id = id;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public Boolean getPrivateTrip() {
        return privateTrip;
    }

    public void setPrivateTrip(Boolean privateTrip) {
        this.privateTrip = privateTrip;
    }
}
