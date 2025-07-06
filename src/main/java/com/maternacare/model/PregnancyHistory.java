package com.maternacare.model;

import javafx.beans.property.*;
import java.time.LocalDate;

public class PregnancyHistory {
    private final IntegerProperty pregnancyNumber;
    private final StringProperty deliveryType;
    private final StringProperty gender;
    private final StringProperty placeOfDelivery;
    private final IntegerProperty yearDelivered;
    private final StringProperty attendedBy;
    private final StringProperty status; // Alive or Not Alive
    private final ObjectProperty<LocalDate> birthDate;
    private final StringProperty ttInjection;

    public PregnancyHistory() {
        this.pregnancyNumber = new SimpleIntegerProperty(0);
        this.deliveryType = new SimpleStringProperty("");
        this.gender = new SimpleStringProperty("");
        this.placeOfDelivery = new SimpleStringProperty("");
        this.yearDelivered = new SimpleIntegerProperty(0);
        this.attendedBy = new SimpleStringProperty("");
        this.status = new SimpleStringProperty("");
        this.birthDate = new SimpleObjectProperty<>(null);
        this.ttInjection = new SimpleStringProperty("");
    }

    // DTO class for serialization
    public static class PregnancyHistoryDTO {
        public int pregnancyNumber;
        public String deliveryType;
        public String gender;
        public String placeOfDelivery;
        public int yearDelivered;
        public String attendedBy;
        public String status;
        public String birthDate;
        public String ttInjection;
    }

    public PregnancyHistoryDTO toDTO() {
        PregnancyHistoryDTO dto = new PregnancyHistoryDTO();
        dto.pregnancyNumber = getPregnancyNumber();
        dto.deliveryType = getDeliveryType();
        dto.gender = getGender();
        dto.placeOfDelivery = getPlaceOfDelivery();
        dto.yearDelivered = getYearDelivered();
        dto.attendedBy = getAttendedBy();
        dto.status = getStatus();
        dto.birthDate = getBirthDate() != null ? getBirthDate().toString() : null;
        dto.ttInjection = getTtInjection();
        return dto;
    }

    public static PregnancyHistory fromDTO(PregnancyHistoryDTO dto) {
        PregnancyHistory history = new PregnancyHistory();
        history.setPregnancyNumber(dto.pregnancyNumber);
        history.setDeliveryType(dto.deliveryType);
        history.setGender(dto.gender);
        history.setPlaceOfDelivery(dto.placeOfDelivery);
        history.setYearDelivered(dto.yearDelivered);
        history.setAttendedBy(dto.attendedBy);
        history.setStatus(dto.status);
        history.setBirthDate(dto.birthDate != null ? LocalDate.parse(dto.birthDate) : null);
        history.setTtInjection(dto.ttInjection);

        return history;
    }

    // Getters and Setters
    public int getPregnancyNumber() {
        return pregnancyNumber.get();
    }

    public void setPregnancyNumber(int value) {
        pregnancyNumber.set(value);
    }

    public IntegerProperty pregnancyNumberProperty() {
        return pregnancyNumber;
    }

    public String getDeliveryType() {
        return deliveryType.get();
    }

    public void setDeliveryType(String value) {
        deliveryType.set(value);
    }

    public StringProperty deliveryTypeProperty() {
        return deliveryType;
    }

    public String getGender() {
        return gender.get();
    }

    public void setGender(String value) {
        gender.set(value);
    }

    public StringProperty genderProperty() {
        return gender;
    }

    public String getPlaceOfDelivery() {
        return placeOfDelivery.get();
    }

    public void setPlaceOfDelivery(String value) {
        placeOfDelivery.set(value);
    }

    public StringProperty placeOfDeliveryProperty() {
        return placeOfDelivery;
    }

    public int getYearDelivered() {
        return yearDelivered.get();
    }

    public void setYearDelivered(int value) {
        yearDelivered.set(value);
    }

    public IntegerProperty yearDeliveredProperty() {
        return yearDelivered;
    }

    public String getAttendedBy() {
        return attendedBy.get();
    }

    public void setAttendedBy(String value) {
        attendedBy.set(value);
    }

    public StringProperty attendedByProperty() {
        return attendedBy;
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String value) {
        status.set(value);
    }

    public StringProperty statusProperty() {
        return status;
    }

    public LocalDate getBirthDate() {
        return birthDate.get();
    }

    public void setBirthDate(LocalDate value) {
        birthDate.set(value);
    }

    public ObjectProperty<LocalDate> birthDateProperty() {
        return birthDate;
    }

    public String getTtInjection() {
        return ttInjection.get();
    }

    public void setTtInjection(String value) {
        ttInjection.set(value);
    }

    public StringProperty ttInjectionProperty() {
        return ttInjection;
    }
}