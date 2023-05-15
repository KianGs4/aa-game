package model;

public class Utils {

    public static String generateGuestUsername(DataBase dataBase) {
        String result = "guest_";
        do {
            result += Integer.valueOf((int) (Math.random() * 9)).toString();
        }
        while (dataBase.userExists(result));
        return result;
    }

    public static String generateRandomAvatarUrl() {
        String avatar = "avatar";
        avatar += Integer.valueOf((int) (Math.random() * 4)).toString();
        avatar += ".png";
        return avatar;
    }
}
