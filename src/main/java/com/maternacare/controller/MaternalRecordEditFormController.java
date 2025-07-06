package com.maternacare.controller;

import com.maternacare.model.MaternalRecord;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;

public class MaternalRecordEditFormController {
    @FXML
    private VBox rootPane;
    @FXML
    private Label formTimestampLabel;
    @FXML
    private TextField patientIdField;
    @FXML
    private TextField fullNameField;
    @FXML
    private DatePicker dateOfBirthPicker;
    @FXML
    private TextField ageField;
    @FXML
    private TextField husbandNameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField contactNumberField;
    @FXML
    private TextField emailField;
    @FXML
    private ComboBox<String> purokCombo;
    @FXML
    private DatePicker lastMenstrualPeriodPicker;
    @FXML
    private DatePicker expectedDeliveryDatePicker;
    @FXML
    private TextField gravidaField;
    @FXML
    private TextField pretermField;
    @FXML
    private TextField paraField;
    @FXML
    private TextField abortionField;
    @FXML
    private TextField termField;
    @FXML
    private TextField livingChildrenField;
    @FXML
    private TextField chiefComplaintField;
    @FXML
    private TextField heightField;
    @FXML
    private TextField ageOfGestationField;
    @FXML
    private TextField weightField;
    @FXML
    private TextField fetalHeartToneField;
    @FXML
    private TextField fundalHeightField;
    @FXML
    private TextField bloodPressureField;
    @FXML
    private ComboBox<String> presentationCombo;
    @FXML
    private DatePicker toComeBackPicker;
    @FXML
    private TextArea remarksField;
    @FXML
    private Label messageLabel;
    @FXML
    private TextField ageOfGestationWeeksField;
    @FXML
    private TextField ageOfGestationDaysField;
    @FXML
    private TextField pulseRateField;
    @FXML
    private TextField respiratoryRateField;
    @FXML
    private TextField barangayResidencyNumberField;
    @FXML
    private Label inlineNotificationLabel;
    private MaternalRecord currentRecord;
    private MaternalRecordsController recordsController;
    private java.util.List<javafx.scene.Node> originalContent;
    private VBox rootVBox;

    public void setRecordForEditing(MaternalRecord record) {
        this.currentRecord = record;
        if (record == null)
            return;
        patientIdField.setText(record.getPatientId());
        fullNameField.setText(record.getFullName());
        dateOfBirthPicker.setValue(record.getDateOfBirth());
        // Calculate and display age if date of birth is set
        if (record.getDateOfBirth() != null) {
            int age = java.time.Period.between(record.getDateOfBirth(), java.time.LocalDate.now()).getYears();
            ageField.setText(String.valueOf(age));
        } else {
            ageField.setText("");
        }
        husbandNameField.setText(record.getHusbandName());
        addressField.setText(record.getAddress());
        contactNumberField.setText(record.getContactNumber());
        emailField.setText(record.getEmail());
        purokCombo.setValue(record.getPurok());
        lastMenstrualPeriodPicker.setValue(record.getLastMenstrualPeriod());
        expectedDeliveryDatePicker.setValue(record.getExpectedDeliveryDate());
        gravidaField.setText(record.getGravida());
        pretermField.setText(record.getPreterm());
        termField.setText(record.getTerm());
        paraField.setText(record.getPara());
        abortionField.setText(record.getAbortion());
        livingChildrenField.setText(record.getLivingChildren());
        chiefComplaintField.setText(record.getChiefComplaint());
        heightField.setText(String.valueOf(record.getHeight()));
        ageOfGestationField.setText(String.valueOf(record.getAgeOfGestation()));
        weightField.setText(String.valueOf(record.getWeight()));
        fetalHeartToneField.setText(String.valueOf(record.getFetalHeartTone()));
        fundalHeightField.setText(String.valueOf(record.getFundalHeight()));
        bloodPressureField.setText(record.getBloodPressure() != null ? record.getBloodPressure() : "");
        presentationCombo.setValue(record.getPresentation());
        toComeBackPicker.setValue(record.getNextAppointment());
        remarksField.setText(record.getRemarks());
        pulseRateField.setText(record.getPulseRate() != null ? record.getPulseRate() : "");
        respiratoryRateField.setText(record.getRespiratoryRate() != null ? record.getRespiratoryRate() : "");
        barangayResidencyNumberField.setText(record.getBarangayResidencyNumber());
    }

