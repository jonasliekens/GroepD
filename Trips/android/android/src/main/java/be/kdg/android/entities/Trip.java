package be.kdg.android.entities;

import be.kdg.android.enums.TravelType;
import be.kdg.android.enums.TripType;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 7/02/13
 * Time: 16:09
 * Copyright @ Soulware.be
 */
public class Trip implements Serializable {
    private Integer id;
    private String name;
    private Set<User> admins;
    private Set<ParticipatedTrip> participatedTrips;
    private Boolean privateTrip;
    private Boolean published;
    private TripType tripType;
    private TravelType travelType;
    private Integer nrDays;
    private Integer nrHours;
    private Set<Stop> stops;
    private Boolean communicationByChat;
    private Boolean communicationByLocation;

    public Trip(){
        initLists();
    }

    private void initLists(){
        this.admins = new HashSet<User>();
        this.participatedTrips = new HashSet<ParticipatedTrip>();
        this.stops = new HashSet<Stop>();
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
}
