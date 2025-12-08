package entity.items;

import entity.base.Buyable;
import logic.Player;

public class Valorant extends Item implements Buyable {

    public Valorant(){
        super("Valorant", 50);
    }

    @Override
    public void buyItem(Player player){
        player.reduceMoney(getPrice());
        player.gainHappiness(3);
    }
}
