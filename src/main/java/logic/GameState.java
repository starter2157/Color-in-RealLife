package logic;

import entity.base.GameMode;
import gui.GameScreen;
import player.Player;
import player.Stats;

import java.util.List;

public class GameState {

    private static GameState instance;

    private final List<Player> players;
    private int currentPlayerIndex;

    private final GameMode gameMode;
    private int currentTurn = 1;
    private final int maxTurn;
    private boolean isLastTurn = false;
    private boolean isPlayerWin = false;
    private boolean isFirstTurn = true;
    private boolean isLastPlayerTurn = false;

    public GameState(List<Player> players, GameMode gameMode) {
        this.players = players;
        this.currentPlayerIndex = 0;
        this.gameMode = gameMode;
        this.maxTurn = TurnSystem.getMaxTurn(gameMode);
        instance = this;
        this.players.get(currentPlayerIndex).startTurn(isFirstTurn);
    }

    // Turn Control

    public void nextPlayerTurn() {
        if(!isLastTurn) isLastTurn = players.get(currentPlayerIndex).isWin(gameMode);
        nextPlayerIndex();
        players.get(currentPlayerIndex).startTurn(isFirstTurn);
    }

    public void nextPlayerIndex(){
        if (getCurrentPlayer().isWin(gameMode)) isPlayerWin = true;
        currentPlayerIndex++;
        if (isLastTurn && currentPlayerIndex == players.size() - 1) isLastPlayerTurn = true;
        if (currentPlayerIndex >= players.size()) {
            currentPlayerIndex = 0;
            currentTurn++;
            isFirstTurn = false;
            if(currentTurn == maxTurn || isPlayerWin) {
                GameScreen.showTurnBanner("Last Turn");
                isLastTurn = true;
            }
            else if(!isLastPlayerTurn) GameScreen.showTurnBanner("Turn " + currentTurn);
            else GameScreen.showTurnBanner("GAME END");
        }
    }

    // Point Calculation Method

    public Player findWinner(){
        int maxPoint = -1;
        int playerPoint = 0;
        Player winner = null;
        for (Player player : players){
            Stats playerStats = player.getStats();
            playerPoint += Math.min(playerStats.getEducation(), TurnSystem.getMaxEducation(gameMode));
            playerPoint += Math.min(playerStats.getHappiness(), TurnSystem.getMaxHappiness(gameMode));
            playerPoint += Math.min(playerStats.getMoney(), TurnSystem.getMaxMoney(gameMode));
            if(playerPoint > maxPoint) winner = player;
        }
        return winner;
    }

    // Getter ans Setter

    public static List<Player> getPlayers() {
        return instance.players;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public boolean isLastPlayerTurn() {
        return isLastPlayerTurn;
    }

    public GameMode getGameMode(){
        return gameMode;
    }
}
