package sw1.hosp;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PC_Cancel {

    @FXML
    private TextField nameField;
    @FXML
    private TextField idField;

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
        String idString = idField.getText();

        if (isInputValid()) {
            int id = Integer.parseInt(idString);
            DatabaseUtil.deletePatient(id, name);

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

    private boolean isInputValid() {
        String errorMessage = "";

        if (nameField.getText() == null || nameField.getText().isEmpty()) {
            errorMessage += "No valid name!\n";
        }
        if (idField.getText() == null || idField.getText().isEmpty()) {
            errorMessage += "No valid patient ID!\n";
        } else {
            try {
                Integer.parseInt(idField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid patient ID (must be an integer)!\n";
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
