package view.game;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.*;
import view.Main;
import view.game.Animations.ShootingAnimation;
import view.user.PrimaryMenu;

import java.io.IOException;
import java.util.ArrayList;

public class TwoPlayerGameMenu extends GameMenu {
    ArrayList<ShootingBall> player2Balls = new ArrayList<>();

    public void start(Stage stage) throws IOException {
        Pane pane = FXMLLoader.load(Main.class.getResource("/FXML/game/gameMenu.fxml"));
        this.pane = pane;
        scoreInView = (Text) pane.getChildren().get(1);
        Game game = new Game(GameSetting.getShootKey(), GameSetting.getFrozenKey(), GameSetting.getNumberOfBalls());
        game.getSecondCentralBall().setCenterY(305);
        game.getSecondCentralBall().setRadius(180);
        game.makeFirstCentralBall(game.getSecondCentralBall());
        super.setGame(game);
        coloringFirstPlayerBalls();
        createBallsForSecondPlayer();

        pane.getChildren().add(super.getGame().getFirstCentralBall());
        pane.getChildren().add(super.getGame().getSecondCentralBall());
        pane.getStylesheets().add(Main.class.getResource("/CSS/style1.css").toString());
        super.getGame().createSampleBalls(super.getGame().getSecondCentralBall(), pane);
        for (ShootingBall shootingBall : super.getGame().getSecondCentralBall().getBalls())
            rotateInPhase1(shootingBall, super.getGame().getRotateSpeed());

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        setGameTime();
        pane.getChildren().get(9).setVisible(false);
        updateRemainingBall();
        if (GameSetting.getLanguage().equals("Persian")) translate(pane);
        if (GameSetting.getSound()) {
            PrimaryMenu.mainSound.play();
            PrimaryMenu.mainSound.loop();
        }
        stage.show();
        shootHandling();
    }

    private void coloringFirstPlayerBalls() {
        for (ShootingBall shootingBall : getGame().getShootingBalls()) {
            shootingBall.getBall().setFill(Color.ORANGE);
        }
    }

    private void createBallsForSecondPlayer() {
        for (int i = getGame().getCurrentBalls(); i > 0; i--) {
            ShootingBall shootingBall = new ShootingBall(Integer.valueOf(i).toString(), 20);
            shootingBall.getBall().setFill(Color.PURPLE);
            player2Balls.add(shootingBall);
        }

    }

    private void shootHandling() {
        createBall(getGame().getShootingBalls().get(0));
        createBall(player2Balls.get(0));
        Main.stage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(getGame().getShoot())) {
                    if (getGame().getCurrentBalls() >= 1 && hasContinue) {
                        new Sound(3).play();
                        pane.getChildren().remove(player2Balls.get(0));
                        shootAction(false);
                    } else {
                        try {
                            endGameSituation();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                if (getGame().getPhase().equals(Phase.PHASE_4) && keyEvent.getCode().equals(KeyCode.LEFT))
                    moveLeft(getGame().getShootingBalls().get(0));
                if (getGame().getPhase().equals(Phase.PHASE_4) && keyEvent.getCode().equals(KeyCode.RIGHT))
                    moveRight(getGame().getShootingBalls().get(0));
                if (keyEvent.getCode().equals(KeyCode.P)) {
                    try {
                        pauseSituation();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                if (keyEvent.getCode().equals(GameSetting.getFrozenKey())) {
                    try {
                        freezeMode();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (keyEvent.getCode().equals(KeyCode.D))
                    moveRight(player2Balls.get(0));
                if (keyEvent.getCode().equals(KeyCode.A))
                    moveLeft(player2Balls.get(0));

                if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                    if (getGame().getCurrentBalls() >= 1 && hasContinue) {
                        new Sound(3).play();
                        pane.getChildren().remove(getGame().getShootingBalls().get(0));
                        shootAction(true);
                    } else {
                        try {
                            endGameSituation();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });
    }

    private void shootAction(boolean playerMode) {
        ShootingAnimation shootingAnimation;
        if (!playerMode)
            shootingAnimation = new ShootingAnimation(getGame().getSecondCentralBall(), getGame().getShootingBalls().get(0), this);
        else shootingAnimation = new ShootingAnimation(getGame().getSecondCentralBall(), player2Balls.get(0), this, -1);
        shootingAnimation.play();
        getGame().shoot();
        player2Balls.remove(0);
        if (!hasContinue) stopRotations();
        if (getGame().getCurrentBalls() >= 1) {
            createBall(getGame().getShootingBalls().get(0));
            createBall(player2Balls.get(0));
        }
        updateSituation();
        scoreInView.setText(Integer.valueOf(currentScore).toString());
        checkPhaseSituation();
    }

}
