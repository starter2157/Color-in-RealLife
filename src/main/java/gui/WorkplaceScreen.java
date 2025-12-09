package gui;

import application.Main;
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
import player.Player;

import static gui.GameScreen.refreshUI;

public class WorkplaceScreen {

    private final Main app;
    private final GameState gameState;
    private final Scene scene;

    private static final double WIDTH = 1080;
    private static final double HEIGHT = 720;

    public WorkplaceScreen(Main app, GameState gameState) {
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
                getClass().getResource("/work_bg.png").toExternalForm()
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

        Label moneyLabel = new Label("เงิน: " + current.getStats().getMoney());
        moneyLabel.setStyle("-fx-text-fill: white;");

        Label happyLabel = new Label("ความสุข: " + current.getStats().getHappiness());
        happyLabel.setStyle("-fx-text-fill: white;");

        leftPanel.getChildren().addAll(nameLabel, moneyLabel, happyLabel);
        ui.setLeft(leftPanel);

        // กลางล่างขวา: ปุ่ม Rest + สเตตัสข้อความ
        VBox restBox = new VBox(8);
        restBox.setAlignment(Pos.CENTER_RIGHT);
        restBox.setPadding(new Insets(0, 40, 40, 0));

        Button btnWork = new Button("Work");
        btnWork.setFont(Font.font(18));

        Label statusLabel = new Label("");
        statusLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16;");

        btnWork.setOnAction(e -> {
            Player p = gameState.getCurrentPlayer();

            // TODO: เติม logic จริง เช่น เพิ่ม happiness / ลดเงิน / ใช้เทิร์น ฯลฯ
            if(p.isEndTurn()){
                statusLabel.setText(p.getName() + " เวลาไม่พอออ!!!");
            } else {

                p.work();

                statusLabel.setText(p.getName() + " ทำงานเรียบร้อยแล้ว!");
            }
        });

        restBox.getChildren().addAll(btnWork, statusLabel);
        ui.setRight(restBox);

        // ล่าง: ปุ่มกลับไปกระดาน
        Button btnBackToCity = new Button("Back to City");
        btnBackToCity.setOnAction(e -> {
            app.showGameScreen(gameState);
            Player player = gameState.getCurrentPlayer();
            if(player.isEndTurn()){
                GameScreen.resetCurrentPlayerToHome(player.getCurrentLocation());
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
