package logic;

import entity.jobs.JobType;
import entity.places.PlaceName;
import player.Inventory;
import player.Stats;

public class Player {
    private String name;
    private Stats stats;
    private JobType jobType;
    private Inventory inventory;
    private int timeUsed;
    private double timeReduce;
    private PlaceName currentLocation;
    private Transportation transportation;
    private final int MAX_TIME_PER_TURN = 600;
    private boolean isEat;

    // Player Initialize

    public Player(String name){
        this.name = name;
        this.stats = new Stats();
        this.jobType = JobType.NEWBIE;
        this.inventory = new Inventory();
        this.timeUsed = 0;
        this.currentLocation = PlaceName.HOME;
        this.isEat = false;
        setTransportation(Transportation.CAR);
    }

    // Player Action Method

    public void eat(){
        useTime(30);
        setEat(true);
    }

    public void rest(){
        useTime(30);
        reduceStress(1);
    }

    public void work(int money){
        useTime(60);
        gainMoney(money);
        gainStress(1);
    }

    public void study(){
        useTime(60);
        gainStress(2);
        gainEducation(1);
    }

    // Player Change Location Method

    public void travel(PlaceName destination){
        if(currentLocation.equals(PlaceName.HOME) && destination.equals(PlaceName.SCHOOL)) useTime((int)( 40 / timeReduce));
        else if(currentLocation.equals(PlaceName.HOME) && destination.equals(PlaceName.WORKPLACE)) useTime((int)( 40 / timeReduce));
        else if(currentLocation.equals(PlaceName.HOME) && destination.equals(PlaceName.STORE)) useTime((int)( 20 / timeReduce));
        else if(currentLocation.equals(PlaceName.HOME) && destination.equals(PlaceName.THEATRE)) useTime((int)( 30 / timeReduce));
        else if(currentLocation.equals(PlaceName.STORE) && destination.equals(PlaceName.SCHOOL)) useTime((int)( 60 / timeReduce));
        else if(currentLocation.equals(PlaceName.STORE) && destination.equals(PlaceName.WORKPLACE)) useTime((int)( 60 / timeReduce));
        else if(currentLocation.equals(PlaceName.STORE) && destination.equals(PlaceName.HOME)) useTime((int)( 20 / timeReduce));
        else if(currentLocation.equals(PlaceName.STORE) && destination.equals(PlaceName.THEATRE)) useTime((int)( 30 / timeReduce));
        else if(currentLocation.equals(PlaceName.THEATRE) && destination.equals(PlaceName.SCHOOL)) useTime((int)( 70 / timeReduce));
        else if(currentLocation.equals(PlaceName.THEATRE) && destination.equals(PlaceName.WORKPLACE)) useTime((int)( 10 / timeReduce));
        else if(currentLocation.equals(PlaceName.THEATRE) && destination.equals(PlaceName.STORE)) useTime((int)( 30 / timeReduce));
        else if(currentLocation.equals(PlaceName.THEATRE) && destination.equals(PlaceName.HOME)) useTime((int)( 30 / timeReduce));
        else if(currentLocation.equals(PlaceName.SCHOOL) && destination.equals(PlaceName.STORE)) useTime((int)( 60 / timeReduce));
        else if(currentLocation.equals(PlaceName.SCHOOL) && destination.equals(PlaceName.WORKPLACE)) useTime((int)( 80 / timeReduce));
        else if(currentLocation.equals(PlaceName.SCHOOL) && destination.equals(PlaceName.HOME)) useTime((int)( 40 / timeReduce));
        else if(currentLocation.equals(PlaceName.SCHOOL) && destination.equals(PlaceName.THEATRE)) useTime((int)( 70 / timeReduce));
        else if(currentLocation.equals(PlaceName.WORKPLACE) && destination.equals(PlaceName.STORE)) useTime((int)( 60 / timeReduce));
        else if(currentLocation.equals(PlaceName.WORKPLACE) && destination.equals(PlaceName.SCHOOL)) useTime((int)( 80 / timeReduce));
        else if(currentLocation.equals(PlaceName.WORKPLACE) && destination.equals(PlaceName.HOME)) useTime((int)( 40 / timeReduce));
        else if(currentLocation.equals(PlaceName.WORKPLACE) && destination.equals(PlaceName.THEATRE)) useTime((int)( 10 / timeReduce));
        setCurrentLocation(destination);
    }

    // Adjust Player Stats

    public void gainStress(int stress){
        stats.setStress(stats.getStress() + stress);
    }

    public void reduceStress(int stress){
        stats.setStress(stats.getStress() - stress);
    }

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
        stats.setMoney(stats.getMoney() + money);
    }

    // Time Management

    private void useTime(int time){
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
        if(!isEat && !isFirstTurn) useTime(120);
        else setEat(false);
    }

    public void endTurn(){
        this.setTimeUsed(0);
        this.setCurrentLocation(PlaceName.HOME);
    }

    /* Win Condition
    1. Mode Short Requirement (10 Rounds)
        - money 1200
        - education 15
        - happiness 500
    2. Mode Medium Requirement (20 Rounds)
        - money 2500
        - education 25
        - happiness 750
    3. Mode Long Requirement (30 Rounds)
        - money 4500
        - education 40
        - happiness 1500
    4. Mode Marathon Requirement (Till one Player meets requirement)
        - money 10000
        - education 82 (learn everything)
        - happiness 4000 */

    public boolean isWin(GameMode gameMode){
        if (gameMode.equals(GameMode.SHORT) &&
                stats.getMoney() >= 1200 &&
                stats.getEducation() >= 15 &&
                stats.getHappiness() >= 500) return true;
        else if(gameMode.equals(GameMode.MEDIUM) &&
                stats.getMoney() >= 2500 &&
                stats.getEducation() >= 25 &&
                stats.getHappiness() >= 750) return true;
        else if(gameMode.equals(GameMode.LONG) &&
                stats.getMoney() >= 4000 &&
                stats.getEducation() >= 40 &&
                stats.getHappiness() >= 1500) return true;
        else return gameMode.equals(GameMode.MARATHON) &&
                    stats.getMoney() >= 10000 &&
                    stats.getEducation() >= 82 &&
                    stats.getHappiness() >= 4000;
    }

    // Getter and Setter

    public void setTransportation(Transportation transportation){
        this.transportation = transportation;
        if(transportation.equals(Transportation.CAR)) setTimeReduce(4);
        else if(transportation.equals(Transportation.BICYCLE)) setTimeReduce(1.5);
        else if(transportation.equals(Transportation.BUS)) setTimeReduce(2);
        else setTimeReduce(1);
    }

    public void setTimeReduce(double timeReduce){
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

    public void setName(String name) {
        this.name = name;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public JobType getJobType() {
        return jobType;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public int getTimeUsed() {
        return timeUsed;
    }

    public void setTimeUsed(int timeUsed) {
        this.timeUsed = timeUsed;
    }

    public double getTimeReduce() {
        return timeReduce;
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
}
