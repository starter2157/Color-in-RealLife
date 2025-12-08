package gui;

import entity.base.GameMode;
import logic.Player;
import logic.TurnSystem;
import player.Stats;

import java.util.List;

public class GameState {

    private static GameState instance;

    private List<Player> players;
    private int currentPlayerIndex;

    private GameMode gameMode;
    private int currentTurn = 1;
    private int maxTurn;
    private boolean isLastTurn = false;
    private boolean isFirstTurn = true;

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

    // Turn Control

    public void nextPlayerTurn() {
        isLastTurn = players.get(currentPlayerIndex).isWin(gameMode);
        nextPlayerIndex();
        players.get(currentPlayerIndex).startTurn(isFirstTurn);
    }

    public void nextPlayerIndex(){
        currentPlayerIndex++;
        if (currentPlayerIndex >= players.size()) {
            currentPlayerIndex = 0;
            currentTurn++;
            isFirstTurn = false;
        }
    }

    // Point Calculation Method

    public String findWinner(List<Player> players){
        int maxPoint = -1;
        int playerPoint = 0;
        String winner = "Player 1";
        for (Player player : players){
            Stats playerStats = player.getStats();
            playerPoint += playerStats.getEducation();
            playerPoint += playerStats.getHappiness();
            playerPoint += playerStats.getMoney();
            if(playerPoint > maxPoint) winner = player.getName();
        }
        return winner;
    }
}
