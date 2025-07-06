package com.maternacare.controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.fxml.FXML;
import com.maternacare.model.MaternalRecord;
import com.maternacare.model.PregnancyHistory;
import com.maternacare.model.VitalSignsEntry;
import javafx.scene.control.Alert.AlertType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.application.Platform;
import javafx.stage.Modality;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.maternacare.model.ChildDetails;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.util.converter.LocalDateStringConverter;
import javafx.scene.control.TableCell;
import javafx.scene.control.ContentDisplay;
import javafx.collections.ListChangeListener;
import java.time.temporal.ChronoUnit;

public class MaternalFormController {

    @FXML
    private VBox rootPane; // Assuming a VBox or similar as the root in maternal_form.fxml

    // Timestamp display
    @FXML
    private Label formTimestampLabel;
    private final DateTimeFormatter timestampFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy 'at' hh:mm a");

    // Personal Information Fields
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

    // Contact Information Fields
    @FXML
    private TextField addressField;
    @FXML
    private TextField contactNumberField;
    @FXML
    private TextField emailField;
    @FXML
    private ComboBox<String> purokCombo;

    // Barangay Residency Number Field
    @FXML
    private TextField barangayResidencyField;

    // Vital Signs Fields
    @FXML
    private TextField ageOfGestationField;
    @FXML
    private TextField weightField;
    @FXML
    private TextField heightField;
    @FXML
    private TextField bloodPressureField;
    @FXML
    private TextField fetalHeartToneField;
    @FXML
    private TextField fundalHeightField;
    @FXML
    private ComboBox<String> presentationCombo;
    @FXML
    private DatePicker toComeBackPicker;
    @FXML
    private TextField chiefComplaintField;

    // Remarks field
    @FXML
    private TextArea remarksField;

    // Pregnancy Information Fields
    @FXML
    private DatePicker lastMenstrualPeriodPicker;
    @FXML
    private DatePicker expectedDeliveryDatePicker;
    @FXML
    private TextField paraField;
    @FXML
    private TextField abortionField;
    @FXML
    private TextField livingChildrenField;

    @FXML
    private VBox pregnancyHistoryContainer;

    @FXML
    private VBox pregnancyHistoryTableInclude;

    @FXML
    private PregnancyHistoryTableController pregnancyHistoryTableController;

    private MaternalRecordsController recordsController;
    private MaternalRecord currentRecord;
    private List<ChildDetailsController> childDetailsControllers = new ArrayList<>();

    // Add new fields for pregnancy history as normal input fields
    @FXML
    private TextField phDeliveryTypeField;
    @FXML
    private TextField phGenderField;
    @FXML
    private TextField phPlaceField;
    @FXML
    private TextField phYearField;
    @FXML
    private TextField phAttendedByField;
    @FXML
    private TextField phStatusField;
    @FXML
    private DatePicker phBirthDatePicker;
    @FXML
    private TextField phTTInjectionField;

    @FXML
    private TableView<PregnancyHistory> pregnancyHistoryTableView;
    @FXML
    private TableColumn<PregnancyHistory, Number> phNumberColumn;
    @FXML
    private TableColumn<PregnancyHistory, String> phDeliveryTypeColumn;
    @FXML
    private TableColumn<PregnancyHistory, String> phGenderColumn;
    @FXML
    private TableColumn<PregnancyHistory, String> phPlaceColumn;
    @FXML
    private TableColumn<PregnancyHistory, Integer> phYearColumn;
    @FXML
    private TableColumn<PregnancyHistory, String> phAttendedByColumn;
    @FXML
    private TableColumn<PregnancyHistory, String> phStatusColumn;
    @FXML
    private TableColumn<PregnancyHistory, LocalDate> phBirthDateColumn;
    @FXML
    private TableColumn<PregnancyHistory, String> phTTInjectionColumn;

    private ObservableList<PregnancyHistory> pregnancyHistoryList = FXCollections.observableArrayList();

    @FXML
    private TextField gravidaField;

    @FXML
    private TextField termField;
    @FXML
    private TextField pretermField;

    @FXML
    private Label inlineNotificationLabel;

    @FXML
    private TextField ageOfGestationWeeksField;
    @FXML
    private TextField ageOfGestationDaysField;

    @FXML
    private TextField pulseRateField;
    @FXML
    private TextField respiratoryRateField;

