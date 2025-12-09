package entity.items;

import entity.base.Buyable;
import player.Player;

public class Lottery extends Item implements Buyable {

    private final int number;

    public Lottery(){
        super("Lottery", 50);
        number = (int)(Math.random() * (50));
    }

    @Override
    public void buyItem(Player player){
        player.getInventory().addItem(new Lottery());
        player.reduceMoney(getPrice());
    }

    public int getNumber(){
        return this.number;
    }
}
