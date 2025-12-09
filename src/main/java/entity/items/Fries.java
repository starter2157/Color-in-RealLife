package entity.items;

import entity.base.Buyable;
import entity.base.Eatable;
import player.Player;

public class Fries extends Item implements Buyable, Eatable {

    public Fries(){
        super("Fries", 50);
    }

    @Override
    public void buyItem(Player player){
        eat(player);
        player.reduceMoney(getPrice());
    }

    @Override
    public  void eat(Player player) {
        player.eat();
        player.reduceHappiness(1);

    }
}
