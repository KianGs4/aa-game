package view.user;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.GameSetting;

import java.net.URL;

public class SettingMenu extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        URL url = ProfileMenu.class.getResource("/FXML/setting/setting.fxml");
        assert url != null;
        AnchorPane anchorPane = FXMLLoader.load(url);
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        if (GameSetting.getLanguage().equals("Persian")) new SettingMenuController(anchorPane).translate();
        stage.show();
    }
}
