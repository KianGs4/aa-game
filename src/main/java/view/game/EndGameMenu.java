package view.game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.Main;
import view.user.PrimaryMenu;

public class EndGameMenu extends Application {
    private static Stage endGameStage;
    private static int currentScore;

    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = FXMLLoader.load(Main.class.getResource("/FXML/game/endGameShow.fxml"));

        ((Text) pane.getChildren().get(0)).setText("your score: " + Integer.valueOf(currentScore).toString());
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setTitle("aa game");
        stage.show();

    }

    public void returnHome(MouseEvent mouseEvent) throws Exception {
        new PrimaryMenu().start(Main.stage);
        endGameStage.close();
    }

    public static void setEndGameStage(Stage endGameStage) {
        EndGameMenu.endGameStage = endGameStage;
    }

    public static void setCurrentScore(int currentScore) {
        EndGameMenu.currentScore = currentScore;
    }
}
