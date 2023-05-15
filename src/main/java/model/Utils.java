package model;

public class Utils {

    public static String generateGuestUsername(DataBase dataBase) {
        String result = "guest_";
        while (dataBase.userExists(result)) {
            result += Integer.valueOf((int) (Math.random() * 9)).toString();
        }
        return result;
    }
}
