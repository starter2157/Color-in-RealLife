package entity.items;

import entity.base.Buyable;
import player.Player;

public class PremierLeague extends Item implements Buyable {

    public PremierLeague(){
        super("PremierLeague", 125);
    }

    @Override
    public void buyItem(Player player){
        player.reduceMoney(getPrice());
        player.gainHappiness(30);
    }
}
