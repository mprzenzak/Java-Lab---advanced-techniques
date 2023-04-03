module com.mprzenzak.standard {
    uses com.mprzenzak.api.AnalysisService;
    requires com.mprzenzak.api;
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mprzenzak.standard to javafx.fxml;
    exports com.mprzenzak.standard;
}
