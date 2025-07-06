package com.maternacare.controller;

import com.maternacare.model.PatientData;
import com.maternacare.model.MaternalRecord;
import com.maternacare.service.MaternalRecordService;
import com.maternacare.MainApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.geometry.Pos;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.io.IOException;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import java.sql.SQLException;
import java.util.ArrayList;

public class DashboardController {
    @FXML
    private AreaChart<String, Number> barChart;
    @FXML
    private PieChart pieChart;
    @FXML
    private TableView<PatientData> recordsTable;
    @FXML
    private TableColumn<PatientData, String> patientIdColumn;
    @FXML
    private TableColumn<PatientData, String> lastNameColumn;
    @FXML
    private TableColumn<PatientData, String> firstNameColumn;
    @FXML
    private TableColumn<PatientData, Integer> ageColumn;
    @FXML
    private TableColumn<PatientData, String> purokColumn;
    @FXML
    private TableColumn<PatientData, String> contactColumn;
    @FXML
    private TableColumn<PatientData, String> emailColumn;
    @FXML
    private TableColumn<PatientData, Double> ageOfGestationColumn;
    @FXML
    private Label totalPatientsLabel;
    @FXML
    private Label completedFormsLabel;
    @FXML
    private Label severeCasesLabel;
    @FXML
    private StackPane totalPatientsIconContainer;
    @FXML
    private StackPane completedFormsIconContainer;
    @FXML
    private StackPane severeCasesIconContainer;
    @FXML
    private HBox purokLegendBox;

    private ObservableList<PatientData> patientData = FXCollections.observableArrayList();
    private FilteredList<PatientData> filteredData;
    private MainApplication mainApplication;
    private MaternalRecordService recordService = new MaternalRecordService();
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    @FXML
    public void initialize() {
        setupTableColumns();
        setupIcons();
        loadMaternalRecords();
        updateCharts();
        updateStatCards();
    }

    private void setupTableColumns() {
        // Disable column reordering for the entire table
        recordsTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        // Setup columns with fixed properties - make all columns unmovable
        patientIdColumn.setSortable(false);
        patientIdColumn.setReorderable(false);
        patientIdColumn.setResizable(true);
        patientIdColumn.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        patientIdColumn.setPrefWidth(120);
        patientIdColumn.setMinWidth(100);
        patientIdColumn.setMaxWidth(150);

        lastNameColumn.setSortable(false);
        lastNameColumn.setReorderable(false);
        lastNameColumn.setResizable(true);
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        lastNameColumn.setPrefWidth(250);
        lastNameColumn.setMinWidth(150);
        lastNameColumn.setMaxWidth(300);

        firstNameColumn.setSortable(false);
        firstNameColumn.setReorderable(false);
        firstNameColumn.setResizable(true);
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        firstNameColumn.setVisible(false);

        ageColumn.setSortable(false);
        ageColumn.setReorderable(false);
        ageColumn.setResizable(true);
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        ageColumn.setStyle("-fx-alignment: CENTER;");
        ageColumn.setPrefWidth(80);
        ageColumn.setMinWidth(60);
        ageColumn.setMaxWidth(100);

        ageOfGestationColumn.setSortable(false);
        ageOfGestationColumn.setReorderable(false);
        ageOfGestationColumn.setResizable(true);
        ageOfGestationColumn.setCellValueFactory(new PropertyValueFactory<>("ageOfGestation"));
        ageOfGestationColumn.setStyle("-fx-alignment: CENTER;");
        ageOfGestationColumn.setPrefWidth(140);
        ageOfGestationColumn.setMinWidth(120);
        ageOfGestationColumn.setMaxWidth(180);

        purokColumn.setSortable(false);
        purokColumn.setReorderable(false);
        purokColumn.setResizable(true);
        purokColumn.setCellValueFactory(new PropertyValueFactory<>("purok"));
        purokColumn.setStyle("-fx-alignment: CENTER;");
        purokColumn.setPrefWidth(150);
        purokColumn.setMinWidth(100);
        purokColumn.setMaxWidth(150);

        contactColumn.setSortable(false);
        contactColumn.setReorderable(false);
        contactColumn.setResizable(true);
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        contactColumn.setPrefWidth(200);
        contactColumn.setMinWidth(120);
        contactColumn.setMaxWidth(200);

        emailColumn.setSortable(false);
        emailColumn.setReorderable(false);
        emailColumn.setResizable(true);
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailColumn.setPrefWidth(280);
        emailColumn.setMinWidth(200);
        emailColumn.setMaxWidth(400);

        // Make the table fit the full width
        recordsTable.setPrefWidth(1200);
        recordsTable.setMaxWidth(Double.MAX_VALUE);
        recordsTable.setMinWidth(800);

        // Set fixed cell size for consistent row height
        recordsTable.setFixedCellSize(40);
    }

