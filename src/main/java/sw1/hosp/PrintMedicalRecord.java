package sw1.hosp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class PrintMedicalRecord {

    @FXML
    private TableView<PrescriptionDate> patientTable;

    @FXML
    private TableColumn<PrescriptionDate, Date> columnId;

    private Patient patient;

    @FXML
    public void initialize() {
        columnId.setCellValueFactory(new PropertyValueFactory<>("date"));

        patientTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !patientTable.getSelectionModel().isEmpty()) {
                Date selectedDate = patientTable.getSelectionModel().getSelectedItem().getDate();
                try {
                    PDF_printer.printPDF(patient, selectedDate);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public static class PrescriptionDate {
        private final Date date;

        public PrescriptionDate(Date date) {
            this.date = date;
        }

        public Date getDate() {
            return date;
        }
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
        loadPatientData();
    }

    private void loadPatientData() {
        if (patient != null) {
            List<Date> prescriptionDates = DatabaseUtil.getPatientMedicalRecord(patient);
            ObservableList<PrescriptionDate> data = FXCollections.observableArrayList();
            for (Date date : prescriptionDates) {
                data.add(new PrescriptionDate(date));
            }
            patientTable.setItems(data);
        }
    }
}
