package view.game;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;
import view.Main;
import view.game.Animations.*;
import view.user.PrimaryMenu;

import java.io.IOException;
import java.util.ArrayList;

public class GameMenu extends Application {

    public Text scoreInView;
    private Game game;
    private final Stage endGameStage = new Stage();


    public Pane pane;
    public boolean hasContinue = true;
    public boolean isClockWise = true;
    public boolean hasPaused = false;

    public static User currentPlayer;
    private int currentScore = 0;
    public int wind = 0;

    private final ArrayList<Timeline> rotations = new ArrayList<>();
    private final Time time = new Time("0:0");
    private final Timeline passingTime = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
        if (time.getSecondsLeft() > 120) {
            try {
                hasContinue = false;
                endGameSituation();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        if (!hasPaused) {
            time.oneSecondPassed();
            ((Text) pane.getChildren().get(6)).setText(time.getCurrentTime());
        }
    }));

    @Override
    public void start(Stage stage) throws IOException {
        Pane pane = FXMLLoader.load(Main.class.getResource("/FXML/game/gameMenu.fxml"));
        this.pane = pane;
        scoreInView = (Text) pane.getChildren().get(1);
        game = new Game(GameSetting.getShootKey(), GameSetting.getFrozenKey(), GameSetting.getNumberOfBalls());
        pane.getChildren().add(game.getFirstCentralBall());
        pane.getChildren().add(game.getSecondCentralBall());
        pane.getStylesheets().add(Main.class.getResource("/CSS/style1.css").toString());
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        setGameTime();
        pane.getChildren().get(8).setVisible(false);
        updateRemainingBall();
        if (GameSetting.getLanguage().equals("Persian")) translate(pane);
        if (GameSetting.getSound()) {
            PrimaryMenu.mainSound.play();
            PrimaryMenu.mainSound.loop();
        }
        stage.show();
        shootHandling();
    }

    private void translate(Pane pane) {
        ((Text) pane.getChildren().get(0)).setText("امتیاز");
        ((Text) pane.getChildren().get(2)).setText("باد");
        ((Text) pane.getChildren().get(4)).setText("تعداد توپ باقی مانده");

    }

    private void setGameTime() {
        ((Text) pane.getChildren().get(6)).setText(time.getCurrentTime());
        passingTime.setCycleCount(Animation.INDEFINITE);
        passingTime.play();

    }

    private void shootHandling() {
        createBall(game.getShootingBalls().get(0));
        game.getShootingBalls().get(0).moveToShoot();
        createBall(game.getShootingBalls().get(1));
        Main.stage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(game.getShoot())) {
                    if (game.getCurrentBalls() >= 1 && hasContinue) {
                        new Sound(3).play();
                        shootAction();
                    } else {
                        try {
                            endGameSituation();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                if (game.getPhase().equals(Phase.PHASE_4) && keyEvent.getCode().equals(KeyCode.LEFT))
                    moveLeft();
                if (game.getPhase().equals(Phase.PHASE_4) && keyEvent.getCode().equals(KeyCode.RIGHT))
                    moveRight();
                if (keyEvent.getCode().equals(KeyCode.P)) {
                    try {
                        pauseSituation();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }


    private void moveLeft() {
        Circle ball = game.getShootingBalls().get(0).getBall();
        if (!(ball.getCenterX() - ball.getRadius() - 15 < 0)) {
            ball.setCenterX(ball.getCenterX() - 10);
            game.getShootingBalls().get(0).getText().setX(game.getShootingBalls().get(0).getText().getX() - 10);
        }
    }

    private void moveRight() {
        Circle ball = game.getShootingBalls().get(0).getBall();
        if (!(ball.getCenterX() + ball.getRadius() + 15 > pane.getWidth())) {
            ball.setCenterX(ball.getCenterX() + 10);
            game.getShootingBalls().get(0).getText().setX(game.getShootingBalls().get(0).getText().getX() + 10);
        }
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
        scoreInView.setText(Integer.valueOf(currentScore).toString());
        checkPhaseSituation();

    }

    private void updateSituation() {
        addScore();
        updateRemainingBall();
    }

    private void updateRemainingBall() {
        ((Text) pane.getChildren().get(5)).setText(Integer.valueOf(game.getCurrentBalls()).toString());
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

    public void stopRotations() {
        for (Timeline rotation : rotations) {
            rotation.stop();
        }
    }

    private void checkPhaseSituation() {
        if (game.getCurrentBalls() == 3 && !GameSetting.isBW_mode()) changeColorToBlue();
        switch (game.getPhase()) {
            case PHASE_1:
                if (4 * game.getCurrentBalls() < game.getNumberOfBalls() * 3) {
                    game.setPhase(Phase.PHASE_2);
                    changePhase(game.getPhase());
                }
                break;
            case PHASE_2:
                new PassingTimeAnimation(this, game.getSecondCentralBall(), 6).play();
                if (2 * game.getCurrentBalls() < game.getNumberOfBalls()) {
                    game.setPhase(Phase.PHASE_3);
                    changePhase(game.getPhase());
                }
                break;
            case PHASE_3:
                new PassingTimeAnimation(this, game.getSecondCentralBall(), 6).play();
                if (4 * game.getCurrentBalls() < game.getNumberOfBalls()) {
                    game.setPhase(Phase.PHASE_4);
                }
                break;
            case PHASE_4:
                new PassingTimeAnimation(this, game.getSecondCentralBall(), 6).play();
                break;
        }
    }

    private void changeColorToBlue() {
        pane.getStyleClass().remove(1);
        pane.getStyleClass().add("blue-pane");
    }

    private void changePhase(Phase phase) {

        if (phase.equals(Phase.PHASE_2)) {
            stopRotations();
            rotations.clear();
            new IncreaseBallRadiusAnimation(game.getSecondCentralBall(), hasPaused).play();
            for (ShootingBall shootingBall : game.getSecondCentralBall().getBalls())
                rotateInPhase2(shootingBall);
        }
        if (phase.equals(Phase.PHASE_3)) {
            new VisibilityModeAnimation(game.getSecondCentralBall(), hasPaused).play();
            if (!GameSetting.isBW_mode()) pane.getStyleClass().add("yellow-pane");
        }

    }


    public void setRotation(ShootingBall shootingBall) {
        switch (game.getPhase()) {
            case PHASE_1:
                rotateInPhase1(shootingBall);
                break;
            case PHASE_2:
            case PHASE_3:
            case PHASE_4:
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
        runTimeLine(circleRotationPhase1TimeLine);
    }

    public void rotateInPhase2(ShootingBall shootingBall) {
        Rotate circleRotationPhase2 = new Rotate();
        circleRotationPhase2.setPivotX(game.getSecondCentralBall().getCenterX());
        circleRotationPhase2.setPivotY(game.getSecondCentralBall().getCenterY());
        addRotation(shootingBall, circleRotationPhase2);
        Timeline circleRotationPhase2TimeLine = (isClockWise) ?
                createClockWisePhase2TimeLine(shootingBall, circleRotationPhase2) :
                createCounterClockWisePhase2TimeLine(shootingBall, circleRotationPhase2);
        runTimeLine(circleRotationPhase2TimeLine);
    }

    private Timeline createClockWisePhase2TimeLine(ShootingBall shootingBall, Rotate circleRotationPhase2) {
        double angle = calculateAngle(shootingBall.getBall(), game.getSecondCentralBall());

        return new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(circleRotationPhase2.angleProperty(), angle)),
                new KeyFrame(Duration.seconds(6), new KeyValue(circleRotationPhase2.angleProperty(), 380 * (6 / game.getRotateSpeed()) + angle)),
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
    }

    private double calculateAngle(Circle ball, Circle ball2) {
        double distance_x = ball.getCenterX() - ball2.getCenterX();
        double distance_y = ball.getCenterY() - ball2.getCenterY();
        return Math.asin(distance_x / (Math.sqrt(distance_x * distance_x + distance_y * distance_y)));
    }

    private Timeline createCounterClockWisePhase2TimeLine(ShootingBall shootingBall, Rotate circleRotationPhase2) {
        double angle = calculateAngle(shootingBall.getBall(), game.getSecondCentralBall());
        return new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(circleRotationPhase2.angleProperty(), angle)),
                new KeyFrame(Duration.seconds(6), new KeyValue(circleRotationPhase2.angleProperty(), -1 * (380 * (6 / game.getRotateSpeed())) + angle)),
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
    }


    private void runTimeLine(Timeline timeline) {
        timeline.setAutoReverse(false);
        timeline.setCycleCount(-1);
        rotations.add(timeline);
        timeline.play();
    }

    private void addRotation(ShootingBall shootingBall, Rotate circleRotationPhase1) {
        shootingBall.getBall().getTransforms().add(circleRotationPhase1);
        shootingBall.getText().getTransforms().add(circleRotationPhase1);
        shootingBall.getLine().getTransforms().add(circleRotationPhase1);
    }


    public void endGameSituation() throws Exception {
        if (pane.getStyleClass().size() >= 2) pane.getStyleClass().remove(1);
        passingTime.stop();
        stopRotations();
        if (!GameSetting.isBW_mode()) {
            if (!hasContinue) {
                LooseGameAnimation looseGameAnimation = new LooseGameAnimation(pane);
                looseGameAnimation.play();
                PrimaryMenu.mainSound.stop();
                new Sound(4).play();
            } else pane.getStyleClass().add("green-pane");
        }
        if (currentScore >= currentPlayer.getHighScore()) {
            currentPlayer.setHighScore(currentScore);
            currentPlayer.setInfo(GameSetting.getDifficulty(), time.getSecondsLeft());
            DataBase.getInstance().updateData();
            DataBase.getInstance().updateRankings();
        }
        runEndGameMenu(endGameStage);

    }

    //TODO close handling
    private void runEndGameMenu(Stage stage) throws Exception {
        EndGameMenu.setEndGameStage(stage);
        EndGameMenu.setCurrentScore(currentScore);
        new EndGameMenu().start(stage);
    }


    private void pauseSituation() throws Exception {
        hasPaused = true;
        for (Timeline timeline : rotations) {
            timeline.pause();
        }
        Stage stage = new Stage();
        PauseMenu.setPauseStage(stage);
        PauseMenu.setGameMenu(this);
        new PauseMenu().start(stage);
    }

    public void resumeGame() {
        hasPaused = false;
        for (Timeline timeline : rotations) {
            timeline.play();
        }
    }

    public ArrayList<Timeline> getRotations() {
        return rotations;
    }

    public Game getGame() {
        return game;
    }

}

