package model;

import javafx.scene.input.KeyCode;

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
    private  double rotateSpeed;
    private  double windSpeed;
    private  int freezeTime;

    private final ArrayList<ShootingBall> shootingBalls = new ArrayList<>();


    public Game(KeyCode shoot, KeyCode freeze, int numberOfBalls) {
        this.shoot = shoot;
        this.freeze = freeze;
        this.numberOfBalls = numberOfBalls;
        currentBalls = numberOfBalls;
        phase = Phase.PHASE_1;
        secondCentralBall = new CentralBall((double) 850 /2, (double) 420 /2, 240);
        firstCentralBall = makeFirstCentralBall(secondCentralBall);
        createBalls(numberOfBalls);
        getConstnats();
    }

    private void getConstnats() {
        switch (GameSetting.getDifficulty()) {
            case "Easy":
                rotateSpeed = 2.5;
                windSpeed= 1.2;
                freezeTime = 7;
                break;
            case "Medium":
                rotateSpeed = 1.25;
                windSpeed= 1.5;
                freezeTime = 5;
                break;
            case "Hard":
                rotateSpeed = 0.83;
                windSpeed= 1.8;
                freezeTime = 3;
                break;
        }
    }

    private void createBalls(int currentBalls) {
        for (int i = currentBalls; i > 0  ; i--) {
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
        return new CentralBall(centralBall.getCenterX(),centralBall.getCenterY(),centralBall.getRadius()/3);
    }

    public void shoot() {
        currentBalls --;
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
}
