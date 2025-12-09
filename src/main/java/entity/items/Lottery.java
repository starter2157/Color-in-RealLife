package entity.items;

import entity.base.Buyable;
import player.Player;

public class Lottery extends Item implements Buyable {

    private int number;

    public Lottery(){
        super("Lottery", 50);
        number = (int)(Math.random() * (100));
    }

    @Override
    public void buyItem(Player player){
        player.getInventory().addItem(new Lottery());
        player.reduceMoney(getPrice());
    }
}
