package com.maternacare.model;

import javafx.beans.property.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MaternalRecord {
    // Personal Information
    private final transient StringProperty patientId;
    private final transient StringProperty fullName;
    private final transient ObjectProperty<LocalDate> dateOfBirth;

    // New fields for husband's name and pregnancy details
    private final transient StringProperty husbandName;
    private final transient StringProperty deliveryType;
    private final transient StringProperty gender;
    private final transient StringProperty remarks;

    // Timestamp for form creation
    private final transient ObjectProperty<LocalDateTime> formTimestamp;

    // Contact Information
    private final transient StringProperty address;
    private final transient StringProperty purok;
    private final transient StringProperty contactNumber;
    private final transient StringProperty email;

    // Vital Signs
    private final transient StringProperty bloodPressure;
    private final transient StringProperty chiefComplaint;
    private final transient StringProperty pulseRate;
    private final transient StringProperty respiratoryRate;

    // Pregnancy Information
    private final transient ObjectProperty<LocalDate> lastMenstrualPeriod;
    private final transient ObjectProperty<LocalDate> expectedDeliveryDate;
    private final transient StringProperty para;
    private final transient StringProperty abortion;
    private final transient StringProperty livingChildren;

    // Medical Information
    private final transient IntegerProperty id;
    private final transient DoubleProperty ageOfGestation;
    private final transient DoubleProperty weight;
    private final transient DoubleProperty height;
    private final transient IntegerProperty fetalHeartTone;
    private final transient StringProperty presentation;
    private final transient DoubleProperty fundalHeight;
    private final transient ObjectProperty<LocalDate> nextAppointment;
    private final transient List<ChildDetails> childDetails;

    // Pregnancy History
    private final List<PregnancyHistory> pregnancyHistory;

    // Follow-up Vital Signs
    private final List<VitalSignsEntry> followUpVitalSigns;

    // New fields for term and preterm
    private final transient StringProperty term;
    private final transient StringProperty preterm;

    // New field for high-risk status
    private boolean highRisk;

    // Add field
    private final transient StringProperty barangayResidencyNumber;

    // New field for manual gravida
    private String manualGravida = null;

    // Default constructor
    public MaternalRecord() {
        // Personal Information
        this.patientId = new SimpleStringProperty("");
        this.fullName = new SimpleStringProperty("");
        this.dateOfBirth = new SimpleObjectProperty<>(null);

        // New fields initialization
        this.husbandName = new SimpleStringProperty("");
        this.deliveryType = new SimpleStringProperty("");
        this.gender = new SimpleStringProperty("");
        this.remarks = new SimpleStringProperty("");
        this.formTimestamp = new SimpleObjectProperty<>(LocalDateTime.now());

        // Contact Information
        this.address = new SimpleStringProperty("");
        this.purok = new SimpleStringProperty("");
        this.contactNumber = new SimpleStringProperty("");
        this.email = new SimpleStringProperty("");

        // Vital Signs
        this.bloodPressure = new SimpleStringProperty("");
        this.chiefComplaint = new SimpleStringProperty("");
        this.pulseRate = new SimpleStringProperty("");
        this.respiratoryRate = new SimpleStringProperty("");

        // Pregnancy Information
        this.lastMenstrualPeriod = new SimpleObjectProperty<>(null);
        this.expectedDeliveryDate = new SimpleObjectProperty<>(null);
        this.para = new SimpleStringProperty("");
        this.abortion = new SimpleStringProperty("");
        this.livingChildren = new SimpleStringProperty("");

        // Medical Information
        this.id = new SimpleIntegerProperty(0);
        this.ageOfGestation = new SimpleDoubleProperty(0.0);
        this.weight = new SimpleDoubleProperty(0.0);
        this.height = new SimpleDoubleProperty(0.0);
        this.fetalHeartTone = new SimpleIntegerProperty(0);
        this.presentation = new SimpleStringProperty("");
        this.fundalHeight = new SimpleDoubleProperty(0.0);
        this.nextAppointment = new SimpleObjectProperty<>(LocalDate.now());
        this.childDetails = new ArrayList<>();

        // Initialize pregnancy history
        this.pregnancyHistory = new ArrayList<>();
        this.followUpVitalSigns = new ArrayList<>();

        // New fields for term and preterm
        this.term = new SimpleStringProperty("");
        this.preterm = new SimpleStringProperty("");

        // Add field
        this.barangayResidencyNumber = new SimpleStringProperty("");
    }

    // Getters and Setters for Personal Information
    public String getPatientId() {
        return patientId.get();
    }

    public void setPatientId(String value) {
        patientId.set(value);
    }

    public StringProperty patientIdProperty() {
        return patientId;
    }

    public String getFullName() {
        return fullName.get();
    }

    public void setFullName(String value) {
        fullName.set(value);
    }

    public StringProperty fullNameProperty() {
        return fullName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth.get();
    }

    public void setDateOfBirth(LocalDate value) {
        dateOfBirth.set(value);
    }

    public ObjectProperty<LocalDate> dateOfBirthProperty() {
        return dateOfBirth;
    }

    // Getters and Setters for New Fields
    public String getHusbandName() {
        return husbandName.get();
    }

    public void setHusbandName(String value) {
        husbandName.set(value);
    }

    public StringProperty husbandNameProperty() {
        return husbandName;
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

    public String getRemarks() {
        return remarks.get();
    }

    public void setRemarks(String value) {
        remarks.set(value);
    }

    public StringProperty remarksProperty() {
        return remarks;
    }

    // Getters and Setters for Timestamp
    public LocalDateTime getFormTimestamp() {
        return formTimestamp.get();
    }

    public void setFormTimestamp(LocalDateTime value) {
        formTimestamp.set(value);
    }

    public ObjectProperty<LocalDateTime> formTimestampProperty() {
        return formTimestamp;
    }

    // Getters and Setters for Contact Information
    public String getAddress() {
        return address.get();
    }

    public void setAddress(String value) {
        address.set(value);
    }

    public StringProperty addressProperty() {
        return address;
    }

    public String getPurok() {
        return purok.get();
    }

    public void setPurok(String value) {
        purok.set(value);
    }

    public StringProperty purokProperty() {
        return purok;
    }

    public String getContactNumber() {
        return contactNumber.get();
    }

    public void setContactNumber(String value) {
        contactNumber.set(value);
    }

    public StringProperty contactNumberProperty() {
        return contactNumber;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String value) {
        email.set(value);
    }

    public StringProperty emailProperty() {
        return email;
    }

    // Getters and Setters for Vital Signs
    public String getBloodPressure() {
        return bloodPressure.get();
    }

    public void setBloodPressure(String value) {
        bloodPressure.set(value);
    }

    public StringProperty bloodPressureProperty() {
        return bloodPressure;
    }

    public String getChiefComplaint() {
        return chiefComplaint.get();
    }

    public void setChiefComplaint(String value) {
        chiefComplaint.set(value);
    }

    public StringProperty chiefComplaintProperty() {
        return chiefComplaint;
    }

    public String getPulseRate() {
        return pulseRate.get();
    }

    public void setPulseRate(String value) {
        pulseRate.set(value);
    }

    public StringProperty pulseRateProperty() {
        return pulseRate;
    }

    public String getRespiratoryRate() {
        return respiratoryRate.get();
    }

    public void setRespiratoryRate(String value) {
        respiratoryRate.set(value);
    }

    public StringProperty respiratoryRateProperty() {
        return respiratoryRate;
    }

    // Getters and Setters for Pregnancy Information
    public LocalDate getLastMenstrualPeriod() {
        return lastMenstrualPeriod.get();
    }

    public void setLastMenstrualPeriod(LocalDate value) {
        lastMenstrualPeriod.set(value);
    }

    public ObjectProperty<LocalDate> lastMenstrualPeriodProperty() {
        return lastMenstrualPeriod;
    }

    public LocalDate getExpectedDeliveryDate() {
        return expectedDeliveryDate.get();
    }

    public void setExpectedDeliveryDate(LocalDate value) {
        expectedDeliveryDate.set(value);
    }

    public ObjectProperty<LocalDate> expectedDeliveryDateProperty() {
        return expectedDeliveryDate;
    }

    public String getPara() {
        return para.get();
    }

    public void setPara(String value) {
        para.set(value);
    }

    public StringProperty paraProperty() {
        return para;
    }

    public String getAbortion() {
        return abortion.get();
    }

    public void setAbortion(String value) {
        abortion.set(value);
    }

    public StringProperty abortionProperty() {
        return abortion;
    }

    public String getLivingChildren() {
        return livingChildren.get();
    }

    public void setLivingChildren(String value) {
        livingChildren.set(value);
    }

    public StringProperty livingChildrenProperty() {
        return livingChildren;
    }

    // Getters and Setters for Medical Information
    public int getId() {
        return id.get();
    }

    public void setId(int value) {
        id.set(value);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public double getAgeOfGestation() {
        return ageOfGestation.get();
    }

    public void setAgeOfGestation(double value) {
        ageOfGestation.set(value);
    }

    public DoubleProperty ageOfGestationProperty() {
        return ageOfGestation;
    }

    public double getWeight() {
        return weight.get();
    }

    public void setWeight(double value) {
        weight.set(value);
    }

    public DoubleProperty weightProperty() {
        return weight;
    }

    public double getHeight() {
        return height.get();
    }

    public void setHeight(double value) {
        height.set(value);
    }

    public DoubleProperty heightProperty() {
        return height;
    }

    public int getFetalHeartTone() {
        return fetalHeartTone.get();
    }

    public void setFetalHeartTone(int value) {
        fetalHeartTone.set(value);
    }

    public IntegerProperty fetalHeartToneProperty() {
        return fetalHeartTone;
    }

    public String getPresentation() {
        return presentation.get();
    }

    public void setPresentation(String value) {
        presentation.set(value);
    }

    public StringProperty presentationProperty() {
        return presentation;
    }

    public double getFundalHeight() {
        return fundalHeight.get();
    }

    public void setFundalHeight(double value) {
        fundalHeight.set(value);
    }

    public DoubleProperty fundalHeightProperty() {
        return fundalHeight;
    }

    public LocalDate getNextAppointment() {
        return nextAppointment.get();
    }

    public void setNextAppointment(LocalDate value) {
        nextAppointment.set(value);
    }

    public ObjectProperty<LocalDate> nextAppointmentProperty() {
        return nextAppointment;
    }

    public List<ChildDetails> getChildDetails() {
        return childDetails;
    }

    public void setChildDetails(List<ChildDetails> details) {
        childDetails.clear();
        if (details != null) {
            childDetails.addAll(details);
        }
    }

    // Getters and Setters for Pregnancy History
    public List<PregnancyHistory> getPregnancyHistory() {
        return pregnancyHistory;
    }

    public void setPregnancyHistory(List<PregnancyHistory> history) {
        pregnancyHistory.clear();
        if (history != null) {
            pregnancyHistory.addAll(history);
        }
    }

    // Getters and Setters for Follow-up Vital Signs
    public List<VitalSignsEntry> getFollowUpVitalSigns() {
        return followUpVitalSigns;
    }

    public void setFollowUpVitalSigns(List<VitalSignsEntry> followUpVitalSigns) {
        this.followUpVitalSigns.clear();
        if (followUpVitalSigns != null) {
            this.followUpVitalSigns.addAll(followUpVitalSigns);
        }
    }

    // Returns the number of pregnancies (gravida) as a String
    public String getGravida() {
        return manualGravida != null ? manualGravida : String.valueOf(pregnancyHistory.size());
    }

    // Getters and Setters for Term and Preterm
    public String getTerm() {
        return term.get();
    }

    public void setTerm(String value) {
        term.set(value);
    }

    public StringProperty termProperty() {
        return term;
    }

    public String getPreterm() {
        return preterm.get();
    }

    public void setPreterm(String value) {
        preterm.set(value);
    }

    public StringProperty pretermProperty() {
        return preterm;
    }

    // Getter and Setter for High-Risk Status
    public boolean isHighRisk() {
        return highRisk;
    }

    public void setHighRisk(boolean highRisk) {
        this.highRisk = highRisk;
    }

    // Add DTO class for serialization
    public static class MaternalRecordDTO {
        public int id;
        public String patientId;
        public String fullName;
        // Backward compatibility fields
        public String lastName;
        public String firstName;
        public String middleName;
        public String address;
        public String purok;
        public String contactNumber;
        public String email;
        public String bloodPressure;
        public String chiefComplaint;
        // Backward compatibility fields
        public String pulseRate;
        public String respiratoryRate;
        public String para;
        public String abortion;
        public String livingChildren;
        public String presentation;
        public double ageOfGestation;
        public double weight;
        public double height;
        public int fetalHeartTone;
        public double fundalHeight;
        public String dateOfBirth;
        public String lastMenstrualPeriod;
        public String expectedDeliveryDate;
        public String nextAppointment;
        public List<ChildDetails> childDetails;
        public String husbandName;
        public String remarks;
        public String formTimestamp;
        public List<PregnancyHistory.PregnancyHistoryDTO> pregnancyHistory;
        public List<VitalSignsEntry> followUpVitalSigns;
        public String term;
        public String preterm;
        public boolean highRisk;
        public String barangayResidencyNumber;
        public String gravida;

        public MaternalRecordDTO() {
            this.childDetails = new ArrayList<>();
            this.pregnancyHistory = new ArrayList<>();
        }
    }

    public MaternalRecordDTO toDTO() {
        MaternalRecordDTO dto = new MaternalRecordDTO();
        dto.id = getId();
        dto.patientId = getPatientId();
        dto.fullName = getFullName();
        dto.address = getAddress();
        dto.purok = getPurok();
        dto.contactNumber = getContactNumber();
        dto.email = getEmail();
        dto.bloodPressure = getBloodPressure();
        dto.chiefComplaint = getChiefComplaint();
        dto.pulseRate = getPulseRate();
        dto.respiratoryRate = getRespiratoryRate();
        dto.para = getPara();
        dto.abortion = getAbortion();
        dto.livingChildren = getLivingChildren();
        dto.presentation = getPresentation();
        dto.ageOfGestation = getAgeOfGestation();
        dto.weight = getWeight();
        dto.height = getHeight();
        dto.fetalHeartTone = getFetalHeartTone();
        dto.fundalHeight = getFundalHeight();
        dto.dateOfBirth = getDateOfBirth() != null ? getDateOfBirth().toString() : null;
        dto.lastMenstrualPeriod = getLastMenstrualPeriod() != null ? getLastMenstrualPeriod().toString() : null;
        dto.expectedDeliveryDate = getExpectedDeliveryDate() != null ? getExpectedDeliveryDate().toString() : null;
        dto.nextAppointment = getNextAppointment() != null ? getNextAppointment().toString() : null;
        dto.childDetails = new ArrayList<>(childDetails);
        dto.husbandName = getHusbandName();
        dto.remarks = getRemarks();
        dto.formTimestamp = getFormTimestamp() != null ? getFormTimestamp().toString() : null;
        dto.pregnancyHistory = pregnancyHistory.stream()
                .map(PregnancyHistory::toDTO)
                .collect(Collectors.toList());
        dto.followUpVitalSigns = new ArrayList<>(followUpVitalSigns);
        dto.term = getTerm();
        dto.preterm = getPreterm();
        dto.highRisk = isHighRisk();
        dto.barangayResidencyNumber = getBarangayResidencyNumber();
        dto.gravida = getGravida();
        return dto;
    }

    public static MaternalRecord fromDTO(MaternalRecordDTO dto) {
        MaternalRecord record = new MaternalRecord();
        record.setId(dto.id);
        record.setPatientId(dto.patientId);

        // Handle migration from old field structure to new one
        if (dto.fullName != null && !dto.fullName.isEmpty()) {
            record.setFullName(dto.fullName);
        } else if (dto.lastName != null || dto.firstName != null || dto.middleName != null) {
            // Migrate from old structure: combine lastName, firstName, middleName into
            // fullName
            StringBuilder fullName = new StringBuilder();
            if (dto.lastName != null && !dto.lastName.isEmpty()) {
                fullName.append(dto.lastName);
            }
            if (dto.firstName != null && !dto.firstName.isEmpty()) {
                if (fullName.length() > 0)
                    fullName.append(" ");
                fullName.append(dto.firstName);
            }
            if (dto.middleName != null && !dto.middleName.isEmpty()) {
                if (fullName.length() > 0)
                    fullName.append(" ");
                fullName.append(dto.middleName);
            }
            record.setFullName(fullName.toString());
        }

        record.setAddress(dto.address);
        record.setPurok(dto.purok);
        record.setContactNumber(dto.contactNumber);
        record.setEmail(dto.email);
        record.setBloodPressure(dto.bloodPressure);
        record.setChiefComplaint(dto.chiefComplaint);
        record.setPulseRate(dto.pulseRate);
        record.setRespiratoryRate(dto.respiratoryRate);
        record.setPara(dto.para);
        record.setAbortion(dto.abortion);
        record.setLivingChildren(dto.livingChildren);
        record.setPresentation(dto.presentation);
        record.setAgeOfGestation(dto.ageOfGestation);
        record.setWeight(dto.weight);
        record.setHeight(dto.height);
        record.setFetalHeartTone(dto.fetalHeartTone);
        record.setFundalHeight(dto.fundalHeight);
        record.setDateOfBirth(dto.dateOfBirth != null ? LocalDate.parse(dto.dateOfBirth) : null);
        record.setLastMenstrualPeriod(
                dto.lastMenstrualPeriod != null ? LocalDate.parse(dto.lastMenstrualPeriod) : null);
        record.setExpectedDeliveryDate(
                dto.expectedDeliveryDate != null ? LocalDate.parse(dto.expectedDeliveryDate) : null);
        record.setNextAppointment(
                dto.nextAppointment != null ? LocalDate.parse(dto.nextAppointment) : null);
        record.setChildDetails(dto.childDetails != null ? dto.childDetails : new ArrayList<>());
        record.setHusbandName(dto.husbandName);
        record.setRemarks(dto.remarks);
        record.setFormTimestamp(
                dto.formTimestamp != null ? LocalDateTime.parse(dto.formTimestamp) : LocalDateTime.now());
        if (dto.pregnancyHistory != null) {
            record.pregnancyHistory.addAll(
                    dto.pregnancyHistory.stream().map(PregnancyHistory::fromDTO).collect(Collectors.toList()));
        }
        record.setFollowUpVitalSigns(dto.followUpVitalSigns);
        record.setTerm(dto.term);
        record.setPreterm(dto.preterm);
        record.setHighRisk(dto.highRisk);
        record.setBarangayResidencyNumber(dto.barangayResidencyNumber);
        record.setGravida(dto.gravida);
        return record;
    }

    public void loadRecord(MaternalRecord record) {
        // ... existing code ...
        lastMenstrualPeriod.set(record.getLastMenstrualPeriod());
        expectedDeliveryDate.set(record.getExpectedDeliveryDate());
        para.set(record.getPara());
        abortion.set(record.getAbortion());
        // ... existing code ...
    }

    public void saveRecord(MaternalRecord record) {
        // ... existing code ...
        record.setLastMenstrualPeriod(lastMenstrualPeriod.get());
        record.setExpectedDeliveryDate(expectedDeliveryDate.get());
        record.setPara(para.get());
        record.setAbortion(abortion.get());
        // ... existing code ...
    }

    // Add getter, setter, and property
    public String getBarangayResidencyNumber() {
        return barangayResidencyNumber.get();
    }

    public void setBarangayResidencyNumber(String value) {
        barangayResidencyNumber.set(value);
    }

    public StringProperty barangayResidencyNumberProperty() {
        return barangayResidencyNumber;
    }

    public void setGravida(String value) {
        if (value != null && !value.trim().isEmpty()) {
            manualGravida = value.trim();
        } else {
            manualGravida = null;
        }
    }

    public String getManualGravida() {
        return manualGravida;
    }
}