package be.kdg.web.forms;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 12/03/13
 * Time: 15:50
 * Copyright @ Soulware.be
 */
public class BroadcastForm {
    @NotEmpty
    String message;
    Integer tripId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getTripId() {
        return tripId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }
}
