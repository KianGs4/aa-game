package view.game.Animations;

import javafx.animation.Transition;
import javafx.util.Duration;
import model.CentralBall;
import model.ShootingBall;

public class VisibilityModeAnimation extends Transition {
    private CentralBall centralBall;
    private boolean visibility;

    public VisibilityModeAnimation(CentralBall centralBall) {
        this.centralBall = centralBall;
        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(2000));
        visibility = true;
    }

    @Override
    protected void interpolate(double v) {
        if (v <= 0.5 && visibility)
            invisible();
        if (v > 0.5 && !visibility)
            visible();

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
