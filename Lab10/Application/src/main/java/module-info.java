module com.mprzenzak.application {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.mprzenzak.cryptosystem;

    opens com.mprzenzak.application to javafx.fxml;
    exports com.mprzenzak.application to javafx.graphics, javafx.fxml;
}
