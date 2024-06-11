package sw1.hosp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            GridPane root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Login Application");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setOnCloseRequest(event -> {
            System.out.println("종료합니다");
            DatabaseUtil.setCommitDB();

        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}