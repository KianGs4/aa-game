package view.game.Animations;

import javafx.animation.Transition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.CentralBall;
import model.ShootingBall;
import view.game.GameMenu;

public class ShootingAnimation extends Transition {
    private final CentralBall centralBall;
    private final ShootingBall shootingBall;
    private final GameMenu  gameMenu;

    public ShootingAnimation(CentralBall centralBall, ShootingBall shootingBall, GameMenu gameMenu) {
        this.centralBall = centralBall;
        this.shootingBall = shootingBall;
        this.gameMenu = gameMenu;
        this.setCycleDuration(Duration.millis(5000));
        this.setCycleCount(-1);
    }

    @Override
    protected void interpolate(double v) {
        double y = shootingBall.getBall().getCenterY() - 10 ;
        if (shootingBall.getBall().getBoundsInParent().intersects(centralBall.getBoundsInParent())) {
            centralBall.addBall(shootingBall);
            shootingBall.makeLine(centralBall, gameMenu.pane);
            gameMenu.setRotation(shootingBall);
            this.stop();
        }
        shootingBall.getBall().setCenterY(y);
        shootingBall.getText().setY(y);
    }
}
