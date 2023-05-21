package view.game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.ToggleSwitch;
import view.user.ProfileMenu;

import java.net.URL;

public class PauseMenu extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        URL url = ProfileMenu.class.getResource("/FXML/game/pauseMenu.fxml");
        assert url != null;
        AnchorPane anchorPane = FXMLLoader.load(url);
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        ToggleSwitch toggleSwitch = new ToggleSwitch( );
        toggleSwitch.setTranslateX(70);
        toggleSwitch.setTranslateY(325);
        anchorPane.getChildren().add(toggleSwitch);
        stage.show();
    }
}
