package model;


import javafx.scene.input.KeyCode;

public class GameSetting {

    private static String difficulty;
    private static String language;
    private static boolean BW_mode;
    private static int numberOfBalls;
    private static int sound;
    private static KeyCode shootKey;
    private static KeyCode frozenKey;


    static {
        difficulty = "Medium";
        language = "English";
        BW_mode = false;
        numberOfBalls = 10;
        sound = 10;
        shootKey = KeyCode.SPACE;
        frozenKey = KeyCode.TAB;
    }

    public static String getDifficulty() {
        return difficulty;
    }

    public static String getLanguage() {
        return language;
    }

    public static boolean isBW_mode() {
        return BW_mode;
    }

    public static int getNumberOfBalls() {
        return numberOfBalls;
    }

    public static KeyCode getShootKey() {
        return shootKey;
    }

    public static KeyCode getFrozenKey() {
        return frozenKey;
    }

    public static int getSound() {
        return sound;
    }

    public static void setDifficulty(String difficulty) {
        GameSetting.difficulty = difficulty;
    }

    public static void setLanguage(String language) {
        GameSetting.language = language;
    }

    public static void setBW_mode(boolean BW_mode) {
        GameSetting.BW_mode = BW_mode;
    }

    public static void setShootKey(KeyCode shootKey) {
        GameSetting.shootKey = shootKey;
    }

    public static void setFrozenKey(KeyCode frozenKey) {
        GameSetting.frozenKey = frozenKey;
    }

    public static void setNumberOfBalls(int numberOfBalls) {
        GameSetting.numberOfBalls = numberOfBalls;
    }

    public static void setSound(int sound) {
        GameSetting.sound = sound;
    }
}
