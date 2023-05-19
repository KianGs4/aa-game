package model;

import javafx.scene.image.Image;

public class User {
    private  String password;
    private  String username;
    public boolean isGuest;
    private String avatar;
    private final int  highScore;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.isGuest = false;
        highScore = 0;
        avatar = "/Images/AVATARS/" + Utils.generateRandomAvatarUrl();
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
}
