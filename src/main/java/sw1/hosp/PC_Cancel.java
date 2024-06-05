package sw1.hosp;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PC_Cancel {

    @FXML
    private TextField nameField;
    @FXML
    private TextField birthDateField;

    private Stage dialogStage;
    private boolean cancelClicked = false;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isCancelClicked() {
        return cancelClicked;
    }

    @FXML
    private void handleCancelRegistration() {
        String name = nameField.getText();
        String birthDateString = birthDateField.getText();

        if (isInputValid()) {
            String birthDate = String.valueOf(LocalDate.parse(birthDateString, DateTimeFormatter.ISO_DATE));

            int id = DatabaseUtil.getPatientID(name, birthDate);
            ReceptionController.getInstance().removePatientById(id);



            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cancel Patient Registration");
            alert.setHeaderText(null);
            alert.setContentText("Patient registration cancelled successfully.");
            alert.showAndWait();

            cancelClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleClose() {
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
