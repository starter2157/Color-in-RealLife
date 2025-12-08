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
import logic.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * หน้าจอโรงเรียน (School) ให้ผู้เล่นมา "เรียน" เป็นขั้น ๆ
 * - มีทั้งหมด 4 ขั้น (level 1..4)
 * - แต่ละขั้นต้องเรียนอย่างน้อย 5 ครั้ง ถึงจะถือว่าเคลียร์
 * - ขั้นถัดไปจะปลดล็อกก็ต่อเมื่อขั้นก่อนหน้าถูกเรียนครบ 5 ครั้งแล้ว
 *
 * NOTE:
 *  - ความก้าวหน้าจะถูกเก็บแบบ static ตามชื่อ player
 */
public class SchoolScreen {

    private final Main app;
    private final GameState gameState;
    private final Scene scene;

    private static final int LEVEL_COUNT = 4;
    private static final int REQUIRED_STUDY_PER_LEVEL = 5;
    private static final double WIDTH = 1080;
    private static final double HEIGHT = 720;

    // เก็บจำนวนครั้งที่เรียนต่อ player ต่อ level: playerName -> [4 ช่อง]
    private static final Map<String, int[]> studyProgressByPlayer = new HashMap<>();

    // UI refs
    private Label[] progressLabels = new Label[LEVEL_COUNT];
    private Button[] studyButtons = new Button[LEVEL_COUNT];

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
        String playerKey = current.getName();

        // ถ้ายังไม่เคยมี progress ของ player นี้ ให้สร้างใหม่
        studyProgressByPlayer.putIfAbsent(playerKey, new int[LEVEL_COUNT]);

        // ====== BG ======
        ImageView bg = new ImageView(
                new Image(getClass().getResource("/school_bg.png").toExternalForm())
        );
        bg.setFitWidth(WIDTH);
        bg.setFitHeight(HEIGHT);
        bg.setPreserveRatio(false);
        bg.setSmooth(true);

        BorderPane ui = new BorderPane();
        ui.setPadding(new Insets(20));

        // ====== TOP ======
        Label title = new Label("School");
        title.setStyle("-fx-text-fill: #333333; -fx-font-size: 28; -fx-font-weight: bold;");

        Label playerLabel = new Label("Student: " + current.getName());
        playerLabel.setStyle("-fx-text-fill: #555555; -fx-font-size: 16;");

