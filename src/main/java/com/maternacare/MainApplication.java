package com.maternacare;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.maternacare.controller.MaternalFormController;
import com.maternacare.controller.MaternalRecordsController;
import com.maternacare.controller.DashboardController;
import com.maternacare.controller.MaternalRecordDetailsController;
import com.maternacare.controller.LogInController;
import java.io.IOException;
import java.nio.file.Paths;
import javafx.scene.layout.Priority;
import com.maternacare.model.MaternalRecord;
import javafx.scene.text.Font;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.StackPane;

public class MainApplication extends Application {

    private Stage primaryStage;
    private BorderPane mainLayout;
    private Scene mainScene;
    private VBox contentContainer; // Make contentContainer accessible
    private MaternalRecordsController recordsController;
    private MaternalFormController formController;
    private LogInController loginController; // Store login controller instance
    private Parent maternalRecordsRoot; // Declare as instance variable
    private Parent maternalFormRoot; // Declare as instance variable

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("MaternaCare");

        // Set the application icon
        try {
            Image applicationIcon = new Image(
                    Paths.get("C:/Users/angel/OneDrive/Desktop/MaternaCare/src/main/resources/images/mylogo.jpg")
                            .toUri().toString());
            primaryStage.getIcons().add(applicationIcon);
        } catch (Exception e) {
            System.err.println("Failed to load application icon: " + e.getMessage());
            e.printStackTrace();
        }

        // Load Poppins font
        try {
            Font.loadFont(getClass().getResourceAsStream("/fonts/Poppins-Regular.ttf"), 12);
            System.out.println("Poppins font loaded successfully");
        } catch (Exception e) {
            System.err.println("Failed to load Poppins font: " + e.getMessage());
        }

