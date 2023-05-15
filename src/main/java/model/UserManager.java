package model;

import com.google.gson.*;
import model.DataBase;
import model.Setting;
import model.User;
import java.io.*;
import java.util.Collection;
import java.util.Set;

public class UserManager {
    public static void load(DataBase dataBase) {
        initializeResources();
        Reader reader;
        try {
            reader = new FileReader(Setting.USERS_PATH.getAddress());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
        if (jsonObject == null)
            return;
        JsonArray usersArray = jsonObject.getAsJsonArray("users");
        for (JsonElement element : usersArray)
            dataBase.addUser(gson.fromJson(element, User.class));
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void initializeResources() {
        File resourceDir = new File(Setting.RESOURCE_PATH.getAddress());
        if (!resourceDir.exists())
            resourceDir.mkdir();

        try {
            new File(Setting.USERS_PATH.getAddress()).createNewFile();
            new File(Setting.LOGGED_IN_USER_PATH.getAddress()).createNewFile();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void updateAllUsers(Collection<User> users) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        JsonObject mainObject = new JsonObject();
        JsonArray usersArray = new JsonArray();
        for (User user : users)
            usersArray.add(gson.toJsonTree(user).getAsJsonObject());
        mainObject.add("users", usersArray);
        try {
            FileWriter fileWriter = new FileWriter(Setting.USERS_PATH.getAddress());
            fileWriter.write(gson.toJson(mainObject));
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static User getLoggedInUser() {
        Reader reader;
        try {
            reader = new FileReader(Setting.LOGGED_IN_USER_PATH.getAddress());
            User user = new Gson().fromJson(reader, User.class);
            reader.close();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setLoggedInUser(User user) {
        try {
            FileWriter fileWriter = new FileWriter(Setting.LOGGED_IN_USER_PATH.getAddress());
            fileWriter.write(new Gson().toJson(user));
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
