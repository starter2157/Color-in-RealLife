package entity.jobs;

import entity.base.Workable;
import player.Player;

public class Job implements Workable {
    private final int salary;

    public Job (int salary) {
        this.salary = salary;
    }

    public void work (Player player) {
        player.gainMoney(getSalary());
    }

    public int getSalary () {
        return salary;
    }
}
