package sw1.hosp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Dr_WorkplaceController {
    @FXML private TextField name;
    @FXML private TextField birthDate;
    @FXML private TextField textdrname;
    @FXML private TextField address;
    @FXML private TextField contact;
    @FXML private TextField textDescription;

    @FXML private TableColumn<MedicalRecord,Integer> medicalrecordId;
    @FXML private TableColumn<MedicalRecord,String> drName;
    @FXML private TableColumn<MedicalRecord,String> date;
    @FXML private TableColumn<MedicalRecord,String> notes;
    @FXML private TableColumn<MedicalRecord,String> diagnosisName;

    @FXML private TableColumn<Medication,Integer> medicationId;
    @FXML private TableColumn<Medication,String> medicationName;
    @FXML private TableColumn<MedicaRecordMedication,String>mediaction_dosage;
    @FXML private TableColumn<Symptom,Integer> symptomId;
    @FXML private TableColumn<Symptom,String> symptomName;

    @FXML private TableColumn<Diagnosis,Integer> diagnosis_id;
    @FXML private TableColumn<Diagnosis,String> diagnosis_name;

    @FXML private TableView<Symptom> symptom_view;
    @FXML private TableView<Medication> medication_view;
    @FXML private TableView<MedicalRecord> medicalrecord_view;
    @FXML private TableView<Diagnosis> diagnosis_view;


    public static ObservableList<Symptom> symptoms = FXCollections.observableArrayList();
    public static ObservableList<Medication> medications = FXCollections.observableArrayList();
    public static ObservableList<Diagnosis> diagnosis = FXCollections.observableArrayList();

    private  Patient DRpatient;
    private Stage stage;
    @FXML
    public void initialize() {
        // 진료기록
        medicalrecordId.setCellValueFactory(new PropertyValueFactory<>("medicalrecordId"));
        drName.setCellValueFactory(new PropertyValueFactory<>("drName"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        notes.setCellValueFactory(new PropertyValueFactory<>("notes"));
        diagnosisName.setCellValueFactory(new PropertyValueFactory<>("diagnosisName"));
        //의약품
        medicationId.setCellValueFactory(new PropertyValueFactory<>("medicationId"));
        medicationName.setCellValueFactory(new PropertyValueFactory<>("medicationName"));

        //증상
        symptomId.setCellValueFactory(new PropertyValueFactory<>("symptomId"));
        symptomName.setCellValueFactory(new PropertyValueFactory<>("symptomName"));

        diagnosis_id.setCellValueFactory(new PropertyValueFactory<>("diagnosis_id"));
        diagnosis_name.setCellValueFactory(new PropertyValueFactory<>("diagnosis_name"));
                //loadMedicalrecord();
        symptom_view.setItems(symptoms);
        medication_view.setItems(medications);
        diagnosis_view.setItems(diagnosis);


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
    public void handleFindDiagnosis() {
        try {
            Pane root = FXMLLoader.load(getClass().getResource("DiagnosisSearch.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            DiagnosisSearchController.setStage(stage);
            stage.showAndWait();
            diagnosis.add(DiagnosisSearchController.getDiagnosis());
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
        loadMedicalrecord();
        initialize();
    }
    public void setDoctorName(String str)
    {
        textdrname.setText(str);
    }

    public void InsertMedicalRecord(){
        String doctor = textdrname.getText();
        int diagId = diagnosis.getFirst().getDiagnosis_id();
        String patient = name.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        LocalDate ldate = LocalDate.now();
        String description = textDescription.getText();

        int[] intArray = new int[medications.size()];
        for(int i=0; i< medications.size(); i++)
        {
            intArray[i] = medications.get(i).getMedicationId();
        }

        DatabaseUtil.setInsertRecord(doctor,diagId,patient,ldate, description);
        DatabaseUtil.setMedicalRecordMedication(intArray);
        stage.close();
    }
    public void cancelhandle(){
        stage.close();
    }
    public void setStage(Stage stage)
    {
        this.stage=stage;
    }
}
