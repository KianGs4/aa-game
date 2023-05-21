module AA {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires javafx.media;

    exports view;
    opens view to javafx.fxml;
    exports model;
    opens model to com.google.gson;
    exports view.user;
    opens view.user to javafx.fxml;
    exports view.game;
    opens view.game to javafx.fxml;
}