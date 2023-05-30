package view.game.Animations;

import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.CentralBall;
import model.ShootingBall;
import view.game.GameMenu;

public class PassingTimeAnimation extends Transition {


    public PassingTimeAnimation(GameMenu gameMenu, int time, ImageView img) {
        this.setCycleCount(1);
        this.setCycleDuration(Duration.seconds(time));
        this.setOnFinished(event -> {
            gameMenu.backToNormal(img);
        });
    }



    @Override
    protected void interpolate(double v) {
    }
}
