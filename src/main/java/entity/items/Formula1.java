package entity.items;

import entity.base.Buyable;
import player.Player;

public class Formula1 extends Item implements Buyable {

    public Formula1(){
        super("Formula 1", 75);
    }

    @Override
    public void buyItem(Player player){
        player.reduceMoney(getPrice());
        player.gainHappiness(12);
    }
}
