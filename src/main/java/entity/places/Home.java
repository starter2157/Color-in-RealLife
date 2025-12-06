package entity.places;

import entity.base.Location;
import entity.base.Restable;
import logic.Player;

public class Home extends Location implements Restable {

    public Home(){
        super(PlaceType.HOME);
    }

    @Override
    public void rest(Player player) {
        player.rest(this.getPlaceType());
    }
}
