package com.maternacare.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Separator;
import javafx.scene.Node;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import com.maternacare.model.MaternalRecord;
import com.maternacare.model.PregnancyHistory;
import com.maternacare.model.VitalSignsEntry;
import com.maternacare.service.MaternalRecordService;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.Parent;
import java.sql.SQLException;

public class MaternalRecordDetailsPageController {

    // Personal & Contact Info
    @FXML
    private Label patientIdLabel;
    @FXML
    private Label fullNameLabel;
    @FXML
    private Label dobLabel;
    @FXML
    private Label ageLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label purokLabel;
    @FXML
    private Label contactNumberLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label husbandNameLabel;
    @FXML
    private Label lmpLabel;
    @FXML
    private Label eddLabel;
    @FXML
    private Label barangayResidencyNumberLabel;

    // Vitals & Pregnancy Info
    @FXML
    private Label aogDisplayLabel;
    @FXML
    private Label aogWeeksLabel;
    @FXML
    private Label aogDaysLabel;
    @FXML
    private Label heightLabel;
    @FXML
    private Label weightLabel;
    @FXML
    private Label fhtLabel;
    @FXML
    private Label presentationLabel;
    @FXML
    private Label chiefComplaintLabel;
    @FXML
    private Label toComeBackLabel;
    @FXML
    private Label remarksLabel;
    @FXML
    private Label pulseRateLabel;
    @FXML
    private Label respiratoryRateLabel;
    @FXML
    private Label bloodPressureLabel;
    @FXML
    private Label fundalHeightLabel;

    // Stats
    @FXML
    private Label gravidaLabel;
    @FXML
    private Label paraLabel;
    @FXML
    private Label livingChildrenLabel;
    @FXML
    private Label abortionLabel;
    @FXML
    private Label termLabel;
    @FXML
    private Label pretermLabel;

    // Dynamic containers
    @FXML
    private VBox pregnancyHistoryContainer;
    @FXML
    private VBox followUpContainer;
    @FXML
    private VBox followUpFormContainer;
    @FXML
    private VBox detailsRoot;

    @FXML
    private Button editRecordButton;
    @FXML
    private Button deleteRecordButton;

    @FXML
    private HBox deleteConfirmBox;
    @FXML
    private Button confirmDeleteButton;
    @FXML
    private Button cancelDeleteButton;

    @FXML
    private Button backButton;

    @FXML
    private Button addHighRiskButton;

    @FXML
    private Button removeHighRiskButton;

    @FXML
    private Label statusMessageLabel;

    @FXML
    private Label inlineNotificationLabel;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");

    private Runnable onBackCallback;

    private MaternalRecord currentRecord;

    private VBox currentlyConfirmingDeleteCard = null;

    private MaternalRecordsController recordsController;
    private ObservableList<MaternalRecord> records;
    private MaternalRecordService maternalRecordService = new MaternalRecordService();

    private java.util.List<javafx.scene.Node> originalContent;

    private VBox rootVBox;

    public MaternalRecordDetailsPageController() {
    }

    public void setOriginalContent(java.util.List<javafx.scene.Node> originalContent) {
        this.originalContent = originalContent;
    }

    public void setOnBackCallback(Runnable callback) {
        this.onBackCallback = callback;
    }

    @FXML
    private void handleBack(ActionEvent event) {
        if (onBackCallback != null) {
            onBackCallback.run();
        }
    }

    @FXML
    private void handleBackDebug(ActionEvent event) {
        handleBack(event);
    }

