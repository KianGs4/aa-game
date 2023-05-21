package view.user;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.GameSetting;
import model.Sound;
import view.Main;

import java.net.URL;

public class PrimaryMenu extends Application {
    public static Sound mainSound = new Sound();
    @Override
    public void start(Stage stage) throws Exception {
        URL url = PrimaryMenu.class.getResource("/FXML/user/primaryMenu.fxml");

        assert url != null;
        AnchorPane anchorPane = FXMLLoader.load(url);
        if (GameSetting.getLanguage().equals("Persian")){
            PrimaryMenuController primaryMenuController = new PrimaryMenuController(anchorPane);
            primaryMenuController.translate();
        }
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        mainSound.setFile(0);
        stage.show();
    }




}
