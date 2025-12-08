package logic;

import player.Stats;

import java.util.List;

public class GameLogic {

    //Next Player Turn Method

    public static void nextPlayerTurn(Player player){
        player.startTurn();
        // May be update GUI Player Stat part
        player.endTurn();
        // GUI player end at home
    }

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
