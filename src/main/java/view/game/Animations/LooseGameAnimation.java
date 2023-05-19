package view.game.Animations;

import javafx.animation.Transition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.CentralBall;
import model.ShootingBall;
import view.Main;
import view.game.GameMenu;

public class LooseGameAnimation extends Transition {
    Pane pane;

    public LooseGameAnimation(Pane pane) {
        this.pane = pane;
        this.setCycleDuration(Duration.millis(5000));
        this.setCycleCount(1);
    }

    @Override
    protected void interpolate(double v) {
        String redColor = "0000";
//TODO increase red by time ;
        pane.getStylesheets().add(Main.class.getResource("/CSS/style1.css").toString());
        pane.getStyleClass().add("red-pane");

        // pane.setStyle("-fx-background-color: red");
    }
}
