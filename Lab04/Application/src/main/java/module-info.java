module com.mprzenzak.application {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.mprzenzak.application to javafx.fxml;
    exports com.mprzenzak.application;
    exports com.mprzenzak.gui.controllers;
    opens com.mprzenzak.gui.controllers to javafx.fxml;
    exports com.mprzenzak;
    opens com.mprzenzak to javafx.fxml;
    exports com.mprzenzak.processing;
}