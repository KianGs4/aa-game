package model;

public enum GameMap {
    MAP1(25),
    MAP2(70),
    MAP3(50);
    GameMap(int angle) {
        this.angle = angle;
    }

    private final int angle;

    public int getAngle() {
        return angle;
    }
}
