package player;

import entity.base.GameMode;
import logic.TurnSystem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatsTest {

    @Test
    void defaultInitialization() {
        Stats stats = new Stats();
        assertEquals(0, stats.getEducation());
        assertEquals(0, stats.getHappiness());
        assertEquals(200, stats.getMoney());
        assertEquals(200, stats.getPointSummation());
    }

    @Test
    void settersClampToNonNegative() {
        Stats stats = new Stats();

        stats.setEducation(10);
        stats.setEducation(-5);
        assertEquals(0, stats.getEducation());

        stats.setHappiness(50);
        stats.setHappiness(-1);
        assertEquals(0, stats.getHappiness());

        stats.setMoney(500);
        stats.setMoney(-100);
        assertEquals(0, stats.getMoney());
    }

    @Test
    void setPointSummationRespectsGameModeCaps() {
        Stats stats = new Stats();

        // กำหนดค่าสูงกว่าขีดจำกัดเพื่อดูว่ามันถูก clamp หรือไม่
        stats.setMoney(2000);
        stats.setHappiness(1500);
        stats.setEducation(10);

        stats.setPointSummation(GameMode.SHORT);
        // start = 200
        // money = min(2000, 1200) = 1200
        // happiness = min(1500, 1000) = 1000
        // education = min(10, 8) * 8 = 8 * 8 = 64
        // total = 200 + 1200 + 1000 + 64 = 2464
        assertEquals(2464, stats.getPointSummation());
    }
}
