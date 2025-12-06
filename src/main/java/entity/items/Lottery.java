package entity.items;

import entity.base.Item;

public class Lottery extends Item {
    private boolean isGrandPrize;
    private int number;

    public Lottery(int number){
        super("Lottery");
        this.setType(ItemType.LOTTERY);
        this.number = number;
    }
}
