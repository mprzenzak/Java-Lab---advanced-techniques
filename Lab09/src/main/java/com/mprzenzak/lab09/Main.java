package com.mprzenzak.lab09;
import com.mprzenzak.lab09.Models.BipPoznanPl;
import com.mprzenzak.lab09.Models.InfoCard;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;

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

    @Override
    public void start(Stage primaryStage) throws Exception {
        TableView<InfoCard> table = new TableView<>();

        String[] columnsNames = {"id", "link", "data", "skrotOrganizacja", "komponentSrodowiska",
                "typKarty", "rodzajKarty", "nrWpisu", "znakSprawy", "daneWnioskodawcy"};

        for (String columnName : columnsNames) {
            TableColumn<InfoCard, String> column = new TableColumn<>(columnName);
            column.setCellValueFactory(new PropertyValueFactory<>(columnName));
            table.getColumns().add(column);
        }

        File file = new File("data/bip.poznan.pl.xml");
        JAXBContext jaxbContext = JAXBContext.newInstance(BipPoznanPl.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        BipPoznanPl root = (BipPoznanPl) unmarshaller.unmarshal(file);
        List<InfoCard> infoCards = root.getData().getInformationCardList().getItems().getInfoCards();
        table.getItems().addAll(infoCards);

        Button transformButton = new Button("Transform to HTML");
        transformButton.setOnAction(e -> transformToHTML());

        VBox vbox = new VBox(transformButton, table);

        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
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
