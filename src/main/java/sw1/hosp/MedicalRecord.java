package sw1.hosp;

import java.util.Date;

public class MedicalRecord {
    private int medicalRecordId;
    private int patientId;
    private int employeeId;
    private Date recordDate;
    private String recordNotes;

    public MedicalRecord(int mrid, String patientName, String mrdrname, String mrdate, String diagnosis, String notes) {
    }

    public MedicalRecord() {

    }

    // Getters and setters
    public int getMedicalRecordId() {
        return medicalRecordId;
    }

    public void setMedicalRecordId(int medicalRecordId) {
        this.medicalRecordId = medicalRecordId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public String getRecordNotes() {
        return recordNotes;
    }

    public void setRecordNotes(String recordNotes) {
        this.recordNotes = recordNotes;
    }
}
