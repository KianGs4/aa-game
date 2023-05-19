package model;

import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class CentralBall extends Circle {
    private final ArrayList<ShootingBall> balls = new ArrayList<>();

    public CentralBall(double v, double v1, double v2) {
        super(v, v1, v2);
    }

    public void addBall(ShootingBall shootingBall) {
        balls.add(shootingBall);
    }
}
