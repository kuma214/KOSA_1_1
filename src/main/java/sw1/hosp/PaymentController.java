package sw1.hosp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class PaymentController {
    @FXML
    private TableView<Patient> patientTable;
    @FXML
    private TableColumn<Patient, Integer> columnId;
    @FXML
    private TableColumn<Patient, String> columnName;
    @FXML
    private TableColumn<Patient, String> columnBirthDate;
    @FXML
    private TableColumn<Patient, String> columnAddress;
    @FXML
    private TableColumn<Patient, String> columnContact;

    private ObservableList<Patient> patientList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        columnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        columnContact.setCellValueFactory(new PropertyValueFactory<>("contact"));

        // Load patients from database
        loadPatients();

        // Add click event handler for table rows
        patientTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !patientTable.getSelectionModel().isEmpty()) {
                Patient selectedPatient = patientTable.getSelectionModel().getSelectedItem();
                //PDF_printer.printPDF(selectedPatient);
                showPatientDetails(selectedPatient);
            }
        });
    }

    private void loadPatients() {
        // Implementation to load patients from the database
        ObservableList<Patient> patients = FXCollections.observableArrayList(DatabaseUtil.getPatients());
        patientTable.setItems(patients);
    }

    private void showPatientDetails(Patient patient) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MedicalRecordList.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Patient Details");
            stage.setScene(new Scene(loader.load()));
            stage.initModality(Modality.APPLICATION_MODAL);

            // Pass the selected patient to the details controller
            PrintMedicalRecord controller = loader.getController();
            controller.setPatient(patient);

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleFild(ActionEvent actionEvent) {
        initialize();
    }
}
