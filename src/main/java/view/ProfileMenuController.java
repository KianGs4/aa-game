package view;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import model.DataBase;
import model.User;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileMenuController implements Initializable {
    public Circle circle;
    public Text defText;
    public TextField changeBox;
    public Text message;

    @FXML
    private Text username;

    @FXML
    private Text password;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User currentUser = PrimaryMenuController.currentUser;
        URL avatar = ProfileMenuController.class.getResource(currentUser.getAvatar());
        Image img = new Image(avatar.toString());
        circle.setFill(new ImagePattern(img));
        username.setText(currentUser.getUsername());
        password.setText(currentUser.getPassword());
        defText.setText("");
        message.setText("");
    }

    public void changeUsername() {
        message.setText("");
        defText.setText("make your change");
        changeBox.setPromptText("username");
        Main.stage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.ENTER)){
                    defText.setText("");
                    if (!DataBase.getInstance().userExists(changeBox.getText())) {
                        PrimaryMenuController.currentUser.setUsername(changeBox.getText());
                        DataBase.getInstance().updateData();
                        message.setStyle("-fx-background-color: green");
                        message.setText("username has changed successfully");
                        username.setText(changeBox.getText());
                    }else {
                     username.setText(PrimaryMenuController.currentUser.getUsername());
                        message.setStyle("-fx-background-color: #dc3838");
                        message.setText("username has already taken");
                    }
                    changeBox.setText("");

                }

            }
        });

    }
}
