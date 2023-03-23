module com.mprzenzak.application {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.mprzenzak.application to javafx.fxml;
    exports com.mprzenzak.application;
}