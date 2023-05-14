package view;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import model.User;

public class SignupMenuController {
    public Text usernameError;
    public Text passwordError;
    public Text passwordConfirmationError;
    public Text success;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField passwordConfirmation;

    @FXML
    private TextField username;

    public void signUp(MouseEvent mouseEvent) {
        setErrors();
        checkUsernameError();
        checkPasswordError();
        if (haveError()) resetFields();
        else {

            success.setText("Your Account created successfully");
        }
    }

    private void setErrors() {
        usernameError.setText("");
        passwordConfirmationError.setText("");
        passwordError.setText("");
    }

    private void checkUsernameError() {
        if (username.getText().length() < 4) {
            usernameError.setText("username must has at least 4 characters");
        }
    }

    private void checkPasswordError() {
        if (password.getText().length() < 6) {
            passwordError.setText("Password is week(password must has at least 6 characters)");
        } else if (password.getText().matches("[\\D]+|[\\d]+|[\\S*\\s+\\S]+")) {
            passwordError.setText("Password is week(password must include at least 1 character,1 number)");
        } else if (!password.getText().equals(passwordConfirmation.getText())) {
            passwordError.setText("Passwords do not match");
            passwordConfirmationError.setText("Passwords do not match");
        }
    }

    private boolean haveError() {
        return !usernameError.getText().equals("") || !passwordError.getText().equals("") || !passwordConfirmationError.getText().equals("");
    }

    private void resetFields() {
        password.setText("");
        passwordConfirmation.setText("");
    }
    private void createAccount() {
        User newUser = new User(username.getText(), password.getText());
    }
}
