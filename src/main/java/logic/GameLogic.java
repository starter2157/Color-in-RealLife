package logic;

import java.util.ArrayList;
import java.util.List;

public class GameLogic {
    private List<Player> players = new ArrayList<Player>();
    private int currentTurn;
    private int maxTurn;

    // Set Player amount and Gamemode

    public void init(int playerAmount, GameMode gameMode){
        for (int i = 1; i <= playerAmount; i++) {
            players.add(new Player("Player" + i));
        }
        this.maxTurn = TurnSystem.getMaxTurn(gameMode);
        this.currentTurn = 0;
    }
}
