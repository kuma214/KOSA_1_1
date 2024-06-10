package sw1.hosp;

public class MedicalRecord {
    private int medicalrecordId;
    private String patientName;
    private String drName;
    private String date;
    private String diagnosisName;
    private String notes;

    public MedicalRecord(int medicalrecordId, String patientName, String drName, String date, String diagnosisName, String notes){
        this.medicalrecordId = medicalrecordId;
        this.drName = drName;
        this.patientName = patientName;
        this.date = date;
        this.diagnosisName = diagnosisName;
        this.notes = notes;
    }

    public int getMedicalrecordId() {
        return medicalrecordId;
    }

    public void setMedicalrecordId(int medicalrecordId) {
        this.medicalrecordId = medicalrecordId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDiagnosisName() {
        return diagnosisName;
    }

    public void setDiagnosisName(String diagnosisName) {
        this.diagnosisName = diagnosisName;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
