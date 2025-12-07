package logic;

import player.Stats;

import java.util.List;

public class GameLogic {
    private int currentTurn;
    private int maxTurn;
    private boolean isLastTurn;

    // Start Game

    public void startGame(List<Player> players, GameMode gameMode){
        this.currentTurn = 0;
        this.maxTurn = TurnSystem.getMaxTurn(gameMode);
        while(!isLastTurn && currentTurn == maxTurn - 1){
            boolean isPlayerWin = false;
            for(Player player : players){
                player.startTurn();
                isPlayerWin = player.isWin(gameMode);
            }
            this.currentTurn += 1;
            if(isPlayerWin) isLastTurn = true;
        }
        players.forEach(Player::startTurn);
        findWinner(players);
    }

    // Point Calculation Method

    public void findWinner(List<Player> players){
        int maxPoint = -1;
        int playerPoint = 0;
        for (Player player : players){
            Stats playerStats = player.getStats();
            playerPoint += playerStats.getEducation();
            playerPoint += playerStats.getHappiness();
            playerPoint += playerStats.getMoney();
        }
    }
}
