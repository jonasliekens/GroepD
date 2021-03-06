package be.kdg.backend.entities;


import be.kdg.backend.enums.TravelType;
import be.kdg.backend.enums.TripType;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
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

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "T_TRIP_ADMINS",
            joinColumns = {@JoinColumn(name = "tripId")},
            inverseJoinColumns = {@JoinColumn(name = "userId")})
    @JsonIgnore
    private Set<User> admins;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trip")
    @JsonIgnore
    private Set<ParticipatedTrip> participatedTrips;

    @NotNull
    private Boolean privateTrip;

    @NotNull
    private Boolean published;

    @Enumerated(EnumType.STRING)
    private TripType tripType;

    @Enumerated(EnumType.STRING)
    private TravelType travelType;

    @NotNull
    private Integer nrDays;

    @NotNull
    private Integer nrHours;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL)
    private Set<Stop> stops;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL)
    private Set<Announcement> announcements;
    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL)
    private Set<Equipment> equipmentSet;    //equipments is not a word
    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL)
        private Set<Expense> expenses;
    private Boolean communicationByChat;
    private Boolean communicationByLocation;

    public Trip() {
        initLists();
    }

    private void initLists() {
        this.admins = new HashSet<User>();
        this.participatedTrips = new HashSet<ParticipatedTrip>();
        this.stops = new HashSet<Stop>();
        this.announcements = new HashSet<Announcement>();
        this.equipmentSet = new HashSet<Equipment>();
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

    public void addParticipatedTrip(ParticipatedTrip participatedTrip) {
        this.participatedTrips.add(participatedTrip);
    }

    public boolean removeParticipatedTrip(ParticipatedTrip participatedTrip) {
        if (participatedTrips.contains(participatedTrip)) {
            participatedTrips.remove(participatedTrip);
            return true;
        } else {
            return false;
        }
    }

    public void setParticipatedTrips(Set<ParticipatedTrip> participatedTrips) {
        this.participatedTrips = participatedTrips;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNrDays(Integer nrDays) {
        this.nrDays = nrDays;
    }

    public void setNrHours(Integer nrHours) {
        this.nrHours = nrHours;
    }

    public String getName() {
        return name;
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

    public Set<User> getAdmins() {

        return admins;
    }

    public Set<ParticipatedTrip> getParticipatedTrips() {
        return participatedTrips;
    }

    public boolean getCommunicationByChat() {
        return communicationByChat;
    }

    public void setCommunicationByChat(Boolean communicationByChat) {
        this.communicationByChat = communicationByChat;
    }

    public boolean getCommunicationByLocation() {
        return communicationByLocation;
    }

    public void setCommunicationByLocation(Boolean communicationByLocation) {
        this.communicationByLocation = communicationByLocation;
    }

    public TripType getTripType() {
        return tripType;
    }

    public void setTripType(TripType tripType) {
        this.tripType = tripType;
    }

    public TravelType getTravelType() {
        return travelType;
    }

    public void setTravelType(TravelType travelType) {
        this.travelType = travelType;
    }

    public Set<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(Set<Announcement> announcements) {
        this.announcements = announcements;
    }

    public void addAnnouncement(Announcement announcement) {
        this.announcements.add(announcement);

    }

    public void addEquipment(Equipment equipment) {
        this.equipmentSet.add(equipment);
    }

    public Set<Equipment> getEquipmentSet() {
        return equipmentSet;
    }

    public void setEquipmentSet(Set<Equipment> equipmentSet) {
        this.equipmentSet = equipmentSet;
    }

    public Set<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(Set<Expense> expenses) {
        this.expenses = expenses;
    }
}
