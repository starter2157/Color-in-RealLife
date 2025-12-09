package gui;

import application.Main;
import entity.items.*;
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
import logic.TurnSystem;
import player.Player;
import player.Stats;

import static gui.GameScreen.gameMode;
import static gui.GameScreen.refreshUI;

public class StoreScreen {

    private final Main app;
    private final GameState gameState;
    private final Scene scene;

    private static final double WIDTH = 1080;
    private static final double HEIGHT = 720;

    public StoreScreen(Main app, GameState gameState) {
        this.app = app;
        this.gameState = gameState;
        this.scene = createScene();
    }

    public Scene getScene() {
        return scene;
    }

    private Scene createScene() {
        // ====== BG ร้านค้าแบบเต็ม 1080x720 ======
        // TODO: เปลี่ยน path รูปให้ตรงกับของจริง เช่น "/store_bg.png"
        ImageView bg = new ImageView(
                new Image(getClass().getResource("/store_bg.png").toExternalForm())
        );
        bg.setFitWidth(WIDTH);
        bg.setFitHeight(HEIGHT);
        bg.setPreserveRatio(false);
        bg.setSmooth(true);

        // ====== UI โปร่งทับบน BG ======
        BorderPane ui = new BorderPane();
        ui.setPadding(new Insets(20));

        // บน: title
        Label title = new Label("Store");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 28; -fx-font-weight: bold;");
        HBox topBox = new HBox(title);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(10, 0, 20, 0));
        ui.setTop(topBox);

        // ซ้าย: ข้อมูลผู้เล่น
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

        // กลาง: สินค้า (อาหาร + lottery + หนังสือพิมพ์)
        GridPane grid = new GridPane();
        grid.setHgap(40);
        grid.setVgap(30);
        grid.setPadding(new Insets(20));
        grid.setAlignment(Pos.CENTER);

        // ใช้ label เดียวไว้โชว์ข้อความหลังซื้อ
        Label statusLabel = new Label("");
        statusLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16;");

        // helper สร้างการ์ดสินค้า
        // imagePath: path รูป, name: ชื่อสินค้า, price: ราคา, row/col: ตำแหน่งใน grid
        addItemCard(grid, 0, 0,
                "/storeItem/fries.png", new Fries(),
                current, moneyLabel, timeUsedLabel, happyLabel, statusLabel);

        addItemCard(grid, 1, 0,
                "/storeItem/burger.png", new Burger(),
                current, moneyLabel, timeUsedLabel, happyLabel, statusLabel);

        addItemCard(grid, 0, 1,
                "/storeItem/set.png", new BigMeal(),
                current, moneyLabel, timeUsedLabel, happyLabel, statusLabel);

        addItemCard(grid, 1, 1,
                "/storeItem/lottery.png", new Lottery(),
                current, moneyLabel, timeUsedLabel,happyLabel, statusLabel);

        addItemCard(grid, 2, 0,
                "/storeItem/scooter.png", new Lottery(),
                current, moneyLabel, timeUsedLabel,happyLabel, statusLabel);

        addItemCard(grid, 2, 1,
                "/storeItem/car.png", new Lottery(),
                current, moneyLabel, timeUsedLabel,happyLabel, statusLabel);

        VBox centerBox = new VBox(10, grid, statusLabel);
        centerBox.setAlignment(Pos.TOP_CENTER);
        ui.setCenter(centerBox);

        // ล่าง: ปุ่มกลับเมือง
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

        // ซ้อน BG + UI
        StackPane root = new StackPane(bg, ui);
        return new Scene(root, WIDTH, HEIGHT);
    }

    private void addItemCard(GridPane grid,
                             int col, int row,
                             String imagePath,
                             Item item,
                             Player player,
                             Label moneyLabel,
                             Label timeUsedLabel,
                             Label happyLabel,
                             Label statusLabel) {

        VBox card = new VBox(8);
        card.setPadding(new Insets(10));
        card.setAlignment(Pos.TOP_CENTER);
        card.setPrefWidth(220);
        card.setStyle(
                "-fx-background-color: rgba(255,255,255,0.9);"
                        + "-fx-background-radius: 10;"
                        + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 5,0,0,2);"
        );

        // รูปสินค้า
        ImageView imgView;
        try {
            imgView = new ImageView(
                    new Image(getClass().getResource(imagePath).toExternalForm())
            );
        } catch (Exception e) {
            // ถ้าหาไฟล์ไม่เจอ ใช้สี่เหลี่ยมเปล่าแทน
            imgView = new ImageView();
            System.out.println("WARN: image not found: " + imagePath);
        }
        imgView.setFitWidth(150);
        imgView.setFitHeight(150);
        imgView.setPreserveRatio(true);

        Label nameLabel = new Label(item.getName());
        nameLabel.setWrapText(true);
        nameLabel.setStyle("-fx-font-size: 14;");

        Label priceLabel = new Label("$" + item.getPrice());
        priceLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: #2e7d32;");

        Button buyBtn = new Button("ซื้อ");
        buyBtn.setFont(Font.font(14));

        buyBtn.setOnAction(e -> {
            int money = player.getStats().getMoney();
            if (player.isEndTurn()) {
                statusLabel.setText("Not Enough Time!!!");
                return;
            }
            if (money >= item.getPrice()) {
                // Buy Item
                player.buyItem(item);

                int newMoney = money - item.getPrice();
                moneyLabel.setText("Money: " + newMoney + " / " + TurnSystem.getMaxMoney(gameMode));
                timeUsedLabel.setText("Time: " + player.getRemainingTime() + " / " + player.getMAX_TIME_PER_TURN());
                happyLabel.setText("Happiness: " + player.getStats().getHappiness() + " / " + TurnSystem.getMaxHappiness(gameMode));
                statusLabel.setText("Buy " + item.getName() + " price $" + item.getPrice() + " สำเร็จ");

            } else {
                statusLabel.setText("เงินไม่พอสำหรับ " + item.getName() + " (ต้องการ $" + item.getPrice() + ")");
            }
        });

        card.getChildren().addAll(imgView, nameLabel, priceLabel, buyBtn);
        grid.add(card, col, row);
    }
}
