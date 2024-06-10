package sw1.hosp;

public class Medication {
    private int medicationId;
    private String medicationName;


    public Medication(int medicationId, String medicationName)
    {
        this.medicationId = medicationId;
        this.medicationName = medicationName;

    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public int getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(int medicationId) {
        this.medicationId = medicationId;
    }
}