        // Load the login screen initially
        showLoginScreen();
        primaryStage.show();
        primaryStage.setMaximized(true);
    }

    public void showLoginScreen() throws IOException {
        System.out.println("Showing login screen...");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LogIn.fxml"));
        Parent loginRoot = loader.load();
        loginController = loader.getController(); // Store the controller
        loginController.setMainApplication(this);

        Scene loginScene = new Scene(loginRoot, 600, 400);
        // Add the login stylesheet
        loginScene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());

        primaryStage.setScene(loginScene);
        primaryStage.setMaximized(true); // Make login screen full screen
        System.out.println("Login screen shown.");
    }

    public void showMainApplicationScene() throws IOException {
        System.out.println("Attempting to show main application scene...");
        // Load the main application layout if not already loaded
        if (mainLayout == null) {
            System.out.println("mainLayout is null, creating new layout.");
            mainLayout = new BorderPane();

            // Create the left sidebar with buttons
            VBox sidebar = new VBox(10); // 10 pixels spacing between buttons
            sidebar.getStyleClass().add("sidebar");
            sidebar.setPadding(new Insets(20, 0, 20, 0));
            sidebar.setPrefWidth(200);

            // Add logo to the top of the sidebar
            try {
                Image logo = new Image(getClass().getResourceAsStream("/images/dashboard-logo.png"));
                ImageView logoView = new ImageView(logo);
                logoView.setFitWidth(90);
                logoView.setPreserveRatio(true);
                VBox.setMargin(logoView, new Insets(0, 0, 20, 0)); // Add margin below the logo
                sidebar.getChildren().add(logoView);
            } catch (Exception e) {
                System.err.println("Failed to load sidebar logo: " + e.getMessage());
            }

            // Create content container
            contentContainer = new VBox(); // Initialize contentContainer
            contentContainer.getStyleClass().add("content-container");
            contentContainer.setMaxWidth(Double.MAX_VALUE);
            contentContainer.setMaxHeight(Double.MAX_VALUE);
            contentContainer.setFillWidth(true);
            VBox.setVgrow(contentContainer, Priority.ALWAYS);

            // Set the sidebar and content in the border pane
            mainLayout.setLeft(sidebar);
            mainLayout.setCenter(contentContainer);

            mainScene = new Scene(mainLayout, 1200, 800);
            mainScene.getStylesheets()
                    .add(getClass().getResource("/styles/main_application.css").toExternalForm());
            primaryStage.setScene(mainScene);
            primaryStage.setMaximized(true); // Maximize the main window
            primaryStage.setFullScreenExitHint("");
            primaryStage.setFullScreen(true);
            System.out.println("mainScene set.");

            // Pre-load the records controller (moved from original position)
            System.out.println("Attempting to load maternal_records.fxml");
            try {
                FXMLLoader recordsLoader = new FXMLLoader(getClass().getResource("/fxml/maternal_records.fxml"));
                System.out.println("Created FXMLLoader for maternal_records.fxml");
                maternalRecordsRoot = recordsLoader.load(); // Assign to instance variable
                System.out.println("Successfully loaded maternal_records.fxml.");
                maternalRecordsRoot.getStylesheets()
                        .add(getClass().getResource("/styles/maternal_records.css").toExternalForm());
                recordsController = recordsLoader.getController();
                recordsController.setMainApplication(this); // Pass MainApplication reference
                System.out.println("Records controller loaded: " + (recordsController != null ? "success" : "null"));
            } catch (IOException ex) {
                System.err.println("Failed to load maternal_records.fxml: " + ex.getMessage());
                ex.printStackTrace();
                recordsController = null; // Set to null on failure
            }

            // Pre-load the form controller (moved from original position)
            System.out.println("Attempting to load maternal_form.fxml");
            try {
                FXMLLoader formLoader = new FXMLLoader(getClass().getResource("/fxml/maternal_form.fxml"));
                System.out.println("Created FXMLLoader for maternal_form.fxml");
                maternalFormRoot = formLoader.load(); // Assign to instance variable
                System.out.println("Successfully loaded maternal_form.fxml.");
                maternalFormRoot.getStylesheets()
                        .add(getClass().getResource("/styles/maternal_form.css").toExternalForm());
                formController = formLoader.getController();
                System.out.println("Form controller loaded: " + (formController != null ? "success" : "null"));
            } catch (IOException ex) {
                System.err.println("Failed to load maternal_form.fxml: " + ex.getMessage());
                ex.printStackTrace();
                formController = null; // Set to null on failure
            }

            // Connect controllers bidirectionally
            System.out.println("Attempting to connect controllers...");
            System.out.println("Form controller: " + (formController != null ? "available" : "null"));
            System.out.println("Records controller: " + (recordsController != null ? "available" : "null"));
            if (formController != null && recordsController != null) {
                formController.setRecordsController(recordsController);
                recordsController.setFormController(formController);
                System.out.println("Controllers connected successfully.");
            } else {
                System.err.println("Controllers not loaded successfully, cannot connect.");
            }

            // Set up button actions
            Button dashboardBtn = createNavButton("Dashboard");
            Button maternalFormBtn = createNavButton("Maternal Form");
            Button maternalRecordsBtn = createNavButton("Maternal Records");

            dashboardBtn.setOnAction(e -> {
                System.out.println("Dashboard button clicked. Clearing contentContainer...");
                contentContainer.getChildren().clear();
                try {
                    System.out.println("Attempting to load dashboard.fxml for display...");
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
                    Parent dashboardRoot = loader.load();
                    System.out.println("Successfully loaded dashboard.fxml for display.");
                    dashboardRoot.getStylesheets()
                            .add(getClass().getResource("/styles/dashboard.css").toExternalForm());
                    DashboardController dashboardController = loader.getController();
                    dashboardController.setMainApplication(this);
                    System.out.println("Adding dashboardRoot to contentContainer.");
                    contentContainer.getChildren().add(dashboardRoot);
                    System.out.println("dashboardRoot added to contentContainer.");
                } catch (IOException ex) {
                    System.out.println("Failed to load dashboard.fxml: " + ex.getMessage());
                    ex.printStackTrace();
                    if (loginController != null) {
                        loginController.setWrongLogInText("Error loading dashboard: " + ex.getMessage());
                    }
                }
                updateButtonStyles(dashboardBtn, maternalFormBtn, maternalRecordsBtn);
            });

            maternalFormBtn.setOnAction(e -> {
                System.out.println("Maternal Form button clicked. Clearing contentContainer...");
                contentContainer.getChildren().clear();
                if (maternalFormRoot != null) {
                    System.out.println("maternalFormRoot is not null, attempting to add to contentContainer...");
                    try {
                        System.out.println("maternalFormRoot class: " + maternalFormRoot.getClass().getName());
                        System.out.println("maternalFormRoot children count: "
                                + maternalFormRoot.getChildrenUnmodifiable().size());
                        System.out.println(
                                "contentContainer children count before add: " + contentContainer.getChildren().size());
                        contentContainer.getChildren().add(maternalFormRoot);
                        System.out.println(
                                "contentContainer children count after add: " + contentContainer.getChildren().size());
                        System.out.println("maternalFormRoot added to contentContainer successfully.");

                        // Check if the form controller is properly initialized
                        if (formController != null) {
                            System.out.println("Form controller is available.");
                            System.out.println("Form controller class: " + formController.getClass().getName());
                            // Try to access some form fields to verify initialization
                            try {
                                System.out.println("Checking form fields...");
                                System.out.println("patientIdField: "
                                        + (formController.getPatientIdField() != null ? "initialized" : "null"));
                                System.out.println("fullNameField: "
                                        + (formController.getFullNameField() != null ? "initialized" : "null"));

                                // Set up pregnancy history controller
                                System.out.println("Setting up pregnancy history controller...");
                                try {
                                    FXMLLoader pregnancyHistoryLoader = new FXMLLoader(
                                            getClass().getResource("/fxml/pregnancy_history_table.fxml"));
                                    Parent pregnancyHistoryRoot = pregnancyHistoryLoader.load();
                                    com.maternacare.controller.PregnancyHistoryTableController pregnancyHistoryController = pregnancyHistoryLoader
                                            .getController();

                                    if (pregnancyHistoryController != null) {
                                        formController.setPregnancyHistoryTableController(pregnancyHistoryController);
                                        System.out.println("Pregnancy history controller set successfully");
                                    } else {
                                        System.err.println("Failed to get pregnancy history controller");
                                    }
                                } catch (Exception ex) {
                                    System.err.println(
                                            "Error setting up pregnancy history controller: " + ex.getMessage());
                                    ex.printStackTrace();
                                }
                            } catch (Exception ex) {
                                System.err.println("Error checking form fields: " + ex.getMessage());
                                ex.printStackTrace();
                            }
                        } else {
                            System.err.println("Form controller is null!");
                        }
                    } catch (Exception ex) {
                        System.err.println("Error adding maternalFormRoot to contentContainer: " + ex.getMessage());
                        ex.printStackTrace();
                        if (loginController != null) {
                            loginController.setWrongLogInText("Error displaying Maternal Form: " + ex.getMessage());
                        }
                    }
                } else {
                    System.err.println("maternalFormRoot is null, cannot add to contentContainer.");
                    if (loginController != null) {
                        loginController.setWrongLogInText("Error: Maternal Form not loaded.");
                    }
                }
                updateButtonStyles(maternalFormBtn, dashboardBtn, maternalRecordsBtn);
            });

            maternalRecordsBtn.setOnAction(e -> {
                System.out.println("Maternal Records button clicked. Clearing contentContainer...");
                contentContainer.getChildren().clear();
                try {
                    System.out.println("Attempting to load maternal_records.fxml for display...");
                    // No need to reload recordsRoot if it's already pre-loaded
                    // Use the instance variable maternalRecordsRoot directly
                    if (maternalRecordsRoot != null) {
                        contentContainer.getChildren().add(maternalRecordsRoot);
                        System.out.println("recordsRoot added to contentContainer.");
                    } else {
                        System.err.println("maternalRecordsRoot is null, cannot add to contentContainer.");
                        if (loginController != null) {
                            loginController.setWrongLogInText("Error: Maternal Records not loaded.");
                        }
                    }
                    // Ensure controllers are connected even when reloading
                    if (formController != null && recordsController != null) {
                        formController.setRecordsController(recordsController);
                        recordsController.setFormController(formController);
                        System.out.println("Controllers connected after reloading maternal records.");
                    } else {
                        System.err
                                .println("Controllers not available after reloading maternal records, cannot connect.");
                    }
                } catch (Exception ex) { // Catch Exception to catch any potential errors
                    System.err.println("Failed to load maternal_records.fxml for display: " + ex.getMessage());
                    ex.printStackTrace();
                    // Optionally show an error message to the user
                    if (loginController != null) {
                        loginController.setWrongLogInText("Error loading Maternal Records: " + ex.getMessage());
                    }
                }
                updateButtonStyles(maternalRecordsBtn, dashboardBtn, maternalFormBtn);
            });

            sidebar.getChildren().addAll(dashboardBtn, maternalFormBtn, maternalRecordsBtn);

            // Set initial button style (Dashboard is default)
            updateButtonStyles(dashboardBtn, maternalFormBtn, maternalRecordsBtn);

            // Load and set initial content (Dashboard) into the content container
            System.out.println("Attempting to load initial dashboard.fxml...");
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
                Parent dashboardRoot = loader.load();
                System.out.println("Successfully loaded initial dashboard.fxml.");
                dashboardRoot.getStylesheets().add(getClass().getResource("/styles/dashboard.css").toExternalForm());
                DashboardController dashboardController = loader.getController();
                dashboardController.setMainApplication(this);
                System.out.println("Adding initial dashboardRoot to contentContainer.");
                contentContainer.getChildren().add(dashboardRoot);
                System.out.println("Initial dashboardRoot added to contentContainer.");
            } catch (IOException ex) {
                System.out.println("Failed to load initial dashboard.fxml: " + ex.getMessage());
                ex.printStackTrace();
                if (loginController != null) {
                    loginController.setWrongLogInText("Error loading initial dashboard: " + ex.getMessage());
                }
            }
        }

        // If mainLayout is already loaded, just ensure contentContainer has the current
        // view
        if (primaryStage.getScene() != mainScene) {
            primaryStage.setScene(mainScene);
            primaryStage.setMaximized(true); // Ensure dashboard is maximized
        }
    }

    private Button createNavButton(String text) {
        Button button = new Button(text);
        button.setMaxWidth(Double.MAX_VALUE);
        button.getStyleClass().add("nav-button");
        return button;
    }

    private void updateButtonStyles(Button selected, Button... others) {
        selected.getStyleClass().add("selected");
        for (Button other : others) {
            other.getStyleClass().remove("selected");
        }
    }

    public void setContent(javafx.scene.Node node) {
        contentContainer.getChildren().setAll(node);
    }

    public Parent getMaternalRecordsRoot() {
        return maternalRecordsRoot;
    }

    public static void main(String[] args) {
        launch(args);
    }
}