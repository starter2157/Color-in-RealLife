package entity.items;

import entity.base.Buyable;
import entity.base.Eatable;
import player.Player;

public class BigMeal extends Item implements Buyable, Eatable {

    public BigMeal(){
        super("BigMeal", 185);
    }

    @Override
    public void buyItem(Player player){
        eat(player);
        player.reduceMoney(getPrice());
    }

    @Override
    public void eat(Player player){
        player.eat();
        player.gainHappiness(15);
    }
}
