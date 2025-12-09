package gui;

import application.Main;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import logic.GameState;
import player.Player;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ResultScreen {

    private final Main app;
    private final GameState gameState;
    private final Scene scene;

    private static final double WIDTH = 1080;
    private static final double HEIGHT = 720;

    public ResultScreen(Main app, GameState gameState) {
        this.app = app;
        this.gameState = gameState;
        this.scene = createScene();
    }

    public Scene getScene() {
        return scene;
    }


    private Scene createScene() {

        // ===== BACKGROUND =====
        ImageView bg = new ImageView(
                new Image(getClass().getResource("/result_bg.png").toExternalForm())
        );
        bg.setFitWidth(WIDTH);
        bg.setFitHeight(HEIGHT);
        bg.setPreserveRatio(false);
        bg.setSmooth(true);

        BorderPane ui = new BorderPane();
        ui.setPadding(new Insets(20));

        // ===== TITLE =====
        Label title = new Label("Game Result");
        title.setStyle("-fx-font-size: 36; -fx-font-weight: bold; -fx-text-fill: #ffffff;");

        VBox titleBox = new VBox(title);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(20, 0, 20, 0));

        ui.setTop(titleBox);


        // ===== PLAYER SUMMARY =====
        VBox summary = new VBox(20);
        summary.setAlignment(Pos.TOP_CENTER);

        List<Player> players = GameState.getPlayers();

        // --- SORT BY MONEY DESC ---
        List<Player> ranking = players.stream()
                .sorted(Comparator.comparingInt(p -> -p.getStats().getMoney()))
                .collect(Collectors.toList());

        int rank = 1;

        for (Player p : ranking) {
            summary.getChildren().add(buildPlayerSummaryBox(p, rank));
            rank++;
        }

        ui.setCenter(summary);


        // ===== BUTTONS =====
        Button backBtn = new Button("Back to Main Menu");
        backBtn.setFont(Font.font(18));
        backBtn.setOnAction(e -> app.showStartScreen());

        // Optional: Play again
//        Button restartBtn = new Button("Play Again");
//        restartBtn.setFont(Font.font(18));
//        restartBtn.setOnAction(e -> {
//            GameState newState = new GameState(players.size());
//            app.showGameScreen(newState);
//        });

        VBox bottom = new VBox(backBtn);
        bottom.setAlignment(Pos.CENTER);
        bottom.setPadding(new Insets(20));

        ui.setBottom(bottom);


        // ===== ROOT STACK =====
        StackPane root = new StackPane(bg, ui);

        return new Scene(root, WIDTH, HEIGHT);
    }


    /**
     * Create a summary card for a player
     */
    private HBox buildPlayerSummaryBox(Player p, int rank) {

        HBox box = new HBox(20);
        box.setAlignment(Pos.CENTER_LEFT);
        box.setPadding(new Insets(15));
        box.setPrefWidth(800);

        box.setStyle(
                "-fx-background-color: rgba(0,0,0,0.6); " +
                        "-fx-background-radius: 10;"
        );

        // Player portrait
        String portraitPath = "/icons/" + p.getName() + ".png";

        ImageView portrait = new ImageView(
                new Image(getClass().getResource(portraitPath).toExternalForm())
        );
        portrait.setFitWidth(80);
        portrait.setFitHeight(80);
        portrait.setPreserveRatio(true);

        // Rank label
        Label rankLabel = new Label("#" + rank);
        rankLabel.setStyle("-fx-font-size: 32; -fx-font-weight: bold; -fx-text-fill: gold;");

        // Player name
        Label name = new Label(p.getName());
        name.setStyle("-fx-font-size: 24; -fx-text-fill: white; -fx-font-weight: bold;");

        // Stats
        Label money = new Label("Money: " + p.getStats().getMoney());
        money.setStyle("-fx-font-size: 18; -fx-text-fill: white;");

        Label happy = new Label("Happiness: " + p.getStats().getHappiness());
        happy.setStyle("-fx-font-size: 18; -fx-text-fill: white;");

        Label school = new Label("Education: " + p.getStats().getEducation());
        school.setStyle("-fx-font-size: 18; -fx-text-fill: white;");

        Label time = new Label("Time Used: " + p.getTimeUsed());
        time.setStyle("-fx-font-size: 18; -fx-text-fill: white;");


        VBox info = new VBox(5, name, money, happy, school, time);

        box.getChildren().addAll(rankLabel, portrait, info);

        return box;
    }

}
