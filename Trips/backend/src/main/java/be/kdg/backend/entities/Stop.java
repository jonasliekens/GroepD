package be.kdg.backend.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "T_STOP")
public class Stop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String name;

    @Column(columnDefinition="text")
    private String description;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    private Integer accuracy;

    @NotNull
    private Integer orderNumber;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "stopId")
    private Set<Picture> pictures;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "stopId")
    private Set<Question> questions;

    @ManyToOne
    @JoinColumn(name = "tripId")
    Trip trip;

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
