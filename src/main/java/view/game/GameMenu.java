package view.game;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;
import view.Main;
import view.game.Animations.IncreaseBallRadiusAnimation;
import view.game.Animations.LooseGameAnimation;
import view.game.Animations.ShootingAnimation;
import view.game.Animations.VisibilityModeAnimation;
import view.user.PrimaryMenu;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

public class GameMenu extends Application {

    public Text scoreInView;
    private Game game;
    private final Stage endGameStage = new Stage();


    public Pane pane;
    private boolean hasContinue = true;

    public static User currentPlayer;
    private int currentScore = 0;

    private ArrayList<Timeline> rotations = new ArrayList<>();

    @Override
    public void start(Stage stage) throws IOException {
        Pane pane = FXMLLoader.load(Main.class.getResource("/FXML/game/gameMenu.fxml"));
        this.pane = pane;
        scoreInView = (Text) pane.getChildren().get(1);

        game = new Game(GameSetting.getShootKey(), GameSetting.getFrozenKey(), GameSetting.getNumberOfBalls());
        pane.getChildren().add(game.getFirstCentralBall());
        pane.getChildren().add(game.getSecondCentralBall());
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        //    pane.getChildren().get(1).requestFocus();
        pane.getChildren().get(3).setVisible(false);
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
                    if (game.getCurrentBalls() >= 1 && hasContinue) shootAction();
                    else {
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

    private void createBall(ShootingBall shootingBall) {
        pane.getChildren().add(shootingBall.getBall());
        pane.getChildren().add(shootingBall.getText());
    }

    private void shootAction() {
        ShootingAnimation shootingAnimation = new ShootingAnimation(game.getSecondCentralBall(), game.getShootingBalls().get(0), this);
        shootingAnimation.play();
        game.shoot();
        if (!hasContinue) stopRotations();
        if (game.getCurrentBalls() >= 2) createBall(game.getShootingBalls().get(1));
        if (game.getCurrentBalls() >= 1) game.getShootingBalls().get(0).moveToShoot();
        addScore();
        scoreInView.setText(Integer.valueOf(currentScore).toString());
        checkPhaseSituation();

    }

    private void addScore() {
        switch (GameSetting.getDifficulty()) {
            case "Easy":
                currentScore += 10;
                break;
            case "Medium":
                currentScore += 20;
                break;
            case "Hard":
                currentScore += 40;
        }
    }

    private void stopRotations() {
        for (Timeline rotation : rotations) {
            rotation.stop();
        }
    }
    private void checkPhaseSituation() {
        switch (game.getPhase()) {
            case PHASE_1:
                if (4 * game.getCurrentBalls() <= game.getNumberOfBalls() * 3) {
                    game.setPhase(Phase.PHASE_2);
                    changePhase(game.getPhase());
                }
            case PHASE_2:
                if ( 2* game.getCurrentBalls() <= game.getNumberOfBalls()) {
                    game.setPhase(Phase.PHASE_3);
                    changePhase(game.getPhase());
                }
        }
    }

    private void changePhase(Phase phase) {

        if (phase.equals(Phase.PHASE_2)) {
            stopRotations();
            rotations.clear();
            new IncreaseBallRadiusAnimation(game.getSecondCentralBall()).play();
            for (ShootingBall shootingBall : game.getSecondCentralBall().getBalls())
                rotateInPhase2(shootingBall);
        }
        if (phase.equals(Phase.PHASE_3)){
            System.out.println("sala");
            new VisibilityModeAnimation(game.getSecondCentralBall()).play();
        }
    }


    public void setRotation(ShootingBall shootingBall) {
        switch (game.getPhase()) {
            case PHASE_1:
                rotateInPhase1(shootingBall);
                break;
            case PHASE_2:
            case PHASE_3:
                rotateInPhase2(shootingBall);
                break;
        }
    }

    private void rotateInPhase1(ShootingBall shootingBall) {
        Rotate circleRotationPhase1 = new Rotate();
        circleRotationPhase1.setPivotX(game.getSecondCentralBall().getCenterX());
        circleRotationPhase1.setPivotY(game.getSecondCentralBall().getCenterY());

        addRotation(shootingBall, circleRotationPhase1);

        Timeline circleRotationPhase1TimeLine = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(circleRotationPhase1.angleProperty(), 0)),
                new KeyFrame(Duration.seconds(game.getRotateSpeed()), new KeyValue(circleRotationPhase1.angleProperty(), 360)),
                new KeyFrame(Duration.ZERO, actionEvent -> {
                    for (ShootingBall shootingBallSelected : game.getSecondCentralBall().getBalls()) {
                        if (shootingBallSelected.equals(shootingBall)) continue;
                        ;
                        if (shootingBallSelected.getBall().getBoundsInParent().intersects(shootingBall.getBall().getBoundsInParent())) {
                            hasContinue = false;
                            try {
                                endGameSituation();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            return;
                        }

                    }
                })
        );
        circleRotationPhase1TimeLine.setAutoReverse(false);
        circleRotationPhase1TimeLine.setCycleCount(Timeline.INDEFINITE);
        rotations.add(circleRotationPhase1TimeLine);
        circleRotationPhase1TimeLine.play();
    }

    private void rotateInPhase2(ShootingBall shootingBall) {
        Rotate circleRotationPhase2 = new Rotate();
        circleRotationPhase2.setPivotX(game.getSecondCentralBall().getCenterX());
        circleRotationPhase2.setPivotY(game.getSecondCentralBall().getCenterY());
        addRotation(shootingBall, circleRotationPhase2);
        Timeline circleRotationPhase2TimeLine = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(circleRotationPhase2.angleProperty(), 0)),
                new KeyFrame(Duration.seconds(8), new KeyValue(circleRotationPhase2.angleProperty(), 420 * (4 / game.getRotateSpeed()))),
                new KeyFrame(Duration.seconds(16), new KeyValue(circleRotationPhase2.angleProperty(), 0)),
                new KeyFrame(Duration.ZERO, actionEvent -> {
                    for (ShootingBall shootingBallSelected : game.getSecondCentralBall().getBalls()) {
                        if (shootingBallSelected.equals(shootingBall)) continue;
                        if (shootingBallSelected.getBall().getBoundsInParent().intersects(shootingBall.getBall().getBoundsInParent())) {
                            hasContinue = false;
                            try {
                                endGameSituation();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            return;
                        }
                    }
                })
        );
        circleRotationPhase2TimeLine.setAutoReverse(false);
        circleRotationPhase2TimeLine.setCycleCount(Timeline.INDEFINITE);
        rotations.add(circleRotationPhase2TimeLine);
        circleRotationPhase2TimeLine.play();
    }

