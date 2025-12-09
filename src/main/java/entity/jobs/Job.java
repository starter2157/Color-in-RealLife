package entity.jobs;

import entity.base.Workable;
import player.Player;

public class Job implements Workable {
    protected int salary;

    public Job (int salary) {
        this.salary = salary;
    }

    public void work (Player player) {
        player.gainMoney(getSalary());
        player.gainStress(1);
    }

    public int getSalary () {
        return salary;
    }
}
