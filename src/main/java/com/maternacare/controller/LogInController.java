package com.maternacare.controller;

import com.maternacare.service.DatabaseConnector;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ProgressIndicator;
import javafx.event.ActionEvent;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.sql.*;

import com.maternacare.MainApplication;

public class LogInController {
    private MainApplication mainApplication;

    public LogInController() {
    }

    @FXML
    private Button button;
    @FXML
    private Label wrongLogIn;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private ProgressIndicator loginProgress;
    @FXML
    private ImageView rightImageView;
    @FXML
    private VBox loginContainer;
    @FXML
    private StackPane imageContainer;
    @FXML
    private HBox rootHBox;

    public void setMainApplication(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }

    // Method to set the text of the wrongLogIn label
    public void setWrongLogInText(String text) {
        Platform.runLater(() -> wrongLogIn.setText(text));
    }

    public void userLogIn(ActionEvent event) throws IOException, SQLException {
        wrongLogIn.setText("");
        checkLogin();
    }

    private void checkLogin() throws SQLException {
        loginProgress.setVisible(true);

        Task<Boolean> loginTask = new Task<>() {
            @Override
            protected Boolean call() {
                String user = username.getText();
                String pass = password.getText();

                try (Connection conn = DatabaseConnector.getConnection()) {
                    if (conn != null) {
                        String sql = "SELECT * FROM login WHERE username = ? AND password = ?";
                        PreparedStatement stmt = conn.prepareStatement(sql);
                        stmt.setString(1, user);
                        stmt.setString(2, pass);

                        ResultSet rs = stmt.executeQuery();
                        if (rs.next()) {
                            Platform.runLater(() -> {
                                wrongLogIn.setText("Login Successfully");
                                loginProgress.setVisible(false);
                                try {
                                    mainApplication.showMainApplicationScene();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                        } else {
                            Platform.runLater(() -> {
                                wrongLogIn.setText("Invalid username or password");
                                loginProgress.setVisible(false);
                            });
                        }
                    } else {
                        wrongLogIn.setText("Failed to connect database");
                        loginProgress.setVisible(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    wrongLogIn.setText("An error occured");
                }
                return null;
            }

            @Override
            protected void succeeded() {
                loginProgress.setVisible(false);
                boolean success = getValue();
                if (success) {
                    wrongLogIn.setText("Login successful!");
                    if (mainApplication != null) {
                        try {
                            mainApplication.showMainApplicationScene();
                        } catch (IOException ex) {
                            setWrongLogInText("Failed to load main scene.");
                        }
                    }
                } else {
                    wrongLogIn.setText("Invalid username or password.");
                }
            }

            @Override
            protected void failed() {
                loginProgress.setVisible(false);
                setWrongLogInText("Error connecting to database.");
            }
        };
        new Thread(loginTask).start();
    }
}