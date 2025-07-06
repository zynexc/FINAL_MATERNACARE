package com.maternacare.controller;

import com.maternacare.model.ChildDetails;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.IOException;
import java.time.LocalDate;

public class ChildDetailsController {
    @FXML
    private Text childNumberText;
    @FXML
    private ComboBox<String> deliveryTypeCombo;
    @FXML
    private ComboBox<String> genderCombo;
    @FXML
    private TextField placeOfDeliveryField;
    @FXML
    private TextField yearDeliveredField;
    @FXML
    private TextField attendedByField;
    @FXML
    private ComboBox<String> statusCombo;
    @FXML
    private DatePicker birthdatePicker;
    @FXML
    private ComboBox<String> tetanusStatusCombo;
    @FXML
    private TextField tetanusYearField;

    private int childNumber;

    @FXML
    public void initialize() {
        // Initialize delivery type options
        ObservableList<String> deliveryTypes = FXCollections.observableArrayList(
                "NSD", "CS", "ABORTION");
        deliveryTypeCombo.setItems(deliveryTypes);

        // Initialize gender options
        ObservableList<String> genders = FXCollections.observableArrayList(
                "Male", "Female");
        genderCombo.setItems(genders);

        // Initialize status options
        ObservableList<String> statuses = FXCollections.observableArrayList(
                "Alive", "Not Alive");
        statusCombo.setItems(statuses);

        // Initialize tetanus status options
        ObservableList<String> tetanusStatuses = FXCollections.observableArrayList(
                "Complete", "Incomplete", "None");
        tetanusStatusCombo.setItems(tetanusStatuses);

        // Add validation for year fields
        yearDeliveredField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*")) {
                yearDeliveredField.setText(newVal.replaceAll("[^\\d]", ""));
            }
        });

        tetanusYearField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*")) {
                tetanusYearField.setText(newVal.replaceAll("[^\\d]", ""));
            }
        });
    }

    public void setChildNumber(int number) {
        this.childNumber = number;
        childNumberText.setText("Child #" + number);
    }

    public void setChildDetails(ChildDetails data) {
        if (data != null) {
            deliveryTypeCombo.setValue(data.getDeliveryType());
            genderCombo.setValue(data.getGender());
            placeOfDeliveryField.setText(data.getPlaceOfDelivery());
            yearDeliveredField.setText(data.getYearDelivered());
            attendedByField.setText(data.getAttendedBy());
            statusCombo.setValue(data.getStatus());
            birthdatePicker.setValue(data.getBirthdate());
            tetanusStatusCombo.setValue(data.getTetanusStatus());
            tetanusYearField.setText(data.getTetanusYear());
        }
    }

    public ChildDetails getData() {
        ChildDetails data = new ChildDetails();
        data.setDeliveryType(deliveryTypeCombo.getValue());
        data.setGender(genderCombo.getValue());
        data.setPlaceOfDelivery(placeOfDeliveryField.getText());
        data.setYearDelivered(yearDeliveredField.getText());
        data.setAttendedBy(attendedByField.getText());
        data.setStatus(statusCombo.getValue());
        data.setBirthdate(birthdatePicker.getValue());
        data.setTetanusStatus(tetanusStatusCombo.getValue());
        data.setTetanusYear(tetanusYearField.getText());
        return data;
    }

    public static VBox createChildDetailsForm(int childNumber) throws IOException {
        FXMLLoader loader = new FXMLLoader(ChildDetailsController.class.getResource("/fxml/child_details_form.fxml"));
        VBox form = loader.load();
        ChildDetailsController controller = loader.getController();
        if (controller != null) {
            controller.setChildNumber(childNumber);
            form.getProperties().put("controller", controller);
        }
        return form;
    }
}