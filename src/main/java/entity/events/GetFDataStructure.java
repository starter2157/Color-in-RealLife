package entity.events;

import logic.Player;

public class GetFDataStructure extends RandomEvent{

    public GetFDataStructure(){
        super("On Wednesday, there is Grader Quiz 4 for the Data Structure course. " +
                "You got 0/300, and all of the previous quiz scores are just 0/800");
    }

    public void trigger(Player player){
        player.gainStress(2);
    }
}
