package view.user;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.GameSetting;
import view.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingMenuController implements Initializable {
    public RadioButton mode;
    public ChoiceBox languageBox, difficultyBox;
    public Button shootButton;
    public Button freezeButton;

    //TODO Handle close and save option
    //TODO make more CSS
    //TODO Handle maps to json


    @FXML
    private Text ballsValue;

    @FXML
    private Pane pane;

    @FXML
    private Parent fxml;
    public Slider slider;

    public AnchorPane mainPane;

    public SettingMenuController() {

    }

    public SettingMenuController(AnchorPane pane) {
        this.mainPane = pane;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        new PrimaryMenu().start(Main.stage);
    }

    public void translate() {
        VBox vBox = (VBox) mainPane.getChildren().get(0);
        HBox hBox = (HBox) mainPane.getChildren().get(1);
        ((Text) vBox.getChildren().get(0)).setText("تنظیمات");
        ((Button) vBox.getChildren().get(1)).setText("عمومی");
        ((Button) vBox.getChildren().get(2)).setText("نقشه ها");
        ((Button) vBox.getChildren().get(3)).setText("بازی");
        ((Button) vBox.getChildren().get(4)).setText("خانه");
        ((Button) hBox.getChildren().get(0)).setText("کمک");
        ((Button) hBox.getChildren().get(1)).setText("لغو");
        ((Button) hBox.getChildren().get(2)).setText("ذخیره");
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
        ((Button) samplePane.getChildren().get(6)).setText(GameSetting.getShootKey().toString());
        ((Button) samplePane.getChildren().get(8)).setText(GameSetting.getFrozenKey().toString());

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
        ((RadioButton) samplePane.getChildren().get(3)).setSelected((GameSetting.isBW_mode()));


    }

    public void changeNumberOfBalls(MouseEvent mouseEvent) {
        ballsValue.setText(Integer.valueOf((int) slider.getValue()).toString());
        GameSetting.setNumberOfBalls(Integer.parseInt(ballsValue.getText()));
    }

    public void selectBWmode(ActionEvent actionEvent) {
        GameSetting.setBW_mode(mode.isSelected());
    }

    public void changeLanguage(ActionEvent actionEvent) {
        GameSetting.setLanguage(languageBox.getValue().toString());
    }

    public void changeDifficulty(ActionEvent actionEvent) {
        GameSetting.setDifficulty(difficultyBox.getValue().toString());
    }

    public void changeShootKey(MouseEvent mouseEvent) {
        final boolean[] keyAlertPressed = new boolean[2];

        Main.stage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (!keyAlertPressed[0] || !keyAlertPressed[1]) {
                    GameSetting.setShootKey(keyEvent.getCode());
                    shootButton.setText(keyEvent.getCode().getName());
                    if (keyAlertPressed[0]) keyAlertPressed[1] = true;
                    keyAlertPressed[0] = true;
                }
            }
        });

    }

    public void changeFreezeKey(MouseEvent mouseEvent) {
        final boolean[] keyAlertPressed = new boolean[2];

        Main.stage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (!keyAlertPressed[0] || !keyAlertPressed[1]) {
                    GameSetting.setFrozenKey(keyEvent.getCode());
                    freezeButton.setText(keyEvent.getCode().getName());
                    if (keyAlertPressed[0]) keyAlertPressed[1] = true;
                    keyAlertPressed[0] = true;
                }
            }
        });
    }
}