    @FXML
    public void initialize() {
        // Explicitly load Poppins Bold font for JavaFX
        Font.loadFont(getClass().getResourceAsStream("/fonts/Poppins-Bold.ttf"), 12);
        System.out.println("MaternalFormController.initialize() called");

        // Debug CSS application
        System.out.println("Checking CSS application...");
        if (rootPane != null) {
            System.out.println("Root pane found: " + rootPane.getClass().getSimpleName());
            System.out.println("Root pane style classes: " + rootPane.getStyleClass());
            System.out.println("Root pane stylesheets: " + rootPane.getStylesheets());
            System.out.println("Root pane background: " + rootPane.getBackground());
        } else {
            System.out.println("Root pane is null!");
        }

        try {
            // Initialize pregnancy history container if null
            if (pregnancyHistoryContainer == null) {
                pregnancyHistoryContainer = new VBox(10);
                pregnancyHistoryContainer.getStyleClass().add("form-section");
            }

            // Initialize pregnancy history list with change listener
            pregnancyHistoryList.addListener((ListChangeListener<PregnancyHistory>) change -> {
                while (change.next()) {
                    if (change.wasAdded() || change.wasRemoved()) {
                        System.out.println("Debug - Pregnancy history list changed. Total entries: " + pregnancyHistoryList.size());
                        updatePregnancyHistoryTableView();
                    }
                }
            });

            System.out.println("Updating timestamp...");
            updateTimestamp();
            System.out.println("Timestamp updated.");

            System.out.println("Initializing combo boxes...");
            // Initialize combo boxes
            purokCombo.setItems(FXCollections.observableArrayList("Purok 1", "Purok 2", "Purok 3", "Purok 4", "Purok 5",
                    "Purok 6"));
            presentationCombo
                    .setItems(FXCollections.observableArrayList("Cephalic", "Breech", "Transverse", "Oblique",
                            "No Information"));
            System.out.println("Combo boxes initialized.");

            System.out.println("Adding tooltips...");
            // Add tooltips
            addTooltips();
            System.out.println("Tooltips added.");

            System.out.println("Marking required fields...");
            // Mark required fields
            markRequiredFields();
            System.out.println("Required fields marked.");

            System.out.println("Setting up date pickers...");
            // Set up date pickers
            setupDatePickers();
            System.out.println("Date pickers set up.");

            System.out.println("Setting up pregnancy history table...");
            // Set up pregnancy history table - will be initialized after scene is loaded
            Platform.runLater(this::initializePregnancyHistoryTableView);
            System.out.println("Pregnancy history table setup scheduled.");

            // Generate and set a new patient ID
            patientIdField.setEditable(false);

            System.out.println("MaternalFormController initialization completed successfully.");

            ageOfGestationField.setEditable(false);
            ageOfGestationWeeksField.setEditable(false);
            ageOfGestationDaysField.setEditable(false);
            lastMenstrualPeriodPicker.valueProperty().addListener((obs, oldDate, newDate) -> {
                updateAOGFields(newDate);
            });
            // If LMP is already set (editing), update AOG
            if (lastMenstrualPeriodPicker.getValue() != null) {
                updateAOGFields(lastMenstrualPeriodPicker.getValue());
            }
        } catch (Exception e) {
            System.err.println("Error during MaternalFormController initialization: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void initializePregnancyHistoryTableView() {
        // Set up columns
        phNumberColumn.setCellValueFactory(cellData -> cellData.getValue().pregnancyNumberProperty());
        phDeliveryTypeColumn.setCellValueFactory(cellData -> cellData.getValue().deliveryTypeProperty());
        phGenderColumn.setCellValueFactory(cellData -> cellData.getValue().genderProperty());
        phPlaceColumn.setCellValueFactory(cellData -> cellData.getValue().placeOfDeliveryProperty());
        phYearColumn.setCellValueFactory(cellData -> cellData.getValue().yearDeliveredProperty().asObject());
        phAttendedByColumn.setCellValueFactory(cellData -> cellData.getValue().attendedByProperty());
        phStatusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        phBirthDateColumn.setCellValueFactory(cellData -> cellData.getValue().birthDateProperty());
        phTTInjectionColumn.setCellValueFactory(cellData -> cellData.getValue().ttInjectionProperty());
        pregnancyHistoryTableView.setItems(pregnancyHistoryList);

        // Make columns not sortable and not movable
        for (TableColumn<?, ?> col : pregnancyHistoryTableView.getColumns()) {
            col.setSortable(false);
            col.setReorderable(false);
        }

        // Configure column widths for better content display
        phNumberColumn.setResizable(true);
        phNumberColumn.setPrefWidth(100);
        phNumberColumn.setMinWidth(80);
        phNumberColumn.setMaxWidth(120);
        phNumberColumn.setStyle("-fx-alignment: CENTER;");

        phDeliveryTypeColumn.setResizable(true);
        phDeliveryTypeColumn.setPrefWidth(140);
        phDeliveryTypeColumn.setMinWidth(120);
        phDeliveryTypeColumn.setMaxWidth(180);
        phDeliveryTypeColumn.setStyle("-fx-alignment: CENTER;");

        phGenderColumn.setResizable(true);
        phGenderColumn.setPrefWidth(100);
        phGenderColumn.setMinWidth(80);
        phGenderColumn.setMaxWidth(120);
        phGenderColumn.setStyle("-fx-alignment: CENTER;");

        phPlaceColumn.setResizable(true);
        phPlaceColumn.setPrefWidth(160);
        phPlaceColumn.setMinWidth(140);
        phPlaceColumn.setMaxWidth(200);
        phPlaceColumn.setStyle("-fx-alignment: CENTER;");

        phYearColumn.setResizable(true);
        phYearColumn.setPrefWidth(120);
        phYearColumn.setMinWidth(100);
        phYearColumn.setMaxWidth(140);
        phYearColumn.setStyle("-fx-alignment: CENTER;");

        phAttendedByColumn.setResizable(true);
        phAttendedByColumn.setPrefWidth(140);
        phAttendedByColumn.setMinWidth(120);
        phAttendedByColumn.setMaxWidth(180);
        phAttendedByColumn.setStyle("-fx-alignment: CENTER;");

        phStatusColumn.setResizable(true);
        phStatusColumn.setPrefWidth(100);
        phStatusColumn.setMinWidth(80);
        phStatusColumn.setMaxWidth(120);
        phStatusColumn.setStyle("-fx-alignment: CENTER;");

        phBirthDateColumn.setResizable(true);
        phBirthDateColumn.setPrefWidth(120);
        phBirthDateColumn.setMinWidth(100);
        phBirthDateColumn.setMaxWidth(140);
        phBirthDateColumn.setStyle("-fx-alignment: CENTER;");

        phTTInjectionColumn.setResizable(true);
        phTTInjectionColumn.setPrefWidth(180);
        phTTInjectionColumn.setMinWidth(160);
        phTTInjectionColumn.setMaxWidth(220);
        phTTInjectionColumn.setStyle("-fx-alignment: CENTER;");

        // Set table size properties
        pregnancyHistoryTableView.setPrefWidth(1200);
        pregnancyHistoryTableView.setMaxWidth(Double.MAX_VALUE);
        pregnancyHistoryTableView.setMinWidth(1000);
        pregnancyHistoryTableView.setFixedCellSize(45);

        // Make columns auto-resize to fill available space
        pregnancyHistoryTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Enable editing
        pregnancyHistoryTableView.setEditable(true);
        phDeliveryTypeColumn.setCellFactory(
                ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList("Normal", "Caesarian", "Abortion")));
        phGenderColumn
                .setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList("Male", "Female")));
        phStatusColumn.setCellFactory(
                ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList("Alive", "Not Alive")));
        phPlaceColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        phYearColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        phAttendedByColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        phTTInjectionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        phBirthDateColumn.setCellFactory(column -> new TableCell<PregnancyHistory, LocalDate>() {
            private final DatePicker datePicker = new DatePicker();
            {
                datePicker.setOnAction(event -> {
                    if (getTableRow() != null && getTableRow().getItem() != null) {
                        commitEdit(datePicker.getValue());
                    }
                });
                datePicker.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
                    if (!isNowFocused) {
                        commitEdit(datePicker.getValue());
                    }
                });
            }

            @Override
            public void startEdit() {
                super.startEdit();
                setGraphic(datePicker);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                datePicker.setValue(getItem());
                datePicker.requestFocus();
            }

            @Override
            public void cancelEdit() {
                super.cancelEdit();
                setText(getItem() != null ? getItem().toString() : "");
                setContentDisplay(ContentDisplay.TEXT_ONLY);
                setGraphic(null);
            }

            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else if (isEditing()) {
                    setGraphic(datePicker);
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    datePicker.setValue(item);
                } else {
                    setText(item != null ? item.toString() : "");
                    setContentDisplay(ContentDisplay.TEXT_ONLY);
                    setGraphic(null);
                }
            }
        });

        // Commit handlers with autosave
        phDeliveryTypeColumn.setOnEditCommit(event -> {
            event.getRowValue().setDeliveryType(event.getNewValue());
            if ("Abortion".equals(event.getNewValue())) {
                event.getRowValue().setGender("N/A");
                event.getRowValue().setPlaceOfDelivery("N/A");
                event.getRowValue().setYearDelivered(0);
                event.getRowValue().setAttendedBy("N/A");
                event.getRowValue().setStatus("N/A");
                event.getRowValue().setBirthDate(null);
                event.getRowValue().setTtInjection("N/A");
                pregnancyHistoryTableView.refresh();
            }
            savePregnancyHistoryOnly();
        });
        phGenderColumn.setOnEditCommit(event -> {
            event.getRowValue().setGender(event.getNewValue());
            savePregnancyHistoryOnly();
        });
        phPlaceColumn.setOnEditCommit(event -> {
            event.getRowValue().setPlaceOfDelivery(event.getNewValue());
            savePregnancyHistoryOnly();
        });
        phYearColumn.setOnEditCommit(event -> {
            try {
                event.getRowValue().setYearDelivered(event.getNewValue().intValue());
                savePregnancyHistoryOnly();
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter a valid year.");
            }
        });
        phAttendedByColumn.setOnEditCommit(event -> {
            event.getRowValue().setAttendedBy(event.getNewValue());
            savePregnancyHistoryOnly();
        });
        phStatusColumn.setOnEditCommit(event -> {
            event.getRowValue().setStatus(event.getNewValue());
            savePregnancyHistoryOnly();
        });
        phTTInjectionColumn.setOnEditCommit(event -> {
            event.getRowValue().setTtInjection(event.getNewValue());
            savePregnancyHistoryOnly();
        });
        phBirthDateColumn.setOnEditCommit(event -> {
            event.getRowValue().setBirthDate(event.getNewValue());
            savePregnancyHistoryOnly();
        });

        // Commit edit on focus lost for all editable columns
        for (TableColumn<PregnancyHistory, ?> col : pregnancyHistoryTableView.getColumns()) {
            col.setOnEditCancel(event -> pregnancyHistoryTableView.edit(-1, null));
        }

        // Set column headers
        phNumberColumn.setText("GRAVIDA");
        phDeliveryTypeColumn.setText("Type of Delivery");
        phPlaceColumn.setText("Place of Delivery");
        phYearColumn.setText("Year Delivered");
        phTTInjectionColumn.setText("TT Injection Status With Year");
    }

    private void addTooltips() {
        // Personal Information
        patientIdField.setTooltip(new Tooltip("Enter the patient's unique identifier"));
        fullNameField.setTooltip(new Tooltip("Enter the patient's full name"));
        dateOfBirthPicker.setTooltip(new Tooltip("Select the patient's date of birth"));
        husbandNameField.setTooltip(new Tooltip("Enter husband's full name"));

        // Contact Information
        addressField.setTooltip(new Tooltip("Enter the patient's complete address"));
        contactNumberField.setTooltip(new Tooltip("Enter the patient's contact number"));
        emailField.setTooltip(new Tooltip("Enter the patient's email address (optional)"));
        purokCombo.setTooltip(new Tooltip("Select the patient's purok"));

        // Vital Signs
        ageOfGestationField.setTooltip(new Tooltip("Enter age of gestation in weeks (e.g., 28wks)"));
        weightField.setTooltip(new Tooltip("Enter weight in pounds"));
        heightField.setTooltip(new Tooltip("Enter height in centimeters"));
        bloodPressureField.setTooltip(new Tooltip("Enter blood pressure in format: 120/80"));
        fetalHeartToneField.setTooltip(new Tooltip("Enter fetal heart tone in beats per minute"));
        fundalHeightField.setTooltip(new Tooltip("Enter fundal height in centimeters"));
        presentationCombo.setTooltip(new Tooltip("Select the position of the baby"));
        toComeBackPicker.setTooltip(new Tooltip("Select the next appointment date"));
        chiefComplaintField.setTooltip(new Tooltip("Enter chief complaint"));

        // Pregnancy Information
        lastMenstrualPeriodPicker.setTooltip(new Tooltip("Select the first day of last menstrual period"));
        expectedDeliveryDatePicker.setTooltip(new Tooltip("Expected delivery date (auto-calculated from LMP)"));
        paraField.setTooltip(new Tooltip("Number of live births"));
        abortionField.setTooltip(new Tooltip("Number of abortions/miscarriages"));
        livingChildrenField.setTooltip(new Tooltip("Number of living children"));

        // Add tooltips for new fields
        remarksField.setTooltip(new Tooltip("Enter any additional remarks or notes"));
    }

    private void markRequiredFields() {
        // Only set prompt text, do not add 'required-field' style class
        addRequiredIndicator(patientIdField, "Patient ID *");
        addRequiredIndicator(fullNameField, "Enter Full Name *");
        addRequiredIndicator(dateOfBirthPicker, "Select Date of Birth *");
        addRequiredIndicator(addressField, "Enter Address *");
        addRequiredIndicator(purokCombo, "Select Purok *");
        addRequiredIndicator(contactNumberField, "Enter Contact Number *");
        addRequiredIndicator(bloodPressureField, "Enter Blood Pressure *");
        addRequiredIndicator(lastMenstrualPeriodPicker, "Select Last Menstrual Period *");
    }

    private void addRequiredIndicator(Control control, String labelText) {
        if (control instanceof TextField) {
            ((TextField) control).setPromptText(labelText);
        } else if (control instanceof DatePicker) {
            ((DatePicker) control).setPromptText(labelText);
        } else if (control instanceof ComboBox) {
            ((ComboBox<?>) control).setPromptText(labelText);
        }
        // Do not add 'required-field' style class by default
    }

    private void setupValidation() {
        // Add validation for numeric fields
        addNumericValidation(chiefComplaintField);
        addNumericValidation(paraField);
        addNumericValidation(abortionField);
        addNumericValidation(livingChildrenField);

        // Add validation for blood pressure format (e.g., "120/80" or "120-80")
        bloodPressureField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.isEmpty() && !newVal.matches("\\d+/\\d+")) {
                bloodPressureField.setStyle("-fx-border-color: #dc3545;");
            } else {
                bloodPressureField.setStyle("");
            }
        });

        // Add validation for contact number (allow + and spaces)
        contactNumberField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.isEmpty() && !newVal.matches("[+]?[\\d\\s-]+")) {
                contactNumberField.setStyle("-fx-border-color: #dc3545;");
                contactNumberField.setTooltip(new Tooltip("Enter a valid phone number (e.g., +63 912 345 6789)"));
            } else {
                contactNumberField.setStyle("");
                contactNumberField.setTooltip(new Tooltip("Enter a valid phone number (e.g., +63 912 345 6789)"));
            }
        });

        // Add validation for email
        emailField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.isEmpty() && !newVal.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                emailField.setStyle("-fx-border-color: #dc3545;");
                emailField.setTooltip(new Tooltip("Enter a valid email address (e.g., name@example.com)"));
            } else {
                emailField.setStyle("");
                emailField.setTooltip(new Tooltip("Enter a valid email address (e.g., name@example.com)"));
            }
        });

        // Add validation for vital signs
        ageOfGestationField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d+wks?")) {
                ageOfGestationField.setStyle("-fx-border-color: #dc3545;");
            } else {
                ageOfGestationField.setStyle("");
            }
        });

        weightField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d+(\\.\\d+)?")) {
                weightField.setStyle("-fx-border-color: #dc3545;");
            } else {
                weightField.setStyle("");
            }
        });

        fetalHeartToneField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d+")) {
                fetalHeartToneField.setStyle("-fx-border-color: #dc3545;");
            } else {
                fetalHeartToneField.setStyle("");
            }
        });

        fundalHeightField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d+(\\.\\d+)?")) {
                fundalHeightField.setStyle("-fx-border-color: #dc3545;");
            } else {
                fundalHeightField.setStyle("");
            }
        });
    }

    private void addNumericValidation(TextField field) {
        field.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*")) {
                field.setText(oldVal);
            }
        });
    }

    public void setRecordsController(MaternalRecordsController controller) {
        this.recordsController = controller;
        generateAndSetPatientId();
    }

    public void editRecord(MaternalRecord record) {
        this.currentRecord = record; // Store the current record being edited
        // Populate fields with record data
        patientIdField.setText(record.getPatientId());
        fullNameField.setText(record.getFullName());
        dateOfBirthPicker.setValue(record.getDateOfBirth());
        husbandNameField.setText(record.getHusbandName());
        addressField.setText(record.getAddress());
        contactNumberField.setText(record.getContactNumber());
        emailField.setText(record.getEmail());
        purokCombo.setValue(record.getPurok());
        ageOfGestationField.setText(String.valueOf(record.getAgeOfGestation()));
        weightField.setText(String.valueOf(record.getWeight()));
        heightField.setText(String.valueOf(record.getHeight()));
        bloodPressureField.setText(record.getBloodPressure());
        fetalHeartToneField.setText(String.valueOf(record.getFetalHeartTone()));
        fundalHeightField.setText(String.valueOf(record.getFundalHeight()));
        presentationCombo.setValue(record.getPresentation());
        toComeBackPicker.setValue(record.getNextAppointment());
        chiefComplaintField.setText(record.getChiefComplaint());
        remarksField.setText(record.getRemarks());
        barangayResidencyField.setText(record.getBarangayResidencyNumber());

        lastMenstrualPeriodPicker.setValue(record.getLastMenstrualPeriod());
        expectedDeliveryDatePicker.setValue(record.getExpectedDeliveryDate());
        paraField.setText(record.getPara());
        abortionField.setText(record.getAbortion());
        livingChildrenField.setText(record.getLivingChildren());
        termField.setText(record.getTerm());
        pretermField.setText(record.getPreterm());
        gravidaField.setText(record.getGravida());

        pulseRateField.setText(record.getPulseRate());
        respiratoryRateField.setText(record.getRespiratoryRate());

        // Update child details forms based on pregnancy history size
        if (record.getPregnancyHistory() != null) {
            updateChildDetailsForms(record.getPregnancyHistory().size());
        } else {
            updateChildDetailsForms(0);
        }

        if (record.getChildDetails() != null) {
            for (int i = 0; i < record.getChildDetails().size() && i < childDetailsControllers.size(); i++) {
                childDetailsControllers.get(i).setChildDetails(record.getChildDetails().get(i));
            }
        }

        // Load pregnancy history into the list
        pregnancyHistoryList.clear();
        if (record.getPregnancyHistory() != null) {
            System.out.println("Debug - Loading pregnancy history: " + record.getPregnancyHistory().size() + " entries");
            pregnancyHistoryList.addAll(record.getPregnancyHistory());
            for (PregnancyHistory history : record.getPregnancyHistory()) {
                System.out.println("Debug - Loading pregnancy history entry: " +
                        "Number=" + history.getPregnancyNumber() +
                        ", Delivery=" + history.getDeliveryType() +
                        ", Gender=" + history.getGender() +
                        ", Place=" + history.getPlaceOfDelivery() +
                        ", Year=" + history.getYearDelivered() +
                        ", AttendedBy=" + history.getAttendedBy() +
                        ", Status=" + history.getStatus() +
                        ", BirthDate="
                        + (history.getBirthDate() != null ? history.getBirthDate().toString() : "null") +
                        ", TT=" + history.getTtInjection());
            }
        } else {
            System.out.println("Debug - No pregnancy history data to load");
        }
        
        // Update the table view with the loaded data
        updatePregnancyHistoryTableView();
    }

    public void loadRecord(MaternalRecord record) {
        editRecord(record);
    }

    @FXML
    private void handleSave() {
        if (!validateRequiredFields()) {
            return;
        }

        try {
            MaternalRecord record = new MaternalRecord();
            record.setFormTimestamp(LocalDateTime.now());
            record.setPatientId(patientIdField.getText());
            record.setFullName(fullNameField.getText());
            record.setDateOfBirth(dateOfBirthPicker.getValue());
            record.setHusbandName(husbandNameField.getText());
            record.setAddress(addressField.getText());
            record.setPurok(purokCombo.getValue());
            record.setContactNumber(contactNumberField.getText());
            record.setEmail(emailField.getText());
            record.setAgeOfGestation(parseDouble(ageOfGestationField.getText()));
            record.setWeight(parseDouble(weightField.getText()));
            record.setHeight(parseDouble(heightField.getText()));
            record.setBloodPressure(bloodPressureField.getText());
            record.setFetalHeartTone(parseInt(fetalHeartToneField.getText()));
            record.setFundalHeight(parseDouble(fundalHeightField.getText()));
            record.setPresentation(presentationCombo.getValue());
            record.setNextAppointment(toComeBackPicker.getValue());
            record.setChiefComplaint(chiefComplaintField.getText());
            record.setBarangayResidencyNumber(barangayResidencyField.getText());
            record.setLastMenstrualPeriod(lastMenstrualPeriodPicker.getValue());
            record.setExpectedDeliveryDate(expectedDeliveryDatePicker.getValue());
            record.setPara(paraField.getText());
            record.setAbortion(abortionField.getText());
            record.setLivingChildren(livingChildrenField.getText());
            record.setTerm(termField.getText());
            record.setPreterm(pretermField.getText());
            record.setRemarks(remarksField.getText());
            record.setPulseRate(pulseRateField.getText());
            record.setRespiratoryRate(respiratoryRateField.getText());
            record.setGravida(gravidaField.getText());
            
            // Create a vital signs entry from the form data
            VitalSignsEntry vitalSignsEntry = new VitalSignsEntry(
                LocalDate.now(),
                bloodPressureField.getText(),
                pulseRateField.getText(),
                respiratoryRateField.getText(),
                remarksField.getText(),
                ageOfGestationField.getText(),
                heightField.getText(),
                weightField.getText(),
                fundalHeightField.getText(),
                fetalHeartToneField.getText(),
                presentationCombo.getValue(),
                chiefComplaintField.getText(),
                toComeBackPicker.getValue()
            );
            
            // Add the vital signs entry to the record
            if (record.getFollowUpVitalSigns() == null) {
                record.setFollowUpVitalSigns(new ArrayList<>());
            }
            record.getFollowUpVitalSigns().add(vitalSignsEntry);
            
            List<ChildDetails> childDetails = new ArrayList<>();
            for (ChildDetailsController controller : childDetailsControllers) {
                childDetails.add(controller.getData());
            }
            record.setChildDetails(childDetails);
            record.setPregnancyHistory(new ArrayList<>(pregnancyHistoryList));
            
            if (recordsController != null) {
                recordsController.saveRecord(record);
            }
            showInlineNotification("Maternal record saved successfully!", false);
            
            // Don't clear the pregnancy history list - keep it for continued editing
            // Only clear the form if this is a new record (not editing)
            if (currentRecord == null) {
                clearForm();
            } else {
                // Update the current record reference
                currentRecord = record;
            }
        } catch (Exception e) {
            showInlineNotification("Failed to save maternal record: " + e.getMessage(), true);
            e.printStackTrace();
        }
    }

    @FXML
    private void handleClear() {
        clearForm();
    }

    private void clearForm() {
        // Reset current record reference
        currentRecord = null;
        
        // Update timestamp when form is cleared
        updateTimestamp();

        // Clear personal information
        patientIdField.clear();
        fullNameField.clear();
        dateOfBirthPicker.setValue(null);
        husbandNameField.clear();

        // Clear contact information
        addressField.clear();
        purokCombo.setValue(null);
        contactNumberField.clear();
        emailField.clear();
        barangayResidencyField.clear();

        // Clear vital signs
        ageOfGestationField.clear();
        weightField.clear();
        heightField.clear();
        bloodPressureField.clear();
        fetalHeartToneField.clear();
        fundalHeightField.clear();
        presentationCombo.setValue(null);
        toComeBackPicker.setValue(null);
        chiefComplaintField.clear();

        // Clear pregnancy information
        lastMenstrualPeriodPicker.setValue(null);
        expectedDeliveryDatePicker.setValue(null);
        paraField.clear();
        abortionField.clear();
        livingChildrenField.clear();
        termField.clear();
        pretermField.clear();
        gravidaField.clear();

        // Clear pregnancy history
        pregnancyHistoryList.clear();
        updatePregnancyHistoryTableView();

        // Clear remarks
        remarksField.clear();
        pulseRateField.clear();
        respiratoryRateField.clear();
        
        // Clear child details
        childDetailsControllers.clear();
        pregnancyHistoryContainer.getChildren().clear();
        
        // Generate new patient ID
        generateAndSetPatientId();
    }

    private boolean validateRequiredFields() {
        boolean isValid = true;

        // Personal Information
        if (patientIdField.getText().trim().isEmpty()) {
            showFieldError(patientIdField, "Patient ID is required");
            isValid = false;
        }

        if (fullNameField.getText().trim().isEmpty()) {
            showFieldError(fullNameField, "Full name is required");
            isValid = false;
        }

        if (dateOfBirthPicker.getValue() == null) {
            showFieldError(dateOfBirthPicker, "Date of birth is required");
            isValid = false;
        }

        // Contact Information
        if (addressField.getText().trim().isEmpty()) {
            showFieldError(addressField, "Address is required");
            isValid = false;
        }

        if (purokCombo.getValue() == null || purokCombo.getValue().trim().isEmpty()) {
            showFieldError(purokCombo, "Purok is required");
            isValid = false;
        }

        if (contactNumberField.getText().trim().isEmpty()) {
            showFieldError(contactNumberField, "Contact number is required");
            isValid = false;
        }

        // Vital Signs
        if (ageOfGestationField.getText().isEmpty()) {
            showFieldError(ageOfGestationField, "Age of gestation is required");
            isValid = false;
        }

        if (weightField.getText().isEmpty()) {
            showFieldError(weightField, "Weight is required");
            isValid = false;
        } else if (!weightField.getText().matches("\\d+(\\.\\d+)?")) {
            showFieldError(weightField, "Invalid weight format");
            isValid = false;
        }

        if (heightField.getText().isEmpty()) {
            showFieldError(heightField, "Height is required");
            isValid = false;
        } else if (!heightField.getText().matches("\\d+(\\.\\d+)?")) {
            showFieldError(heightField, "Invalid height format");
            isValid = false;
        }

        if (bloodPressureField.getText().isEmpty()) {
            showFieldError(bloodPressureField, "Blood pressure is required");
            isValid = false;
        } else if (!bloodPressureField.getText().matches("\\d+/\\d+")) {
            showFieldError(bloodPressureField, "Invalid blood pressure format (e.g., 120/80)");
            isValid = false;
        }

        if (fetalHeartToneField.getText().isEmpty()) {
            showFieldError(fetalHeartToneField, "Fetal heart tone is required");
            isValid = false;
        } else if (!fetalHeartToneField.getText().matches("\\d+")) {
            showFieldError(fetalHeartToneField, "Invalid FHT format");
            isValid = false;
        }

        if (fundalHeightField.getText().isEmpty()) {
            showFieldError(fundalHeightField, "Fundal height is required");
            isValid = false;
        } else if (!fundalHeightField.getText().matches("\\d+(\\.\\d+)?")) {
            showFieldError(fundalHeightField, "Invalid fundal height format");
            isValid = false;
        }

        if (presentationCombo.getValue() == null) {
            showFieldError(presentationCombo, "Presentation is required");
            isValid = false;
        }

        if (toComeBackPicker.getValue() == null) {
            showFieldError(toComeBackPicker, "To come back date is required");
            isValid = false;
        }

        // Pregnancy Information
        if (lastMenstrualPeriodPicker.getValue() == null) {
            showFieldError(lastMenstrualPeriodPicker, "Last menstrual period is required");
            isValid = false;
        }

        return isValid;
    }

    private void showFieldError(Control field, String message) {
        field.setStyle("-fx-border-color: #dc3545;");
        Tooltip tooltip = new Tooltip(message);
        tooltip.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-font-size: 12px;");
        field.setTooltip(tooltip);
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void updateChildDetailsForms(int gravida) {
        try {
            // Clear existing forms
            pregnancyHistoryContainer.getChildren().clear();
            childDetailsControllers.clear();

            // Add new forms based on gravida number
            for (int i = 1; i <= gravida; i++) {
                VBox childForm = ChildDetailsController.createChildDetailsForm(i);
                ChildDetailsController controller = (ChildDetailsController) childForm.getProperties()
                        .get("controller");
                if (controller != null) {
                    childDetailsControllers.add(controller);
                }
                pregnancyHistoryContainer.getChildren().add(childForm);
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to create child details forms: " + e.getMessage());
        }
    }

    private void updateTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        formTimestampLabel.setText("Form filled on: " + now.format(timestampFormatter));
    }

    // Add getter methods for debugging
    public TextField getPatientIdField() {
        return patientIdField;
    }

    public TextField getFullNameField() {
        return fullNameField;
    }

    private void setupDatePickers() {
        // Add listener to date of birth to calculate age
        dateOfBirthPicker.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                int age = Period.between(newVal, LocalDate.now()).getYears();
                ageField.setText(String.valueOf(age));
            }
        });

        // Add listener to last menstrual period to calculate expected delivery date
        lastMenstrualPeriodPicker.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                // Add 280 days (40 weeks) to LMP to get EDD
                LocalDate edd = newVal.plusDays(280);
                expectedDeliveryDatePicker.setValue(edd);
            }
        });

        // Add tooltips for date pickers
        dateOfBirthPicker.setTooltip(new Tooltip("Select patient's date of birth"));
        lastMenstrualPeriodPicker.setTooltip(new Tooltip("Select first day of last menstrual period"));
        expectedDeliveryDatePicker.setTooltip(new Tooltip("Expected delivery date (calculated from LMP)"));
        toComeBackPicker.setTooltip(new Tooltip("Select next appointment date"));
    }

    // Method to manually set the pregnancy history table controller
    public void setPregnancyHistoryTableController(PregnancyHistoryTableController controller) {
        System.out.println(
                "Debug - Setting pregnancy history table controller: " + (controller != null ? "not null" : "null"));
        this.pregnancyHistoryTableController = controller;
        if (controller != null) {
            controller.setReadOnly(false);
            System.out.println("Debug - Pregnancy history table controller set manually and made editable.");
        } else {
            System.out.println("Debug - Pregnancy history table controller is null, cannot set read-only mode.");
        }
    }

    // Helper method to safely parse double
    private double parseDouble(String value) {
        try {
            if (value == null || value.trim().isEmpty())
                return 0.0;
            return Double.parseDouble(value.replaceAll("[^\\d.]", ""));
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    // Helper method to safely parse int
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
        // Add a new pregnancy history entry
        PregnancyHistory ph = new PregnancyHistory();
        ph.setPregnancyNumber(pregnancyHistoryList.size() + 1);
        pregnancyHistoryList.add(ph);
        
        // Update the table view to reflect the changes
        updatePregnancyHistoryTableView();
        
        System.out.println("Debug - Added pregnancy history entry. Total entries: " + pregnancyHistoryList.size());
    }

    @FXML
    private void handleDeletePregnancyHistory() {
        PregnancyHistory selected = pregnancyHistoryTableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            pregnancyHistoryList.remove(selected);
            // Re-number the remaining pregnancy histories
            for (int i = 0; i < pregnancyHistoryList.size(); i++) {
                pregnancyHistoryList.get(i).setPregnancyNumber(i + 1);
            }
            
            // Update the table view to reflect the changes
            updatePregnancyHistoryTableView();
            
            System.out.println("Debug - Deleted pregnancy history entry. Total entries: " + pregnancyHistoryList.size());
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a pregnancy history entry to delete.");
        }
    }

    private void updatePregnancyHistoryTableView() {
        pregnancyHistoryTableView.setItems(FXCollections.observableArrayList(pregnancyHistoryList));
    }

    private void autosaveMaternalRecord() {
        handleSave();
    }

    private void savePregnancyHistoryOnly() {
        if (currentRecord != null) {
            currentRecord.setPregnancyHistory(new ArrayList<>(pregnancyHistoryList));
            if (recordsController != null) {
                recordsController.saveRecord(currentRecord);
            }
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

    private void generateAndSetPatientId() {
        if (recordsController != null) {
            List<MaternalRecord> records = recordsController.getAllRecords();
            int maxId = 0;
            for (MaternalRecord record : records) {
                String pid = record.getPatientId();
                if (pid != null && pid.startsWith("P")) {
                    try {
                        int num = Integer.parseInt(pid.substring(1));
                        if (num > maxId)
                            maxId = num;
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
            String newId = String.format("P%03d", maxId + 1);
            patientIdField.setText(newId);
        } else {
            patientIdField.setText("P001");
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
        long totalDays = ChronoUnit.DAYS.between(lmp, today);
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