        VBox topBox = new VBox(5, title, playerLabel);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(10, 0, 20, 0));
        ui.setTop(topBox);

        // ====== CENTER: 4 Level ======
        HBox levelRow = new HBox(40);
        levelRow.setAlignment(Pos.CENTER);
        levelRow.setPadding(new Insets(20));

        for (int i = 0; i < LEVEL_COUNT; i++) {
            levelRow.getChildren().add(createLevelCard(i, current));
        }

        ui.setCenter(levelRow);

        // ====== BOTTOM: Back ======
        Button backBtn = new Button("Back to City");
        backBtn.setFont(Font.font(16));
        backBtn.setOnAction(e -> app.showGameScreen(gameState));

        VBox bottomBox = new VBox(backBtn);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(10));
        ui.setBottom(bottomBox);

        StackPane root = new StackPane(bg, ui);

        // update UI ให้ตรงกับ progress
        refreshLevelUI(current);

        return new Scene(root, WIDTH, HEIGHT);
    }

    // สร้างการ์ด 1 level
    private VBox createLevelCard(int levelIndex, Player currentPlayer) {
        int levelNumber = levelIndex + 1;

        VBox box = new VBox(8);
        box.setAlignment(Pos.TOP_CENTER);
        box.setPadding(new Insets(10));
        box.setPrefWidth(220);

        box.setStyle(
                "-fx-background-color: rgba(255,255,255,0.95);"
                        + "-fx-background-radius: 10;"
                        + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25),5,0,0,2);"
        );

        String imagePath = "/schoolItem/step" + levelNumber + ".png";

        ImageView icon;
        try {
            icon = new ImageView(
                    new Image(getClass().getResource(imagePath).toExternalForm())
            );
        } catch (Exception e) {
            icon = new ImageView();
            System.out.println("WARN: school image not found: " + imagePath);
        }
        icon.setFitWidth(180);
        icon.setFitHeight(140);
        icon.setPreserveRatio(true);

        Label levelTitle = new Label("Level " + levelNumber);
        levelTitle.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

        Label desc = new Label("เรียนขั้นนี้อย่างน้อย " + REQUIRED_STUDY_PER_LEVEL + " ครั้ง");
        desc.setStyle("-fx-font-size: 12; -fx-text-fill: #555555;");
        desc.setWrapText(true);

        Label progress = new Label("0 / " + REQUIRED_STUDY_PER_LEVEL);
        progress.setStyle("-fx-font-size: 14; -fx-text-fill: #333333;");
        progressLabels[levelIndex] = progress;

        Button studyBtn = new Button("เรียน");
        studyBtn.setFont(Font.font(14));
        studyButtons[levelIndex] = studyBtn;

        studyBtn.setOnAction(e -> {
            String key = currentPlayer.getName();
            int[] arr = studyProgressByPlayer.computeIfAbsent(key, k -> new int[LEVEL_COUNT]);

            // ถ้า level นี้ยังไม่ unlock ก็ไม่ให้เรียน
            if (!isLevelUnlocked(key, levelIndex)) {
                return;
            }

            // เพิ่มจำนวนเรียน 1 ครั้ง (สามารถเกิน 5 ได้)
            arr[levelIndex]++;

            // TODO: ใส่ logic เพิ่ม stat / หักเวลา ฯลฯ ได้ตรงนี้

            // อัปเดตทุกปุ่มและ progress อีกครั้ง
            refreshLevelUI(currentPlayer);
        });

        box.getChildren().addAll(icon, levelTitle, desc, progress, studyBtn);
        return box;
    }

    // level 0 ปลดล็อกเสมอ, level i>0 ปลดล็อกเมื่อ level i-1 เรียนครบ 5 ครั้ง
    private boolean isLevelUnlocked(String playerKey, int levelIndex) {
        int[] arr = studyProgressByPlayer.computeIfAbsent(playerKey, k -> new int[LEVEL_COUNT]);
        if (levelIndex == 0) return true;
        return arr[levelIndex - 1] >= REQUIRED_STUDY_PER_LEVEL;
    }

    // อัปเดตตัวเลข progress และ enable/disable ปุ่มตามเงื่อนไข
    private void refreshLevelUI(Player currentPlayer) {
        String key = currentPlayer.getName();
        int[] arr = studyProgressByPlayer.computeIfAbsent(key, k -> new int[LEVEL_COUNT]);

        for (int i = 0; i < LEVEL_COUNT; i++) {
            int count = arr[i];

            // update progress label
            if (progressLabels[i] != null) {
                progressLabels[i].setText(count + " / " + REQUIRED_STUDY_PER_LEVEL);

                if (count >= REQUIRED_STUDY_PER_LEVEL) {
                    progressLabels[i].setStyle(
                            "-fx-font-size: 14;"
                                    + "-fx-text-fill: #2e7d32;"
                                    + "-fx-font-weight: bold;"
                    );
                } else {
                    progressLabels[i].setStyle(
                            "-fx-font-size: 14;"
                                    + "-fx-text-fill: #333333;"
                    );
                }
            }

            // update button enable/disable
            if (studyButtons[i] != null) {

                boolean unlocked;

                // Level 1 always unlocked
                if (i == 0) {
                    unlocked = true;
                } else {
                    unlocked = arr[i - 1] >= REQUIRED_STUDY_PER_LEVEL;
                }

                // 🔥 ถ้าขั้นนี้เรียนครบแล้ว → disable ปุ่มทันที
                if (arr[i] >= REQUIRED_STUDY_PER_LEVEL) {
                    studyButtons[i].setDisable(true);
                }
                else {
                    studyButtons[i].setDisable(!unlocked);
                }
            }
        }
    }

}
