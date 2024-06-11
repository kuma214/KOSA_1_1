package sw1.hosp;

public class Diagnosis {
    private int diagnosis_id;
    private String diagnosis_name;

    public Diagnosis(int diagnosisId, String diagnosisName) {
        this.diagnosis_id = diagnosisId;
        this.diagnosis_name = diagnosisName;
    }

    public int getDiagnosis_id() {
        return diagnosis_id;
    }

    public void setDiagnosis_id(int diagnosis_id) {
        this.diagnosis_id = diagnosis_id;
    }

    public String getDiagnosis_name() {
        return diagnosis_name;
    }

    public void setDiagnosis_name(String diagnosis_name) {
        this.diagnosis_name = diagnosis_name;
    }
}
