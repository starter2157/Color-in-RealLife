package gui;

import application.Main;
import entity.base.GameMode;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import logic.GameState;

public class StartScreen {

    private final Main app;
    private final Scene scene;

    public StartScreen(Main app) {
        this.app = app;
        this.scene = createScene();
    }

    private Scene createScene() {

        // Title
        Label title = new Label("ColorRiakSii");
        title.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");

        // Choose number of players
        Label choosePlayerLabel = new Label("Choose number of players:");
        choosePlayerLabel.setStyle("-fx-font-size: 16px;");

        ComboBox<Integer> playerCountBox = new ComboBox<>();
        playerCountBox.getItems().addAll(2, 3, 4);
        playerCountBox.getSelectionModel().selectFirst();

        // Choose game mode
        Label chooseModeLabel = new Label("Choose Game Mode:");
        chooseModeLabel.setStyle("-fx-font-size: 16px;");

        ComboBox<GameMode> modeBox = new ComboBox<>();
        modeBox.getItems().addAll(GameMode.SHORT, GameMode.MEDIUM, GameMode.LONG, GameMode.MARATHON);
        modeBox.getSelectionModel().selectFirst();

        // Error label
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        // Start Button
        Button startButton = new Button("Start Game");
        startButton.setStyle("-fx-font-size: 16px; -fx-padding: 8 16px;");

        startButton.setOnAction(e -> {
            Integer playerCount = playerCountBox.getValue();
            GameMode gameMode = modeBox.getValue();

            if (playerCount == null) {
                errorLabel.setText("Please select number of players.");
                return;
            }
            if (gameMode == null) {
                errorLabel.setText("Please select a game mode.");
                return;
            }

            // Start game with chosen mode
            app.startNewGame(playerCount, gameMode);
        });

        // Layout
        VBox root = new VBox(18,
                title,
                choosePlayerLabel, playerCountBox,
                chooseModeLabel, modeBox,
                startButton,
                errorLabel
        );

        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        return new Scene(root, 1080, 720);
    }

    public Scene getScene() {
        return scene;
    }
}
