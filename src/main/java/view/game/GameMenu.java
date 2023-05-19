package view.game;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.CentralBall;
import model.Game;
import model.GameSetting;
import model.ShootingBall;
import view.Main;
import view.game.Animations.ShootingAnimation;

import java.io.IOException;
import java.net.URL;

public class GameMenu extends Application {

    private Game game;
    private Pane pane;

    @Override
    public void start(Stage stage) throws IOException {

        Pane pane = FXMLLoader.load(Main.class.getResource("/FXML/game/gameMenu.fxml"));
        this.pane = pane;
        game = new Game(GameSetting.getShootKey(), GameSetting.getFrozenKey(), GameSetting.getNumberOfBalls());
        pane.getChildren().add(game.getFirstCentralBall());
        pane.getChildren().add(game.getSecondCentralBall());
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        pane.getChildren().get(1).requestFocus();
        pane.getChildren().get(1).setVisible(false);
        stage.show();
        setRotation(game.getSecondCentralBall());
        createBall();
    }

    private void createBall() {
        pane.getChildren().add(game.getShootingBalls().get(0));
        game.getShootingBalls().get(0).moveToShoot();
        pane.getChildren().add(game.getShootingBalls().get(1));
        Main.stage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(game.getShoot())) {
                    shootAction();
                }
            }
        });
    }

    private void shootAction() {
        ShootingAnimation shootingAnimation = new ShootingAnimation(game.getSecondCentralBall(),game.getShootingBalls().get(0), this.pane );
        shootingAnimation.play();
        game.shoot();
        pane.getChildren().add(game.getShootingBalls().get(1));
        game.getShootingBalls().get(0).moveToShoot();

    }


    private void setRotation(Node centralBall) {
        switch (game.getPhase()) {
            case PHASE_1:

        }
    }


}
