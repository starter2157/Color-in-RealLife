package entity.items;

import entity.base.Buyable;
import player.Player;

public class Uma extends Item implements Buyable {

    public Uma(){
        super("Uma", 90);
    }

    @Override
    public void buyItem(Player player){
        player.reduceMoney(getPrice());
        player.gainHappiness(15);
    }
}
