package sw1.hosp;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Dr_WorkplaceController {
    @FXML
    private Label Id;
    @FXML
    private Label Name;
    @FXML
    private Label Date;
    @FXML
    private Label Address;
    @FXML
    private Label number;

    public void setPatient(Patient patient) {
        //Id.setText(String.valueOf(patient.getId()));
        Name.setText(patient.getName());
        Date.setText(patient.getBirthDate());
        Address.setText(patient.getAddress());
        number.setText(patient.getContact());
    }
}