    private void loadMaternalRecords() {
        patientData.clear();
        try {
            List<MaternalRecord> records = recordService.loadRecords();
            java.util.Set<String> addedPatientIds = new java.util.HashSet<>();

            for (MaternalRecord record : records) {
                if (record.getDateOfBirth() != null) {
                    int age = java.time.Period.between(record.getDateOfBirth(), java.time.LocalDate.now()).getYears();
                    boolean is10to17 = (age >= 10 && age <= 17);
                    boolean isHighRisk = record.isHighRisk();

                    if (is10to17 || isHighRisk) {
                        // Avoid duplicates if a patient is both 10-17 and high-risk
                        if (!addedPatientIds.contains(record.getPatientId())) {
                            patientData.add(new PatientData(
                                    record.getPatientId(),
                                    record.getFullName(),
                                    age,
                                    record.getPurok(),
                                    record.getAgeOfGestation(),
                                    record.getBloodPressure(),
                                    record.getWeight(),
                                    record.getHeight(),
                                    record.getNextAppointment(),
                                    record.getContactNumber(),
                                    record.getEmail()));
                            addedPatientIds.add(record.getPatientId());
                        }
                    }
                }
            }
            recordsTable.setItems(patientData);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load maternal records: " + e.getMessage());
        }
    }

    private void updateStatCards() {
        try {
            List<MaternalRecord> allRecords = recordService.loadRecords();
            long completedForms = allRecords.stream().filter(r -> r.getFormTimestamp() != null).count();

            totalPatientsLabel.setText(String.valueOf(allRecords.size()));
            completedFormsLabel.setText(String.valueOf(completedForms));
            severeCasesLabel.setText(String.valueOf(patientData.size()));
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to update statistics: " + e.getMessage());
        }
    }