    public void setRecordsController(MaternalRecordsController controller) {
        this.recordsController = controller;
    }

    public void setOriginalContent(java.util.List<javafx.scene.Node> originalContent) {
        this.originalContent = originalContent;
    }

    public void setRootVBox(VBox rootVBox) {
        this.rootVBox = rootVBox;
    }

    @FXML
    private void handleBack() {
        if (rootVBox != null && originalContent != null) {
            rootVBox.getChildren().setAll(originalContent);
        } else if (recordsController != null && currentRecord != null && originalContent != null) {
            recordsController.showRecordDetails(currentRecord, originalContent);
        }
    }

    @FXML
    private void handleSave() {
        if (currentRecord == null || recordsController == null) {
            showMessage("Unable to save: missing record or controller.", true);
            return;
        }
        // Update currentRecord with form values
        currentRecord.setPatientId(patientIdField.getText());
        currentRecord.setFullName(fullNameField.getText());
        currentRecord.setDateOfBirth(dateOfBirthPicker.getValue());
        currentRecord.setHusbandName(husbandNameField.getText());
        currentRecord.setAddress(addressField.getText());
        currentRecord.setContactNumber(contactNumberField.getText());
        currentRecord.setEmail(emailField.getText());
        currentRecord.setPurok(purokCombo != null ? purokCombo.getValue() : null);
        currentRecord.setLastMenstrualPeriod(lastMenstrualPeriodPicker.getValue());
        currentRecord.setExpectedDeliveryDate(expectedDeliveryDatePicker.getValue());
        currentRecord.setPara(paraField.getText());
        currentRecord.setAbortion(abortionField.getText());
        currentRecord.setLivingChildren(livingChildrenField.getText());
        currentRecord.setTerm(termField.getText());
        currentRecord.setPreterm(pretermField.getText());
        currentRecord.setChiefComplaint(chiefComplaintField.getText());
        currentRecord.setHeight(parseDouble(heightField.getText()));
        currentRecord.setAgeOfGestation(parseDouble(ageOfGestationField.getText()));
        currentRecord.setWeight(parseDouble(weightField.getText()));
        currentRecord.setFetalHeartTone(parseInt(fetalHeartToneField.getText()));
        currentRecord.setFundalHeight(parseDouble(fundalHeightField.getText()));
        currentRecord.setBloodPressure(bloodPressureField.getText() != null ? bloodPressureField.getText().trim() : "");
        currentRecord.setPresentation(presentationCombo != null ? presentationCombo.getValue() : null);
        currentRecord.setNextAppointment(toComeBackPicker.getValue());
        currentRecord.setRemarks(remarksField.getText());
        currentRecord.setPulseRate(pulseRateField.getText() != null ? pulseRateField.getText().trim() : "");
        currentRecord.setRespiratoryRate(
                respiratoryRateField.getText() != null ? respiratoryRateField.getText().trim() : "");
        currentRecord.setBarangayResidencyNumber(barangayResidencyNumberField.getText());
        currentRecord.setGravida(gravidaField.getText());

        // TODO: update pregnancy history and other fields as needed

        // Save the record
        recordsController.saveRecord(currentRecord);

        showMessage("Maternal record saved successfully!", false);

        // Go back to details page after a short delay
        new Thread(() -> {
            try {
                Thread.sleep(1200);
            } catch (InterruptedException ignored) {
            }
            javafx.application.Platform.runLater(this::handleBack);
        }).start();
    }

