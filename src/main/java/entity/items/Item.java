package entity.items;

import entity.base.Buyable;
import player.Player;

public abstract class Item implements Buyable {
    private String name;
    private int price;

    public Item(String name, int price){
        this.name = name;
        this.price = price;
    }

    public abstract void buyItem(Player player);

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
