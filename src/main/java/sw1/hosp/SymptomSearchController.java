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


public class SymptomSearchController {
    private static Symptom symptom;
    private static Stage sfstage;

    @FXML
    private TableView<Symptom> resultTable;
    @FXML
    private TableColumn<Symptom, Integer> symptomId;
    @FXML
    private TableColumn<Symptom, String> symptomName;
    @FXML
    private TextField searchField;

    public static void setStage(Stage stage) {
        sfstage = stage;
    }

    private void initialize(){
        symptomId.setCellValueFactory(new PropertyValueFactory<>("symptomId"));
        symptomName.setCellValueFactory(new PropertyValueFactory<>("symptomName"));
    }


    @FXML
    private void handleSymptomSearch(){
        initialize();
        String search = searchField.getText();

        ObservableList<Symptom> symptom_data = FXCollections.observableArrayList(DatabaseUtil.searchSymptom(search));

        resultTable.setItems(symptom_data);

        resultTable.setOnMouseClicked(event -> {
            if (event.getClickCount()==1){
                Symptom selectedSymptom = resultTable.getSelectionModel().getSelectedItem();
                setSymptom(selectedSymptom);
                sfstage.close();
            }
        });

    }

    public static Symptom getSymptom() {
        return symptom;
    }

    public static void setSymptom(Symptom symptom) {
        SymptomSearchController.symptom = symptom;
    }
}
