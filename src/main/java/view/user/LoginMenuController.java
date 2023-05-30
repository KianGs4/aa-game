package view.user;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import model.DataBase;
import model.User;
import model.UserManager;
import model.Utils;
import view.Main;

public class LoginMenuController {
    private final DataBase dataBase = DataBase.getInstance();
    public Text passwordError;
    public Text usernameError;
    public RadioButton radioButton;

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    public void signIn(MouseEvent mouseEvent) throws Exception {
        setErrors();
        checkErrors();
        if (haveError()) resetFields();
        else {
            User currentUser = dataBase.getUser(username.getText());
            if(radioButton.isSelected()) UserManager.setLoggedInUser(currentUser);
            goToProfile(currentUser);
        }
    }

    private void setErrors() {
        usernameError.setText("");
        passwordError.setText("");
    }

    private void checkErrors() {
        if (!dataBase.userExists(username.getText())) usernameError.setText("The username does not exist");
        else if (!dataBase.getUser(username.getText()).isPasswordCorrect(password.getText()))
            passwordError.setText("The password entered is incorrect");
    }



    private boolean haveError() {
        return !usernameError.getText().equals("") || !passwordError.getText().equals("");
    }

    private void resetFields() {
        password.setText("");
    }

    public void loginAsGuest(MouseEvent mouseEvent) throws Exception {
        User guest = new User(Utils.generateGuestUsername(dataBase),"@guest" );
        dataBase.addGuest(guest);
        if(radioButton.isSelected()) UserManager.setLoggedInUser(guest);
        goToProfile(guest);
    }

    private void goToProfile(User user) throws Exception {
        PrimaryMenuController.setProfileController(user);
        new PrimaryMenu().start(Main.stage);
    }

}
