package gui;

import logic.Player;
import java.util.ArrayList;
import java.util.List;

public class GameState {

    private final List<Player> players = new ArrayList<>();
    private int currentPlayerIndex = 0;
    private int round = 1;

    public GameState(int playerCount) {
        for (int i = 1; i <= playerCount; i++) {
            players.add(new Player("Player " + i));
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public int getRound() {
        return round;
    }

    public void nextTurn() {
        currentPlayerIndex++;
        if (currentPlayerIndex >= players.size()) {
            currentPlayerIndex = 0;
            round++;
        }
    }
}
