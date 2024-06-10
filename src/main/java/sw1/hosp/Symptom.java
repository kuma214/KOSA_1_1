package sw1.hosp;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Symptom {
    private int symptomId;
    private String symptomName;


    public Symptom(int symptomId, String symptomName)
    {
        this.symptomId = symptomId;
        this.symptomName = symptomName;

    }

    public int getSymptomId() {
        return symptomId;
    }

    public void setSymptomId(int symptomId) {
        this.symptomId = symptomId;
    }

    public String getSymptomName() {
        return symptomName;
    }

    public void setSymptomName(String symptomName) {
        this.symptomName = symptomName;
    }


//    private IntegerProperty symptomId;
//    private StringProperty symptomName;
//
//    public Symptom(int symptomId, String symptomName){
//        this.symptomId = new SimpleIntegerProperty(symptomId);
//        this.symptomName = new SimpleStringProperty(symptomName);
//    }
//
//    public int getSymptomId() {
//        return symptomId.get();
//    }
//
//    public IntegerProperty symptomIdProperty() {
//        return symptomId;
//    }
//
//    public void setSymptomId(int symptomId) {
//        this.symptomId.set(symptomId);
//    }
//
//    public String getSymptomName() {
//        return symptomName.get();
//    }
//
//    public StringProperty symptomNameProperty() {
//        return symptomName;
//    }
//
//    public void setSymptomName(String symptomName) {
//        this.symptomName.set(symptomName);
//    }
}
