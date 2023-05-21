package view.user;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.User;
import view.Main;
import view.game.GameMenu;

import java.io.IOException;

public class PrimaryMenuController {
    public static User currentUser;

    public  AnchorPane pane;

    public PrimaryMenuController() {
    }

    public PrimaryMenuController(AnchorPane pane) {
        this.pane = pane;
    }

    public static void setProfileController(User user) {
        currentUser = user;
    }


    public void goToProfile(MouseEvent mouseEvent) throws Exception {

        new ProfileMenu().start(Main.stage);
    }

    public void translate() {
        VBox vbox = (VBox) pane.getChildren().get(0);
        ((Button) vbox.getChildren().get(0)).setText("بازی جدید");
        ((Button) vbox.getChildren().get(1)).setText("ادامه بازی");
        ((Button) vbox.getChildren().get(2)).setText("پروفایل");
        ((Button) vbox.getChildren().get(3)).setText("تابلوی نتایج");
        ((Button) vbox.getChildren().get(4)).setText("تنظیمات");
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
