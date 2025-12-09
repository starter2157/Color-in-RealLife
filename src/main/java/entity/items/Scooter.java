package entity.items;

import entity.base.Buyable;
import entity.base.Eatable;
import entity.base.Transportation;
import player.Player;

public class Scooter extends Item implements Buyable{

    public Scooter(){
        super("Scooter", 299);
    }

    @Override
    public void buyItem(Player player){
        player.setTransportation(Transportation.SCOOTER);
        player.reduceMoney(getPrice());
    }
}
