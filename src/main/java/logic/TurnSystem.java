package logic;

import entity.base.GameMode;

public class TurnSystem {

    public static int getMaxTurn(GameMode gameMode) {
        if(gameMode.equals(GameMode.SHORT)) return 10;
        else if(gameMode.equals(GameMode.MEDIUM)) return 20;
        else if(gameMode.equals(GameMode.LONG)) return 30;
        else return Integer.MAX_VALUE;
    }

    public static int getMaxMoney(GameMode gameMode){
        if(gameMode.equals(GameMode.SHORT)) return 1200;
        else if(gameMode.equals(GameMode.MEDIUM)) return 2500;
        else if(gameMode.equals(GameMode.LONG)) return 4000;
        else return 8000;
    }

    public static int getMaxEducation(GameMode gameMode){
        if(gameMode.equals(GameMode.SHORT)) return 8;
        else if(gameMode.equals(GameMode.MEDIUM)) return 18;
        else if(gameMode.equals(GameMode.LONG)) return 26;
        else return 34;
    }

    public static int getMaxHappiness(GameMode gameMode){
        if(gameMode.equals(GameMode.SHORT)) return 1000;
        else if(gameMode.equals(GameMode.MEDIUM)) return 2000;
        else if(gameMode.equals(GameMode.LONG)) return 3000;
        else return 5000;
    }

}
