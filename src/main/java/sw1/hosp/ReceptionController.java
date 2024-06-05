package sw1.hosp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReceptionController {

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


    // 싱글톤 인스턴스
    private static ReceptionController instance;

    public ReceptionController() {
        instance = this;
    }

    // 다른 클래스에서 환자 리스트에 환자 목록 올리기 위해 인스턴스 호출
    public static ReceptionController getInstance() {
        if (instance == null) {
            instance = new ReceptionController();
        }
        return instance;
    }

    // 환자 목록에 환자를 추가하는 메소드
    public void addPatientToList(Patient patient) {
        patientList.add(patient);

        // Bind the table's items to the patient list
        patientTable.setItems(patientList);
        DatabaseUtil.addIDlist(patient);
    }

    // 환자 목록에서 특정 환자를 제거하는 메소드 이름과 생년월일을 비교한다
    public void removePatientById(int id) {
        Patient patientToRemove = null;
        for (Patient patient : patientList) {
            if (patient.getId() == id) {
                patientToRemove = patient;
                break;
            }
        }
        if (patientToRemove != null) {
            patientList.remove(patientToRemove);
            //DatabaseUtil.deleteIDlist(id);
        }
        DatabaseUtil.deleteIDlist(id);
        initialize();
    }

    @FXML
    public void initialize() {
        // Initialize the patient table with the columns
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        columnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        columnContact.setCellValueFactory(new PropertyValueFactory<>("contact"));

        // Load patients from database
        loadPatients();
    }

    private void loadPatients() {
        // Implementation to load patients from the database
        ObservableList<Patient> patients = FXCollections.observableArrayList(DatabaseUtil.getPatients());
        patientTable.setItems(patients);
    }


//    @FXML
//    private void handleRegisterPatient() {
//        // 환자 등록 로직 구현
//        // 예: 사용자 입력을 받아 새로운 환자를 추가
//        int newId = patientList.size() + 1;
//        Patient newPatient = new Patient(newId, "New Patient", "2000-01-01", "789 Elm St", "555-9999", "Dermatology");
//        patientList.add(newPatient);
//    }

    // 환자 불러오기 창으로 이동 = PC_Find
    @FXML
    private void handleFindRegistration() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sw1/hosp/PatientFindRegistration.fxml"));
            Stage dialogStage = new Stage();
            dialogStage.setTitle("접수 환자 추가");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(loader.load());
            dialogStage.setScene(scene);

            PC_Find controller = loader.getController();
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not open the cancel patient registration dialog");
            alert.setContentText("Please try again later.");
            alert.showAndWait();
        }
    }

    // 환자 등록취소 창으로 이동 = PC_Cancel
    @FXML
    private void handleCancelRegistration() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sw1/hosp/PatientCancelRegistration.fxml"));
            Stage dialogStage = new Stage();
            dialogStage.setTitle("환자 접수 취소");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(loader.load());
            dialogStage.setScene(scene);

            PC_Cancel controller = loader.getController();
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();


        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not open the cancel patient registration dialog");
            alert.setContentText("Please try again later.");
            alert.showAndWait();
        }
    }
}