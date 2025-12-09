package entity.items;

import entity.base.Buyable;
import player.Player;

public class Valorant extends Item implements Buyable {

    public Valorant(){
        super("Valorant", 65);
    }

    @Override
    public void buyItem(Player player){
        player.reduceMoney(getPrice());
        player.gainHappiness(10);
        player.useTime(45);
    }
}
