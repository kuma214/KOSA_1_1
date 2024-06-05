package sw1.hosp;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PatientRegistrationController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField birthDateField;
    @FXML
    private TextField genderField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField contactField;

    private Stage dialogStage;
    private boolean registerClicked = false;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isRegisterClicked() {
        return registerClicked;
    }

    @FXML
    private void handleRegisterPatient() {
        String name = nameField.getText();
        String birthDateString = birthDateField.getText();
        String gender = genderField.getText();
        String address = addressField.getText();
        String contact = contactField.getText();

        if (isInputValid()) {
            LocalDate birthDate = LocalDate.parse(birthDateString, DateTimeFormatter.ISO_DATE);
            DatabaseUtil.addPatient(name, birthDate, gender, address, contact);

            Patient patient = DatabaseUtil.getPatientByNameAndBirthDate(name, birthDateString);
            ReceptionController.getInstance().addPatientToList(patient);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Register Patient");
            alert.setHeaderText(null);
            alert.setContentText("Patient registered successfully.");
            alert.showAndWait();

            registerClicked = true;
            dialogStage.close();
        }
    }

    // 취소 누르면 꺼짐
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    // 값이 전부 입력 되었는지 확인하는 메소드
    private boolean isInputValid() {
        String errorMessage = "";

        if (nameField.getText() == null || nameField.getText().isEmpty()) {
            errorMessage += "No valid name!\n";
        }
        if (birthDateField.getText() == null || birthDateField.getText().isEmpty()) {
            errorMessage += "No valid birth date!\n";
        }
        if (genderField.getText() == null || genderField.getText().isEmpty()) {
            errorMessage += "No valid gender!\n";
        }
        if (addressField.getText() == null || addressField.getText().isEmpty()) {
            errorMessage += "No valid address!\n";
        }
        if (contactField.getText() == null || contactField.getText().isEmpty()) {
            errorMessage += "No valid contact!\n";
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