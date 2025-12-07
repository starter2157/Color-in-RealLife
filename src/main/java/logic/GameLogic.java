package logic;

import java.util.ArrayList;
import java.util.List;

public class GameLogic {
    private List<Player> players = new ArrayList<Player>();
    private int currentTurn;
    private int maxTurn;
    private boolean isLastTurn;
    private GameMode gameMode;
    private int playerAmount;

    // Set Player amount and GameMode

    public void init(int playerAmount, GameMode gameMode){
        this.gameMode = gameMode;
        this.playerAmount = playerAmount;
        for (int i = 1; i <= playerAmount; i++) {
            players.add(new Player("Player" + i));
        }
        this.maxTurn = TurnSystem.getMaxTurn(gameMode);
        this.currentTurn = 0;
    }

    // Start Game

    public void start(){
        while(!isLastTurn){
            boolean isPlayerWin = false;
            for(Player player : players){
                player.startTurn();
                isPlayerWin = player.isWin(gameMode);
            }
            if(isPlayerWin) isLastTurn = true;
        }
        for(Player player : players){
            player.startTurn();
        }
        pointCalculate();
    }

    // Point Calculation Method

    public void pointCalculate(){

    }
}
