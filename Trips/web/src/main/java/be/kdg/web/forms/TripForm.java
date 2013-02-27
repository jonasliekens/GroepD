package be.kdg.web.forms;

/**
 * User: Bart Verhavert
 * Date: 22/02/13 13:28
 */
public class TripForm {
    private String name;
    private Boolean privateTrip;
//    private TripType type;
    private Integer nrDays;
    private Integer nrHours;

    private boolean communicationByChat;
    private boolean communicationByLocation;

    public TripForm() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getPrivateTrip() {
        return privateTrip;
    }

    public void setPrivateTrip(Boolean privateTrip) {
        this.privateTrip = privateTrip;
    }

    public Integer getNrDays() {
        return nrDays;
    }

    public void setNrDays(Integer nrDays) {
        this.nrDays = nrDays;
    }

    public Integer getNrHours() {
        return nrHours;
    }

    public void setNrHours(Integer nrHours) {
        this.nrHours = nrHours;
    }

    public boolean getCommunicationByChat() {
        return communicationByChat;
    }

    public void setCommunicationByChat(boolean communicationByChat) {
        this.communicationByChat = communicationByChat;
    }

    public boolean getCommunicationByLocation() {
        return communicationByLocation;
    }

    public void setCommunicationByLocation(boolean communicationByLocation) {
        this.communicationByLocation = communicationByLocation;
    }
}
