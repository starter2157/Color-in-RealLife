package application;

import gui.GameScreen;
import gui.GameState;
import gui.StartScreen;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.GameMode;

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
        gameState = new GameState(playerCount, GameMode.MEDIUM);
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

    public static void main(String[] args) {
        launch(args);
    }
}
