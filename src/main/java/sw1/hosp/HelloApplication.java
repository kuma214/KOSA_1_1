package sw1.hosp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login Application");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        Label userLabel = new Label("Username:");
        grid.add(userLabel, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pwLabel = new Label("Password:");
        grid.add(pwLabel, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);

        Button loginButton = new Button("Login");
        grid.add(loginButton, 1, 3);

        Label messageLabel = new Label();
        grid.add(messageLabel, 1, 4);

        loginButton.setOnAction(e -> {
            String username = userTextField.getText();
            String password = pwBox.getText();

            if (authenticate(username, password)) {
                messageLabel.setText("Login successful!");
            } else {
                messageLabel.setText("Login failed!");
            }
        });

        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private boolean authenticate(String username, String password) {
        // 간단한 인증 로직
        // 실제 애플리케이션에서는 데이터베이스 또는 외부 서비스와 연동합니다.
        return "user".equals(username) && "pass".equals(password);
    }

    public static void main(String[] args) {
        launch(args);
    }
}