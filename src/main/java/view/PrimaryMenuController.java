package view;

import controller.ParentController;
import javafx.scene.control.Menu;
import javafx.scene.input.MouseEvent;

public class PrimaryMenuController {
    private static ParentController profileController;

    public static void setProfileController(ParentController profileController) {
        profileController = profileController;
    }


    public void goToProfile(MouseEvent mouseEvent) throws Exception {
        new ProfileMenu().start(Main.stage);
    }


}
