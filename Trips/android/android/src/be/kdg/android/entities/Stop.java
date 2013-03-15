package be.kdg.android.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Stop implements Serializable {
    private Integer id;
    private String name;
    private String description;
    private Double latitude;
    private Double longitude;
    private Integer accuracy;
    private Integer orderNumber;
    private Set<Picture> pictures;
    private Set<Question> questions;
    private Trip trip;

    public Stop() {
        initLists();
    }

    public Stop(String name, Double latitude, Double longitude, Integer orderNumber) {
        initLists();
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.orderNumber = orderNumber;
    }

    private void initLists(){
        this.questions = new HashSet<Question>();
        this.pictures = new HashSet<Picture>();
    }

    public Integer getId() {
        return id;
    }

    private void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
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

    public void addPicture(Picture picture){
        this.pictures.add(picture);
    }

    public void addQuestion(Question question){
        this.questions.add(question);
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

    public Set<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(Set<Picture> pictures) {
        this.pictures = pictures;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }
}
