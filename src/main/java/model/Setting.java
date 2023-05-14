package model;

public enum Setting {
    RESOURCE_PATH("Resources/"),
    USERS_PATH("Resources/user.user"),
    LOGGED_IN_USER_PATH("Resources/loggedIn.user");

    private final String address;

    private Setting(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
}
