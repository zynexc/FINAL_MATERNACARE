package com.maternacare.controller;

import com.maternacare.model.MaternalRecord;
import com.maternacare.model.PregnancyHistory;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import javafx.collections.FXCollections;
import java.util.ArrayList;
import java.util.List;

public class MaternalRecordDetailsController {

        @FXML
        private Label formTimestampLabel;
        @FXML
        private Label patientIdLabel;
        @FXML
        private Label fullNameLabel;
        @FXML
        private Label dateOfBirthLabel;
        @FXML
        private Label patientAgeLabel;
        @FXML
        private Label husbandNameLabel;
        @FXML
        private Label addressLabel;
        @FXML
        private Label purokLabel;
        @FXML
        private Label contactNumberLabel;
        @FXML
        private Label emailLabel;
        @FXML
        private Label ageOfGestationLabel;
        @FXML
        private Label weightLabel;
        @FXML
        private Label heightLabel;
        @FXML
        private Label bloodPressureLabel;
        @FXML
        private Label chiefComplaintLabel;
        @FXML
        private Label fetalHeartToneLabel;
        @FXML
        private Label fundalHeightLabel;
        @FXML
        private Label presentationLabel;
        @FXML
        private Label lastMenstrualPeriodLabel;
        @FXML
        private Label expectedDeliveryDateLabel;
        @FXML
        private Label paraLabel;
        @FXML
        private Label abortionLabel;
        @FXML
        private Label livingChildrenLabel;
        @FXML
        private Label nextAppointmentLabel;
        @FXML
        private Label remarksLabel;

        @FXML
        private VBox pregnancyHistoryContainer;

        private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        private DateTimeFormatter timestampFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy 'at' hh:mm a");

        @FXML
        public void initialize() {
                // Initialize the pregnancy history container if it's null
                if (pregnancyHistoryContainer == null) {
                        pregnancyHistoryContainer = new VBox(10);
                        pregnancyHistoryContainer.getStyleClass().add("form-section");
                }
        }

        public void setRecord(MaternalRecord record) {
                if (record != null) {
                        formTimestampLabel.setText(
                                        record.getFormTimestamp() != null
                                                        ? record.getFormTimestamp().format(timestampFormatter)
                                                        : "N/A");
                        patientIdLabel.setText(record.getPatientId() != null ? record.getPatientId() : "N/A");
                        fullNameLabel.setText(record.getFullName() != null ? record.getFullName() : "N/A");
                        dateOfBirthLabel
                                        .setText(record.getDateOfBirth() != null
                                                        ? record.getDateOfBirth().format(dateFormatter)
                                                        : "N/A");
                        patientAgeLabel.setText(record.getDateOfBirth() != null
                                        ? String.valueOf(Period.between(record.getDateOfBirth(), LocalDate.now())
                                                        .getYears())
                                        : "N/A");
                        husbandNameLabel.setText(record.getHusbandName() != null ? record.getHusbandName() : "N/A");

                        addressLabel.setText(record.getAddress() != null ? record.getAddress() : "N/A");
                        purokLabel.setText(record.getPurok() != null ? record.getPurok() : "N/A");
                        contactNumberLabel
                                        .setText(record.getContactNumber() != null ? record.getContactNumber() : "N/A");
                        emailLabel.setText(record.getEmail() != null ? record.getEmail() : "N/A");

                        ageOfGestationLabel.setText(
                                        record.getAgeOfGestation() > 0.0
                                                        ? String.format("%.1f weeks", record.getAgeOfGestation())
                                                        : "N/A");
                        weightLabel.setText(record.getWeight() > 0.0 ? String.format("%.1f kg", record.getWeight())
                                        : "N/A");
                        heightLabel.setText(record.getHeight() > 0.0 ? String.format("%.1f cm", record.getHeight())
                                        : "N/A");
                        bloodPressureLabel
                                        .setText(record.getBloodPressure() != null ? record.getBloodPressure() : "N/A");
                        chiefComplaintLabel.setText(
                                        record.getChiefComplaint() != null ? record.getChiefComplaint() : "N/A");
                        fetalHeartToneLabel.setText(
                                        record.getFetalHeartTone() > 0 ? record.getFetalHeartTone() + " bpm" : "N/A");
                        fundalHeightLabel.setText(
                                        record.getFundalHeight() > 0.0
                                                        ? String.format("%.1f cm", record.getFundalHeight())
                                                        : "N/A");
                        presentationLabel.setText(record.getPresentation() != null ? record.getPresentation() : "N/A");

                        lastMenstrualPeriodLabel.setText(
                                        record.getLastMenstrualPeriod() != null
                                                        ? record.getLastMenstrualPeriod().format(dateFormatter)
                                                        : "N/A");
                        expectedDeliveryDateLabel.setText(
                                        record.getExpectedDeliveryDate() != null
                                                        ? record.getExpectedDeliveryDate().format(dateFormatter)
                                                        : "N/A");
                        paraLabel.setText(record.getPara());
                        abortionLabel.setText(record.getAbortion());
                        livingChildrenLabel.setText(record.getLivingChildren());
                        nextAppointmentLabel.setText(
                                        record.getNextAppointment() != null
                                                        ? record.getNextAppointment().format(dateFormatter)
                                                        : "N/A");
                        remarksLabel.setText(record.getRemarks() != null ? record.getRemarks() : "N/A");

                        // Load pregnancy history
                        loadPregnancyHistory(record);
                }
        }

