package logic;

import player.Stats;

import java.util.List;

public class GameLogic {

    // Point Calculation Method

    public static String findWinner(List<Player> players){
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
