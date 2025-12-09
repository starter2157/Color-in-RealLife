package player;

import entity.base.GameMode;
import entity.base.Transportation;
import entity.items.Item;
import entity.base.PlaceName;
import entity.items.Lottery;
import entity.jobs.*;
import logic.TurnSystem;

public class Player {
    private final String name;
    private Stats stats;
    private Job job;
    private Inventory inventory;
    private int timeUsed;
    private int timeReduce;
    private PlaceName currentLocation;
    private Transportation transportation;
    private final int MAX_TIME_PER_TURN = 600;
    private boolean isEat = false;
    private boolean[] isPaid = {false, false, false, false};
    private int[] learningProgress = {0, 0, 0, 0};

    // Player Initialize

    public Player(String name){
        this.name = name;
        this.stats = new Stats();
        this.job = new Newbie();
        this.inventory = new Inventory();
        this.timeUsed = 0;
        this.currentLocation = PlaceName.HOME;
        setTransportation(Transportation.WALK);
    }

    // Player Action Method

    public void buyItem(Item item){
        item.buyItem(this);
    }

    public void eat(){
        useTime(30);
        setEat(true);
    }

    public void rest(){
        useTime(60);
        gainHappiness(10);
    }

    public void work(){
        getJob().work(this);
        useTime(60);
    }

    public void study(){
        useTime(90);
        gainEducation(1);
        if(stats.getEducation() == 34) setJob(new Director());
        else if(stats.getEducation() == 22) setJob(new Manager());
        else if(stats.getEducation() == 12) setJob(new Senior());
        else if(stats.getEducation() == 5) setJob(new Junior());
    }

    // Player Change Location Method

    public void travel(PlaceName destination){
        if(currentLocation.equals(PlaceName.HOME) && destination.equals(PlaceName.SCHOOL)) useTime(40 / timeReduce);
        else if(currentLocation.equals(PlaceName.HOME) && destination.equals(PlaceName.WORKPLACE)) useTime(40 / timeReduce);
        else if(currentLocation.equals(PlaceName.HOME) && destination.equals(PlaceName.STORE)) useTime(20 / timeReduce);
        else if(currentLocation.equals(PlaceName.HOME) && destination.equals(PlaceName.THEATRE)) useTime(30 / timeReduce);
        else if(currentLocation.equals(PlaceName.STORE) && destination.equals(PlaceName.SCHOOL)) useTime(60 / timeReduce);
        else if(currentLocation.equals(PlaceName.STORE) && destination.equals(PlaceName.WORKPLACE)) useTime(60 / timeReduce);
        else if(currentLocation.equals(PlaceName.STORE) && destination.equals(PlaceName.HOME)) useTime(20 / timeReduce);
        else if(currentLocation.equals(PlaceName.STORE) && destination.equals(PlaceName.THEATRE)) useTime(30 / timeReduce);
        else if(currentLocation.equals(PlaceName.THEATRE) && destination.equals(PlaceName.SCHOOL)) useTime(70 / timeReduce);
        else if(currentLocation.equals(PlaceName.THEATRE) && destination.equals(PlaceName.WORKPLACE)) useTime(10 / timeReduce);
        else if(currentLocation.equals(PlaceName.THEATRE) && destination.equals(PlaceName.STORE)) useTime(30 / timeReduce);
        else if(currentLocation.equals(PlaceName.THEATRE) && destination.equals(PlaceName.HOME)) useTime(30 / timeReduce);
        else if(currentLocation.equals(PlaceName.SCHOOL) && destination.equals(PlaceName.STORE)) useTime(60 / timeReduce);
        else if(currentLocation.equals(PlaceName.SCHOOL) && destination.equals(PlaceName.WORKPLACE)) useTime(80 / timeReduce);
        else if(currentLocation.equals(PlaceName.SCHOOL) && destination.equals(PlaceName.HOME)) useTime(40 / timeReduce);
        else if(currentLocation.equals(PlaceName.SCHOOL) && destination.equals(PlaceName.THEATRE)) useTime(70 / timeReduce);
        else if(currentLocation.equals(PlaceName.WORKPLACE) && destination.equals(PlaceName.STORE)) useTime(60 / timeReduce);
        else if(currentLocation.equals(PlaceName.WORKPLACE) && destination.equals(PlaceName.SCHOOL)) useTime(80 / timeReduce);
        else if(currentLocation.equals(PlaceName.WORKPLACE) && destination.equals(PlaceName.HOME)) useTime(40 / timeReduce);
        else if(currentLocation.equals(PlaceName.WORKPLACE) && destination.equals(PlaceName.THEATRE)) useTime(10 / timeReduce);
        setCurrentLocation(destination);
    }

