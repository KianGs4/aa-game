package model;

import javafx.scene.image.Image;

import java.util.HashMap;

public class User {
    private  String password;
    private  String username;
    public boolean isGuest;
    private String avatar;
    private int  highScore;
    private model.Pair info;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.isGuest = false;
        highScore = 0;
        avatar = "/Images/AVATARS/" + Utils.generateRandomAvatarUrl();
        info = new Pair("Medium",0);
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public boolean isPasswordCorrect(String password) {
        return this.password.equals(password);
    }

    public int getHighScore() {
        return highScore;
    }

    public String getAvatar() {
        return avatar;
    }

    public int getTimeInfo() {
        return info.getV2();
    }

    public int getDifficultyOfScore(){
        switch (info.getV1()) {
            case "Easy":
                return 1;
            case "Medium":
                return 2;
            case "Hard":
                return 3;
        }
        return 0;
    }

    public boolean isGuest() {
        return isGuest;
    }

    public void setGuest(boolean guest) {
        isGuest = guest;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public void setInfo(String difficulty, int time) {
        this.info.setV1(difficulty);
        this.info.setV2(time);
    }
}
