package view.user;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import model.DataBase;
import model.User;
import view.Main;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileMenuController implements Initializable {
    public Circle circle;
    public Text defText;
    public TextField changeBox;
    public Text message;
    
    public AnchorPane pane;
    private Alert alert;
    private DialogPane dialog;

    @FXML
    private Text username;

    @FXML
    private Text password;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User currentUser = PrimaryMenuController.currentUser;
        String avatar;
        if (currentUser.getAvatar().matches("/Images.+"))
             avatar = ProfileMenuController.class.getResource(currentUser.getAvatar()).toString();
        else avatar = currentUser.getAvatar();
        Image img = new Image(avatar.toString());
        circle.setFill(new ImagePattern(img));
        username.setText(currentUser.getUsername());
        password.setText(currentUser.getPassword());
        defText.setText("");
        message.setText("");
        changeBox.setVisible(false);
        initializeDefaultAvatars();
    }

    private void initializeDefaultAvatars() {
        for (int i = 0; i <= 5; i++) {
            createDefaultAvatar(i);
        }
    }

    private void createDefaultAvatar(int counter) {
        ImageView output = new ImageView();
        output.setImage(new Image(ProfileMenu.class.getResource("/Images/AVATARS/avatar" + counter + ".png").toExternalForm()));
        output.setFitWidth(87);
        output.setFitHeight(72);
        pane.getChildren().add(output);
        setImagePlace(output,counter);
        output.setOnMouseClicked(mouseEvent -> {
            PrimaryMenuController.currentUser.setAvatar(output.getImage().getUrl().toString());
            Image img = new Image(PrimaryMenuController.currentUser.getAvatar());
            circle.setFill(new ImagePattern(img));
            DataBase.getInstance().updateData();
        });
    }

    private void setImagePlace(ImageView output, int counter) {
        if (counter % 2 == 0) output.setTranslateX(572);
        else output.setTranslateX(669);
        output.setTranslateY(163 +  Math.floor( (double) counter/2) * 100 );
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

    public void selectPic(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("jpg file", "*.jpg")
                , new FileChooser.ExtensionFilter("jpeg file", "*.jpeg")
                , new FileChooser.ExtensionFilter("png file", "*.png")
        );
        File selectedPic = fileChooser.showOpenDialog(Main.stage);
        if (selectedPic == null) return;
        PrimaryMenuController.currentUser.setAvatar(selectedPic.getPath().toString());
        Image img = new Image(PrimaryMenuController.currentUser.getAvatar());
        circle.setFill(new ImagePattern(img));
        DataBase.getInstance().updateData();
    }
}
