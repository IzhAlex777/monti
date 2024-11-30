package var_two;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameSession {
    private final List<Door> doors;
    private final int winningDoorId;

    public GameSession(int numDoors)
    {
        Random random = new Random();

        winningDoorId = random.nextInt(numDoors);

        doors = IntStream.range(0, numDoors)
                .mapToObj(Door::new)
                .collect(Collectors.toList());
    }

    public int getWinningDoorId()
    {
        return this.winningDoorId;
    }

    public List<Door> getDoors()
    {
        return new ArrayList<>(this.doors);
    }
}
