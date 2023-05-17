package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Slider;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.RotateEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;

public class SettingMenuController {

    @FXML
    private Text ballsValue;
    @FXML
    private Pane pane;

    @FXML
    private Parent fxml;
    public Slider slider;


    public void back(MouseEvent mouseEvent) throws Exception {
        new PrimaryMenu().start(Main.stage);
    }

    public void openGameSetting(MouseEvent mouseEvent) {
        try {
            fxml = FXMLLoader.load(getClass().getResource("/FXML/setting/game.fxml"));
            pane.getChildren().removeAll();
            pane.getChildren().setAll(fxml);
        } catch (IOException ignored) {
        }
    }

    public void changeNumberOfBalls(MouseEvent mouseEvent) {
        ballsValue.setText(Integer.valueOf((int) slider.getValue()).toString());
    }
}
