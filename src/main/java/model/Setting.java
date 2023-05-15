package model;

public enum Setting {
    RESOURCE_PATH("DataBase/"),
    USERS_PATH( "DataBase/user.user"),
    LOGGED_IN_USER_PATH("DataBase/loggedIn.user");

    private final String address;

    private Setting(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
}
