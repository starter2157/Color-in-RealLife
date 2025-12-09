package player;

import entity.base.GameMode;
import logic.GameState;
import logic.TurnSystem;

public class Stats {
    private int education;
    private int happiness;
    private int money;
    private int pointSummation;

    // Stats Initialize

    public Stats(){
        this.education = 0;
        this.happiness = 0;
        this.money = 200;
        this.pointSummation =200;
    }

    // Getter and Setter

    public void setPointSummation(GameMode gameMode) {
        pointSummation += Math.min(getMoney(), TurnSystem.getMaxMoney(gameMode));
        pointSummation += Math.min(getHappiness(), TurnSystem.getMaxHappiness(gameMode));
        pointSummation += Math.min(getEducation(), TurnSystem.getMaxEducation(gameMode)) * 8;
    }

    public int getPointSummation(){
        return pointSummation;
    }

    public int getEducation() {
        return education;
    }

    public void setEducation(int education) {
        this.education = Math.max(0, education);
    }

    public int getHappiness() {
        return happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = Math.max(0, happiness);
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = Math.max(0, money);
    }

}
