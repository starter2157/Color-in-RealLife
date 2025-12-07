package logic;

import application.Main;
import entity.places.PlaceName;
import player.Inventory;
import entity.jobs.Job;
import player.Stats;

public class Player {
    private String name;
    private Stats stats;
    private Job job;
    private Inventory inventory;
    private int timeUsed;
    private City cityMap;
    private PlaceName currentLocation;
    private Transportation transportation;
    private final int MAX_TIME_PER_TURN = 1000;

    // Player Initialize

    public Player(String name){
        this.name = name;
        this.stats = new Stats();
        this.job = null;
        this.inventory = new Inventory();
        this.timeUsed = 0;
        this.cityMap = new City();
        this.currentLocation = PlaceName.HOME;
        this.transportation = Transportation.WALK;
    }

    // Player Method

    public void rest(int amount){
        stats.reduceStress(amount);
    }

    private void work(int amount){
        stats.setMoney(stats.getMoney() + amount);
        stats.gainStress(1);
    }

    private void study(){
        stats.setEducation(stats.getEducation() + 1);
        stats.gainStress(2);
    }



    private void useTime(int amount){
        this.timeUsed += amount;
    }

    public void gainStress(int stress){
        stats.gainStress(stress);
    }

    public void startTurn(){
        while(timeUsed < MAX_TIME_PER_TURN){
            this.doAction();
        }
    }

    public void doAction(){
        // do something
    }

    // Win Condition

    public boolean isWin(GameMode gameMode){
        if(gameMode.equals(GameMode.SHORT) && stats.getMoney() == 1500 && stats.getEducation() == 20 && stats.getHappiness() == 500) return true;
        else if(gameMode.equals(GameMode.MEDIUM) && stats.getMoney() == 2000 && stats.getEducation() == 35 && stats.getHappiness() == 1000) return true;
        else if(gameMode.equals(GameMode.LONG) && stats.getMoney() == 4000 && stats.getEducation() == 50 && stats.getHappiness() == 1500) return true;
        else return gameMode.equals(GameMode.MARATHON) && stats.getMoney() == 7500 && stats.getEducation() == 75 && stats.getHappiness() == 2000;
    }

    // Getter and Setter
}
