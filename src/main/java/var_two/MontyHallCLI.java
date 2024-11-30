package var_two;
import java.util.Map;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
public class MontyHallCLI {
    private static Options createOptions() {
        Options options = new Options();

        options.addOption("d", "doors", true, "Number of doors, must be 3 or more");
        options.addOption("i", "iterations", true, "Number of iterations");
        options.addOption("h", "help", false, "Print this message");

        return options;
    }

    private static void run(int numDoors, int iterations)
    {
        System.out.printf("\nRunning simulation... (%d doors, %d iterations)\n", numDoors, iterations);

        Map<String, Long> result = MontyHall.runSimulations(numDoors, iterations);

        System.out.println("\nResults:\n");

        result.entrySet().stream()
                .sorted(
                        Map.Entry.comparingByValue(
                                (v1, v2) -> v2.compareTo(v1)
                        )
                ).forEachOrdered( entry ->
                        System.out.printf("%s\t(%d/%d)\n", entry.getKey(), entry.getValue(), iterations)
                );
    }

    public static void main(String[] args) throws ParseException
    {

        Options opts = createOptions();
        String command = "java -jar monty-hall-1.0-SNAPSHOT-jar-with-dependencies.jar";

        CommandLineParser parser = new BasicParser();
        CommandLine cmd = parser.parse(opts, args);

        HelpFormatter helpFormatter = new HelpFormatter();

        int doors = Integer.parseInt(cmd.getOptionValue("d", "10"));
        int iterations = Integer.parseInt(cmd.getOptionValue("i", "100"));

        if(cmd.hasOption("h"))
        {
            helpFormatter.printHelp(command, opts);
        }
        else if(doors < 3)
        {
            System.out.println("ERROR: Number of doors must be 3 or more.\n");
            helpFormatter.printHelp(command, opts);
        }
        else
        {
            run(doors, iterations);
        }
    }
}
