package var_two;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class MontyHall {
    public static Map<String, Long> runSimulations(int numDoors, int iterations, Game.Strategy[] strategies)
    {
        List<GameSession> sessions = IntStream.range(0, iterations)
                .mapToObj(i -> new GameSession(numDoors))
                .collect(toList());

        return Arrays.stream(strategies).parallel()
                .collect(
                        toMap(strategy -> strategy.toString(), strategy -> numberOfWins(sessions, strategy))
                );
    }

    public static Map<String, Long> runSimulations(int numDoors, int iterations)
    {
        return runSimulations(numDoors, iterations, Game.Strategy.values());
    }

    private static long numberOfWins(List<GameSession> sessions, Game.Strategy strategy)
    {
        return sessions.stream()
                .map(session -> new Game(session, strategy))
                .filter(game -> game.playerWon())
                .count();
    }
}
