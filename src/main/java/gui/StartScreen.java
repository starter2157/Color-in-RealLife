package gui;

import application.Main;
import entity.base.GameMode;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class StartScreen {

    private final Main app;
    private final Scene scene;

    private static final double WIDTH = 1080;
    private static final double HEIGHT = 720;

    public StartScreen(Main app) {
        this.app = app;
        this.scene = createScene();
    }

    public Scene getScene() {
        return scene;
    }

    private Scene createScene() {

        // ---------- BACKGROUND (ภาพที่มีโลโก้ ColorRiakSii อยู่แล้ว) ----------
        Image bgImage = new Image(
                getClass().getResource("/start_bg.png").toExternalForm()
        );
        ImageView bgView = new ImageView(bgImage);
        bgView.setFitWidth(WIDTH);
        bgView.setFitHeight(HEIGHT);
        bgView.setPreserveRatio(false);
        bgView.setSmooth(true);

        // ---------- LABEL + SELECTOR: Number of Players ----------
        Label choosePlayerLabel = new Label("Number of Players");
        choosePlayerLabel.setTextFill(Color.BLACK);
        choosePlayerLabel.setStyle(
                "-fx-font-size: 16px;" +
                        "-fx-font-weight: bold;"
        );

        ComboBox<Integer> playerCountBox = new ComboBox<>();
        playerCountBox.getItems().addAll(2, 3, 4);
        playerCountBox.getSelectionModel().selectFirst();
        playerCountBox.setPrefWidth(220);
        playerCountBox.setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-background-radius: 6;" +
                        "-fx-padding: 4 8;"
        );

        // ---------- LABEL + SELECTOR: Game Mode ----------
        Label chooseModeLabel = new Label("Game Mode");
        chooseModeLabel.setTextFill(Color.BLACK);
        chooseModeLabel.setStyle(
                "-fx-font-size: 16px;" +
                        "-fx-font-weight: bold;"
        );

        ComboBox<GameMode> modeBox = new ComboBox<>();
        modeBox.getItems().addAll(
                GameMode.SHORT,
                GameMode.MEDIUM,
                GameMode.LONG,
                GameMode.MARATHON
        );
        modeBox.getSelectionModel().selectFirst();
        modeBox.setPrefWidth(220);
        modeBox.setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-background-radius: 6;" +
                        "-fx-padding: 4 8;"
        );

        // ---------- ERROR LABEL ----------
        Label errorLabel = new Label();
        errorLabel.setStyle(
                "-fx-text-fill: #ff7777;" +
                        "-fx-font-size: 13px;"
        );

        // ---------- START BUTTON (PNG) ----------
        Image startImg = new Image(
                getClass().getResource("/start_button.png").toExternalForm()
        );
        ImageView startView = new ImageView(startImg);
        startView.setFitWidth(220);    // ปรับขนาดปุ่มให้ไม่ใหญ่เกินไป
        startView.setPreserveRatio(true);
        startView.setSmooth(false);    // ให้ pixel คม ๆ

        Button startButton = new Button();
        startButton.setGraphic(startView);
        startButton.setBackground(Background.EMPTY);
        startButton.setStyle("-fx-padding: 0; -fx-background-color: transparent;");

        // Hover animation
        startButton.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(120), startButton);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });

        startButton.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(120), startButton);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });


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

            errorLabel.setText("");
            app.startNewGame(playerCount, gameMode);
        });

        // ---------- MENU BOX (ไม่มีพื้นหลังดำ) ----------
        VBox menuBox = new VBox(14);
        menuBox.setAlignment(Pos.TOP_CENTER);
        menuBox.setPadding(new Insets(10));
        menuBox.setMaxWidth(320);
        // ไม่มีพื้นหลังทึบ
        menuBox.setStyle("-fx-background-color: transparent;");

        menuBox.getChildren().addAll(
                choosePlayerLabel, playerCountBox,
                chooseModeLabel, modeBox,
                startButton,
                errorLabel
        );

        // ---------- ROOT STACKPANE ----------
        StackPane root = new StackPane();
        root.getChildren().add(bgView);
        root.getChildren().add(menuBox);

        // วางเมนูให้อยู่ใต้โลโก้ (ขยับลงล่างหน่อย)
        StackPane.setAlignment(menuBox, Pos.TOP_CENTER);
        // ลอง 360 ถ้าอยากต่ำกว่านี้ก็เพิ่มเลขได้
        StackPane.setMargin(menuBox, new Insets(360, 0, 0, 0));

        return new Scene(root, WIDTH, HEIGHT);
    }
}
