package view.game.Animations;

import javafx.animation.Transition;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.CentralBall;
import model.Phase;
import model.ShootingBall;
import model.Utils;
import view.game.GameMenu;

public class ShootingAnimation extends Transition {
    private final CentralBall centralBall;
    private final ShootingBall shootingBall;
    private final GameMenu gameMenu;
    private int secondPlayerMode = 1;

    public ShootingAnimation(CentralBall centralBall, ShootingBall shootingBall, GameMenu gameMenu) {
        this.centralBall = centralBall;
        this.shootingBall = shootingBall;
        this.gameMenu = gameMenu;
        this.setCycleDuration(Duration.millis(5000));
        this.setCycleCount(-1);
    }

    public ShootingAnimation(CentralBall centralBall, ShootingBall shootingBall, GameMenu gameMenu, int playerMode) {
        this.centralBall = centralBall;
        this.shootingBall = shootingBall;
        this.gameMenu = gameMenu;
        this.setCycleDuration(Duration.millis(5000));
        this.setCycleCount(-1);
        secondPlayerMode = playerMode;
    }

    @Override
    protected void interpolate(double v) {
        double y = shootingBall.getBall().getCenterY() - (secondPlayerMode * 10);
        double x = shootingBall.getBall().getCenterX() + (((double) gameMenu.wind / 4) * secondPlayerMode);
        if (shootingBall.getBall().getBoundsInParent().intersects(centralBall.getBoundsInParent())) {
            stick();
            this.stop();
        }

        if (shootingBall.getBall().getCenterY() - 10 - shootingBall.getBall().getRadius()/2 < 0 ||
            shootingBall.getBall().getCenterY() + 10 + shootingBall.getBall().getRadius()/2 > gameMenu.pane.getHeight()) {
            outOfWindow();
            stop();
        }
        shootingBall.getBall().setCenterY(y);
        shootingBall.getText().setY(y);
        shootingBall.getBall().setCenterX(x);
        shootingBall.getText().setX(x);
    }

    private void stick() {
        centralBall.addBall(shootingBall);
        shootingBall.makeLine(centralBall, gameMenu.pane);
        gameMenu.setRotation(shootingBall);
        if (gameMenu.getGame().getPhase().equals(Phase.PHASE_4)) {
            gameMenu.wind = Utils.generateRandomWind(gameMenu.getGame().getWindSpeed());
            ((Text) gameMenu.pane.getChildren().get(3)).setText(Integer.valueOf(gameMenu.wind).toString());
        }
    }

    private void outOfWindow() {
        try {
            gameMenu.hasContinue = false;
            gameMenu.endGameSituation();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
