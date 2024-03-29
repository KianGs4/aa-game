package view.user;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;

public class SignupMenu extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        URL url = SignupMenu.class.getResource("/FXML/user/signupMenu.fxml");
        GridPane gridPane = FXMLLoader.load(url);
        Scene scene = new Scene(gridPane);
        stage.setScene(scene);
        stage.show();
    }
}
