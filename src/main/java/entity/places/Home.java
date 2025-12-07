package entity.places;

import entity.base.Restable;
import logic.Player;

public class Home extends Location implements Restable {

    public Home(){
        super(PlaceName.HOME, 0);
    }

    @Override
    public void rest(Player player) {
        player.rest(200);
    }
}
