package gui;

import logic.GameLogic;
import logic.GameMode;
import logic.Player;
import logic.TurnSystem;

import java.util.ArrayList;
import java.util.List;

import static logic.GameLogic.findWinner;

public class GameState {

    private static GameState instance;

    private List<Player> players;
    private int currentPlayerIndex;

    private GameMode gameMode;
    private int currentTurn = 1;
    private int maxTurn;
    private boolean isLastTurn = false;

    public GameState(List<Player> players, GameMode gameMode) {
        this.players = players;
        this.currentPlayerIndex = 0;
        this.gameMode = gameMode;
        this.maxTurn = TurnSystem.getMaxTurn(gameMode);
        instance = this;
    }

    public static List<Player> getPlayers() {
        return instance.players;
    }

    public static GameState getInstance() {
        return instance;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public void nextTurn() {
        nextPlayerIndex();
        GameLogic.nextPlayerTurn(players.get(currentPlayerIndex));
        isLastTurn = players.get(currentTurn).isWin(gameMode);
        GameScreen.refreshUI();
    }

    public void nextPlayerIndex(){
        currentPlayerIndex++;
        if (currentPlayerIndex >= players.size()) {
            currentPlayerIndex = 0;
            currentTurn++;
        }
    }
}
