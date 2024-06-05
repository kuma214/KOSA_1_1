package sw1.hosp;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    @FXML
    private void handleLoginButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (DatabaseUtil.authenticate(username, password)) {
            messageLabel.setText("Login successful!");
        } else {
            messageLabel.setText("Login failed!");
        }
    }
}