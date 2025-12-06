package entity.items;

import entity.base.Buyable;
import logic.Player;

public class Item implements Buyable {
    private String name;
    private int price;
    private ItemType type;
    private int effectValue;

    public Item(String name){
        this.name = name;
    }

    @Override
    public void buyItem(Player player){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = Math.max(1, price);
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public int getEffectValue() {
        return effectValue;
    }

    public void setEffectValue(int effectValue) {
        this.effectValue = Math.max(0, effectValue);
    }
}
