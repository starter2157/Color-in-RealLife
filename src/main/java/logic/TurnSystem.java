package logic;

import entity.base.GameMode;

public class TurnSystem {

    public static int getMaxTurn(GameMode gameMode) {
        if(gameMode.equals(GameMode.SHORT)) return 2;
        else if(gameMode.equals(GameMode.MEDIUM)) return 20;
        else if(gameMode.equals(GameMode.LONG)) return 30;
        else return Integer.MAX_VALUE;
    }

}
