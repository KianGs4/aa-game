package view.game.Animations;

import javafx.animation.Transition;
import javafx.util.Duration;
import model.CentralBall;
import model.ShootingBall;

public class VisibilityModeAnimation extends Transition {
    private final CentralBall centralBall;
    private boolean visibility;
    private boolean hasPaused;

    public VisibilityModeAnimation(CentralBall centralBall, boolean hasPaused) {
        this.centralBall = centralBall;
        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(2000));
        visibility = true;
        this.hasPaused = hasPaused;
    }

    @Override
    protected void interpolate(double v) {
        if(!hasPaused) {
            if (v <= 0.5 && visibility)
                invisible();
            if (v > 0.5 && !visibility)
                visible();
        }

    }

    private void visible() {
        for (ShootingBall shootingBall : centralBall.getBalls()) {
            shootingBall.getBall().setOpacity(1);
            shootingBall.getText().setOpacity(1);
            shootingBall.getLine().setOpacity(1);
        }
      visibility = true;
    }
    private void invisible() {
        for (ShootingBall shootingBall : centralBall.getBalls()) {
            shootingBall.getBall().setOpacity(0);
            shootingBall.getText().setOpacity(0);
            shootingBall.getLine().setOpacity(0);
        }
        visibility = false;
    }

}
