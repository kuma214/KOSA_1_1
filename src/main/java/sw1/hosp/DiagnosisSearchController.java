package sw1.hosp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sw1.hosp.Symptom;


public class DiagnosisSearchController {
    private static Diagnosis diagnosis;
    private static Stage dfstage;

    @FXML
    private TableView<Diagnosis> diagnosis_view;
    @FXML
    private TableColumn<Diagnosis, Integer> diagnosis_id;
    @FXML
    private TableColumn<Diagnosis, String> diagnosis_name;
    @FXML
    private TextField searchDiag;

    public static void setStage(Stage stage) {
        dfstage = stage;
    }

    private void initialize(){
        diagnosis_id.setCellValueFactory(new PropertyValueFactory<>("diagnosis_id"));
        diagnosis_name.setCellValueFactory(new PropertyValueFactory<>("diagnosis_name"));
    }


    @FXML
    private void handleDiagnosisSearch(){
        initialize();
        String search = searchDiag.getText();

        ObservableList<Diagnosis> diagnosis_data = FXCollections.observableArrayList(DatabaseUtil.searchDiagnosis(search));

        diagnosis_view.setItems(diagnosis_data);

        diagnosis_view.setOnMouseClicked(event -> {
            if (event.getClickCount()==1){
                Diagnosis selectedDiagnosis = diagnosis_view.getSelectionModel().getSelectedItem();
                setDiagnosis(selectedDiagnosis);
                dfstage.close();
            }
        });

    }

    public static Diagnosis getDiagnosis() {
        return diagnosis;
    }

    public static void setDiagnosis(Diagnosis diagnosis) {
        DiagnosisSearchController.diagnosis = diagnosis;
    }
}
