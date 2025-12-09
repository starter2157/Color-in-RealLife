package gui;

import application.Main;
import entity.base.PlaceName;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import logic.GameState;
import logic.TurnSystem;
import player.Player;
import player.Stats;

import java.util.HashMap;
import java.util.Map;

import static gui.GameScreen.*;

public class SchoolScreen {

    private final Main app;
    private final GameState gameState;
    private final Scene scene;

    private final int LEVEL_COUNT = 4;
    private final int[] REQUIRED_STUDY_EACH_LEVEL = {5, 7, 10, 12};
    private final int[] COST_PER_LEVEL = {100, 200, 300, 400};

    private static final double WIDTH = 1080;
    private static final double HEIGHT = 720;

    private final Label[] progressLabels = new Label[LEVEL_COUNT];
    private final Button[] studyButtons = new Button[LEVEL_COUNT];

    public SchoolScreen(Main app, GameState gameState) {
        this.app = app;
        this.gameState = gameState;
        this.scene = createScene();
    }

    public Scene getScene() {
        return scene;
    }

    private Scene createScene() {

        Player current = gameState.getCurrentPlayer();

        // ---------- Background ----------
        ImageView bg = new ImageView(new Image(
                getClass().getResource("/school_bg.png").toExternalForm()
        ));
        bg.setFitWidth(WIDTH);
        bg.setFitHeight(HEIGHT);
        bg.setPreserveRatio(false);

        // ---------- UI Layer ----------
        BorderPane ui = new BorderPane();
        ui.setPadding(new Insets(20));

        // ---------- LEFT: STATUS PANEL ----------
        VBox statusBox = createStatusBox(current);
        ui.setLeft(statusBox);

        // ---------- CENTER: LEVEL CARDS ----------
        HBox levelRow = new HBox(40);
        levelRow.setPadding(new Insets(20));
        levelRow.setAlignment(Pos.CENTER);

        for (int i = 0; i < LEVEL_COUNT; i++) {
            levelRow.getChildren().add(createLevelCard(i, current, statusBox.getChildren().get(1), statusBox.getChildren().get(4), statusBox.getChildren().get(3)));
        }

        ui.setCenter(levelRow);

        // ---------- BOTTOM: Back button ----------
        Button backBtn = new Button("Back to City");
        backBtn.setFont(Font.font(16));

        backBtn.setOnAction(e -> {
            app.showGameScreen(gameState);
            Player player = gameState.getCurrentPlayer();
            if(player.isEndTurn()){
                GameScreen.resetCurrentPlayerToHome(player.getCurrentLocation());
                refreshUI();
            }
        });

        VBox bottomBox = new VBox(backBtn);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(10));
        ui.setBottom(bottomBox);

        // ---------- Root Stack ----------
        StackPane root = new StackPane(bg, ui);

        refreshLevelUI(current);

