package entity.items;

import entity.base.Buyable;
import entity.base.Eatable;
import entity.base.Transportation;
import player.Player;

public class Car extends Item implements Buyable{

    public Car(){
        super("Car", 499);
    }

    @Override
    public void buyItem(Player player){
        player.setTransportation(Transportation.CAR);
        player.reduceMoney(getPrice());
    }
}
