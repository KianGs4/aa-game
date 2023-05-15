package view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class LoginMenuController {
    public Text passwordError;
    public Text usernameError;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    public void signIn(MouseEvent mouseEvent) {
        System.out.println(username.getText());
        System.out.println(password.getText());
        if (username.getText().length() < 4){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("submit error");
            alert.setContentText("username is week");
            alert.showAndWait();
        }
    }

}
