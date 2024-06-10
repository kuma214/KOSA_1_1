package sw1.hosp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class MedicationSearchController {
    private static Stage mfstage;
    private static Medication medication;
    @FXML
    private TableView<Medication> medicationTable;
    @FXML
    private TableColumn<Medication, Integer> medicationId;
    @FXML
    private TableColumn<Medication, String> medicationName;
    @FXML
    private TextField medisrcField;



    public static void setStage(Stage stage) {
        mfstage = stage;
    }
    private void initialize(){
        medicationId.setCellValueFactory(new PropertyValueFactory<>("medicationId"));
        medicationName.setCellValueFactory(new PropertyValueFactory<>("medicationName"));
    }

    @FXML
    private void handleSearchMedication(){
        initialize();
        String search = medisrcField.getText();

        ObservableList<Medication> medication_data = FXCollections.observableArrayList(DatabaseUtil.searchMedication(search));

        medicationTable.setItems(medication_data);

        medicationTable.setOnMouseClicked(event -> {
            if (event.getClickCount()==1){
                Medication selectedMedication = medicationTable.getSelectionModel().getSelectedItem();
                setMedication(selectedMedication);
                mfstage.close();
            }
        });
    }

    public static Medication getMedication() {
        return medication;
    }

    public static void setMedication(Medication medication) {
        MedicationSearchController.medication = medication;
    }
}
