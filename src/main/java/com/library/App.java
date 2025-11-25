package com.library;

import com.library.db.DBConnector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // CHANGED: Load LandingView.fxml instead of LoginView.fxml
        URL fxmlLocation = getClass().getResource("/com/library/view/LandingView.fxml");
        
        if (fxmlLocation == null) {
            System.err.println("CRITICAL ERROR: LandingView.fxml not found!");
            System.err.println("Make sure it is in: src/main/resources/com/library/view/");
            System.exit(1);
        }

        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        stage.setTitle("University Library - Welcome");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        DBConnector.getInstance().close();
        super.stop();
    }

    public static void main(String[] args) {
        launch();
    }
}