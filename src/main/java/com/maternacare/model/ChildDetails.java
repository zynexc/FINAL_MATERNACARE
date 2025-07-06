package com.maternacare.model;

import java.time.LocalDate;

public class ChildDetails {
    private String deliveryType;
    private String gender;
    private String placeOfDelivery;
    private String yearDelivered;
    private String attendedBy;
    private String status;
    private LocalDate birthdate;
    private String tetanusStatus;
    private String tetanusYear;

    // Getters and Setters
    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPlaceOfDelivery() {
        return placeOfDelivery;
    }

    public void setPlaceOfDelivery(String placeOfDelivery) {
        this.placeOfDelivery = placeOfDelivery;
    }

    public String getYearDelivered() {
        return yearDelivered;
    }

    public void setYearDelivered(String yearDelivered) {
        this.yearDelivered = yearDelivered;
    }

    public String getAttendedBy() {
        return attendedBy;
    }

    public void setAttendedBy(String attendedBy) {
        this.attendedBy = attendedBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getTetanusStatus() {
        return tetanusStatus;
    }

    public void setTetanusStatus(String tetanusStatus) {
        this.tetanusStatus = tetanusStatus;
    }

    public String getTetanusYear() {
        return tetanusYear;
    }

    public void setTetanusYear(String tetanusYear) {
        this.tetanusYear = tetanusYear;
    }
}