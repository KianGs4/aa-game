package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import model.GameSetting;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingMenuController implements Initializable {
    //TODO Handle close and save option
    //TODO make more CSS

    @FXML
    private Text ballsValue;
    @FXML
    private Pane pane;

    @FXML
    private Parent fxml;
    public Slider slider;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        new PrimaryMenu().start(Main.stage);
    }

    public void openGameSetting(MouseEvent mouseEvent) {
        try {
            fxml = FXMLLoader.load(getClass().getResource("/FXML/setting/game.fxml"));
            pane.getChildren().removeAll();
            pane.getChildren().setAll(fxml);
            setDefaultOfGameSetting();
        } catch (IOException ignored) {
        }
    }

    private void setDefaultOfGameSetting() {
        Pane samplePane = (Pane) pane.getChildren().get(0);
        ((Slider) samplePane.getChildren().get(0)).setValue(GameSetting.getNumberOfBalls());
        ((Text) samplePane.getChildren().get(2)).setText(Integer.valueOf(GameSetting.getNumberOfBalls()).toString());
        ((ChoiceBox) samplePane.getChildren().get(4)).getItems().addAll("Easy", "Medium", "Hard");
        ((ChoiceBox) samplePane.getChildren().get(4)).setValue(GameSetting.getDifficulty());

    }

    public void openGeneralSetting(MouseEvent mouseEvent) {
        try {
            fxml = FXMLLoader.load(getClass().getResource("/FXML/setting/general.fxml"));
            pane.getChildren().removeAll();
            pane.getChildren().setAll(fxml);
            pane.getChildren().setAll(fxml);
            setDefaultOfGeneralSetting();

        } catch (IOException ignored) {
        }
    }

    private void setDefaultOfGeneralSetting() {
        Pane samplePane = (Pane) pane.getChildren().get(0);
        ((ChoiceBox) samplePane.getChildren().get(0)).getItems().addAll("English", "Persian");
        ((ChoiceBox) samplePane.getChildren().get(0)).setValue(GameSetting.getLanguage());
        ((Slider) samplePane.getChildren().get(4)).setValue(GameSetting.getSound());
    }

    public void changeNumberOfBalls(MouseEvent mouseEvent) {
        ballsValue.setText(Integer.valueOf((int) slider.getValue()).toString());
    }

}