    private void addRotation(ShootingBall shootingBall, Rotate circleRotationPhase1) {
        shootingBall.getBall().getTransforms().add(circleRotationPhase1);
        shootingBall.getText().getTransforms().add(circleRotationPhase1);
        shootingBall.getLine().getTransforms().add(circleRotationPhase1);
    }


    private void endGameSituation() throws Exception {
        stopRotations();
        pane.getStylesheets().add(Main.class.getResource("/CSS/style1.css").toString());
        if (!hasContinue) {
            LooseGameAnimation looseGameAnimation = new LooseGameAnimation(pane);
            looseGameAnimation.play();
        } else pane.getStyleClass().add("green-pane");
        if (currentScore >= currentPlayer.getHighScore()) {
            currentPlayer.setHighScore(currentScore);
            DataBase.getInstance().updateData();
            DataBase.getInstance().updateRankings();
        }
        runEndGameMenu(endGameStage);

    }

    private void runEndGameMenu(Stage stage) throws IOException {
        Pane pane = FXMLLoader.load(Main.class.getResource("/FXML/game/endGameShow.fxml"));

        ((Text) pane.getChildren().get(0)).setText("your score:" + Integer.valueOf(currentScore).toString());
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setTitle("aa game");
        stage.show();
    }


    public void returnHome(MouseEvent mouseEvent) throws Exception {
        endGameStage.close();
        new PrimaryMenu().start(Main.stage);
    }
}

