module com.atm.atm2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    opens com.atm.atm2 to javafx.fxml;
    exports com.atm.atm2;
}