        return new Scene(root, WIDTH, HEIGHT);
    }

    // -----------------------------------------------------
    //  STATUS BOX (เหมือน HomeScreen เป๊ะ)
    // -----------------------------------------------------
    private VBox createStatusBox(Player current) {
        Stats stats = current.getStats();

        VBox box = new VBox(10);
        box.setPadding(new Insets(20));
        box.setStyle(
                "-fx-background-color: rgba(0,0,0,0.55);" +
                        "-fx-background-radius: 10;"
        );
        box.setPrefWidth(220);

        Label nameLabel = new Label(current.getName());
        nameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 20; -fx-font-weight: bold;");

        Label moneyLabel = new Label("Money: " + stats.getMoney() + " / " + TurnSystem.getMaxMoney(gameMode));
        moneyLabel.setStyle("-fx-text-fill: white;");

        Label happyLabel = new Label("Happiness: " + stats.getHappiness() + " / " + TurnSystem.getMaxHappiness(gameMode));
        happyLabel.setStyle("-fx-text-fill: white;");

        Label eduLabel = new Label("Education Level: " + stats.getEducation() + " / " + TurnSystem.getMaxEducation(gameMode));
        eduLabel.setStyle("-fx-text-fill: white;");

        Label timeLabel = new Label("Time: " + current.getRemainingTime() + " / " + current.getMAX_TIME_PER_TURN());
        timeLabel.setStyle("-fx-text-fill: white;");

        box.getChildren().addAll(nameLabel, moneyLabel, happyLabel, eduLabel, timeLabel);

        return box;
    }

    // -----------------------------------------------------
    //  CREATE LEVEL CARD
    // -----------------------------------------------------
    private VBox createLevelCard(int levelIndex, Player currentPlayer, Node moneyLabel, Node timeLabel, Node educationLabel) {
        int levelNum = levelIndex + 1;

        VBox box = new VBox(8);
        box.setAlignment(Pos.TOP_CENTER);
        box.setPadding(new Insets(10));
        box.setPrefWidth(220);
        box.setStyle(
                "-fx-background-color: rgba(255,255,255,0.95);" +
                        "-fx-background-radius: 10;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 5, 0, 0, 2);"
        );

        String imgPath = "/schoolItem/step" + levelNum + ".png";
        ImageView icon;
        try {
            icon = new ImageView(new Image(getClass().getResource(imgPath).toExternalForm()));
        } catch (Exception e) {
            icon = new ImageView();
        }
        icon.setFitWidth(180);
        icon.setFitHeight(140);
        icon.setPreserveRatio(true);

        Label title = new Label("Level " + levelNum);
        title.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

        Label desc = new Label(
                "เรียนขั้นต่ำ " + REQUIRED_STUDY_EACH_LEVEL[levelIndex] + " ครั้ง\n" +
                        "ค่าลงทะเบียนครั้งเดียว " + COST_PER_LEVEL[levelIndex] + " ฿"
        );
        desc.setWrapText(true);

        Label progress = new Label("0 / " + REQUIRED_STUDY_EACH_LEVEL[levelIndex]);
        progressLabels[levelIndex] = progress;

        Button btnStudy = new Button("เรียน");
        btnStudy.setFont(Font.font(14));
        studyButtons[levelIndex] = btnStudy;

        btnStudy.setOnAction(e -> {
            handleStudy(levelIndex, currentPlayer);
            ((Label) educationLabel).setText("Education Level: " + currentPlayer.getStats().getEducation() + " / " + TurnSystem.getMaxEducation(gameMode));
            ((Label) timeLabel).setText("Time: " + currentPlayer.getRemainingTime() + " / " + currentPlayer.getMAX_TIME_PER_TURN());
            ((Label) moneyLabel).setText("Money: " + currentPlayer.getStats().getMoney() + " / " + TurnSystem.getMaxMoney(gameMode));

        });

        box.getChildren().addAll(icon, title, desc, progress, btnStudy);

        return box;
    }

    // -----------------------------------------------------
    //  HANDLE STUDY BUTTON
    // -----------------------------------------------------
    private void handleStudy(int levelIndex, Player player) {
        int[] progress = player.getLearningProgress();
        boolean[] paid = player.getIsPaid();

        if (!isLevelUnlocked(player, levelIndex) || player.isEndTurn()) return;

        // pay registration fee
        if (!paid[levelIndex]) {
            if (!payForLevel(player, levelIndex)) {
                return;
            }
            paid[levelIndex] = true;
        }

        // update progress
        progress[levelIndex]++;

        // call player action
        player.study();

        // refresh UI
        refreshLevelUI(player);
        refreshUI(); // update game board display
    }

    // -----------------------------------------------------
    //  PAY REGISTRATION FEE
    // -----------------------------------------------------
    private boolean payForLevel(Player player, int levelIndex) {
        int cost = COST_PER_LEVEL[levelIndex];
        Stats stats = player.getStats();

        if (stats.getMoney() < cost) {
            showPopup("เงินไม่พอ", "ต้องใช้เงิน " + cost + " ฿");
            return false;
        }

        player.reduceMoney(cost);
        showPopup("สมัครเรียนสำเร็จ", "คุณจ่าย " + cost + " ฿");

        return true;
    }

    private void showPopup(String header, String content) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(header);
        a.setContentText(content);
        a.showAndWait();
    }

    // -----------------------------------------------------
    //  CHECK LEVEL UNLOCK
    // -----------------------------------------------------
    private boolean isLevelUnlocked(Player player, int levelIndex) {
        if (levelIndex == 0) return true;

        int[] arr = player.getLearningProgress();
        return arr[levelIndex - 1] >= REQUIRED_STUDY_EACH_LEVEL[levelIndex - 1];
    }

    // -----------------------------------------------------
    //  REFRESH LEVEL UI
    // -----------------------------------------------------
    private void refreshLevelUI(Player player) {
        int[] arr = player.getLearningProgress();

        for (int i = 0; i < LEVEL_COUNT; i++) {
            int count = arr[i];

            progressLabels[i].setText(count + " / " + REQUIRED_STUDY_EACH_LEVEL[i]);

            boolean unlocked = (i == 0) || (arr[i - 1] >= REQUIRED_STUDY_EACH_LEVEL[i - 1]);

            if (count >= REQUIRED_STUDY_EACH_LEVEL[i]) {
                studyButtons[i].setDisable(true);
                progressLabels[i].setStyle("-fx-text-fill: #2e7d32; -fx-font-weight: bold;");
            } else {
                studyButtons[i].setDisable(!unlocked);
                progressLabels[i].setStyle("-fx-text-fill: #333333;");
            }
        }
    }
}
