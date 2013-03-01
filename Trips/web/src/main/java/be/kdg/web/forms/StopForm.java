package be.kdg.web.forms;

/**
 * Created by IntelliJ IDEA.
 * Author: Nick De Waele
 * Date: 27/02/13
 */
public class StopForm {
    private String name;
    private String description;
    private Double latitude;
    private Double longitude;
    private Integer accuracy;
    private String orderOption;
    private Integer orderNumber;

    public StopForm() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Integer accuracy) {
        this.accuracy = accuracy;
    }

    public String getOrderOption() {
        return orderOption;
    }

    public void setOrderOption(String orderOption) {
        this.orderOption = orderOption;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }
}
