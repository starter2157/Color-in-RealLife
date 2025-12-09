package player;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {

    @Test
    void newInventoryIsEmpty() {
        Inventory inventory = new Inventory();
        assertEquals(0, inventory.numberOfItem());
        assertTrue(inventory.getItems().isEmpty());
    }

    @Test
    void addItemIncreasesCount() {
        Inventory inventory = new Inventory();

        // ไม่จำเป็นต้องมี Lottery จริง ๆ ก็ได้ ใช้ null เพื่อเช็ค count อย่างเดียว
        inventory.addItem(null);
        assertEquals(1, inventory.numberOfItem());
        assertEquals(1, inventory.getItems().size());

        inventory.addItem(null);
        assertEquals(2, inventory.numberOfItem());
        assertEquals(2, inventory.getItems().size());
    }
}
