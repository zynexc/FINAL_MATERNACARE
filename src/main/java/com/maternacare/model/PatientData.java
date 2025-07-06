package com.maternacare.model;

import javafx.beans.property.*;
import java.time.LocalDate;

public class PatientData {
    private final StringProperty patientId;
    private final StringProperty name;
    private final IntegerProperty age;
    private final StringProperty purok;
    private final DoubleProperty ageOfGestation;
    private final StringProperty bloodPressure;
    private final DoubleProperty weight;
    private final DoubleProperty height;
    private final ObjectProperty<LocalDate> nextAppointment;
    private final StringProperty contactNumber;
    private final StringProperty email;

    public PatientData(String patientId, String name, int age, String purok,
            double ageOfGestation, String bloodPressure,
            double weight, double height, LocalDate nextAppointment, String contactNumber, String email) {
        this.patientId = new SimpleStringProperty(patientId);
        this.name = new SimpleStringProperty(name);
        this.age = new SimpleIntegerProperty(age);
        this.purok = new SimpleStringProperty(purok);
        this.ageOfGestation = new SimpleDoubleProperty(ageOfGestation);
        this.bloodPressure = new SimpleStringProperty(bloodPressure);
        this.weight = new SimpleDoubleProperty(weight);
        this.height = new SimpleDoubleProperty(height);
        this.nextAppointment = new SimpleObjectProperty<>(nextAppointment);
        this.contactNumber = new SimpleStringProperty(contactNumber);
        this.email = new SimpleStringProperty(email);
    }

    // Getters and setters for patientId
    public String getPatientId() {
        return patientId.get();
    }

    public void setPatientId(String value) {
        patientId.set(value);
    }

    public StringProperty patientIdProperty() {
        return patientId;
    }

    // Getters and setters for name
    public String getName() {
        return name.get();
    }

    public void setName(String value) {
        name.set(value);
    }

    public StringProperty nameProperty() {
        return name;
    }

    // Getters and setters for age
    public int getAge() {
        return age.get();
    }

    public void setAge(int value) {
        age.set(value);
    }

    public IntegerProperty ageProperty() {
        return age;
    }

    // Getters and setters for purok
    public String getPurok() {
        return purok.get();
    }

    public void setPurok(String value) {
        purok.set(value);
    }

    public StringProperty purokProperty() {
        return purok;
    }

    // Getters and setters for ageOfGestation
    public double getAgeOfGestation() {
        return ageOfGestation.get();
    }

    public void setAgeOfGestation(double value) {
        ageOfGestation.set(value);
    }

    public DoubleProperty ageOfGestationProperty() {
        return ageOfGestation;
    }

    // Getters and setters for bloodPressure
    public String getBloodPressure() {
        return bloodPressure.get();
    }

    public void setBloodPressure(String value) {
        bloodPressure.set(value);
    }

    public StringProperty bloodPressureProperty() {
        return bloodPressure;
    }

    // Getters and setters for weight
    public double getWeight() {
        return weight.get();
    }

    public void setWeight(double value) {
        weight.set(value);
    }

    public DoubleProperty weightProperty() {
        return weight;
    }

    // Getters and setters for height
    public double getHeight() {
        return height.get();
    }

    public void setHeight(double value) {
        height.set(value);
    }

    public DoubleProperty heightProperty() {
        return height;
    }

    // Getters and setters for nextAppointment
    public LocalDate getNextAppointment() {
        return nextAppointment.get();
    }

    public void setNextAppointment(LocalDate value) {
        nextAppointment.set(value);
    }

    public ObjectProperty<LocalDate> nextAppointmentProperty() {
        return nextAppointment;
    }

    // Getters and setters for contactNumber
    public String getContactNumber() {
        return contactNumber.get();
    }

    public void setContactNumber(String value) {
        contactNumber.set(value);
    }

    public StringProperty contactNumberProperty() {
        return contactNumber;
    }

    // Getters and setters for email
    public String getEmail() {
        return email.get();
    }

    public void setEmail(String value) {
        email.set(value);
    }

    public StringProperty emailProperty() {
        return email;
    }
}