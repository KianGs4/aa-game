package model;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;

public class ShootingBall extends StackPane {
    private final Circle ball;
    private Line line;
    private Text text;

    public ShootingBall(String text) {
        ball = new Circle((double) 850 / 2, 646, 14);
       initialiseText(text);
        this.getChildren().add(ball);
        this.getChildren().add(this.text);
    }
    public ShootingBall(String text, double y) {
        ball = new Circle((double) 850 / 2, y, 14);
        initialiseText(text);
        this.getChildren().add(ball);
        this.getChildren().add(this.text);
    }


    private void initialiseText(String text) {
        this.text = new Text(text);
        if (text.equals("")) return;
        this.text.setY(ball.getCenterY() + ball.getRadius()/3 );

        if (Integer.parseInt(text) < 10 ) {
            this.text.setX(ball.getCenterX() - ball.getRadius()/3);
        } else {
            this.text.setX(ball.getCenterX() - ball.getRadius()/2);
        }
        this.text.setBoundsType(TextBoundsType.VISUAL);
        this.text.setTextAlignment(TextAlignment.CENTER);
        this.text.setFill(Paint.valueOf("white"));
    }

    public void moveToShoot() {
        text.setY(text.getY() - 50);
        ball.setCenterY(ball.getCenterY() - 50);
    }

    public double getRadius() {
        return ball.getRadius();
    }

    public void makeLine(Circle circle, Pane pane) {
        line = new Line();
        line.setStartX(circle.getCenterX());
        line.setStartY(circle.getCenterY());
        line.setEndX(this.ball.getCenterX());
        line.setEndY(this.ball.getCenterY());
        pane.getChildren().add(line);
    }

    public Line getLine() {
        return line;
    }

    public Circle getBall() {
        return ball;
    }

    public Text getText() {
        return text;
    }
}
