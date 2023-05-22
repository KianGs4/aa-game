package view.game.Animations;

import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import model.CentralBall;
import model.ShootingBall;
import view.game.GameMenu;

public class PassingTimeAnimation extends Transition {
    private GameMenu gameMenu;
    private CentralBall centralBall;
    private int time;

    public PassingTimeAnimation(GameMenu gameMenu, CentralBall centralBall, int time) {
        this.time = time;
        this.gameMenu = gameMenu;
        this.centralBall = centralBall;
        this.setCycleDuration(Duration.seconds(time));
        this.setOnFinished(changeDirection());
        this.setCycleCount(1);
    }

    private EventHandler<ActionEvent> changeDirection() {
        return actionEvent -> {
            gameMenu.stopRotations();
            gameMenu.getRotations().clear();
            gameMenu.isClockWise = !gameMenu.isClockWise;
            for (ShootingBall shootingBall: centralBall.getBalls() ) {
                    gameMenu.rotateInPhase2(shootingBall);
            }
        };
    }

    @Override
    protected void interpolate(double v) {
        if (!gameMenu.hasContinue) this.stop();
    }
}
