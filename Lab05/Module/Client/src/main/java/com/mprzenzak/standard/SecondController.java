package com.mprzenzak.standard;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class SecondController {
    @FXML
    public Label errorLabel;
    @FXML
    private VBox rootVBox;
    @FXML
    TableView<Result> resultsTableView;
    @FXML
    private TableColumn<Result, String> resultColumn;
    @FXML
    private TableColumn<Result, Integer> trueColumn;
    @FXML
    private TableColumn<Result, Integer> falseColumn;
    @FXML
    private TableColumn<Result, Integer> sumaColumn;
    @FXML
    private Label factorValue;
    @FXML
    private Label observedComplianceValue;
    private ObservableList<Controller.Request> requests;
    private List<Boolean> randomResponses;

    public void initialize() {
        resultColumn.setCellValueFactory(new PropertyValueFactory<>("result"));
        trueColumn.setCellValueFactory(new PropertyValueFactory<>("yes"));
        falseColumn.setCellValueFactory(new PropertyValueFactory<>("no"));
        sumaColumn.setCellValueFactory(new PropertyValueFactory<>("sum"));
    }

    @FXML
    public void handleBackButton(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mprzenzak/standard/main_layout.fxml"));
            AnchorPane firstRoot = loader.load();
            Controller controller = loader.getController();
            controller.setRequests(requests);
            controller.getTableView().setItems(requests);
            controller.setRandomResponses(randomResponses);
            Scene scene = rootVBox.getScene();
            scene.setRoot(firstRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class Result {
        private String result;
        private IntegerProperty yes;
        private IntegerProperty no;
        private IntegerProperty sum;

        public Result(String result, int yes, int no, int sum) {
            this.result = result;
            this.yes = new SimpleIntegerProperty(yes);
            this.no = new SimpleIntegerProperty(no);
            this.sum = new SimpleIntegerProperty(sum);
        }

        public String getResult() {
            return result;
        }

        public Integer getYes() {
            return yes.get();
        }

        public IntegerProperty yesProperty() {
            return yes;
        }

        public Integer getNo() {
            return no.get();
        }

        public IntegerProperty noProperty() {
            return no;
        }

        public Integer getSum() {
            return sum.get();
        }

        public IntegerProperty sumProperty() {
            return sum;
        }
    }

    public Label getFactorValue() {
        return factorValue;
    }

    public Label getErrorLabel() {
        return errorLabel;
    }

    public Label getObservedComplianceValue() {
        return observedComplianceValue;
    }

    public void setRequests(ObservableList<Controller.Request> requests) {
        this.requests = requests;
    }

    public void setRandomResponses(List<Boolean> randomResponses) {
        this.randomResponses = randomResponses;
    }
}
