package sw1.hosp;

public class MedicaRecordMedication {
    private int medication_id;
    private String medication_dosage;

    public MedicaRecordMedication(int medication_id, String medication_dosage)
    {
        this.medication_id=medication_id;
        this.medication_dosage=medication_dosage;
    }

    public int getMedication_id() {
        return medication_id;
    }

    public void setMedication_id(int medication_id) {
        this.medication_id = medication_id;
    }

    public String getMedication_dosage() {
        return medication_dosage;
    }

    public void setMedication_dosage(String medication_dosage) {
        this.medication_dosage = medication_dosage;
    }
}
