package com.mprzenzak.lab09;

import com.mprzenzak.lab09.Models.BipPoznanPl;
import com.mprzenzak.lab09.Models.InfoCard;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Main extends Application {
    private BipPoznanPl root;

    @FXML
    private VBox vbox;
    @FXML
    private TableView<InfoCard> table;
    @FXML
    private Button transformButton;
    @FXML
    private Button saveButton;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        loader.setController(this);
        vbox = loader.load();

        String[] columnsNames = {"id", "link", "data", "skrotOrganizacja", "komponentSrodowiska",
                "typKarty", "rodzajKarty", "nrWpisu", "znakSprawy", "daneWnioskodawcy"};

        int i = 0;
        for (TableColumn<InfoCard, ?> column : table.getColumns()) {
            TableColumn<InfoCard, String> stringColumn = (TableColumn<InfoCard, String>) column;
            stringColumn.setCellValueFactory(new PropertyValueFactory<>(columnsNames[i]));
            stringColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));

            final int index = i;
            stringColumn.setOnEditCommit(event -> {
                InfoCard infoCard = event.getRowValue();
                String newValue = event.getNewValue();
                switch (columnsNames[index]) {
                    case "id":
                        infoCard.setId(newValue);
                        break;
                    case "link":
                        infoCard.setLink(newValue);
                        break;
                    case "data":
                        infoCard.setData(newValue);
                        break;
                    case "skrotOrganizacja":
                        infoCard.setSkrotOrganizacja(newValue);
                        break;
                    case "komponentSrodowiska":
                        infoCard.setKomponentSrodowiska(newValue);
                        break;
                    case "typKarty":
                        infoCard.setTypKarty(newValue);
                        break;
                    case "rodzajKarty":
                        infoCard.setRodzajKarty(newValue);
                        break;
                    case "nrWpisu":
                        infoCard.setNrWpisu(newValue);
                        break;
                    case "znakSprawy":
                        infoCard.setZnakSprawy(newValue);
                        break;
                    case "daneWnioskodawcy":
                        infoCard.setDaneWnioskodawcy(newValue);
                        break;
                }
            });

            i++;
        }

        File file = new File("data/bip.poznan.pl.xml");
        JAXBContext jaxbContext = JAXBContext.newInstance(BipPoznanPl.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        root = (BipPoznanPl) unmarshaller.unmarshal(file);
        List<InfoCard> infoCards = root.getData().getInformationCardList().getItems().getInfoCards();
        table.getItems().addAll(infoCards);

        transformButton.setOnAction(e -> transformToHTML());
        saveButton.setOnAction(e -> saveData());

        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    private void saveData() {
        try {
            JAXBContext context = JAXBContext.newInstance(BipPoznanPl.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(root, new File("data/bip.poznan.pl.xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void transformToHTML() {
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Source xslt = new StreamSource(new File("xslt/info_card.xsl"));
            Transformer transformer = factory.newTransformer(xslt);

            Source xml = new StreamSource(new File("data/bip.poznan.pl.xml"));
            FileWriter writer = new FileWriter(new File("output.html"));
            transformer.transform(xml, new StreamResult(writer));
        } catch (TransformerException | IOException e) {
            e.printStackTrace();
        }
    }
}
