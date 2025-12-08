package gui;

import logic.GameLogic;
import logic.GameMode;
import logic.Player;
import logic.TurnSystem;

import java.util.ArrayList;
import java.util.List;

import static logic.GameLogic.findWinner;

public class GameState {

    private static final List<Player> players = new ArrayList<>();
    private GameMode gameMode;
    private int currentTurn = 1;
    private int maxTurn;
    private boolean isLastTurn = false;
    private int currentPlayerIndex = 0;

    public GameState(int playerCount, GameMode gameMode) {
        for (int i = 1; i <= playerCount; i++) {
            players.add(new Player("Player " + i));
        }
        this.gameMode = gameMode;
        this.maxTurn = TurnSystem.getMaxTurn(gameMode);
        while(!(isLastTurn && currentPlayerIndex == 0) && currentTurn == maxTurn - 1){
            nextTurn();
        }

        // final turn

        for(int i=0; i<players.size(); i++){
            nextTurn();
        }

        GameLogic.findWinner(players);
    }

    public static List<Player> getPlayers() {
        return players;
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
        GameLogic.nextPlayerTurn(players.get(currentPlayerIndex));
        isLastTurn = players.get(currentTurn).isWin(gameMode);
        GameScreen.refreshUI();
        currentPlayerIndex++;
        if (currentPlayerIndex >= players.size()) {
            currentPlayerIndex = 0;
            currentTurn++;
        }

    }
}
