package model;

public class User {
    private final String password;
    private final String username;
    public boolean isGuest = false;
    private final int  highScore;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.isGuest = false;
        highScore = 0;
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

    public boolean isGuest() {
        return isGuest;
    }

    public void setGuest(boolean guest) {
        isGuest = guest;
    }
}
