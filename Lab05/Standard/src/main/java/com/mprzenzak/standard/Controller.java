package com.mprzenzak.standard;

import com.mprzenzak.api.AnalysisException;
import com.mprzenzak.api.AnalysisService;
import com.mprzenzak.api.DataSet;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.*;

public class Controller {

    @FXML
    private VBox rootVBox;
    @FXML
    private TableView<Request> tableView;
    @FXML
    private TableColumn<Request, String> requestColumn;
    @FXML
    private TableColumn<Request, Boolean> decisionColumn;
    @FXML
    private ComboBox<String> factorTypeComboBox;
    private ObservableList<Request> requests;
    private ObservableList<SecondController.Result> results;
    private final ServiceLoader<AnalysisService> loader = ServiceLoader.load(AnalysisService.class);
    private List<Boolean> randomResponses;

    @FXML
    public void initialize() {
        requests = FXCollections.observableArrayList();
        String[] exampleRequests = {
                "Wniosek o dofinansowanie na zakup samochodu",
                "Wniosek o zburzenie tamy bobrów",
                "Wniosek o wydanie pozwolenia na budowę",
                "Wniosek o zwrot podatku",
                "Wniosek o dofinansowanie na zakup roweru",
                "Wniosek o zburzenie budynku",
                "Wniosek o wydanie pozwolenia na rozbiórkę budynku",
                "Wniosek o zwrot kosztów leczenia",
                "Wniosek o dofinansowanie remontu domu",
                "Wniosek o wydanie pozwolenia na budowę domu",
                "Wniosek o zezwolenie na organizację imprezy masowej",
                "Wniosek o zwrot kosztów szkolenia",
                "Wniosek o dofinansowanie zakupu sprzętu sportowego",
                "Wniosek o wydanie pozwolenia na wycinkę drzew",
                "Wniosek o zezwolenie na prowadzenie działalności gospodarczej",
                "Wniosek o zwrot kosztów podróży służbowej",
                "Wniosek o dofinansowanie zakupu komputera",
                "Wniosek o wydanie pozwolenia na budowę altanki ogrodowej",
                "Wniosek o zezwolenie na organizację wystawy",
                "Wniosek o zwrot kosztów szkoleń zawodowych",
                "Wniosek o dofinansowanie zakupu kosiarki do trawy",
                "Wniosek o wydanie pozwolenia na budowę hali sportowej",
                "Wniosek o zezwolenie na prowadzenie sprzedaży alkoholu",
                "Wniosek o zwrot kosztów wakacji rehabilitacyjnych",
                "Wniosek o dofinansowanie zakupu sprzętu medycznego",
                "Wniosek o wydanie pozwolenia na organizację pikniku",
                "Wniosek o zezwolenie na rozbiórkę wieży",
                "Wniosek o zwrot kosztów badań naukowych",
                "Wniosek o dofinansowanie zakupu mebli do biura",
                "Wniosek o wydanie pozwolenia na budowę drogi",
                "Wniosek o zezwolenie na organizację koncertu",
                "Wniosek o zwrot kosztów opieki nad osobą starszą",
                "Wniosek o dofinansowanie zakupu książek",
                "Wniosek o wydanie pozwolenia na budowę magazynu",
                "Wniosek o zezwolenie na prowadzenie wypożyczalni rowerów",
                "Wniosek o zwrot kosztów leków na receptę",
                "Wniosek o dofinansowanie zakupu sprzętu do fotografii",
                "Wniosek o wydanie pozwolenia na budowę basenu",
                "Wniosek o zezwolenie na organizację festiwalu",
                "Wniosek o zwrot kosztów szkoleń językowych",
                "Wniosek o dofinansowanie zakupu materiałów budowlanych",
                "Wniosek o wydanie pozwolenia na budowę parkingu",
                "Wniosek o zezwolenie na organizację targów",
                "Wniosek o zwrot kosztów leczenia dentystycznego",
                "Wniosek o dofinansowanie zakupu sprzętu muzycznego",
                "Wniosek o wydanie pozwolenia na budowę wiaty garażowej",
                "Wniosek o zezwolenie na prowadzenie sklepu internetowego",
                "Wniosek o zwrot kosztów opieki nad dzieckiem",
                "Wniosek o dofinansowanie zakupu sprzętu do kolarstwa",
                "Wniosek o wydanie pozwolenia na budowę obiektu sportowego",
                "Wniosek o zezwolenie na organizację zawodów sportowych",
                "Wniosek o zwrot kosztów kuracji odchudzającej",
                "Wniosek o dofinansowanie zakupu sprzętu do nurkowania",
                "Wniosek o wydanie pozwolenia na budowę kortów tenisowych",
                "Wniosek o zezwolenie na prowadzenie firmy transportowej",
                "Wniosek o zwrot kosztów leczenia choroby przewlekłej",
                "Wniosek o dofinansowanie zakupu sprzętu do jazdy na rolkach",
                "Wniosek o wydanie pozwolenia na budowę lodowiska",
                "Wniosek o zezwolenie na organizację pokazu mody",
                "Wniosek o zwrot kosztów terapii",
                "Wniosek o dofinansowanie zakupu materiałów biurowych",
                "Wniosek o wydanie pozwolenia na budowę ścieżki rowerowej",
                "Wniosek o zezwolenie na prowadzenie stoiska na jarmarku",
                "Wniosek o zwrot kosztów rehabilitacji po wypadku",
                "Wniosek o dofinansowanie zakupu sprzętu sportowego dla niepełnosprawnych",
                "Wniosek o wydanie pozwolenia na budowę obiektu hotelowego",
                "Wniosek o zezwolenie na organizację konferencji naukowej",
                "Wniosek o zwrot kosztów rehabilitacji po operacji",
                "Wniosek o dofinansowanie zakupu sprzętu do windsurfingu",
                "Wniosek o wydanie pozwolenia na budowę siłowni zewnętrznej",
                "Wniosek o zezwolenie na prowadzenie firmy cateringowej",
                "Wniosek o zwrot kosztów leczenia choroby nowotworowej",
                "Wniosek o dofinansowanie zakupu sprzętu do biegania",
                "Wniosek o wydanie pozwolenia na budowę parku linowego",
                "Wniosek o zezwolenie na organizację koncertu jazzowego",
                "Wniosek o zwrot kosztów pobytu w sanatorium",
                "Wniosek o dofinansowanie zakupu sprzętu do narciarstwa",
                "Wniosek o wydanie pozwolenia na budowę centrum handlowego",
                "Wniosek o zezwolenie na organizację festynu",
                "Wniosek o zwrot kosztów opieki nad osobą niepełnosprawną",
                "Wniosek o dofinansowanie zakupu sprzętu do żeglarstwa",
                "Wniosek o wydanie pozwolenia na budowę boiska sportowego",
                "Wniosek o zezwolenie na prowadzenie firmy szkoleniowej",
                "Wniosek o zwrot kosztów leczenia choroby psychicznej",
                "Wniosek o dofinansowanie zakupu sprzętu AGD",
                "Wniosek o wydanie pozwolenia na budowę drogi rowerowej",
                "Wniosek o zezwolenie na organizację konkursu literackiego",
                "Wniosek o zwrot kosztów badań lekarskich",
                "Wniosek o dofinansowanie zakupu sprzętu do narciarstwa biegowego",
                "Wniosek o wydanie pozwolenia na budowę obiektu rekreacyjnego",
                "Wniosek o zezwolenie na prowadzenie firmy szkoleniowej dla psów",
                "Wniosek o zwrot kosztów rehabilitacji po udarze",
                "Wniosek o dofinansowanie zakupu sprzętu AGD dla niepełnosprawnych",
                "Wniosek o wydanie pozwolenia na budowę toru kartingowego",
                "Wniosek o zezwolenie na organizację turnieju szachowego",
                "Wniosek o zwrot kosztów terapii uzależnień",
                "Wniosek o dofinansowanie zakupu sprzętu do fitnessu",
                "Wniosek o wydanie pozwolenia na budowę skateparku",
                "Wniosek o zezwolenie na prowadzenie sklepu z pamiątkami",
                "Wniosek o zwrot kosztów leczenia choroby serca",
                "Wniosek o dofinansowanie zakupu sprzętu wspinaczkowego",
                "Wniosek o wydanie pozwolenia na budowę elektrowni słonecznej",
                "Wniosek o zezwolenie na organizację konkursu wędkarskiego",
                "Wniosek o zwrot kosztów leczenia choroby autoimmunologicznej",
                "Wniosek o dofinansowanie zakupu sprzętu do turystyki",
                "Wniosek o wydanie pozwolenia na budowę placu zabaw",
                "Wniosek o zezwolenie na prowadzenie firmy cateringowej na evencie"
        };

        Random rand = new Random();
        for (int i = 0; i < 20; i++) {
            int index = rand.nextInt(exampleRequests.length);
            requests.add(new Request(exampleRequests[index], false));
        }

        tableView.setItems(requests);


        requestColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        decisionColumn.setCellValueFactory(cellData -> cellData.getValue().acceptProperty().asObject());

        decisionColumn.setCellFactory(column -> new TableCell<Request, Boolean>() {
            private final ImageView imageView = new ImageView();

            {
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                imageView.setFitWidth(20);
                imageView.setFitHeight(20);
            }

            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    imageView.setImage(new Image(Objects.requireNonNull(getClass().getResource(item ? "/icons/check.png" : "/icons/cross.png")).toExternalForm()));
                    setGraphic(imageView);
                    setOnMouseClicked(e -> {
                        Request request = getTableView().getItems().get(getIndex());
                        request.setAccept(!request.getAccept());
                    });
                }
            }
        });
    }

    @FXML
    private void handleNextButton(ActionEvent event) {
        if (randomResponses == null) {
            randomResponses = new ArrayList<>();
            for (int i = 0; i < requests.size(); i++) {
                randomResponses.add(new Random().nextBoolean());
            }
        }

        int tt = 0, tf = 0, ft = 0, ff = 0;
        for (int i = 0; i < requests.size(); i++) {
            boolean userAnswer = requests.get(i).getAccept();
            boolean randomAnswer = randomResponses.get(i);

            if (userAnswer && randomAnswer) {
                tt++;
            } else if (userAnswer) {
                tf++;
            } else if (randomAnswer) {
                ft++;
            } else {
                ff++;
            }
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mprzenzak/standard/second_layout.fxml"));
            AnchorPane secondRoot = loader.load();
            SecondController secondController = loader.getController();
            calculateFactor(tt, ft, tf, ff, secondController);

            results = FXCollections.observableArrayList(
                    new SecondController.Result("Tak", tt, ft, tt + ft),
                    new SecondController.Result("Nie", tf, ff, tf + ff),
                    new SecondController.Result("Suma", tt + tf, ft + ff, tt + tf + ft + ff)
            );
            secondController.resultsTableView.setItems(results);
            secondController.setRequests(requests);
            secondController.setRandomResponses(randomResponses);

            Label observedComplianceValueLabel = secondController.getObservedComplianceValue();
            observedComplianceValueLabel.setText(String.valueOf(calculateObservedComplianceValue(tt, ft, tf, ff)));

            Label factorValueLabel = secondController.getFactorValue();
            factorValueLabel.setText(getCalculatedFactor());

            Scene scene = rootVBox.getScene();
            scene.setRoot(secondRoot);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void calculateFactor(int tt, int ft, int tf, int ff, SecondController secondController) {
        DataSet dataSet = new DataSet();
        String[][] data = new String[2][2];
        data[0][0] = String.valueOf(tt);
        data[0][1] = String.valueOf(ft);
        data[1][0] = String.valueOf(tf);
        data[1][1] = String.valueOf(ff);
        dataSet.setData(data);

        AnalysisService analysisService;

        for (AnalysisService service : loader) {
            if (service.getName().equals(factorTypeComboBox.getValue() + "AnalysisService")) {
                analysisService = service;
                try {
                    analysisService.submit(dataSet);
                } catch (AnalysisException e) {
                    secondController.getErrorLabel().setText("Podczas przetwarzania danych wystąpił błąd.");
                }
                break;
            }
        }
    }

    private String getCalculatedFactor() {
        String displayValue = null;
        try {
            for (AnalysisService service : loader) {
                if (service.getName().equals(factorTypeComboBox.getValue() + "AnalysisService")) {
                    displayValue = String.valueOf(service.retrieve(true));
                    break;
                }
            }
        } catch (AnalysisException e) {
            e.printStackTrace();
        }
        return displayValue;
    }

    private double calculateObservedComplianceValue(int tt, int ft, int tf, int ff) {
        int total = tt + ft + tf + ff;
        return (double) (tt + ff) / total;
    }

    public static class Request {
        private final StringProperty name;
        private final BooleanProperty accept;

        public Request(String name, boolean accept) {
            this.name = new SimpleStringProperty(name);
            this.accept = new SimpleBooleanProperty(accept);
        }

        public StringProperty nameProperty() {
            return name;
        }

        public boolean getAccept() {
            return accept.get();
        }

        public BooleanProperty acceptProperty() {
            return accept;
        }

        public void setAccept(boolean accept) {
            this.accept.set(accept);
        }
    }

    public void setRandomResponses(List<Boolean> randomResponses) {
        this.randomResponses = randomResponses;
    }

    public TableView<Request> getTableView() {
        return tableView;
    }

    public void setRequests(ObservableList<Request> requests) {
        this.requests = requests;
    }
}
