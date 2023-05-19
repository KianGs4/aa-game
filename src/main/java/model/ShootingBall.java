package model;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import view.Main;

public class ShootingBall extends StackPane {
    Circle ball = new Circle( 14);
    Line line;
    Text text;

    public ShootingBall(String text) {
        this.text = new Text(text);
        this.text.setBoundsType(TextBoundsType.VISUAL);
        this.text.setFill(Paint.valueOf("white"));
        this.getChildren().add(ball);
        this.getChildren().add(this.text);
        this.setLayoutX((double) 850 / 2);
        this.setLayoutY(646);
    }

    public void moveToShoot() {
        this.setLayoutY(this.getLayoutY() - 50);
        ball.setCenterY(ball.getCenterY() - 10);
    }

    public double getRadius() {
        return ball.getRadius();
    }

    public void makeLine(Circle circle, Pane pane) {
        line = new Line();
        line.setStartX(circle.getCenterX());
        line.setStartY(circle.getCenterY());
        line.setEndX(circle.getCenterX() + this.getChildren().get(0).getLayoutX());
        line.setEndY(this.getLayoutY());
          this.getChildren().add(line);
    }

    public Line getLine() {
        return line;
    }
}
