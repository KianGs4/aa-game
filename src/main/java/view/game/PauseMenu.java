package view.game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Game;
import model.GameSetting;
import model.ToggleSwitch;
import view.Main;
import view.user.PrimaryMenu;
import view.user.ProfileMenu;

import java.io.IOException;
import java.net.URL;


public class PauseMenu extends Application {

    private static Stage pauseStage;
    private static GameMenu gameMenu;
    private static Text shootText;
    private static Text freezeText;
    public AnchorPane pausePane;


    @Override
    public void start(Stage stage) throws Exception {
        URL url = ProfileMenu.class.getResource("/FXML/game/pauseMenu.fxml");
        assert url != null;
        AnchorPane anchorPane = FXMLLoader.load(url);
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        ToggleSwitch toggleSwitch = new ToggleSwitch();
        toggleSwitch.setTranslateX(70);
        toggleSwitch.setTranslateY(325);
        anchorPane.getChildren().add(toggleSwitch);
        stage.setTitle("aa game");
        stage.show();
        stage.setOnCloseRequest(windowEvent -> backToGame(stage));
    }

    static  {
        shootText = new Text("Shoot: " + GameSetting.getShootKey().getName());
        shootText.setTranslateX(32);
        shootText.setTranslateY(100);
        shootText.setFont(Font.font("Berlin Sans FB", 30.0));
        shootText.setFill(Color.WHITESMOKE);
        freezeText = new Text("freeze: " + GameSetting.getFrozenKey().getName());
        freezeText.setTranslateX(32);
        freezeText.setTranslateY(150);
        freezeText.setFont(Font.font("Berlin Sans FB", 30.0));
        freezeText.setFill(Color.WHITESMOKE);
    }

    private void logout(Stage stage) {
        stage.close();
    }


    public void backToGame(MouseEvent mouseEvent) {
        gameMenu.resumeGame();
        logout(pauseStage);
    }

    public void backToGame(Stage stage) {
        gameMenu.resumeGame();
        logout(stage);
    }

    public static void setPauseStage(Stage pauseStage) {
        PauseMenu.pauseStage = pauseStage;
    }

    public static void setGameMenu(GameMenu gameMenu) {
        PauseMenu.gameMenu = gameMenu;
    }

    public void exit(MouseEvent mouseEvent) throws Exception {
        new PrimaryMenu().start(Main.stage);
        logout(pauseStage);
    }

    public void save(MouseEvent mouseEvent) {
    }

    public void restart(MouseEvent mouseEvent) throws IOException {
        new GameMenu().start(Main.stage);
        logout(pauseStage);
    }

    public void showKeys(MouseEvent mouseEvent) {
        if (pausePane.getChildren().get(3).isVisible())
           goToInfoMode();
         else  goToMainPauseMode();
    }

    private void goToInfoMode() {
        pausePane.getChildren().get(3).setVisible(false);
        pausePane.getChildren().get(4).setVisible(false);
        pausePane.getChildren().get(5).setVisible(false);
        pausePane.getChildren().get(6).setVisible(false);
        pausePane.getChildren().add(shootText);
        pausePane.getChildren().add(freezeText);
    }
    private void goToMainPauseMode() {
        pausePane.getChildren().get(3).setVisible(true);
        pausePane.getChildren().get(4).setVisible(true);
        pausePane.getChildren().get(5).setVisible(true);
        pausePane.getChildren().get(6).setVisible(true);
        pausePane.getChildren().remove(shootText);
        pausePane.getChildren().remove(freezeText);
    }
}