    private void showMessage(String message, boolean isError) {
        showInlineNotification(message, isError);
        if (messageLabel != null) {
            messageLabel.setText(message);
            messageLabel.setVisible(true);
            messageLabel.setManaged(true);
        }
    }

    private void showInlineNotification(String message, boolean isError) {
        if (inlineNotificationLabel != null) {
            inlineNotificationLabel.setText(message);
            inlineNotificationLabel.getStyleClass().removeAll("success", "error");
            inlineNotificationLabel.getStyleClass().add(isError ? "error" : "success");
            inlineNotificationLabel.setVisible(true);
            inlineNotificationLabel.setManaged(true);
            // Hide after 1.5 seconds
            new Thread(() -> {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException ignored) {
                }
                javafx.application.Platform.runLater(() -> {
                    inlineNotificationLabel.setVisible(false);
                    inlineNotificationLabel.setManaged(false);
                });
            }).start();
        }
    }

    // Helper methods for parsing
    private double parseDouble(String value) {
        try {
            if (value == null || value.trim().isEmpty()) {
                return 0.0;
            }
            String cleaned = value.replaceAll("[^\\d.]", "");
            return Double.parseDouble(cleaned);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private int parseInt(String value) {
        try {
            if (value == null || value.trim().isEmpty())
                return 0;
            return Integer.parseInt(value.replaceAll("[^\\d]", ""));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @FXML
    private void handleAddPregnancyHistory() {
        // TODO: Implement add pregnancy history logic
    }

    @FXML
    private void handleDeletePregnancyHistory() {
        // TODO: Implement delete pregnancy history logic
    }

    @FXML
    private void handleClear() {
        // Reset all fields to the current record's values (not blank)
        if (currentRecord != null) {
            setRecordForEditing(currentRecord);
        }
    }

    @FXML
    private void initialize() {
        rootPane.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.getStylesheets()
                        .add(getClass().getResource("/styles/maternal_record_edit_form.css").toExternalForm());
            }
        });

        // Initialize purok combo box
        if (purokCombo != null) {
            purokCombo.setItems(javafx.collections.FXCollections.observableArrayList(
                    "Purok 1", "Purok 2", "Purok 3", "Purok 4", "Purok 5", "Purok 6", "Purok 7", "Purok 8"));
        }

        // Add listener to last menstrual period to calculate expected delivery date and
        // AOG
        lastMenstrualPeriodPicker.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                // Add 280 days (40 weeks) to LMP to get EDD
                expectedDeliveryDatePicker.setValue(newVal.plusDays(280));
                updateAOGFields(newVal);
            }
        });
        // If LMP is already set (editing), update AOG
        if (lastMenstrualPeriodPicker.getValue() != null) {
            updateAOGFields(lastMenstrualPeriodPicker.getValue());
        }
        if (presentationCombo != null) {
            presentationCombo.setItems(javafx.collections.FXCollections.observableArrayList(
                    "Cephalic", "Breech", "Transverse", "Oblique", "No Information"));
        }
    }

    private void updateAOGFields(java.time.LocalDate lmp) {
        if (lmp == null) {
            ageOfGestationField.setText("");
            ageOfGestationWeeksField.setText("");
            ageOfGestationDaysField.setText("");
            return;
        }
        java.time.LocalDate today = java.time.LocalDate.now();
        long totalDays = java.time.temporal.ChronoUnit.DAYS.between(lmp, today);
        if (totalDays < 0)
            totalDays = 0;
        long weeks = totalDays / 7;
        long days = totalDays % 7;
        double weeksDecimal = totalDays / 7.0;
        ageOfGestationField.setText(weeks + " weeks and " + days + " days");
        ageOfGestationWeeksField.setText(String.format("%.1f", weeksDecimal));
        ageOfGestationDaysField.setText(String.valueOf(totalDays));
    }
}