    @FXML
    private void initialize() {
        detailsRoot.getStylesheets()
                .add(getClass().getResource("/styles/maternal_record_details.css").toExternalForm());
        if (editRecordButton != null) {
            editRecordButton.setOnAction(e -> handleEditRecord());
        }
        if (deleteRecordButton != null) {
            deleteRecordButton.setOnAction(e -> showDeleteConfirmation());
        }
        if (confirmDeleteButton != null) {
            confirmDeleteButton.setOnAction(e -> handleConfirmDelete());
        }
        if (cancelDeleteButton != null) {
            cancelDeleteButton.setOnAction(e -> hideDeleteConfirmation());
        }
        if (backButton != null) {
            backButton.setOnAction(e -> handleBack(null));
        }
        if (addHighRiskButton != null) {
            addHighRiskButton.setOnAction(e -> handleAddHighRiskPatient());
        }
        if (removeHighRiskButton != null) {
            removeHighRiskButton.setOnAction(e -> handleRemoveHighRiskPatient());
        }
        if (deleteConfirmBox != null) {
            deleteConfirmBox.setVisible(false);
            deleteConfirmBox.setManaged(false);
        }
    }

    public void setMaternalRecord(MaternalRecord record) {
        this.currentRecord = record;
        // Personal & Contact Info
        patientIdLabel.setText(record.getPatientId());
        fullNameLabel.setText(record.getFullName());
        dobLabel.setText(formatDate(record.getDateOfBirth()));
        ageLabel.setText(record.getDateOfBirth() != null
                ? String.valueOf(LocalDate.now().getYear() - record.getDateOfBirth().getYear())
                : "");
        addressLabel.setText(record.getAddress());
        purokLabel.setText(record.getPurok());
        contactNumberLabel.setText(record.getContactNumber());
        emailLabel.setText(record.getEmail());
        husbandNameLabel.setText(record.getHusbandName() != null ? record.getHusbandName() : "N/A");
        lmpLabel.setText(formatDate(record.getLastMenstrualPeriod()));
        eddLabel.setText(formatDate(record.getExpectedDeliveryDate()));
        if (barangayResidencyNumberLabel != null) {
            String brn = record.getBarangayResidencyNumber();
            barangayResidencyNumberLabel.setText(brn != null && !brn.isEmpty() ? brn : "N/A");
        }

        // Vitals & Pregnancy Info
        if (record.getLastMenstrualPeriod() != null) {
            java.time.LocalDate lmp = record.getLastMenstrualPeriod();
            java.time.LocalDate today = java.time.LocalDate.now();
            long totalDays = java.time.temporal.ChronoUnit.DAYS.between(lmp, today);
            if (totalDays < 0)
                totalDays = 0;
            long weeks = totalDays / 7;
            long days = totalDays % 7;
            double weeksDecimal = totalDays / 7.0;
            if (aogDisplayLabel != null)
                aogDisplayLabel.setText(weeks + " weeks and " + days + " days");
            if (aogWeeksLabel != null)
                aogWeeksLabel.setText(String.format("%.1f %s", weeksDecimal, weeksDecimal == 1.0 ? "week" : "weeks"));
            if (aogDaysLabel != null)
                aogDaysLabel.setText(totalDays + (totalDays == 1 ? " day" : " days"));
        } else {
            if (aogDisplayLabel != null)
                aogDisplayLabel.setText("");
            if (aogWeeksLabel != null)
                aogWeeksLabel.setText("");
            if (aogDaysLabel != null)
                aogDaysLabel.setText("");
        }
        heightLabel.setText(String.valueOf(record.getHeight()));
        weightLabel.setText(String.valueOf(record.getWeight()));
        fhtLabel.setText(String.valueOf(record.getFetalHeartTone()));
        presentationLabel.setText(record.getPresentation());
        if (chiefComplaintLabel != null) {
            String complaint = record.getChiefComplaint();
            chiefComplaintLabel.setText(complaint != null && !complaint.isEmpty() ? complaint : "N/A");
        }
        toComeBackLabel.setText(formatDate(record.getNextAppointment()));
        if (record.getRemarks() != null && !record.getRemarks().isEmpty()) {
            remarksLabel.setText("\n" + record.getRemarks());
        } else {
            remarksLabel.setText("");
        }
        if (pulseRateLabel != null) {
            String pulse = record.getPulseRate();
            pulseRateLabel.setText(pulse != null && !pulse.isEmpty() ? pulse : "N/A");
        }
        if (respiratoryRateLabel != null) {
            String resp = record.getRespiratoryRate();
            respiratoryRateLabel.setText(resp != null && !resp.isEmpty() ? resp : "N/A");
        }
        if (bloodPressureLabel != null) {
            String bp = record.getBloodPressure();
            bloodPressureLabel.setText(bp != null && !bp.isEmpty() ? bp : "N/A");
        }
        if (fundalHeightLabel != null) {
            double fh = record.getFundalHeight();
            fundalHeightLabel.setText(fh > 0.0 ? String.format("%.1f", fh) : "N/A");
        }

        // Stats
        gravidaLabel.setText(record.getGravida());
        paraLabel.setText(record.getPara());
        termLabel.setText(record.getTerm());
        pretermLabel.setText(record.getPreterm());
        abortionLabel.setText(record.getAbortion());
        livingChildrenLabel.setText(record.getLivingChildren());

        // Pregnancy History
        pregnancyHistoryContainer.getChildren().clear();
        int count = 1;
        for (PregnancyHistory history : record.getPregnancyHistory()) {
            pregnancyHistoryContainer.getChildren().add(createPregnancyHistoryCard(history, count++));
        }

        // Follow-Up Vital Signs
        followUpContainer.getChildren().clear();
        for (VitalSignsEntry entry : record.getFollowUpVitalSigns()) {
            followUpContainer.getChildren().add(createFollowUpCard(entry));
        }

        // Show/hide buttons based on high risk status
        if (addHighRiskButton != null) {
            addHighRiskButton.setVisible(!record.isHighRisk());
            addHighRiskButton.setManaged(!record.isHighRisk());
        }
        if (removeHighRiskButton != null) {
            removeHighRiskButton.setVisible(record.isHighRisk());
            removeHighRiskButton.setManaged(record.isHighRisk());
        }
    }

