package logic;

import entity.base.GameMode;
import org.junit.jupiter.api.Test;
import player.Player;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {

    @Test
    void constructorAndGettersWorkCorrectly() {
        Player p1 = new Player("Alice");
        Player p2 = new Player("Bob");
        List<Player> players = Arrays.asList(p1, p2);

        GameState state = new GameState(players, GameMode.SHORT);

        // static instance list
        assertEquals(2, GameState.getPlayers().size());
        assertSame(p1, GameState.getPlayers().get(0));

        // current player index & player
        assertEquals(0, state.getCurrentPlayerIndex());
        assertSame(p1, state.getCurrentPlayer());

        // game mode
        assertEquals(GameMode.SHORT, state.getGameMode());

        // last-player flag should be false at start
        assertFalse(state.isLastPlayerTurn());
    }
}
