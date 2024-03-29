package model;

import model.User;
import model.UserManager;

import java.util.ArrayList;
import java.util.HashMap;


public class DataBase {

    private static DataBase instance = null;
    private final HashMap<String, User> users = new HashMap<>();
    private final ArrayList<User> userRankings = new ArrayList<>();

    private DataBase() {
    }

    public static void load() {
        if (instance == null) {
            instance = new DataBase();
            UserManager.load(instance);
        }
    }

    public static DataBase getInstance() {
        return instance;
    }

    public void updateData() {
        UserManager.updateAllUsers(users.values());
    }

    public User getUser(String username) {
        if (!users.containsKey(username)) return null;
        return users.get(username);
    }

    public void addUser(User user) {
        users.put(user.getUsername(), user);
        userRankings.add(user);
        updateRankings();
        // do we need to call updateRankings() here?
        updateData();
    }

    public void removeUser(User user) {
        users.remove(user.getUsername());
        userRankings.remove(user);
        updateData();
    }

    public void addGuest(User user) {
        users.put(user.getUsername(), user);
        userRankings.add(user);
    }

    public void removeGuests() {
        for (User user : users.values()) {
            if (user.isGuest()) users.remove(user.getUsername());
        }
    }


    public void updateRankings() {
        userRankings.sort((o1, o2) -> o1.getUsername().compareTo(o2.getUsername()));
        userRankings.sort((o1, o2) -> o2.getTimeInfo() - o1.getTimeInfo());
        userRankings.sort(((o1, o2) -> o2.getHighScore() - o1.getHighScore()));
    }



    public int getUserRank(User user) {
        for (int i = 0; i < userRankings.size(); i++) {
            if (userRankings.get(i).equals(user)) return i + 1;
        }
        return 0;
    }

    public ArrayList<User> getUserRankings() {
        return userRankings;
    }

    public boolean userExists(String username) {
        return users.containsKey(username);
    }

}