    private String formatDate(LocalDate date) {
        return date != null ? date.format(dateFormatter) : "";
    }

    private Node createPregnancyHistoryCard(PregnancyHistory history, int number) {
        VBox card = new VBox(8);
        card.getStyleClass().add("history-box");
        card.setPadding(new Insets(12));
        card.setStyle("-fx-border-color: #e0e0e0; -fx-border-radius: 4; -fx-background-color: #fff;");

        // Title with pregnancy number
        Label titleLabel = new Label("Patient #" + number);
        titleLabel.getStyleClass().add("history-title");
        titleLabel.setStyle(
                "-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: white; -fx-background-color: #eb0000; -fx-background-radius: 6; -fx-padding: 4 12;");

        // Details grid (single column, label and value per row)
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.getColumnConstraints().addAll(
                new ColumnConstraints(), new ColumnConstraints());
        int row = 0;
        grid.add(new Label("Delivery:"), 0, row);
        grid.add(new Label(history.getDeliveryType()), 1, row++);
        grid.add(new Label("Gender:"), 0, row);
        grid.add(new Label(history.getGender()), 1, row++);
        grid.add(new Label("Place:"), 0, row);
        grid.add(new Label(history.getPlaceOfDelivery()), 1, row++);
        grid.add(new Label("Year:"), 0, row);
        grid.add(new Label(String.valueOf(history.getYearDelivered())), 1, row++);
        grid.add(new Label("Attended By:"), 0, row);
        grid.add(new Label(history.getAttendedBy()), 1, row++);
        grid.add(new Label("Status:"), 0, row);
        grid.add(new Label(history.getStatus()), 1, row++);
        grid.add(new Label("Birth Date:"), 0, row);
        grid.add(new Label(formatDate(history.getBirthDate())), 1, row++);
        grid.add(new Label("TT Injection:"), 0, row);
        grid.add(new Label(history.getTtInjection()), 1, row++);

        card.getChildren().addAll(titleLabel, grid);
        return card;
    }

