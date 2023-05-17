package view;

import controller.ParentController;
import javafx.scene.control.Menu;
import javafx.scene.input.MouseEvent;
import model.User;

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
}
