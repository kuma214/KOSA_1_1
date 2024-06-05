package sw1.hosp;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PC_Find {
    @FXML
    private TextField nameField;
    @FXML
    private TextField birthDateField;

    private Stage dialogStage;
    private boolean addClicked = false;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isCancelClicked() {
        return addClicked;
    }

    @FXML
    private void handleAddPatient() {
        String name = nameField.getText();
        String birthDateString = birthDateField.getText();

        if (isInputValid()) {
            String birthDate = String.valueOf(LocalDate.parse(birthDateString, DateTimeFormatter.ISO_DATE));
            Patient patient = DatabaseUtil.getPatientByNameAndBirthDate(name, birthDate);
            if (patient != null) {
                //ReceptionController.getInstance().addPatientToList(patient);
                addClicked = true;
                dialogStage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Patient Not Found");
                alert.setHeaderText(null);
                alert.setContentText("No patient found with the provided name and birth date.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

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
