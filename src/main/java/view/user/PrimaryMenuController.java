package view.user;

import javafx.scene.input.MouseEvent;
import model.User;
import view.Main;
import view.game.GameMenu;

import java.io.IOException;

public class PrimaryMenuController {
    public static User currentUser;

    public static void setProfileController(User user) {
        currentUser = user;
    }


    public void goToProfile(MouseEvent mouseEvent) throws Exception {

        new ProfileMenu().start(Main.stage);
    }


    public void goToScoreBoardMenu(MouseEvent mouseEvent) throws Exception {
        new ScoreboardMenu().start(Main.stage);
    }

    public void goToSetting(MouseEvent mouseEvent) throws Exception {
        new SettingMenu().start(Main.stage);
    }

    public void goToGame(MouseEvent mouseEvent) throws IOException {
        GameMenu.currentPlayer = currentUser;
        new GameMenu().start(Main.stage);
    }
}
