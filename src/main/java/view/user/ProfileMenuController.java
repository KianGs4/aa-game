package view.user;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import model.DataBase;
import model.User;
import view.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileMenuController implements Initializable {
    public Circle circle;
    public Text defText;
    public TextField changeBox;
    public Text message;
    private Alert alert;
    private DialogPane dialog;

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
        changeBox.setVisible(false);
    }

    public void changeUsername() {
        changeBox.setVisible(true);
        message.setText("");
        defText.setText("make your change");
        changeBox.setPromptText("username");
        Main.stage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                    defText.setText("");
                    if (!DataBase.getInstance().userExists(changeBox.getText())) {
                        PrimaryMenuController.currentUser.setUsername(changeBox.getText());
                        DataBase.getInstance().updateData();
                        message.setStyle("-fx-background-color: green");
                        message.setText("username has changed successfully");
                        username.setText(changeBox.getText());
                    } else {
                        username.setText(PrimaryMenuController.currentUser.getUsername());
                        message.setStyle("-fx-background-color: #dc3838");
                        message.setText("username has already taken");
                    }
                    changeBox.setText("");

                }

            }
        });
    }

    public void changePassword() {
        changeBox.setVisible(true);
        message.setText("");
        defText.setText("make your change");
        changeBox.setPromptText("password");
        Main.stage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                    defText.setText("");
                    if (changeBox.getText().matches("[\\D]+|[\\d]+|[.]{0,5}")) {
                        message.setStyle("-fx-background-color: #dc3838");
                        message.setText("password is weak");
                    } else {
                        PrimaryMenuController.currentUser.setPassword(changeBox.getText());
                        DataBase.getInstance().updateData();
                        message.setStyle("-fx-background-color: green");
                        password.setText(changeBox.getText());
                        message.setText("password has changed successfully");

                    }
                    changeBox.setText("");

                }

            }
        });

    }

    public void showAlert(MouseEvent mouseEvent) throws Exception {
        alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete your account?",ButtonType.YES,ButtonType.CANCEL);
        alert.setHeaderText("Warning");
        dialog = alert.getDialogPane();
        dialog.getStylesheets().add(getClass().getResource("/CSS/profileChange.css").toString());
        dialog.getStyleClass().add("dialog");
        alert.showAndWait();
        if(alert.getResult().getText().equals("Yes"))
            deleteAccount();
    }

    private void deleteAccount() throws Exception {
        DataBase.getInstance().removeUser(PrimaryMenuController.currentUser);
        new Main().start(Main.stage);
    }

    public void goToLoginMenu(MouseEvent mouseEvent) throws Exception {
        new Main().start(Main.stage);
    }

    public void goToPrimaryMenu(MouseEvent mouseEvent) throws Exception {
        new PrimaryMenu().start(Main.stage);
    }
}
