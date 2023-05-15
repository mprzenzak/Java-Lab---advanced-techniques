module com.mprzenzak.lab09 {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.xml.bind;


    opens com.mprzenzak.lab09 to javafx.fxml;
    opens com.mprzenzak.lab09.Models to javafx.fxml, jakarta.xml.bind, javafx.base;
    exports com.mprzenzak.lab09;
}