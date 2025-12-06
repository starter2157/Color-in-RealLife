package entity.jobs;

import entity.base.Workable;
import logic.Player;

public class Job implements Workable {
    protected JobType jobType;

    public Job(JobType jobType){
        this.jobType = jobType;
    }

    public void work(Player player){

    }

    public JobType getJobType() {
        return jobType;
    }
}
