package view.game.Animations;

import javafx.animation.Transition;
import javafx.util.Duration;
import model.CentralBall;
import model.ShootingBall;

public class IncreaseBallRadiusAnimation extends Transition {
    private CentralBall centralBall;
    private boolean bigMode;
    private boolean smallMode;

    public IncreaseBallRadiusAnimation(CentralBall centralBall) {
        this.centralBall = centralBall;
        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(2000));
        bigMode = false;
        smallMode = true;
    }

    @Override
    protected void interpolate(double v) {
        if (v <= 0.5) {
            if (smallMode) increaseRadius();
        }
        if (v > 0.5){
            if (bigMode) reduceRadius();
        }
    }

    private void increaseRadius() {
        for (ShootingBall shootingBall : centralBall.getBalls()) {
            shootingBall.getBall().setRadius(shootingBall.getBall().getRadius()*21/20);
        }
        bigMode = true;
        smallMode = false;
    }
    private void reduceRadius() {
        for (ShootingBall shootingBall : centralBall.getBalls()) {
            shootingBall.getBall().setRadius(shootingBall.getBall().getRadius()*20/21);
        }
        bigMode = false;
        smallMode = true;
    }
}