    private Node createFollowUpCard(VitalSignsEntry entry) {
        VBox card = new VBox(8);
        card.getStyleClass().add("followup-box");
        card.setPadding(new Insets(12));
        card.setStyle("-fx-border-color: #e0e0e0; -fx-border-radius: 4; -fx-background-color: #fafafa;");

        // Date label
        Label dateLabel = new Label(entry.getDate() != null ? entry.getDate().format(dateFormatter) : "N/A");
        dateLabel.getStyleClass().add("followup-date-label");
        dateLabel.setStyle(
                "-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: white; -fx-background-color: #eb0000; -fx-background-radius: 6; -fx-padding: 4 12;");

        // Only show the date label at the top
        card.getChildren().add(dateLabel);

        // Details grid (single column, label and value per row)
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.getColumnConstraints().addAll(
                new ColumnConstraints(), new ColumnConstraints());
        int row = 0;
        grid.add(new Label("Blood Pressure:"), 0, row);
        grid.add(new Label(entry.getBloodPressure()), 1, row++);
        grid.add(new Label("Pulse Rate:"), 0, row);
        grid.add(new Label(entry.getPulseRate()), 1, row++);
        grid.add(new Label("Respiratory Rate:"), 0, row);
        grid.add(new Label(entry.getRespiratoryRate()), 1, row++);
        grid.add(new Label("Age of Gestation:"), 0, row);
        grid.add(new Label(entry.getAog()), 1, row++);
        grid.add(new Label("Height (cm):"), 0, row);
        grid.add(new Label(entry.getHeight()), 1, row++);
        grid.add(new Label("Weight (lbs):"), 0, row);
        grid.add(new Label(entry.getWeight()), 1, row++);
        grid.add(new Label("Fetal Heart Tone:"), 0, row);
        grid.add(new Label(entry.getFht()), 1, row++);
        grid.add(new Label("Presentation:"), 0, row);
        grid.add(new Label(entry.getPresentation()), 1, row++);
        grid.add(new Label("Chief Complaint:"), 0, row);
        grid.add(new Label(entry.getChiefComplaint()), 1, row++);
        grid.add(new Label("To come Back:"), 0, row);
        grid.add(new Label(formatDate(entry.getToComeBack())), 1, row++);
        grid.add(new Label("Remarks:"), 0, row);
        grid.add(new Label(entry.getRemarks()), 1, row++);

        card.getChildren().add(grid);

        return card;
    }

