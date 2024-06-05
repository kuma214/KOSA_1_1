module sw1.hosp {
    requires javafx.controls;
    requires javafx.fxml;


    opens sw1.hosp to javafx.fxml;
    exports sw1.hosp;
}