    // Adjust Player Stats

    public void gainEducation(int education){
        stats.setEducation(stats.getEducation() + education);
    }

    public void gainHappiness(int happiness){
        stats.setHappiness(stats.getHappiness() + happiness);
    }

    public void reduceHappiness(int happiness){
        stats.setHappiness(stats.getHappiness() - happiness);
    }

    public void gainMoney(int money){
        stats.setMoney(stats.getMoney() + money);
    }

    public void reduceMoney(int money){
        stats.setMoney(stats.getMoney() - money);
    }

    // Time Management

    public void useTime(int time){
        if (time < 0) return;
        this.timeUsed = Math.min(MAX_TIME_PER_TURN, timeUsed + time);
    }

    public int getRemainingTime(){
        return MAX_TIME_PER_TURN - timeUsed;
    }

    public int getMaxTimePerTurn(){
        return MAX_TIME_PER_TURN;
    }

    // Turn Management

    public void startTurn(boolean isFirstTurn){
        this.setTimeUsed(0);
        if(!isEat() && !isFirstTurn) useTime(120);
        else setEat(false);
        Inventory playerInventory = getInventory();
        int lotteryNumber = (int)(Math.random() * (100));
        if(playerInventory.numberOfItem() != 0){
            for(Lottery lottery : playerInventory.getItems()){
                if(lotteryNumber == lottery.getNumber()) gainMoney(500);
            }
            playerInventory.getItems().clear();
        }
    }

    public void endTurn(){
        this.setTimeUsed(0);
        this.setCurrentLocation(PlaceName.HOME);
    }

    /* Win Condition (Look in TurnSystem)
    1. Mode Short Requirement (10 Rounds)
    2. Mode Medium Requirement (20 Rounds)
    3. Mode Long Requirement (30 Rounds)
    4. Mode Marathon Requirement (Till one Player meets requirement) */

    public boolean isWin(GameMode gameMode){
        if ( stats.getMoney() >= TurnSystem.getMaxMoney(gameMode) &&
                stats.getEducation() >= TurnSystem.getMaxEducation(gameMode) &&
                stats.getHappiness() >= TurnSystem.getMaxHappiness(gameMode)) return true;
        return false;
    }

    // Getter and Setter

    public void setTransportation(Transportation transportation){
        this.transportation = transportation;
        if(getTransportation().equals(Transportation.CAR)) setTimeReduce(4);
        else if(getTransportation().equals(Transportation.SCOOTER)) setTimeReduce(2);
        else setTimeReduce(1);
    }

    public void setTimeReduce(int timeReduce){
        this.timeReduce = timeReduce;
    }

    public void setCurrentLocation(PlaceName placeName){
        this.currentLocation = placeName;
    }

    public Stats getStats() {
        return stats;
    }

    public String getName() {
        return name;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int getTimeUsed() {
        return timeUsed;
    }

    public void setTimeUsed(int timeUsed) {
        this.timeUsed = timeUsed;
    }

    public PlaceName getCurrentLocation() {
        return currentLocation;
    }

    public Transportation getTransportation() {
        return transportation;
    }

    public int getMAX_TIME_PER_TURN() {
        return MAX_TIME_PER_TURN;
    }

    public boolean isEat() {
        return isEat;
    }

    public void setEat(boolean eat) {
        isEat = eat;
    }

    public Job getJob() {
        return job;
    }

    public boolean isEndTurn(){
        return getTimeUsed() >= getMAX_TIME_PER_TURN();
    }

    public boolean[] getIsPaid() {
        return isPaid;
    }

    public int[] getLearningProgress() {
        return learningProgress;
    }
}
