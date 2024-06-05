package sw1.hosp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PC_Find {
    @FXML
    private TextField nameField;
    @FXML
    private TextField birthDateField;

    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void handleAddPatient() {
        String name = nameField.getText();
        String birthDateString = birthDateField.getText();

        if (isInputValid()) {   // 입력값이 비어있는지 확인
            String birthDate = String.valueOf(LocalDate.parse(birthDateString, DateTimeFormatter.ISO_DATE));
            Patient patient = DatabaseUtil.getPatientByNameAndBirthDate(name, birthDate);
            if (patient != null) {
                System.out.println("patient:"+patient);
                ReceptionController.getInstance().addPatientToList(patient);
                dialogStage.close();
            } else {    // 만약 해당하는 환자가 없을 경우 환자 추가 화면으로 이동
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/sw1/hosp/PatientRegistration.fxml"));
                    Stage dialogStage = new Stage();
                    dialogStage.setTitle("Register Patient");
                    dialogStage.initModality(Modality.WINDOW_MODAL);
                    Scene scene = new Scene(loader.load());
                    dialogStage.setScene(scene);

                    PatientRegistrationController controller = loader.getController();
                    controller.setDialogStage(dialogStage);

                    dialogStage.showAndWait();
                } catch (IOException e) {
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Could not open the register patient dialog");
                    alert.setContentText("Please try again later.");
                    alert.showAndWait();
                }
            }
        }
    }

    // 취소를 누를 경우 그냥 창이 꺼짐
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    // 입력시 값이 없는 것을 경고하기 위해 작성된 함수문
    // 환자 이름과 생년월이 입력된 경우 true 반환, 아닐경우 false 반환 후 경고문을 출력한다.
    private boolean isInputValid() {
        String errorMessage = "";

        if (nameField.getText() == null || nameField.getText().isEmpty()) {
            errorMessage += "No valid name!\n";
        }
        if (birthDateField.getText() == null || birthDateField.getText().isEmpty()) {
            errorMessage += "No valid birth date!\n";
        } else {
            try {
                LocalDate.parse(birthDateField.getText(), DateTimeFormatter.ISO_DATE);
            } catch (Exception e) {
                errorMessage += "No valid birth date. Use the format yyyy-MM-dd!\n";
            }
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