        private void loadPregnancyHistory(MaternalRecord record) {
                // Clear existing pregnancy history
                if (pregnancyHistoryContainer == null) {
                        pregnancyHistoryContainer = new VBox(10);
                        pregnancyHistoryContainer.getStyleClass().add("form-section");
                }
                pregnancyHistoryContainer.getChildren().clear();

                // Show all pregnancy history entries with pregnancy number > 0
                List<PregnancyHistory> validHistory = new ArrayList<>();
                if (record.getPregnancyHistory() != null) {
                        System.out.println("Debug - Processing " + record.getPregnancyHistory().size()
                                        + " pregnancy history entries");
                        for (PregnancyHistory history : record.getPregnancyHistory()) {
                                // Accept any pregnancy history entry that has a pregnancy number > 0
                                if (history.getPregnancyNumber() > 0) {
                                        validHistory.add(history);
                                        System.out.println("Debug - Added pregnancy history #"
                                                        + history.getPregnancyNumber() + " to valid list");
                                } else {
                                        System.out.println(
                                                        "Debug - Skipping pregnancy history with number 0 or negative");
                                }
                        }
                }

                if (!validHistory.isEmpty()) {
                        for (PregnancyHistory history : validHistory) {
                                VBox historyBox = createPregnancyHistoryBox(history);
                                pregnancyHistoryContainer.getChildren().add(historyBox);
                        }
                } else {
                        Label noHistoryLabel = new Label("No pregnancy history recorded");
                        noHistoryLabel.getStyleClass().add("form-label");
                        pregnancyHistoryContainer.getChildren().add(noHistoryLabel);
                }
        }

        private VBox createPregnancyHistoryBox(PregnancyHistory history) {
                VBox box = new VBox(8);
                box.getStyleClass().add("history-box");
                box.setPadding(new Insets(12));
                box.setStyle("-fx-border-color: #e0e0e0; -fx-border-radius: 4; -fx-background-color: #fafafa;");

                // Title with pregnancy number
                Label titleLabel = new Label("Pregnancy #" + history.getPregnancyNumber());
                titleLabel.getStyleClass().add("history-title");
                titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #2c3e50;");

                // Create a simple grid for the details
                GridPane grid = new GridPane();
                grid.setHgap(15);
                grid.setVgap(8);
                grid.setPadding(new Insets(8, 0, 0, 0));

                // Add pregnancy history details in a compact format
                addSimpleHistoryDetail(grid, "Delivery:", history.getDeliveryType(), 0);
                addSimpleHistoryDetail(grid, "Gender:", history.getGender(), 1);
                addSimpleHistoryDetail(grid, "Place:", history.getPlaceOfDelivery(), 2);
                addSimpleHistoryDetail(grid, "Year:", String.valueOf(history.getYearDelivered()), 3);
                addSimpleHistoryDetail(grid, "Attended By:", history.getAttendedBy(), 4);
                addSimpleHistoryDetail(grid, "Status:", history.getStatus(), 5);
                addSimpleHistoryDetail(grid, "Birth Date:",
                                history.getBirthDate() != null ? history.getBirthDate().format(dateFormatter) : "N/A",
                                6);
                addSimpleHistoryDetail(grid, "TT Injection:", history.getTtInjection(), 7);

                box.getChildren().addAll(titleLabel, grid);
                return box;
        }

        private void addSimpleHistoryDetail(GridPane grid, String label, String value, int row) {
                Label labelNode = new Label(label);
                labelNode.setStyle("-fx-font-weight: bold; -fx-text-fill: #555; -fx-font-size: 11px;");

                Label valueNode = new Label(value != null && !value.trim().isEmpty() ? value : "N/A");
                valueNode.setStyle("-fx-text-fill: #333; -fx-font-size: 11px;");

                grid.add(labelNode, 0, row);
                grid.add(valueNode, 1, row);
        }
}