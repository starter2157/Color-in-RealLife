package application;

import gui.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.GameMode;
import logic.Player;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private Stage primaryStage;
    private GameState gameState;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Board Game with Screens");
        showStartScreen();
        primaryStage.show();
    }

    // Called from StartScreen when user clicks "Start Game"
    public void startNewGame(int playerCount) {
        List<Player> players = new ArrayList<>();
        for (int i = 1; i <= playerCount; i++) {
            players.add(new Player("Player " + i));
        }
        gameState = new GameState(players, GameMode.MEDIUM);
        GameScreen gameScreen = new GameScreen(this, gameState);
        Scene scene = gameScreen.getScene();

        primaryStage.setScene(scene);

        // make window exactly match the scene size
        primaryStage.sizeToScene();

        // FIXED WINDOW SIZE (no resize)
        primaryStage.setResizable(false);

        primaryStage.show();
    }

    // Called from GameScreen when user clicks "Back to Start"
    public void showStartScreen() {
        StartScreen startScreen = new StartScreen(this);
        Scene scene = startScreen.getScene();
        primaryStage.setScene(scene);
    }

    public void showGameScreen(GameState state) {
        GameScreen game = new GameScreen(this, state);
        primaryStage.setScene(game.getScene());
    }

    public void showHomeScreen(GameState state) {
        HomeScreen home = new HomeScreen(this, state);
        primaryStage.setScene(home.getScene());
    }

    public void showStoreScreen(GameState state) {
        StoreScreen store = new StoreScreen(this, state);
        primaryStage.setScene(store.getScene());
    }

    public void showTheatreScreen(GameState state) {
        TheatreScreen theatre = new TheatreScreen(this, state);
        primaryStage.setScene(theatre.getScene());
    }

    public void showSchoolScreen(GameState state) {
        SchoolScreen school = new SchoolScreen(this, state);
        primaryStage.setScene(school.getScene());
    }

    public void showWorkplaceScreen(GameState state) {
        WorkplaceScreen workplace = new WorkplaceScreen(this, state);
        primaryStage.setScene(workplace.getScene());
    }


    public static void main(String[] args) {
        launch(args);
    }
}
