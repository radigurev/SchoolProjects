module com.example.gausscalculator {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.gausscalculator to javafx.fxml;
    exports com.example.gausscalculator;
}