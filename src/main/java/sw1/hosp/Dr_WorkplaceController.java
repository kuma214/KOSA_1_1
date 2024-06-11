package sw1.hosp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Dr_WorkplaceController {
    @FXML private TextField name;
    @FXML private TextField birthDate;
    @FXML private TextField drname;
    @FXML private TextField address;
    @FXML private TextField contact;

    @FXML private TableColumn<MedicalRecord,Integer> medicalrecordId;
    @FXML private TableColumn<MedicalRecord,String> drName;
    @FXML private TableColumn<MedicalRecord,String> date;
    @FXML private TableColumn<MedicalRecord,String> notes;
    @FXML private TableColumn<MedicalRecord,String> diagnosisName;

    @FXML private TableColumn<Medication,Integer> medicationId;
    @FXML private TableColumn<Medication,String> medicationName;

    @FXML private TableColumn<Symptom,Integer> symptomId;
    @FXML private TableColumn<Symptom,String> symptomName;

    @FXML private TableView<Symptom> symptom_view;
    @FXML private TableView<Medication> medication_view;
    @FXML private TableView<MedicalRecord> medicalrecord_view;


    public static ObservableList<Symptom> symptoms = FXCollections.observableArrayList();
    public static ObservableList<Medication> medications = FXCollections.observableArrayList();

    public static Patient DRpatient;

    @FXML
    public void initialize() {
        medicalrecordId.setCellValueFactory(new PropertyValueFactory<>("medicalrecordId"));
        drName.setCellValueFactory(new PropertyValueFactory<>("drName"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        notes.setCellValueFactory(new PropertyValueFactory<>("notes"));
        diagnosisName.setCellValueFactory(new PropertyValueFactory<>("diagnosisName"));

        medicationId.setCellValueFactory(new PropertyValueFactory<>("medicationId"));
        medicationName.setCellValueFactory(new PropertyValueFactory<>("medicationName"));
        symptomId.setCellValueFactory(new PropertyValueFactory<>("symptomId"));
        symptomName.setCellValueFactory(new PropertyValueFactory<>("symptomName"));

        //loadMedicalrecord();
        symptom_view.setItems(symptoms);
        medication_view.setItems(medications);


    }

    public void handleFindSymptom(){
        try {
            Pane root = FXMLLoader.load(getClass().getResource("SymptomSearch.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            sw1.hosp.SymptomSearchController.setStage(stage);
            stage.showAndWait();

            symptoms.add(sw1.hosp.SymptomSearchController.getSymptom());
            initialize();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void handleFindMedication() {
        try {
            Pane root = FXMLLoader.load(getClass().getResource("MedicationSearch.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            MedicationSearchController.setStage(stage);
            stage.showAndWait();
            medications.add(MedicationSearchController.getMedication());
            initialize();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadMedicalrecord(){
        ObservableList<MedicalRecord> mr = FXCollections.observableArrayList(DatabaseUtil.loadDataFromMedicalRecord(DRpatient));
        medicalrecord_view.setItems(mr);
    }

    public void setPatient(Patient patient) {
        //Id.setText(String.valueOf(patient.getId()));
        DRpatient = patient;
        name.setText(patient.getName());
        birthDate.setText(patient.getBirthDate());
        address.setText(patient.getAddress());
        contact.setText(patient.getContact());
    }
}
