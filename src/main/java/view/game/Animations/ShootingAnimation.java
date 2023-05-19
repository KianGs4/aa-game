package view.game.Animations;

import javafx.animation.Transition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.CentralBall;
import model.ShootingBall;

public class ShootingAnimation extends Transition {
    private CentralBall centralBall;
    private ShootingBall shootingBall;
    private Pane pane;

    public ShootingAnimation(CentralBall centralBall, ShootingBall shootingBall, Pane pane) {
        this.centralBall = centralBall;
        this.shootingBall = shootingBall;
        this.pane = pane;
        this.setCycleDuration(Duration.millis(1000));
        this.setCycleCount(-1);
    }

    @Override
    protected void interpolate(double v) {
        double y = shootingBall.getLayoutY() - 10 ;
        if (shootingBall.getBoundsInParent().intersects(centralBall.getBoundsInParent())) {
            centralBall.addBall(shootingBall);
            this.stop();
        }
        shootingBall.setLayoutY(y);
    }
}
