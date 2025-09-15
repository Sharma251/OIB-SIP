package com.reservation.reservation.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bookings")
public class TrainDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pnr_number", nullable = false, unique = true)
    private String pnrNumber;
    
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "trainNumberber")
    private String trainNumber;

    @Column(name = "train_name")
    private String trainName;

    @Column(name = "origin")
    private String origin;

    @Column(name = "destination")
    private String destination;

    @Column(name = "status")
    private String status; 
    
    // New fields for passenger and booking details

    @Column(name = "passenger_name")
    private String passengerName;

    @Column(name = "passenger_age")
    private Integer passengerAge;

    @Column(name = "gender")
    private String gender;

    @Column(name = "journey_date")
    private String journeyDate;

    @Column(name = "class_type")
    private String classType;

    @Column(name = "seat_preference")
    private String seatPreference;

    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> schedule = new ArrayList<>();

    public TrainDetails() {}

    public TrainDetails(String trainNumber, String trainName, String destination, String status) {    
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.destination = destination;
        this.status = status;
    }

    // Getters and setters for existing and new fields
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPnrNumber() { return pnrNumber; }
    public void setPnrNumber(String pnrNumber) { this.pnrNumber = pnrNumber; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String gettrainNumber() { return trainNumber; }
    public void settrainNumber(String trainNumber) { this.trainNumber = trainNumber; }

    public String getTrainName() { return trainName; }
    public void setTrainName(String trainName) { this.trainName = trainName; }

    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public List<Schedule> getSchedule() { return schedule; }
    public void setSchedule(List<Schedule> schedule) {
        this.schedule.clear();
        if (schedule != null) {
            schedule.forEach(s -> s.setTrain(this));
            this.schedule.addAll(schedule);
        }
    }

    public String getPassengerName() { return passengerName; }
    public void setPassengerName(String passengerName) { this.passengerName = passengerName; }

    public Integer getPassengerAge() { return passengerAge; }
    public void setPassengerAge(Integer passengerAge) { this.passengerAge = passengerAge; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getJourneyDate() { return journeyDate; }
    public void setJourneyDate(String journeyDate) { this.journeyDate = journeyDate; }

    public String getClassType() { return classType; }
    public void setClassType(String classType) { this.classType = classType; }

    public String getSeatPreference() { return seatPreference; }
    public void setSeatPreference(String seatPreference) { this.seatPreference = seatPreference; }

}
