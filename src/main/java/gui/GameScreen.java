package gui;

import application.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import logic.Player;

public class GameScreen {

    private final Main app;
    private final GameState gameState;
    private final Scene scene;

    private Label turnLabel;
    private VBox playerListBox;

    public GameScreen(Main app, GameState gameState) {
        this.app = app;
        this.gameState = gameState;
        this.scene = createScene();
        refreshUI();
    }

    private Scene createScene() {
        BorderPane root = new BorderPane();

        // TOP: turn info
        turnLabel = new Label();
        turnLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        HBox topBar = new HBox(turnLabel);
        topBar.setAlignment(Pos.CENTER);
        topBar.setPadding(new Insets(10));

        // LEFT: player list
        playerListBox = new VBox(10);
        playerListBox.setPadding(new Insets(10));
        playerListBox.setStyle("-fx-background-color: #222;");
        playerListBox.setPrefWidth(220);

        // CENTER: board placeholder
        Label boardPlaceholder = new Label("BOARD GOES HERE");
        boardPlaceholder.setStyle(
                "-fx-border-color: gray; " +
                        "-fx-border-width: 2px; " +
                        "-fx-font-size: 20px;"
        );
        boardPlaceholder.setMinSize(300, 300);
        BorderPane.setAlignment(boardPlaceholder, Pos.CENTER);

        // BOTTOM: buttons
        Button btnEndTurn = new Button("End Turn");
        Button btnBackToStart = new Button("Back to Start");

        btnEndTurn.setOnAction(e -> {
            gameState.nextTurn();
            refreshUI();
        });

        btnBackToStart.setOnAction(e -> app.showStartScreen());

        HBox bottomBar = new HBox(10, btnEndTurn, btnBackToStart);
        bottomBar.setAlignment(Pos.CENTER);
        bottomBar.setPadding(new Insets(10));

        root.setTop(topBar);
        root.setLeft(playerListBox);
        root.setCenter(boardPlaceholder);
        root.setBottom(bottomBar);

        return new Scene(root, 800, 600);
    }

    private void refreshUI() {
        var current = gameState.getCurrentPlayer();
        turnLabel.setText(
                "Round " + gameState.getRound() +
                        " | Turn: " + current.getName() +
                        " (Players: " + gameState.getPlayers().size() + ")"
        );

        playerListBox.getChildren().clear();
        for (Player p : gameState.getPlayers()) {
            VBox card = new VBox(3);
            Label name = new Label(p.getName());
            name.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");
            Label money = new Label("Money: " + p.getStats().getMoney());
            money.setStyle("-fx-text-fill: white;");
            Label happy = new Label("Happiness: " + p.getStats().getHappiness());
            happy.setStyle("-fx-text-fill: white;");
            card.getChildren().addAll(name, money, happy);
            card.setPadding(new Insets(5));
            card.setStyle("-fx-border-color: #555; -fx-border-width: 1px;");
            playerListBox.getChildren().add(card);
        }
    }

    public Scene getScene() {
        return scene;
    }
}
