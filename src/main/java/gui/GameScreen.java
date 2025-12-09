package gui;

import application.Main;
import entity.base.GameMode;
import entity.base.PlaceName;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import logic.GameState;
import logic.TurnSystem;
import player.Player;

import javax.print.attribute.standard.Destination;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameScreen {

    private static Main app = null;
    private static GameState gameState = null;
    public static GameMode gameMode;
    private Scene scene;

    private ImageView mapView;
    private static Pane root;  // ใช้ disable/enable ตอน player เดิน

    // map locations and tokens
    private static Map<PlaceName, Point2D> locationPoints = new HashMap<>();
    private static List<ImageView> playerTokens = new ArrayList<>();

    // 4 corner HUD panels
    private static VBox p1Panel;
    private static VBox p2Panel;
    private static VBox p3Panel;
    private static VBox p4Panel;

    private static final String PANEL_BASE_STYLE =
            "-fx-background-color: rgba(0,0,0,0.55);"
                    + "-fx-padding: 10;"
                    + "-fx-background-radius: 10;";

    public GameScreen(Main app, GameState gameState, GameMode gameMode) {
        this.app = app;
        GameScreen.gameState = gameState;
        this.scene = createScene();
        this.gameMode = gameMode;
        refreshUI();
    }

    public Scene getScene() {
        return scene;
    }

    private Scene createScene() {

        // ===== ROOT PANE (fixed 1080x720) =====
        root = new Pane();
        root.setPrefSize(1080, 720);

        // ===== MAP IMAGE =====
        Image mapImage = new Image(
                getClass().getResource("/city_map.png").toExternalForm()
        );
        mapView = new ImageView(mapImage);
        mapView.setFitWidth(1080);
        mapView.setFitHeight(720);
        mapView.setPreserveRatio(false); // เน้นให้เต็มฉาก

        // add map first (ล่างสุด)
        root.getChildren().add(mapView);

        // ===== LOCATION COORDINATES =====
        locationPoints.put(PlaceName.HOME,       new Point2D(240, 300));
        locationPoints.put(PlaceName.STORE,      new Point2D(260, 560));
        locationPoints.put(PlaceName.THEATRE,    new Point2D(820, 520));
        locationPoints.put(PlaceName.SCHOOL,     new Point2D(640, 140));
        locationPoints.put(PlaceName.WORKPLACE,  new Point2D(980, 280));

        // เคลียร์ token เก่าออกก่อน (กันซ้ำตอนกลับจากหน้าจออื่น)
        playerTokens.clear();

        // ===== PLAYER TOKENS =====
        List<Player> players = GameState.getPlayers();
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);

            String portraitPath = "/icons/Player " + (i + 1) + ".png";
            Image img = new Image(
                    getClass().getResource(portraitPath).toExternalForm()
            );
            ImageView token = new ImageView(img);
            token.setFitWidth(48);
            token.setFitHeight(48);
            token.setPreserveRatio(true);

            Point2D pt = locationPoints.get(p.getCurrentLocation());
            if (pt == null) pt = locationPoints.get(PlaceName.HOME);
            token.setLayoutX(pt.getX());
            token.setLayoutY(pt.getY());

            playerTokens.add(token);
            root.getChildren().add(token);  // อยู่เหนือ map
        }

        // ===== LOCATION BUTTONS =====
        String locationBtnStyle =
                "-fx-background-color: rgba(0,0,0,0.4);"
                        + "-fx-text-fill: white;"
                        + "-fx-background-radius: 8;"
                        + "-fx-font-weight: bold;"
                        + "-fx-padding: 4 8;";

        Button btnHome  = new Button("Home");
        Button btnStore = new Button("Store");
        Button btnThea  = new Button("Theatre");
        Button btnSch   = new Button("School");
        Button btnWork  = new Button("Workplace");

        btnHome.setStyle(locationBtnStyle);
        btnStore.setStyle(locationBtnStyle);
        btnThea.setStyle(locationBtnStyle);
        btnSch.setStyle(locationBtnStyle);
        btnWork.setStyle(locationBtnStyle);

        placeButtonAt(btnHome,  PlaceName.HOME,      -20, -40);
        placeButtonAt(btnStore, PlaceName.STORE,     -20, -40);
        placeButtonAt(btnThea,  PlaceName.THEATRE,   -20, -40);
        placeButtonAt(btnSch,   PlaceName.SCHOOL,    -20, -40);
        placeButtonAt(btnWork,  PlaceName.WORKPLACE, -20, -40);

        root.getChildren().addAll(btnHome, btnStore, btnThea, btnSch, btnWork);

        // ===== ปุ่มกดแต่ละที่ =====

        // ไป Home → เดินก่อน พอถึงแล้วค่อยเข้า HomeScreen
        btnHome.setOnAction(e -> {
            System.out.println("Button Home clicked");
            SoundManager.playClick();
            moveCurrentPlayerTo(PlaceName.HOME, () -> app.showHomeScreen(gameState));
        });

        // ไป Store → เดินก่อน พอถึงแล้วค่อยเข้า StoreScreen
        btnStore.setOnAction(e -> {
            System.out.println("Button Store clicked");
            SoundManager.playClick();
            moveCurrentPlayerTo(PlaceName.STORE, () -> app.showStoreScreen(gameState));
        });

        btnThea.setOnAction(e -> {
            System.out.println("Button Theatre clicked");
            SoundManager.playClick();
            moveCurrentPlayerTo(PlaceName.THEATRE, () -> app.showTheatreScreen(gameState));
        });

        btnSch.setOnAction(e -> {
            System.out.println("Button School clicked");
            SoundManager.playClick();
            moveCurrentPlayerTo(PlaceName.SCHOOL, () -> app.showSchoolScreen(gameState));
        });

        btnWork.setOnAction(e -> {
            System.out.println("Button Workplace clicked");
            SoundManager.playClick();
            moveCurrentPlayerTo(PlaceName.WORKPLACE, () -> app.showWorkplaceScreen(gameState));
        });

        // ===== HUD PANELS =====
        p1Panel = makePlayerPanel();
        p2Panel = makePlayerPanel();
        p3Panel = makePlayerPanel();
        p4Panel = makePlayerPanel();

        p1Panel.setLayoutX(20);
        p1Panel.setLayoutY(20);

        p2Panel.setLayoutX(1080 - 220);
        p2Panel.setLayoutY(20);

        p3Panel.setLayoutX(20);
        p3Panel.setLayoutY(720 - 220);

        p4Panel.setLayoutX(1080 - 220);
        p4Panel.setLayoutY(720 - 220);

        root.getChildren().addAll(p1Panel, p2Panel, p3Panel, p4Panel);

        // ===== ปุ่ม End Turn / Back =====
        Button btnEndTurn = new Button("End Turn");
        Button btnBack = new Button("Back");

        btnEndTurn.setOnAction(e -> {
            gameState.nextPlayerTurn();
            // เริ่มเทิร์นใหม่ → ส่งคนถัดไปกลับ HOME
            resetCurrentPlayerToHome(gameState.getCurrentPlayer().getCurrentLocation());
            refreshUI();
        });

        btnBack.setOnAction(e -> app.showStartScreen());

        HBox turnButtons = new HBox(12, btnEndTurn, btnBack);
        turnButtons.setSpacing(12);
        turnButtons.setLayoutX(1080 / 2.0 - 80);
        turnButtons.setLayoutY(720 - 40);
        root.getChildren().add(turnButtons);

        // ===== Scene =====
        scene = new Scene(root, 1080, 720);
        return scene;
    }

    private void placeButtonAt(Button btn, PlaceName place, double dx, double dy) {
        Point2D base = locationPoints.get(place);
        btn.setLayoutX(base.getX() + dx);
        btn.setLayoutY(base.getY() + dy);
    }

    private VBox makePlayerPanel() {
        VBox box = new VBox(5);
        box.setStyle(PANEL_BASE_STYLE);
        box.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        box.setPrefWidth(200);
        return box;
    }

    private static HBox statRow(String iconPath, String text) {
        Image iconImg = new Image(
                GameScreen.class.getResource(iconPath).toExternalForm()
        );
        ImageView icon = new ImageView(iconImg);
        icon.setFitWidth(18);
        icon.setFitHeight(18);
        icon.setPreserveRatio(true);

        Label label = new Label(text);
        label.setStyle("-fx-text-fill: white;");

        return new HBox(6, icon, label);
    }

    private static VBox buildPanelForPlayer(Player p, int playerIndex) {
        VBox box = new VBox(5);

        boolean isCurrent = (playerIndex == gameState.getCurrentPlayerIndex());
        String borderColor = isCurrent ? "#ffd54f" : "#ffffff33";

        box.setStyle(PANEL_BASE_STYLE +
                "-fx-border-color: " + borderColor + ";" +
                "-fx-border-width: 2;");

        String portraitPath = "/icons/Player " + (playerIndex + 1) + ".png";
        Image portraitImg = new Image(
                GameScreen.class.getResource(portraitPath).toExternalForm()
        );
        ImageView portrait = new ImageView(portraitImg);
        portrait.setFitWidth(48);
        portrait.setFitHeight(48);
        portrait.setPreserveRatio(true);

        Label name = new Label(p.getName());
        name.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16;");

        HBox moneyRow = statRow("/icons/money.png",
                " " + p.getStats().getMoney() + " / " + TurnSystem.getMaxMoney(gameMode));
        HBox happyRow = statRow("/icons/happy.png",
                " " + p.getStats().getHappiness() + " / " + TurnSystem.getMaxHappiness(gameMode));
        Label educationLabel = new Label(
                "Education Level: " + p.getStats().getEducation() + " / " + TurnSystem.getMaxEducation(gameMode));
        educationLabel.setStyle("-fx-text-fill: white;");
        Label timeLabel = new Label(
                "Time: " + p.getRemainingTime() + " / " + p.getMaxTimePerTurn());
        timeLabel.setStyle("-fx-text-fill: white;");

        box.getChildren().addAll(portrait, name, moneyRow, happyRow, educationLabel, timeLabel);
        return box;
    }

    public static void refreshUI() {
        List<Player> players = GameState.getPlayers();

        // 1) เคลียร์ + ซ่อนทุก panel ก่อน
        if (p1Panel != null) {
            p1Panel.getChildren().clear();
            p1Panel.setVisible(false);
            p1Panel.setManaged(false);
        }
        if (p2Panel != null) {
            p2Panel.getChildren().clear();
            p2Panel.setVisible(false);
            p2Panel.setManaged(false);
        }
        if (p3Panel != null) {
            p3Panel.getChildren().clear();
            p3Panel.setVisible(false);
            p3Panel.setManaged(false);
        }
        if (p4Panel != null) {
            p4Panel.getChildren().clear();
            p4Panel.setVisible(false);
            p4Panel.setManaged(false);
        }

        // 2) มี player กี่คน ก็เปิดเท่านั้นแหละ

        if (players.size() > 0 && p1Panel != null) {
            p1Panel.getChildren().add(buildPanelForPlayer(players.get(0), 0));
            p1Panel.setVisible(true);
            p1Panel.setManaged(true);
        }
        if (players.size() > 1 && p2Panel != null) {
            p2Panel.getChildren().add(buildPanelForPlayer(players.get(1), 1));
            p2Panel.setVisible(true);
            p2Panel.setManaged(true);
        }
        if (players.size() > 2 && p3Panel != null) {
            p3Panel.getChildren().add(buildPanelForPlayer(players.get(2), 2));
            p3Panel.setVisible(true);
            p3Panel.setManaged(true);
        }
        if (players.size() > 3 && p4Panel != null) {
            p4Panel.getChildren().add(buildPanelForPlayer(players.get(3), 3));
            p4Panel.setVisible(true);
            p4Panel.setManaged(true);
        }

        updateTokenVisibility();
    }


    // ตอนเริ่มเทิร์นของ current player ให้ย้ายกลับ HOME แบบ instant
    public static void resetCurrentPlayerToHome(PlaceName currentLocation) {
        Player player = gameState.getCurrentPlayer();
        Point2D currentPt = locationPoints.get(currentLocation);
        Point2D homePt = locationPoints.get(PlaceName.HOME);
        ImageView token = playerTokens.get(gameState.getCurrentPlayerIndex());
        if(currentLocation != PlaceName.HOME){
            Timeline goHome = new Timeline(
                    new KeyFrame(Duration.ZERO,
                            new KeyValue(token.layoutXProperty(), currentPt.getX()),
                            new KeyValue(token.layoutYProperty(), currentPt.getY())
                    ),
                    new KeyFrame(Duration.millis(450),
                            new KeyValue(token.layoutXProperty(), homePt.getX()),
                            new KeyValue(token.layoutYProperty(), homePt.getY())
                    )
            );

            goHome.setOnFinished(ev -> {

                if(gameState.isLastPlayerTurn() && player.isEndTurn()) {
                    delayScreenChange(() -> app.showResultScreen(gameState));
                    Player winner = gameState.findWinner();
                    // Redirect to end screen
                }

                // Set logical position to HOME
                player.setCurrentLocation(PlaceName.HOME);

                // End turn
                player.endTurn();
                gameState.nextPlayerTurn();

                refreshUI();
                root.setDisable(false);


            });

            goHome.play();
        } else if (homePt != null) {
            token.setLayoutX(homePt.getX());
            token.setLayoutY(homePt.getY());
        }

    }

    // เวอร์ชันใหม่: ส่ง callback มาให้ทำหลังเดินถึงที่หมาย (และยังไม่หมดเทิร์น)
    private static void moveCurrentPlayerTo(PlaceName destination, Runnable onArrive) {
        int idx = gameState.getCurrentPlayerIndex();
        Player player = gameState.getCurrentPlayer();
        PlaceName currentLocation = player.getCurrentLocation();

        if (currentLocation == destination) {
            // ถ้าอยู่ที่เดิมอยู่แล้ว ไม่ต้องเดิน แต่อาจมีการเปิดหน้าจอ
            if (onArrive != null) onArrive.run();
            return;
        }

        Point2D fromPt = locationPoints.get(currentLocation);
        Point2D toPt = locationPoints.get(destination);
        ImageView token = playerTokens.get(idx);

        // เดินทาง (กินเวลา)
        player.travel(destination);

        // ระหว่างเดิน: disable ทั้ง root ห้ามกดปุ่มอื่น
        root.setDisable(true);

        Timeline goToDestination = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(token.layoutXProperty(), fromPt.getX()),
                        new KeyValue(token.layoutYProperty(), fromPt.getY())
                ),
                new KeyFrame(Duration.millis(600),
                        new KeyValue(token.layoutXProperty(), toPt.getX()),
                        new KeyValue(token.layoutYProperty(), toPt.getY())
                )
        );
        goToDestination.play();

        goToDestination.setOnFinished(e -> {

            refreshUI();

            // ถ้าใช้เวลาเกินเทิร์น → จบเทิร์น + เปลี่ยนคนเล่น + กลับ HOME
            if (player.isEndTurn()) {
                resetCurrentPlayerToHome(destination);
            } else {
                root.setDisable(false);
                // ยังอยู่ในเทิร์นเดิม → เรียก callback (เข้า HomeScreen / StoreScreen) ถ้ามี
                if (onArrive != null) {
                    onArrive.run();
                }
            }
        });
    }

    private static void updateTokenVisibility() {
        int current = gameState.getCurrentPlayerIndex();
        for (int i = 0; i < playerTokens.size(); i++) {
            playerTokens.get(i).setVisible(i == current);
        }
    }

    public static void delayScreenChange(Runnable action) {
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(e -> action.run());
        delay.play();
    }

    public static void showTurnBanner(String turnNumber) {

        Label banner = new Label(turnNumber);

        banner.setStyle(
                "-fx-font-size: 64px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: black;" +
                        "-fx-background-color: rgba(135,206,250,0.9);" +  // sky-blue box
                        "-fx-padding: 20 60;" +
                        "-fx-background-radius: 20;" +
                        "-fx-border-color: navy;" +
                        "-fx-border-width: 4;" +
                        "-fx-border-radius: 20;"
        );

        // Center on screen
        banner.setLayoutX(1080 / 2.0 - 200);
        banner.setLayoutY(720 / 2.0 - 100);

        root.getChildren().add(banner);

        // Show for 3 seconds then fade out and remove
        PauseTransition delay = new PauseTransition(Duration.seconds(3));

        delay.setOnFinished(e -> {
            root.getChildren().remove(banner);
        });

        delay.play();
    }

    public static Main getApp() {
        return app;
    }
}
