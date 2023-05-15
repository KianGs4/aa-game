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
            // do we need to call updateRankings() here?
            updateData();
        }

        public void updateRankings() {
        }

        public int getUserRank(User user) {
            for (int i = 0; i < userRankings.size(); i++) {
                if (userRankings.get(i).equals(user)) return i + 1;
            }
            return 0;
        }



        public boolean userExists(String username) {
            return users.containsKey(username);
        }

    }
