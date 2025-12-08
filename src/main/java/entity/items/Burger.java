package entity.items;

import entity.base.Buyable;
import entity.base.Eatable;
import player.Player;

public class Burger extends Item implements Buyable, Eatable {

    public Burger(){
        super("Burger", 95);
    }

    @Override
    public void buyItem(Player player){
        eat(player);
        player.reduceMoney(getPrice());
    }

    @Override
    public void eat(Player player){
        player.eat();
        player.gainHappiness(2);
    }
}
