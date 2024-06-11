module sw1.hosp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires ojdbc8;


    opens sw1.hosp to javafx.fxml;
    exports sw1.hosp;
}