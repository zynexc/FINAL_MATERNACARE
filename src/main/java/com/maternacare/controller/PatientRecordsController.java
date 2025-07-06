package com.maternacare.controller;

import com.maternacare.model.PatientData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;

public class PatientRecordsController {
    @FXML
    private TableView<PatientData> recordsTable;
    @FXML
    private TableColumn<PatientData, String> patientIdColumn;
    @FXML
    private TableColumn<PatientData, String> nameColumn;
    @FXML
    private TableColumn<PatientData, Integer> ageColumn;
    @FXML
    private TableColumn<PatientData, String> purokColumn;
    @FXML
    private TableColumn<PatientData, Double> ageOfGestationColumn;
    @FXML
    private TableColumn<PatientData, String> bloodPressureColumn;
    @FXML
    private TableColumn<PatientData, Double> weightColumn;
    @FXML
    private TableColumn<PatientData, Double> heightColumn;
    @FXML
    private TableColumn<PatientData, LocalDate> nextAppointmentColumn;
    @FXML
    private TextField searchField;

    private ObservableList<PatientData> patientData = FXCollections.observableArrayList();
    private FilteredList<PatientData> filteredData;

    @FXML
    public void initialize() {
        setupTableColumns();
        setupTableSearch();
        addSampleData();
    }

    private void setupTableColumns() {
        patientIdColumn.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        purokColumn.setCellValueFactory(new PropertyValueFactory<>("purok"));
        ageOfGestationColumn.setCellValueFactory(new PropertyValueFactory<>("ageOfGestation"));
        bloodPressureColumn.setCellValueFactory(new PropertyValueFactory<>("bloodPressure"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
        heightColumn.setCellValueFactory(new PropertyValueFactory<>("height"));
        nextAppointmentColumn.setCellValueFactory(new PropertyValueFactory<>("nextAppointment"));
    }

    private void setupTableSearch() {
        filteredData = new FilteredList<>(patientData, p -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(patient -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                return patient.getPatientId().toLowerCase().contains(lowerCaseFilter) ||
                        patient.getName().toLowerCase().contains(lowerCaseFilter) ||
                        String.valueOf(patient.getAge()).contains(lowerCaseFilter) ||
                        patient.getPurok().toLowerCase().contains(lowerCaseFilter) ||
                        String.valueOf(patient.getAgeOfGestation()).contains(lowerCaseFilter) ||
                        patient.getBloodPressure().toLowerCase().contains(lowerCaseFilter) ||
                        String.valueOf(patient.getWeight()).contains(lowerCaseFilter) ||
                        String.valueOf(patient.getHeight()).contains(lowerCaseFilter);
            });
        });

        SortedList<PatientData> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(recordsTable.comparatorProperty());
        recordsTable.setItems(sortedData);
    }

    private void addSampleData() {
        // Add sample records with the new constructor format
        patientData.add(new PatientData(
                "P001",
                "Santos, Maria",
                12,
                "Purok 1",
                28.0,
                "110/70",
                45.0,
                150.0,
                LocalDate.now().plusDays(7),
                "09123456789",
                "maria.santos@email.com"));

        patientData.add(new PatientData(
                "P002",
                "Cruz, Ana",
                16,
                "Purok 2",
                32.0,
                "115/75",
                52.0,
                155.0,
                LocalDate.now().plusDays(14),
                "09234567890",
                "ana.cruz@email.com"));

        patientData.add(new PatientData(
                "P003",
                "Reyes, Sofia",
                17,
                "Purok 3",
                24.0,
                "118/78",
                55.0,
                158.0,
                LocalDate.now().plusDays(21),
                "09345678901",
                "sofia.reyes@email.com"));

        patientData.add(new PatientData(
                "P004",
                "Garcia, Teresa",
                42,
                "Purok 4",
                30.0,
                "125/85",
                65.0,
                162.0,
                LocalDate.now().plusDays(28),
                "09456789012",
                "teresa.garcia@email.com"));

        patientData.add(new PatientData(
                "P005",
                "Torres, Carmen",
                54,
                "Purok 5",
                26.0,
                "130/90",
                68.0,
                160.0,
                LocalDate.now().plusDays(35),
                "09567890123",
                "carmen.torres@email.com"));
    }

    @FXML
    private void handleRefresh() {
        searchField.clear();
        patientData.clear();
        addSampleData();
    }
}