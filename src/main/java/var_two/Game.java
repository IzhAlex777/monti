package var_two;

import java.util.List;

public class Game {
    public enum Strategy {
        ALWAYS_SWITCH, NO_SWITCH, SWITCH_ON_LAST
    }

    private final int winningDoorId;
    private final List<Door> doors;
    private int selectedDoorId = -1;

    public Game(GameSession session, Strategy strategy) {
        this.doors = session.getDoors();
        this.winningDoorId = session.getWinningDoorId();
        play(strategy);
    }

    public boolean playerWon()
    {
        return this.selectedDoorId == this.winningDoorId;
    }

    private void play(Strategy strategy){
        selectedDoorId = selectDoor();

        boolean isDone = false;
        while(!isDone)
        {
            openDoor();

            if(strategy == Strategy.ALWAYS_SWITCH)
                selectedDoorId = selectDoor();
            else if(strategy == Strategy.SWITCH_ON_LAST && this.doors.size() == 3)
                selectedDoorId = selectDoor();

            isDone = this.doors.size() == 2; // no choice left when two doors are left
        }
    }

    private int selectDoor(){
        Door door = this.doors.stream()
                .filter(d -> d.getId() != this.selectedDoorId)
                .findAny().get();
        return door.getId(); // non-existent id if null
    }

    private void openDoor(){
        Door door = this.doors.stream()
                .filter(d -> d.getId() != this.winningDoorId && d.getId() != this.selectedDoorId)
                .findAny().get();

        this.doors.remove(door); // removal signifies opening the door
    }
}
