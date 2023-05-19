package view.user;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;

public class PrimaryMenu extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        URL url = PrimaryMenu.class.getResource("/FXML/user/primaryMenu.fxml");

        assert url != null;
        AnchorPane anchorPane = FXMLLoader.load(url);
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }



}
