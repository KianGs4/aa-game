package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;

public class Main extends Application {
    public static Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Main.stage = stage;
        URL url = Main.class.getResource("/FXML/main.fxml");
        AnchorPane anchorPane = FXMLLoader.load(url);
        Scene scene = new Scene(anchorPane);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }
}
