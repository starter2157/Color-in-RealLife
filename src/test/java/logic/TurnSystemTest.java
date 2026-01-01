package logic;

import entity.base.GameMode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TurnSystemTest {

    @Test
    void getMaxTurnByGameMode() {
        assertEquals(10, TurnSystem.getMaxTurn(GameMode.SHORT));
        assertEquals(20, TurnSystem.getMaxTurn(GameMode.MEDIUM));
        assertEquals(30, TurnSystem.getMaxTurn(GameMode.LONG));
    }

    @Test
    void getMaxMoneyByGameMode() {
        assertEquals(1200, TurnSystem.getMaxMoney(GameMode.SHORT));
        assertEquals(2500, TurnSystem.getMaxMoney(GameMode.MEDIUM));
        assertEquals(4000, TurnSystem.getMaxMoney(GameMode.LONG));
    }

    @Test
    void getMaxEducationByGameMode() {
        assertEquals(8, TurnSystem.getMaxEducation(GameMode.SHORT));
        assertEquals(18, TurnSystem.getMaxEducation(GameMode.MEDIUM));
        assertEquals(26, TurnSystem.getMaxEducation(GameMode.LONG));
    }

    @Test
    void getMaxHappinessByGameMode() {
        assertEquals(1000, TurnSystem.getMaxHappiness(GameMode.SHORT));
        assertEquals(2000, TurnSystem.getMaxHappiness(GameMode.MEDIUM));
        assertEquals(3000, TurnSystem.getMaxHappiness(GameMode.LONG));
    }
}