    private void handleEditFollowUp(VitalSignsEntry entry) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/vital_signs_form.fxml"));
            Node form = loader.load();
            followUpFormContainer.getChildren().setAll(form);
            VitalSignsFormController controller = loader.getController();
            controller.setPatientName(currentRecord.getFullName());
            controller.setOnSaveCallback(updatedEntry -> {
                // Replace the old entry with the updated one
                int idx = currentRecord.getFollowUpVitalSigns().indexOf(entry);
                if (idx != -1) {
                    currentRecord.getFollowUpVitalSigns().set(idx, updatedEntry);
                    saveAndRefreshFollowUps();
                }
                followUpFormContainer.getChildren().clear();
            });
            controller.setOnBackCallback(() -> followUpFormContainer.getChildren().clear());
            // Pre-fill the form fields
            controller.prefillFields(entry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveAndRefreshFollowUps() {
        // Save the current record using the new individual record method
        if (currentRecord != null) {
            try {
                maternalRecordService.updateRecord(currentRecord);
            } catch (SQLException e) {
                e.printStackTrace();
                showInlineNotification("Failed to save changes: " + e.getMessage(), true);
            }
        }
        // Then refresh the followUpContainer
        followUpContainer.getChildren().clear();
        for (VitalSignsEntry entry : currentRecord.getFollowUpVitalSigns()) {
            followUpContainer.getChildren().add(createFollowUpCard(entry));
        }
    }

    @FXML
    private void handleFollowUp() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/vital_signs_form.fxml"));
            Node form = loader.load();
            followUpFormContainer.getChildren().setAll(form);

            // Set up callback to clear form after save
            VitalSignsFormController controller = loader.getController();
            controller.setOnSaveCallback(entry -> {
                // Add entry to followUpContainer (optional: update list)
                followUpFormContainer.getChildren().clear();
                // Optionally, refresh followUpContainer here
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setRecordsController(MaternalRecordsController controller, ObservableList<MaternalRecord> records) {
        this.recordsController = controller;
        this.records = records;
    }

    private void handleEditRecord() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/maternal_record_edit_form.fxml"));
            VBox editFormRoot = loader.load();
            com.maternacare.controller.MaternalRecordEditFormController editController = loader.getController();
            editController.setRecordForEditing(currentRecord);
            editController.setRecordsController(recordsController);
            editController.setOriginalContent(originalContent);
            editController.setRootVBox(rootVBox);
            if (rootVBox != null) {
                rootVBox.getChildren().setAll(editFormRoot);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Add a public method to allow navigation back to details with originalContent
    public void showDetailsWithOriginalContent(MaternalRecord record) {
        if (recordsController != null) {
            recordsController.showRecordDetails(record, originalContent);
        }
    }

    private void showDeleteConfirmation() {
        if (deleteConfirmBox != null) {
            deleteConfirmBox.setVisible(true);
            deleteConfirmBox.setManaged(true);
        }
    }

    private void hideDeleteConfirmation() {
        if (deleteConfirmBox != null) {
            deleteConfirmBox.setVisible(false);
            deleteConfirmBox.setManaged(false);
        }
    }

    private void handleConfirmDelete() {
        if (recordsController != null && currentRecord != null && records != null) {
            recordsController.deleteRecord(currentRecord);
            if (onBackCallback != null) {
                onBackCallback.run();
            }
        }
    }

    private void handleDeleteRecord() {
        showDeleteConfirmation();
    }

    private void showInlineNotification(String message, boolean isError) {
        if (inlineNotificationLabel != null) {
            inlineNotificationLabel.setText(message);
            inlineNotificationLabel.setVisible(true);
            inlineNotificationLabel.setManaged(true);
            if (isError) {
                inlineNotificationLabel.setStyle("-fx-text-fill: #eb0000;");
            } else {
                inlineNotificationLabel.setStyle("-fx-text-fill: #28a745;");
            }
            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {
                }
                javafx.application.Platform.runLater(() -> {
                    inlineNotificationLabel.setVisible(false);
                    inlineNotificationLabel.setManaged(false);
                });
            }).start();
        }
    }

    private void handleAddHighRiskPatient() {
        if (currentRecord != null) {
            currentRecord.setHighRisk(true);
            if (recordsController != null) {
                recordsController.saveRecord(currentRecord);
            }
            showInlineNotification("Patient added to High-Risk Patient Records.", false);
            if (statusMessageLabel != null) {
                statusMessageLabel.setText("");
                statusMessageLabel.setVisible(false);
                statusMessageLabel.setManaged(false);
            }
            // Update button visibility
            if (addHighRiskButton != null) {
                addHighRiskButton.setVisible(false);
                addHighRiskButton.setManaged(false);
            }
            if (removeHighRiskButton != null) {
                removeHighRiskButton.setVisible(true);
                removeHighRiskButton.setManaged(true);
            }
        }
    }

    private void handleRemoveHighRiskPatient() {
        if (currentRecord != null) {
            currentRecord.setHighRisk(false);
            if (recordsController != null) {
                recordsController.saveRecord(currentRecord);
            }
            showInlineNotification("This has been removed from the high risk patient.", false);
            if (statusMessageLabel != null) {
                statusMessageLabel.setText("");
                statusMessageLabel.setVisible(false);
                statusMessageLabel.setManaged(false);
            }
            // Update button visibility
            if (addHighRiskButton != null) {
                addHighRiskButton.setVisible(true);
                addHighRiskButton.setManaged(true);
            }
            if (removeHighRiskButton != null) {
                removeHighRiskButton.setVisible(false);
                removeHighRiskButton.setManaged(false);
            }
        }
    }

    public void setRootVBox(VBox rootVBox) {
        this.rootVBox = rootVBox;
    }
}