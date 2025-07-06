package com.maternacare.controller;

import com.maternacare.model.PregnancyHistory;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

public class PregnancyHistoryTableController {
    @FXML
    private TextField gravidaCountField;
    @FXML
    private Button updateRowsButton;
    @FXML
    private TableView<PregnancyHistory> pregnancyHistoryTable;
    @FXML
    private TableColumn<PregnancyHistory, Integer> pregnancyNumberColumn;
    @FXML
    private TableColumn<PregnancyHistory, String> deliveryTypeColumn;
    @FXML
    private TableColumn<PregnancyHistory, String> genderColumn;
    @FXML
    private TableColumn<PregnancyHistory, String> placeOfDeliveryColumn;
    @FXML
    private TableColumn<PregnancyHistory, Integer> yearDeliveredColumn;
    @FXML
    private TableColumn<PregnancyHistory, String> attendedByColumn;
    @FXML
    private TableColumn<PregnancyHistory, String> statusColumn;
    @FXML
    private TableColumn<PregnancyHistory, LocalDate> birthDateColumn;
    @FXML
    private TableColumn<PregnancyHistory, String> ttInjectionColumn;

    private ObservableList<PregnancyHistory> pregnancyHistoryList;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
    private boolean isReadOnly = false;

    @FXML
    public void initialize() {
        pregnancyHistoryList = FXCollections.observableArrayList();
        pregnancyHistoryTable.setItems(pregnancyHistoryList);
        setupTableColumns();

        // Make table editable
        pregnancyHistoryTable.setEditable(true);

        // Add double-click handler for editing
        pregnancyHistoryTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                handleDoubleClickEdit();
            }
        });

        // Add a default pregnancy history entry if the list is empty
        if (pregnancyHistoryList.isEmpty()) {
            addDefaultPregnancyHistory();
        }
    }

    public void setReadOnly(boolean readOnly) {
        this.isReadOnly = readOnly;
        // Hide the gravida count input and update button in read-only mode
        gravidaCountField.setVisible(!readOnly);
        updateRowsButton.setVisible(!readOnly);
        // Make table non-editable in read-only mode
        pregnancyHistoryTable.setEditable(!readOnly);
    }

    private void setupTableColumns() {
        // Set table properties
        pregnancyHistoryTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        // Set column titles
        pregnancyNumberColumn.setText("GRAVIDA");
        deliveryTypeColumn.setText("Type of Delivery");
        placeOfDeliveryColumn.setText("Place of Delivery");
        yearDeliveredColumn.setText("Year Delivered");
        ttInjectionColumn.setText("TT Injection Status With Year");
        
        // Set cell value factories
        pregnancyNumberColumn.setCellValueFactory(cellData -> cellData.getValue().pregnancyNumberProperty().asObject());
        deliveryTypeColumn.setCellValueFactory(cellData -> cellData.getValue().deliveryTypeProperty());
        genderColumn.setCellValueFactory(cellData -> cellData.getValue().genderProperty());
        placeOfDeliveryColumn.setCellValueFactory(cellData -> cellData.getValue().placeOfDeliveryProperty());
        yearDeliveredColumn.setCellValueFactory(cellData -> cellData.getValue().yearDeliveredProperty().asObject());
        attendedByColumn.setCellValueFactory(cellData -> cellData.getValue().attendedByProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        birthDateColumn.setCellValueFactory(cellData -> cellData.getValue().birthDateProperty());
        ttInjectionColumn.setCellValueFactory(cellData -> cellData.getValue().ttInjectionProperty());

        // Configure column properties - make all columns unmovable and set proper widths
        pregnancyNumberColumn.setSortable(false);
        pregnancyNumberColumn.setReorderable(false);
        pregnancyNumberColumn.setResizable(true);
        pregnancyNumberColumn.setPrefWidth(100);
        pregnancyNumberColumn.setMinWidth(80);
        pregnancyNumberColumn.setMaxWidth(120);
        pregnancyNumberColumn.setStyle("-fx-alignment: CENTER;");

        deliveryTypeColumn.setSortable(false);
        deliveryTypeColumn.setReorderable(false);
        deliveryTypeColumn.setResizable(true);
        deliveryTypeColumn.setPrefWidth(140);
        deliveryTypeColumn.setMinWidth(120);
        deliveryTypeColumn.setMaxWidth(180);
        deliveryTypeColumn.setStyle("-fx-alignment: CENTER;");

        genderColumn.setSortable(false);
        genderColumn.setReorderable(false);
        genderColumn.setResizable(true);
        genderColumn.setPrefWidth(100);
        genderColumn.setMinWidth(80);
        genderColumn.setMaxWidth(120);
        genderColumn.setStyle("-fx-alignment: CENTER;");

        placeOfDeliveryColumn.setSortable(false);
        placeOfDeliveryColumn.setReorderable(false);
        placeOfDeliveryColumn.setResizable(true);
        placeOfDeliveryColumn.setPrefWidth(160);
        placeOfDeliveryColumn.setMinWidth(140);
        placeOfDeliveryColumn.setMaxWidth(200);
        placeOfDeliveryColumn.setStyle("-fx-alignment: CENTER;");

        yearDeliveredColumn.setSortable(false);
        yearDeliveredColumn.setReorderable(false);
        yearDeliveredColumn.setResizable(true);
        yearDeliveredColumn.setPrefWidth(120);
        yearDeliveredColumn.setMinWidth(100);
        yearDeliveredColumn.setMaxWidth(140);
        yearDeliveredColumn.setStyle("-fx-alignment: CENTER;");

        attendedByColumn.setSortable(false);
        attendedByColumn.setReorderable(false);
        attendedByColumn.setResizable(true);
        attendedByColumn.setPrefWidth(140);
        attendedByColumn.setMinWidth(120);
        attendedByColumn.setMaxWidth(180);
        attendedByColumn.setStyle("-fx-alignment: CENTER;");

        statusColumn.setSortable(false);
        statusColumn.setReorderable(false);
        statusColumn.setResizable(true);
        statusColumn.setPrefWidth(100);
        statusColumn.setMinWidth(80);
        statusColumn.setMaxWidth(120);
        statusColumn.setStyle("-fx-alignment: CENTER;");

        birthDateColumn.setSortable(false);
        birthDateColumn.setReorderable(false);
        birthDateColumn.setResizable(true);
        birthDateColumn.setPrefWidth(120);
        birthDateColumn.setMinWidth(100);
        birthDateColumn.setMaxWidth(140);
        birthDateColumn.setStyle("-fx-alignment: CENTER;");

        ttInjectionColumn.setSortable(false);
        ttInjectionColumn.setReorderable(false);
        ttInjectionColumn.setResizable(true);
        ttInjectionColumn.setPrefWidth(180);
        ttInjectionColumn.setMinWidth(160);
        ttInjectionColumn.setMaxWidth(220);
        ttInjectionColumn.setStyle("-fx-alignment: CENTER;");

        // Set table size properties
        pregnancyHistoryTable.setPrefWidth(1200);
        pregnancyHistoryTable.setMaxWidth(Double.MAX_VALUE);
        pregnancyHistoryTable.setMinWidth(1000);
        pregnancyHistoryTable.setFixedCellSize(45);

        // Make columns editable
        deliveryTypeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        genderColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        placeOfDeliveryColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        yearDeliveredColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        attendedByColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        statusColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        ttInjectionColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        // Add commit handlers for editable columns
        deliveryTypeColumn.setOnEditCommit(event -> {
            PregnancyHistory history = event.getRowValue();
            history.setDeliveryType(event.getNewValue());
        });
        genderColumn.setOnEditCommit(event -> {
            PregnancyHistory history = event.getRowValue();
            history.setGender(event.getNewValue());
        });
        placeOfDeliveryColumn.setOnEditCommit(event -> {
            PregnancyHistory history = event.getRowValue();
            history.setPlaceOfDelivery(event.getNewValue());
        });
        yearDeliveredColumn.setOnEditCommit(event -> {
            PregnancyHistory history = event.getRowValue();
            try {
                history.setYearDelivered(event.getNewValue());
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter a valid year.");
            }
        });
        attendedByColumn.setOnEditCommit(event -> {
            PregnancyHistory history = event.getRowValue();
            history.setAttendedBy(event.getNewValue());
        });
        statusColumn.setOnEditCommit(event -> {
            PregnancyHistory history = event.getRowValue();
            history.setStatus(event.getNewValue());
        });
        ttInjectionColumn.setOnEditCommit(event -> {
            PregnancyHistory history = event.getRowValue();
            history.setTtInjection(event.getNewValue());
        });

        // Format birth date column
        birthDateColumn.setCellFactory(column -> new TableCell<>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

            @Override
            protected void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (empty || date == null) {
                    setText(null);
                } else {
                    setText(formatter.format(date));
                }
            }
        });
    }

    @FXML
    private void handleUpdateRows() {
        if (isReadOnly) {
            return; // Don't allow updating rows in read-only mode
        }
        try {
            int gravida = Integer.parseInt(gravidaCountField.getText());
            if (gravida < 0) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Gravida count cannot be negative.");
                return;
            }
            updatePregnancyHistoryRows(gravida);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter a valid number for gravida count.");
        }
    }

    public void updatePregnancyHistoryRows(int gravida) {
        // Clear existing rows
        pregnancyHistoryList.clear();

        // Add new rows based on gravida count
        for (int i = 1; i <= gravida; i++) {
            PregnancyHistory history = new PregnancyHistory();
            history.setPregnancyNumber(i);
            pregnancyHistoryList.add(history);
        }
    }

    @FXML
    private void handleDoubleClickEdit() {
        if (isReadOnly) {
            return; // Don't allow editing records in read-only mode
        }
        PregnancyHistory selectedHistory = pregnancyHistoryTable.getSelectionModel().getSelectedItem();
        if (selectedHistory == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a pregnancy record to edit.");
            return;
        }

        // Create dialog for editing pregnancy details
        Dialog<PregnancyHistory> dialog = new Dialog<>();
        dialog.setTitle("Edit Pregnancy Record");
        dialog.setHeaderText("Edit details for Pregnancy #" + selectedHistory.getPregnancyNumber());

        // Create the custom dialog content
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));

        ComboBox<String> deliveryTypeField = new ComboBox<>();
        deliveryTypeField.setItems(FXCollections.observableArrayList("Normal", "Caesarian", "Abortion"));
        deliveryTypeField.setValue(selectedHistory.getDeliveryType());
        TextField genderField = new TextField(selectedHistory.getGender());
        TextField placeOfDeliveryField = new TextField(selectedHistory.getPlaceOfDelivery());
        TextField yearDeliveredField = new TextField(String.valueOf(selectedHistory.getYearDelivered()));
        TextField attendedByField = new TextField(selectedHistory.getAttendedBy());
        TextField statusField = new TextField(selectedHistory.getStatus());
        DatePicker birthDatePicker = new DatePicker(selectedHistory.getBirthDate());
        TextField ttInjectionField = new TextField(selectedHistory.getTtInjection());

        grid.add(new Label("Type of Delivery:"), 0, 0);
        grid.add(deliveryTypeField, 1, 0);
        grid.add(new Label("Gender:"), 0, 1);
        grid.add(genderField, 1, 1);
        grid.add(new Label("Place of Delivery:"), 0, 2);
        grid.add(placeOfDeliveryField, 1, 2);
        grid.add(new Label("Year Delivered:"), 0, 3);
        grid.add(yearDeliveredField, 1, 3);
        grid.add(new Label("Attended By:"), 0, 4);
        grid.add(attendedByField, 1, 4);
        grid.add(new Label("Status:"), 0, 5);
        grid.add(statusField, 1, 5);
        grid.add(new Label("Birth Date:"), 0, 6);
        grid.add(birthDatePicker, 1, 6);
        grid.add(new Label("TT Injection:"), 0, 7);
        grid.add(ttInjectionField, 1, 7);

        dialog.getDialogPane().setContent(grid);

        // Add buttons to the dialog
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // Convert the result to a PregnancyHistory when the save button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                try {
                    selectedHistory.setDeliveryType(deliveryTypeField.getValue());
                    selectedHistory.setGender(genderField.getText());
                    selectedHistory.setPlaceOfDelivery(placeOfDeliveryField.getText());
                    selectedHistory.setYearDelivered(Integer.parseInt(yearDeliveredField.getText()));
                    selectedHistory.setAttendedBy(attendedByField.getText());
                    selectedHistory.setStatus(statusField.getText());
                    selectedHistory.setBirthDate(birthDatePicker.getValue());
                    selectedHistory.setTtInjection(ttInjectionField.getText());
                    return selectedHistory;
                } catch (NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter a valid year.");
                    return null;
                }
            }
            return null;
        });

        dialog.showAndWait();
    }

    public ObservableList<PregnancyHistory> getPregnancyHistoryList() {
        return pregnancyHistoryList;
    }

    public void setPregnancyHistoryList(ObservableList<PregnancyHistory> list) {
        this.pregnancyHistoryList = list;
        pregnancyHistoryTable.setItems(pregnancyHistoryList);

        // Update gravida count field if there are records and not in read-only mode
        if (!list.isEmpty() && !isReadOnly) {
            gravidaCountField.setText(String.valueOf(list.size()));
        } else if (list.isEmpty() && !isReadOnly) {
            // Only add default entry if the list is empty and we're not in read-only mode
            addDefaultPregnancyHistory();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void addDefaultPregnancyHistory() {
        PregnancyHistory defaultHistory = new PregnancyHistory();
        defaultHistory.setPregnancyNumber(1);
        defaultHistory.setDeliveryType("");
        defaultHistory.setGender("");
        defaultHistory.setPlaceOfDelivery("");
        defaultHistory.setYearDelivered(0);
        defaultHistory.setAttendedBy("");
        defaultHistory.setStatus("");
        defaultHistory.setBirthDate(null);
        defaultHistory.setTtInjection("");
        pregnancyHistoryList.add(defaultHistory);

        // Update the gravida count field
        if (!isReadOnly) {
            gravidaCountField.setText("1");
        }
    }

    public TableView<PregnancyHistory> getPregnancyHistoryTable() {
        return pregnancyHistoryTable;
    }
}