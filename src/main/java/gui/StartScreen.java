package gui;

import application.Main;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class StartScreen {

    private final Main app;
    private final Scene scene;

    public StartScreen(Main app) {
        this.app = app;
        this.scene = createScene();
    }

    private Scene createScene() {
        Label title = new Label("ColorRiakSii");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        Label chooseLabel = new Label("Choose number of players:");
        chooseLabel.setStyle("-fx-font-size: 16px;");

        ComboBox<Integer> playerCountBox = new ComboBox<>();
        playerCountBox.getItems().addAll(2, 3, 4);
        playerCountBox.getSelectionModel().selectFirst();

        Button startButton = new Button("Start Game");

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        startButton.setOnAction(e -> {
            Integer count = playerCountBox.getValue();
            if (count == null) {
                errorLabel.setText("Please select number of players");
                return;
            }
            app.startNewGame(count);
        });

        VBox root = new VBox(15, title, chooseLabel, playerCountBox, startButton, errorLabel);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        return new Scene(root, 1080, 720);
    }

    public Scene getScene() {
        return scene;
    }
}
