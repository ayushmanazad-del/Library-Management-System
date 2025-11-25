package com.library.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.io.IOException;

public class LandingController {

    @FXML private Button enterButton;

    @FXML
    private void handleEnter() {
        try {
            // Load the Login View
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/library/view/LoginView.fxml"));
            Parent root = loader.load();
            
            // Get the current stage (window)
            Stage stage = (Stage) enterButton.getScene().getWindow();
            
            // Switch the scene
            Scene scene = new Scene(root);
            stage.setTitle("LMS - Login");
            stage.setScene(scene);
            stage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading LoginView.fxml");
        }
    }
}
