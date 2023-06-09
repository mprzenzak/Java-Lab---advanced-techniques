import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Diary extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        URL fxmlURL = getClass().getResource("diary.fxml");
        Parent root = FXMLLoader.load(fxmlURL);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("diary.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Diary Application");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