    private void updateCharts() {
        // Clear existing data
        barChart.getData().clear();
        pieChart.getData().clear();

        // Prepare data for bar chart (age groups for all cases)
        XYChart.Series<String, Number> ageSeries = new XYChart.Series<>();
        ageSeries.setName("Age Distribution (All Cases)");

        // Count patients in age groups (12-45 years)
        int[] ageGroups = new int[7]; // 12-15, 16-20, 21-25, 26-30, 31-35, 36-40, 41-45

        try {
            // Use all records for age distribution, not just patientData
            List<MaternalRecord> allRecordsForAge = recordService.loadRecords();
            for (MaternalRecord record : allRecordsForAge) {
                if (record.getDateOfBirth() != null) {
                    int age = Period.between(record.getDateOfBirth(), LocalDate.now()).getYears();
                    if (age >= 12 && age <= 45) {
                        int groupIndex;
                        if (age >= 12 && age <= 15) {
                            groupIndex = 0; // 12-15 group
                        } else {
                            groupIndex = ((age - 16) / 5) + 1; // 16-45 groups
                        }
                        if (groupIndex < ageGroups.length) {
                            ageGroups[groupIndex]++;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error",
                    "Failed to load records for age chart: " + e.getMessage());
        }

        // Add data to bar chart
        String[] ageLabels = {
                "12-15", "16-20", "21-25", "26-30", "31-35", "36-40", "41-45"
        };

        for (int i = 0; i < ageGroups.length; i++) {
            ageSeries.getData().add(new XYChart.Data<>(ageLabels[i], ageGroups[i]));
        }

        barChart.getData().add(ageSeries);

        // Prepare data for pie chart (patients per purok)
        int[] purokCounts = new int[6]; // Purok 1 to 6
        List<java.util.Set<String>> purokPatientIds = new ArrayList<>();
        for (int i = 0; i < 6; i++)
            purokPatientIds.add(new java.util.HashSet<>());

        try {
            // Use all records for pie chart, not just patientData
            List<MaternalRecord> allRecordsForPie = recordService.loadRecords();
            for (MaternalRecord record : allRecordsForPie) {
                String purok = record.getPurok();
                if (purok != null && purok.matches("Purok [1-6]")) {
                    int purokNumber = Integer.parseInt(purok.split(" ")[1]);
                    String patientId = record.getPatientId();
                    if (!purokPatientIds.get(purokNumber - 1).contains(patientId)) {
                        purokPatientIds.get(purokNumber - 1).add(patientId);
                        purokCounts[purokNumber - 1]++;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load records for charts: " + e.getMessage());
        }

        // Add data to pie chart
        final int total;
        {
            int t = 0;
            for (int count : purokCounts)
                t += count;
            total = t;
        }
        for (int i = 0; i < 6; i++) {
            PieChart.Data data = new PieChart.Data("", purokCounts[i]); // Hide label
            pieChart.getData().add(data);
        }
        // Add tooltips to pie chart slices
        Platform.runLater(() -> {
            for (int i = 0; i < pieChart.getData().size(); i++) {
                PieChart.Data data = pieChart.getData().get(i);
                int count = purokCounts[i];
                double percent = total > 0 ? (count * 100.0 / total) : 0.0;
                String tooltipText = String.format("Purok %d: %.1f%%", i + 1, percent);
                Tooltip tooltip = new Tooltip(tooltipText);
                tooltip.setShowDelay(javafx.util.Duration.ZERO);
                tooltip.setHideDelay(javafx.util.Duration.ZERO);
                tooltip.setShowDuration(javafx.util.Duration.INDEFINITE);
                tooltip.setStyle(
                        "-fx-background-color: #FEE2E2; -fx-text-fill: #eb0000; -fx-font-weight: bold; -fx-font-size: 13px; -fx-background-radius: 8; -fx-padding: 8 14 8 14; -fx-border-color: #eb0000; -fx-border-width: 1; -fx-border-radius: 8;");
                Tooltip.install(data.getNode(), tooltip);
            }
        });

        // Remove pie chart radial lines (chart lines)
        pieChart.lookupAll(".chart-pie-label-line").forEach(node -> node.setStyle("-fx-stroke: transparent;"));

        // Custom legend for Purok colors
        purokLegendBox.getChildren().clear();
        for (int i = 0; i < pieChart.getData().size(); i++) {
            PieChart.Data data = pieChart.getData().get(i);
            // Get the color from the pie slice
            String color = data.getNode().getStyle();
            // Fallback to default JavaFX pie colors if not set
            if (color == null || color.isEmpty()) {
                // JavaFX default pie colors
                String[] defaultColors = { "#f3622d", "#fba71b", "#57b757", "#41a9c9", "#4258c9", "#9a42c8" };
                color = "-fx-pie-color: " + defaultColors[i % defaultColors.length] + ";";
            }
            String pieColor = color.replace("-fx-pie-color:", "").replace(";", "").trim();
            Circle circle = new Circle(7, Paint.valueOf(pieColor));
            Label label = new Label("Purok " + (i + 1));
            label.setStyle("-fx-font-size: 13px; -fx-padding: 0 6 0 4;");
            HBox legendItem = new HBox(4, circle, label);
            legendItem.setAlignment(Pos.CENTER);
            purokLegendBox.getChildren().add(legendItem);
        }
    }

    public void setMainApplication(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }

    @FXML
    private void handleLogout() {
        if (mainApplication != null) {
            try {
                mainApplication.showLoginScreen();
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Logout Error", "Failed to return to login screen.");
            }
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void setupIcons() {
        // Person Icon
        FontAwesomeIconView usersIcon = new FontAwesomeIconView(FontAwesomeIcon.USER);
        usersIcon.setSize("3em");
        usersIcon.setFill(Color.web("#eb0000"));
        totalPatientsIconContainer.getChildren().add(usersIcon);

        // Note Icon
        FontAwesomeIconView fileIcon = new FontAwesomeIconView(FontAwesomeIcon.FILE_TEXT_ALT);
        fileIcon.setSize("3em");
        fileIcon.setFill(Color.web("#eb0000"));
        completedFormsIconContainer.getChildren().add(fileIcon);

        // Warning Icon
        FontAwesomeIconView warningIcon = new FontAwesomeIconView(FontAwesomeIcon.EXCLAMATION_TRIANGLE);
        warningIcon.setSize("3em");
        warningIcon.setFill(Color.web("#eb0000"));
        severeCasesIconContainer.getChildren().add(warningIcon);
    }

    @FXML
    private void handleViewRecords() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/maternal_records.fxml"));
            VBox recordsView = loader.load();
            recordsView.getStylesheets().add(getClass().getResource("/styles/maternal_records.css").toExternalForm());

            MaternalRecordsController controller = loader.getController();
            controller.setDashboardController(this);

            // Get the parent of the dashboard VBox and replace it with the records view
            VBox dashboardVBox = (VBox) totalPatientsLabel.getScene().getRoot();
            VBox parentContainer = (VBox) dashboardVBox.getParent();
            parentContainer.getChildren().setAll(recordsView);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRefresh() {
        loadMaternalRecords();
        updateCharts();
        updateStatCards();
    }
}