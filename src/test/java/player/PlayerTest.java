package player;

import entity.base.PlaceName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void constructorInitializesFields() {
        Player player = new Player("Alice");

        assertEquals("Alice", player.getName());
        assertNotNull(player.getStats());
        assertNotNull(player.getInventory());
        assertNotNull(player.getJob());

        assertEquals(0, player.getTimeUsed());
        assertEquals(player.getMAX_TIME_PER_TURN(), player.getRemainingTime());
        assertFalse(player.isEat());
        assertEquals(PlaceName.HOME, player.getCurrentLocation());
    }

    @Test
    void gainAndReduceMoneyAffectStatsAndClampAtZero() {
        Player player = new Player("Bob");
        Stats stats = player.getStats();

        int initialMoney = stats.getMoney();

        player.gainMoney(300);
        assertEquals(initialMoney + 300, stats.getMoney());

        player.reduceMoney(100);
        assertEquals(initialMoney + 200, stats.getMoney());

        // ลองลดจนติดลบ ดูว่า clamp เป็น 0
        player.reduceMoney(10_000);
        assertEquals(0, stats.getMoney());
    }

    @Test
    void gainAndReduceHappinessClampAtZero() {
        Player player = new Player("Carol");
        Stats stats = player.getStats();

        player.gainHappiness(50);
        assertEquals(50, stats.getHappiness());

        player.reduceHappiness(20);
        assertEquals(30, stats.getHappiness());

        player.reduceHappiness(1000);
        assertEquals(0, stats.getHappiness());
    }

    @Test
    void gainEducationUsesStats() {
        Player player = new Player("Dave");
        Stats stats = player.getStats();

        player.gainEducation(3);
        assertEquals(3, stats.getEducation());

        player.gainEducation(2);
        assertEquals(5, stats.getEducation());
    }

    @Test
    void useTimeDoesNotAcceptNegativeAndClampsToMax() {
        Player player = new Player("Eve");
        int maxTime = player.getMAX_TIME_PER_TURN();

        assertEquals(0, player.getTimeUsed());
        assertEquals(maxTime, player.getRemainingTime());

        // negative ไม่ควรมีผล
        player.useTime(-10);
        assertEquals(0, player.getTimeUsed());

        // ใช้เวลาไปบางส่วน
        player.useTime(100);
        assertEquals(100, player.getTimeUsed());
        assertEquals(maxTime - 100, player.getRemainingTime());
        assertFalse(player.isEndTurn());

        // ใช้เวลาเกิน max => clamp
        player.useTime(maxTime);   // ตอนนี้ timeUsed ควรเป็น max
        assertEquals(maxTime, player.getTimeUsed());
        assertEquals(0, player.getRemainingTime());
        assertTrue(player.isEndTurn());
    }

    @Test
    void eatUsesTimeAndSetsEatFlag() {
        Player player = new Player("Frank");
        int before = player.getTimeUsed();

        player.eat();

        assertTrue(player.isEat());
        assertEquals(before + 30, player.getTimeUsed());
    }

    @Test
    void restUsesTimeAndIncreaseHappiness() {
        Player player = new Player("Grace");
        Stats stats = player.getStats();

        int beforeTime = player.getTimeUsed();
        int beforeHappy = stats.getHappiness();

        player.rest();

        assertEquals(beforeTime + 60, player.getTimeUsed());
        assertEquals(beforeHappy + 10, stats.getHappiness());
    }
}
