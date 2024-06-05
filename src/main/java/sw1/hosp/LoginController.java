package sw1.hosp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        String userType = DatabaseUtil.authenticate(username, password);
        System.out.println(userType);
        if (userType != null) {
            loadDashboard(userType, event);
        } else {
            messageLabel.setText("Login failed!");
        }
    }

    private void loadDashboard(String userType, ActionEvent event) {
        try {
            String fxmlFile;
            if ("Doctor".equals(userType)) {
                fxmlFile = "/sw1/hosp/DoctorDashboard.fxml";
            } else {
                fxmlFile = "/sw1/hosp/GeneralDashboard.fxml";
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle(userType + " Dashboard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}