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

    public static int generateRandomWind(double windRate) {
        double random = Math.random() * 10;
        int sign = (int) (Math.random() * 2) ;
        if (sign == 0) random *= -1;
        random *= windRate;
        if (random >= 15) random = 15;
        else if (random <= -15) random = -15;
        return (int) random;
    }
}
