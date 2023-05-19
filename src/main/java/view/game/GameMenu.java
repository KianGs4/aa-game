package view.game;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Game;
import model.GameSetting;
import model.ShootingBall;
import view.Main;
import view.game.Animations.ShootingAnimation;

import java.io.IOException;

public class GameMenu extends Application {

    private Game game;


    public Pane pane;

    @Override
    public void start(Stage stage) throws IOException {

        Pane pane = FXMLLoader.load(Main.class.getResource("/FXML/game/gameMenu.fxml"));
        this.pane = pane;
        game = new Game(GameSetting.getShootKey(), GameSetting.getFrozenKey(), GameSetting.getNumberOfBalls());
        pane.getChildren().add(game.getFirstCentralBall());
        pane.getChildren().add(game.getSecondCentralBall());
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        //    pane.getChildren().get(1).requestFocus();
        pane.getChildren().get(1).setVisible(false);
        stage.show();
        shootHandling();
    }

    private void shootHandling() {
        createBall(game.getShootingBalls().get(0));
        game.getShootingBalls().get(0).moveToShoot();
        createBall(game.getShootingBalls().get(1));
        Main.stage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(game.getShoot())) {
                    shootAction();
                }
            }
        });
    }

    private void createBall(ShootingBall shootingBall) {
        pane.getChildren().add(shootingBall.getBall());
        pane.getChildren().add(shootingBall.getText());
    }

    private void shootAction() {
        ShootingAnimation shootingAnimation = new ShootingAnimation(game.getSecondCentralBall(), game.getShootingBalls().get(0), this);
        shootingAnimation.play();
        game.shoot();
        createBall(game.getShootingBalls().get(1));
        game.getShootingBalls().get(0).moveToShoot();

    }


    public void setRotation(ShootingBall shootingBall) {
        switch (game.getPhase()) {
            case PHASE_1:
                rotateInPhase1(shootingBall);
        }
    }

    private void rotateInPhase1(ShootingBall shootingBall) {
        Rotate circleRotationPhase1 = new Rotate();
        circleRotationPhase1.setPivotX(game.getSecondCentralBall().getCenterX());
        circleRotationPhase1.setPivotY(game.getSecondCentralBall().getCenterY());

        shootingBall.getBall().getTransforms().add(circleRotationPhase1);
        shootingBall.getText().getTransforms().add(circleRotationPhase1);
        shootingBall.getLine().getTransforms().add(circleRotationPhase1);

        Timeline circleRotationPhase1TimeLine = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(circleRotationPhase1.angleProperty(), 0)),
                new KeyFrame(Duration.seconds(2.5), new KeyValue(circleRotationPhase1.angleProperty(), 360))
        );


        circleRotationPhase1TimeLine.setCycleCount(Timeline.INDEFINITE);
        circleRotationPhase1TimeLine.play();


    }


}
