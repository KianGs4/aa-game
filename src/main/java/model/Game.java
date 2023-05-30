package model;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class Game {
    //TODO add gameSetting To Game
    private final KeyCode shoot;
    private final KeyCode freeze;
    private final int numberOfBalls;
    private int currentBalls;
    private Phase phase;
    private final CentralBall firstCentralBall;
    private final CentralBall secondCentralBall;
    private double rotateSpeed;
    private double windSpeed;
    private int freezeTime;

    private final ArrayList<ShootingBall> shootingBalls = new ArrayList<>();


    public Game(KeyCode shoot, KeyCode freeze, int numberOfBalls) {
        this.shoot = shoot;
        this.freeze = freeze;
        this.numberOfBalls = numberOfBalls;
        currentBalls = numberOfBalls;
        phase = Phase.PHASE_1;
        secondCentralBall = new CentralBall((double) 850 / 2, (double) 420 / 2, 240);
        firstCentralBall = makeFirstCentralBall(secondCentralBall);
        createBalls(numberOfBalls);
        getConstants();
    }

    public void createSampleBalls(CentralBall secondCentralBall, Pane pane) {
        for (int i = 0; i < 315; i += GameSetting.getMap().getAngle()) {
            ShootingBall shootingBall = new ShootingBall("");
            shootingBall.getBall().setCenterX(Math.cos(i * GameSetting.getMap().getAngle()) * secondCentralBall.getRadius() + secondCentralBall.getCenterX());
            shootingBall.getBall().setCenterY(Math.sin(i * GameSetting.getMap().getAngle()) * secondCentralBall.getRadius() + secondCentralBall.getCenterY());
            pane.getChildren().add(shootingBall.getBall());
            shootingBall.makeLine(secondCentralBall, pane);
            secondCentralBall.getBalls().add(shootingBall);
            if (GameSetting.getMap().equals(GameMap.MAP1)) {
                if (i < 115) i *=2;
                else i += 50;
            }
        }
    }

    public void getConstants() {
        switch (GameSetting.getDifficulty()) {
            case "Easy":
                rotateSpeed = 2.5;
                windSpeed = 1.2;
                freezeTime = 7;
                break;
            case "Medium":
                rotateSpeed = 1.25;
                windSpeed = 1.5;
                freezeTime = 5;
                break;
            case "Hard":
                rotateSpeed = 0.83;
                windSpeed = 1.8;
                freezeTime = 3;
                break;
        }
    }

    private void createBalls(int currentBalls) {
        for (int i = currentBalls; i > 0; i--) {
            shootingBalls.add(new ShootingBall(Integer.valueOf(i).toString()));
        }
    }

    public CentralBall getFirstCentralBall() {
        return firstCentralBall;
    }

    public CentralBall getSecondCentralBall() {
        return secondCentralBall;
    }

    public Phase getPhase() {
        return phase;
    }

    public ArrayList<ShootingBall> getShootingBalls() {
        return shootingBalls;
    }

    public int getCurrentBalls() {
        return currentBalls;
    }

    public KeyCode getShoot() {
        return shoot;
    }

    public KeyCode getFreeze() {
        return freeze;
    }

    private CentralBall makeFirstCentralBall(CentralBall centralBall) {
        return new CentralBall(centralBall.getCenterX(), centralBall.getCenterY(), centralBall.getRadius() / 3);
    }

    public void shoot() {
        currentBalls--;
        shootingBalls.remove(0);
    }

    public double getRotateSpeed() {
        return rotateSpeed;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public int getFreezeTime() {
        return freezeTime;
    }

    public int getNumberOfBalls() {
        return numberOfBalls;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public void setRotateSpeed(double rotateSpeed) {
        this.rotateSpeed = rotateSpeed;
    }
}
