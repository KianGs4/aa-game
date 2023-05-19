package view.game.Animations;

import javafx.animation.Transition;
import javafx.util.Duration;
import model.CentralBall;
import model.ShootingBall;

public class RotatePhase1Animation extends Transition {
    private int angle = 0;
    private final double fixed_y, fixed_x;
    private final ShootingBall shootingBall;
    private final CentralBall centralBall;

    public RotatePhase1Animation(ShootingBall shootingBall, CentralBall centralBall) {
        this.shootingBall = shootingBall;
        this.centralBall = centralBall;
        this.setCycleDuration(Duration.millis(2500));
        this.setCycleCount(-1);
        fixed_x = shootingBall.getBall().getCenterX();
        fixed_y = shootingBall.getBall().getCenterY();
    }


    @Override
    protected void interpolate(double v) {

        angle += 5;
        if (angle == 360) {
            angle = 0;
        }
        double x1 = fixed_x - centralBall.getCenterX();
        double y1 = fixed_y - centralBall.getCenterY();

        double x2 = x1 * Math.cos(angle) - y1 * Math.sin(angle);
        double y2 = x1 * Math.sin(angle) + y1 * Math.cos(angle);

        shootingBall.getBall().setCenterX(x2);
        shootingBall.getBall().setCenterY(y2);
    }
}
