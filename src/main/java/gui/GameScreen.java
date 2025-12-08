package gui;

// package game; // <- add or remove depending on your project
import application.Main;
import entity.places.PlaceName;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import logic.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameScreen {

    private final Main app;
    private static GameState gameState = null;
    private Scene scene;

    private ImageView mapView;
    private Pane mapLayer; // map + player tokens

    // map locations and tokens
    private Map<PlaceName, Point2D> locationPoints = new HashMap<>();
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

    public GameScreen(Main app, GameState gameState) {
        this.app = app;
        GameScreen.gameState = gameState;
        this.scene = createScene();
        refreshUI();
    }

    public Scene getScene() {
        return scene;
    }

    private Scene createScene() {

        // ========== MAP LAYER (background + tokens) ==========
        Image mapImage = new Image(
                getClass().getResource("/city_map.png").toExternalForm()
        );
        mapView = new ImageView(mapImage);
        mapView.setPreserveRatio(true);
        mapView.setSmooth(true);
        mapView.setFitWidth(1080);
        mapView.setFitHeight(720);

        mapLayer = new Pane();
        mapLayer.getChildren().add(mapView);

        // approximate coordinates on a 1280x720 map (tweak as you like)
        locationPoints.put(PlaceName.HOME,         new Point2D(240, 300));
        locationPoints.put(PlaceName.STORE,          new Point2D(260, 560));
        locationPoints.put(PlaceName.THEATRE, new Point2D(820, 520));
        locationPoints.put(PlaceName.SCHOOL,    new Point2D(640, 140));
        locationPoints.put(PlaceName.WORKPLACE,     new Point2D(980, 280));

        // one token per player (small portrait)
        List<Player> players = GameState.getPlayers();
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);

            String portraitPath = "/icons/p" + (i + 1) + ".png";
            Image img = new Image(
                    getClass().getResource(portraitPath).toExternalForm()
            );
            ImageView token = new ImageView(img);
            token.setFitWidth(48);
            token.setFitHeight(48);
            token.setPreserveRatio(true);

            Point2D pt = locationPoints.get(p.getCurrentLocation());
            token.setLayoutX(pt.getX());
            token.setLayoutY(pt.getY());

            playerTokens.add(token);
            mapLayer.getChildren().add(token);
        }

        // root StackPane to layer HUD on top of map
        StackPane root = new StackPane();
        root.getChildren().add(mapLayer);

        // ========== HUD: 4 corner panels ==========

        p1Panel = makePlayerPanel();
        StackPane.setAlignment(p1Panel, Pos.TOP_LEFT);
        StackPane.setMargin(p1Panel, new Insets(15, 0, 0, 15));

        p2Panel = makePlayerPanel();
        StackPane.setAlignment(p2Panel, Pos.TOP_RIGHT);
        StackPane.setMargin(p2Panel, new Insets(15, 15, 0, 0));

        p3Panel = makePlayerPanel();
        StackPane.setAlignment(p3Panel, Pos.BOTTOM_LEFT);
        StackPane.setMargin(p3Panel, new Insets(0, 0, 15, 15));

        p4Panel = makePlayerPanel();
        StackPane.setAlignment(p4Panel, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(p4Panel, new Insets(0, 15, 15, 0));

        root.getChildren().addAll(p1Panel, p2Panel, p3Panel, p4Panel);

        // ========== BOTTOM BUTTONS (movement + turn) ==========

        Button btnHome = new Button("Home");
        Button btnStore = new Button("Store");
        Button btnTheatre = new Button("Theatre");
        Button btnSchool = new Button("School");
        Button btnWorkplace = new Button("Workplace");
        Button btnEndTurn = new Button("End Turn");
        Button btnBack = new Button("Back");

        btnHome.setOnAction(e -> moveCurrentPlayerTo(PlaceName.HOME));
        btnStore.setOnAction(e -> moveCurrentPlayerTo(PlaceName.STORE));
        btnTheatre.setOnAction(e -> moveCurrentPlayerTo(PlaceName.THEATRE));
        btnSchool.setOnAction(e -> moveCurrentPlayerTo(PlaceName.SCHOOL));
        btnWorkplace.setOnAction(e -> moveCurrentPlayerTo(PlaceName.WORKPLACE));

        btnEndTurn.setOnAction(e -> {
            gameState.nextTurn();
            refreshUI();
        });

        btnBack.setOnAction(e -> app.showStartScreen());

        HBox moveButtons = new HBox(12, btnHome, btnStore, btnTheatre, btnSchool, btnWorkplace);
        moveButtons.setAlignment(Pos.CENTER);
        moveButtons.setPadding(new Insets(10));

        HBox turnButtons = new HBox(12, btnEndTurn, btnBack);
        turnButtons.setAlignment(Pos.CENTER);
        turnButtons.setPadding(new Insets(10));

        VBox buttonLayer = new VBox(10, moveButtons, turnButtons);
        buttonLayer.setAlignment(Pos.BOTTOM_CENTER);
        buttonLayer.setMouseTransparent(false);

        StackPane.setAlignment(buttonLayer, Pos.BOTTOM_CENTER);
        StackPane.setMargin(buttonLayer, new Insets(30));

        root.getChildren().add(buttonLayer);

        // ========== Scene + resizing behaviour ==========
        scene = new Scene(root, 1080, 720);

        scene.widthProperty().addListener((obs, oldV, newV) ->
                mapView.setFitWidth(newV.doubleValue())
        );
        scene.heightProperty().addListener((obs, oldV, newV) ->
                mapView.setFitHeight(newV.doubleValue())
        );

        return scene;
    }

    // small helper to build a HUD panel shell
    private VBox makePlayerPanel() {
        VBox box = new VBox(5);
        box.setStyle(PANEL_BASE_STYLE);
        box.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        box.setPrefWidth(200);
        return box;
    }

    // row like: [icon][text]
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

        String portraitPath = "/icons/p" + (playerIndex + 1) + ".png";
        Image portraitImg = new Image(
                GameScreen.class.getResource(portraitPath).toExternalForm()
        );
        ImageView portrait = new ImageView(portraitImg);
        portrait.setFitWidth(48);
        portrait.setFitHeight(48);
        portrait.setPreserveRatio(true);

        Label name = new Label(p.getName());
        name.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16;");

        HBox moneyRow = statRow("/icons/money.png", " " + p.getStats().getMoney());
        HBox happyRow = statRow("/icons/happy.png", " " + p.getStats().getHappiness());

        Label timeLabel = new Label(
                "Time: " + p.getRemainingTime() + " / " + p.getMaxTimePerTurn()
        );
        timeLabel.setStyle("-fx-text-fill: white;");

        box.getChildren().addAll(portrait, name, moneyRow, happyRow, timeLabel);
        return box;
    }

    public static void refreshUI() {
        List<Player> players = GameState.getPlayers();

        p1Panel.getChildren().clear();
        p2Panel.getChildren().clear();
        p3Panel.getChildren().clear();
        p4Panel.getChildren().clear();

        if (players.size() > 0) p1Panel.getChildren().add(
                buildPanelForPlayer(players.get(0), 0));
        if (players.size() > 1) p2Panel.getChildren().add(
                buildPanelForPlayer(players.get(1), 1));
        if (players.size() > 2) p3Panel.getChildren().add(
                buildPanelForPlayer(players.get(2), 2));
        if (players.size() > 3) p4Panel.getChildren().add(
                buildPanelForPlayer(players.get(3), 3));

        updateTokenVisibility();
    }

    // animate token + update state
    private void moveCurrentPlayerTo(PlaceName destination) {
        int idx = gameState.getCurrentPlayerIndex();
        Player player = gameState.getCurrentPlayer();
        PlaceName currentLocation = player.getCurrentLocation();

        if (currentLocation == destination) return;

        Point2D fromPt = locationPoints.get(currentLocation);
        Point2D toPt = locationPoints.get(destination);
        ImageView token = playerTokens.get(idx);

        player.travel(destination);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(token.layoutXProperty(), fromPt.getX()),
                        new KeyValue(token.layoutYProperty(), fromPt.getY())
                ),
                new KeyFrame(Duration.millis(600),
                        new KeyValue(token.layoutXProperty(), toPt.getX()),
                        new KeyValue(token.layoutYProperty(), toPt.getY())
                )
        );
        timeline.play();

        timeline.setOnFinished(e -> {
            refreshUI();

            if (player.getTimeUsed() >= player.getMAX_TIME_PER_TURN()) {
                player.endTurn();
                gameState.nextTurn();
                refreshUI();  // MUST refresh again AFTER changing current player
            }
        });

    }

    private static void updateTokenVisibility() {
        int current = gameState.getCurrentPlayerIndex();
        for (int i = 0; i < playerTokens.size(); i++) {
            playerTokens.get(i).setVisible(i == current);
        }
    }

}
