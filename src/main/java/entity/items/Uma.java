package entity.items;

import entity.base.Buyable;
import player.Player;

public class Uma extends Item implements Buyable {

    public Uma(){
        super("Uma", 55);
    }

    @Override
    public void buyItem(Player player){
        player.reduceMoney(getPrice());
        player.gainHappiness(10);
        player.useTime(30);
    }
}
