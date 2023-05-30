package view.user;

import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import model.DataBase;
import model.User;
import view.Main;

import java.net.URL;

import java.util.ResourceBundle;

public class ScoreBoardMenuController implements Initializable {
    public Text label;
    public Text username;
    public Circle circle_currentUser;
    public Text userPoint;
    public Circle showCircle_currentUser;
    public VBox vBox;
    public Button back;
    private int showMode = 0;
    private int indexShow = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User currentUser = PrimaryMenuController.currentUser;
        username.setText(currentUser.getUsername());
        username.maxWidth(username.getText().length());
        String avatar;
        if (currentUser.getAvatar().matches("/Images.+"))
            avatar = ProfileMenuController.class.getResource(currentUser.getAvatar()).toString();
        else avatar = currentUser.getAvatar();
        Image img = new Image(avatar);
        circle_currentUser.setFill(new ImagePattern(img));
        username.setText(currentUser.getUsername());
        userPoint.setText(Integer.valueOf(currentUser.getHighScore()).toString());
        showCircle_currentUser.setFill(new ImagePattern(img));
        showNodes();
    }

    private void showNodes() {
        indexShow = 0;
        for (Node hBox : vBox.getChildren()) {
            if (hBox instanceof HBox) {
                loadNode(hBox);
                indexShow++;
            }
        }
    }

    private void loadNode(Node node) {
        ObservableList<Node> children = ((HBox) node).getChildren();
        if (indexShow < DataBase.getInstance().getUserRankings().size()) {
            User user = DataBase.getInstance().getUserRankings().get(indexShow);
            if (user.equals(PrimaryMenuController.currentUser))
                showCurrentUser(node);
             else node.getStyleClass().remove("current-user-in-scoreboard");

            if (showMode == 0 || showMode == user.getDifficultyOfScore()) {
                addUserToRanking(children, user);
                node.setVisible(true);
            } else {
                indexShow++;
                loadNode(node);
            }

        } else
            node.setVisible(false);


    }

    public void back(MouseEvent mouseEvent) throws Exception {
        new PrimaryMenu().start(Main.stage);
    }

    private void showCurrentUser(Node node) {
        ((HBox) node).getStylesheets().add(getClass().getResource("/CSS/scoreboard.css").toString());
        ((HBox) node).getStyleClass().add("current-user-in-scoreboard");
    }

    private void addUserToRanking(ObservableList<Node> children, User user) {
        URL avatar = ProfileMenuController.class.getResource(user.getAvatar());
        assert avatar != null;
        Image img = new Image(avatar.toString());
        ((Circle) children.get(1)).setFill(new ImagePattern(img));
        ((Label) children.get(2)).setText(user.getUsername());
        ((Text) children.get(3)).setText(Integer.valueOf(user.getHighScore()).toString());
    }

    public void DefaultMode(MouseEvent mouseEvent) {
        showMode = 0;
        showNodes();
    }

    public void MediumMode(MouseEvent mouseEvent) {
        showMode = 2;
        showNodes();

    }

    public void EasyMode(MouseEvent mouseEvent) {
        showMode = 1;
        showNodes();
    }

    public void HardMode(MouseEvent mouseEvent) {
        showMode = 3;
        showNodes();
    }
}





