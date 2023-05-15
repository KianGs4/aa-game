package view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import model.DataBase;
import model.User;
import model.Utils;

public class LoginMenuController {
    private final DataBase dataBase = DataBase.getInstance();
    public Text passwordError;
    public Text usernameError;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    public void signIn(MouseEvent mouseEvent) {
        setErrors();
        checkErrors();
        if (haveError()) resetFields();
        else {
            //profile
        }
    }

    private void setErrors() {
        usernameError.setText("");
        passwordError.setText("");
    }

    private void checkErrors() {
        if (dataBase.userExists(username.getText())) usernameError.setText("The username does not exist");
        else if (!dataBase.getUser(username.getText()).isPasswordCorrect(password.getText()))
            passwordError.setText("The password entered is incorrect");
    }



    private boolean haveError() {
        return !usernameError.getText().equals("") || !passwordError.getText().equals("");
    }

    private void resetFields() {
        password.setText("");
    }

    public void loginAsGuest(MouseEvent mouseEvent) {
        dataBase.addGuest(new User(Utils.generateGuestUsername(dataBase),"@guest" ));
        //profile
    }

}
