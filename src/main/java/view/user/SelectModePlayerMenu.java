package view.user;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.Main;
import view.game.GameMenu;
import view.game.TwoPlayerGameMenu;

import java.io.IOException;
import java.net.URL;

public class SelectModePlayerMenu extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        URL url = SelectModePlayerMenu.class.getResource("/FXML/user/selectModePlayer.fxml");
        assert url != null;
        AnchorPane anchorPane = FXMLLoader.load(url);
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }

    public void onePlayerMode(MouseEvent mouseEvent) throws IOException {
        new GameMenu().start(Main.stage);
    }
    public void twoPlayerMode(MouseEvent mouseEvent) throws IOException {
        new TwoPlayerGameMenu().start(Main.stage);
    }
}
