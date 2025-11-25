package com.library.view;

    import com.library.model.Member;
    import com.library.viewmodel.LoginViewModel;
    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.Parent;
    import javafx.scene.Scene;
    import javafx.scene.control.Label;
    import javafx.scene.control.PasswordField;
    import javafx.scene.control.TextField;
    import javafx.stage.Stage;

    import java.io.IOException;

    public class LoginController {
        @FXML private TextField usernameField;
        @FXML private PasswordField passwordField;
        @FXML private Label messageLabel;

        private LoginViewModel viewModel;

        @FXML
        public void initialize() {
            viewModel = new LoginViewModel();

            // Two-way binding (or one-way to VM)
            viewModel.usernameProperty().bind(usernameField.textProperty());
            viewModel.passwordProperty().bind(passwordField.textProperty());
            messageLabel.textProperty().bind(viewModel.loginMessageProperty());
        }

        @FXML
        private void handleLogin() {
            if (viewModel.login()) {
                openMainView(viewModel.authenticatedMemberProperty().get());
                closeStage();
            }
        }

        private void openMainView(Member member) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
                Parent root = loader.load();
                
                // Pass Member to MainController -> MainViewModel
                MainController mainController = loader.getController();
                mainController.initData(member);
                
                Stage stage = new Stage();
                stage.setTitle("Library Management System");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        private void closeStage() {
            ((Stage) usernameField.getScene().getWindow()).close();
        }
    }
