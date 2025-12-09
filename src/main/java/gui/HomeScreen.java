package gui;

import application.Main;
import entity.base.PlaceName;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import logic.GameState;
import logic.TurnSystem;
import player.Player;
import player.Stats;

import static gui.GameScreen.*;

public class HomeScreen {

    private final Main app;
    private final GameState gameState;
    private final Scene scene;

    private static final double WIDTH = 1080;
    private static final double HEIGHT = 720;

    public HomeScreen(Main app, GameState gameState) {
        this.app = app;
        this.gameState = gameState;
        this.scene = createScene();
    }

    public Scene getScene() {
        return scene;
    }

    private Scene createScene() {
        // ====== พื้นหลังแบบฟิตเต็ม 1080x720 ======
        Image bgImage = new Image(
                getClass().getResource("/home_bg.png").toExternalForm()
        );
        ImageView bg = new ImageView(bgImage);
        bg.setFitWidth(WIDTH);
        bg.setFitHeight(HEIGHT);
        bg.setPreserveRatio(false);   // <<< ทำให้เต็มจอเป๊ะ 1080x720
        bg.setSmooth(true);

        // ====== UI layer โปร่ง วางทับบนพื้นหลัง ======
        BorderPane ui = new BorderPane();
        ui.setPadding(new Insets(20));

        // ซ้าย: ข้อมูลผู้เล่นปัจจุบัน
        Player current = gameState.getCurrentPlayer();

        VBox leftPanel = new VBox(10);
        leftPanel.setPadding(new Insets(20));
        leftPanel.setStyle(
                "-fx-background-color: rgba(0,0,0,0.55);"
                        + "-fx-background-radius: 10;"
        );

        Label nameLabel = new Label(current.getName());
        nameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 20; -fx-font-weight: bold;");

        Stats playerStats = current.getStats();

        Label moneyLabel = new Label("Money: " + playerStats.getMoney() + " / " + TurnSystem.getMaxMoney(gameMode));
        moneyLabel.setStyle("-fx-text-fill: white;");
        Label happyLabel = new Label("Happiness: " + playerStats.getHappiness() + " / " + TurnSystem.getMaxHappiness(gameMode));
        happyLabel.setStyle("-fx-text-fill: white;");
        Label educationalLabel = new Label("Education Level: " + playerStats.getEducation() + " / " + TurnSystem.getMaxEducation(gameMode));
        educationalLabel.setStyle("-fx-text-fill: white;");
        Label timeUsedLabel = new Label("Time: " + current.getRemainingTime() + " / " + current.getMAX_TIME_PER_TURN());
        timeUsedLabel.setStyle("-fx-text-fill: white;");

        leftPanel.getChildren().addAll(nameLabel, moneyLabel, happyLabel, educationalLabel, timeUsedLabel);
        ui.setLeft(leftPanel);

        // กลางล่างขวา: ปุ่ม Rest + สเตตัสข้อความ
        VBox restBox = new VBox(8);
        restBox.setAlignment(Pos.CENTER_RIGHT);
        restBox.setPadding(new Insets(0, 40, 40, 0));

        Button btnRest = new Button("Rest");
        btnRest.setFont(Font.font(18));

        Label statusLabel = new Label("");
        statusLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16;");

        btnRest.setOnAction(e -> {
            Player p = gameState.getCurrentPlayer();

            // TODO: เติม logic จริง เช่น เพิ่ม happiness / ลดเงิน / ใช้เทิร์น ฯลฯ
            if(p.isEndTurn()){
                statusLabel.setText(p.getName() + " NoTime!!!");
            } else {

                p.rest();

                timeUsedLabel.setText("Time: " + current.getRemainingTime() + " / " + current.getMAX_TIME_PER_TURN());
                happyLabel.setText("Happiness: " + current.getStats().getHappiness() + " / " + TurnSystem.getMaxHappiness(gameMode));
                statusLabel.setText(p.getName() + " Rested!");
            }
        });

        restBox.getChildren().addAll(btnRest, statusLabel);
        ui.setRight(restBox);

        // ล่าง: ปุ่มกลับไปกระดาน
        Button btnBackToCity = new Button("Back to City");
        btnBackToCity.setOnAction(e -> {
            app.showGameScreen(gameState);
            Player player = gameState.getCurrentPlayer();
            if(player.isEndTurn() && gameState.isLastPlayerTurn()){
                delayScreenChange(() -> getApp().showResultScreen(gameState));
            }
            if(player.isEndTurn()){
                player.setCurrentLocation(PlaceName.HOME);
                player.endTurn();
                gameState.nextPlayerTurn();
                refreshUI();
            }
        });

        VBox bottomBox = new VBox(btnBackToCity);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(10));
        ui.setBottom(bottomBox);

        // ====== ซ้อนพื้นหลังกับ UI ======
        StackPane root = new StackPane();
        root.getChildren().addAll(bg, ui);

        return new Scene(root, WIDTH, HEIGHT);
    }
}
