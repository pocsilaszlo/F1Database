import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class is responsible for registering the tournaments and retrieving the data.
 */
public class Races {

    /**
     * The list of races in the database.
     */
    private final ArrayList<Race> races;
    /**
     * Object to store the current race.
     */
    private Race currentRace;
    /**
     * Object to store the current query.
     */
    private Query currentQuery;

    /**
     * Constructor of the Race class.
     */
    public Races() {
        this.races = new ArrayList<>();
        readFromFile();
    }

    /**
     * Reads all lines of input-hf.txt and executes the commands line by line until EXIT is found or the file is finished.
     */
    private void readFromFile() {
        String fileName = "textfiles/input-hf.txt";
        try (Stream<String> stream = Files.lines(Paths.get(fileName), Charset.forName("windows-1250"))) {
            List<String> lines = stream.collect(Collectors.toList());
            String line;
            int i = 0;
            do {
                line = lines.get(i);
                executeCommand(line);
                i++;
            } while (!line.equals("EXIT") && i < lines.size());
        } catch (IOException e) {
            System.out.println("Failed to open file! ("+ fileName +")");
        }
    }

    /**
     * Executes the given commands and handles exceptions.
     * @param line A line in the file containing the following: command word - associated arguments (separated by ;)
     *
     */
    public void executeCommand(String line) {
        String[] command = line.split(";");
        try {
            switch (command[0]) {
                case "RACE":
                    if (command.length != 5)
                        throw new IllegalArgumentException("The RACE command must have 4 arguments! (RACE;year;location;raceID;pointMultiplier)");

                    race(Integer.parseInt(command[1]), command[2], Integer.parseInt(command[3]), Double.parseDouble(command[4]));
                    break;
                case "RESULT":
                    if (command.length != 4)
                        throw new IllegalArgumentException("The RESULT command must have 3 arguments! (RESULT;position;name;teamName)");

                    result(Integer.parseInt(command[1]), command[2], command[3]);
                    break;
                case "FASTEST":
                    if (command.length != 3)
                        throw new IllegalArgumentException("The FASTEST command must have 2 arguments! (FASTEST;name;teamName");

                    fastest(command[1], command[2]);
                    break;
                case "FINISH":
                    if (command.length != 1)
                        throw new IllegalArgumentException("The FINISH command must have no arguments!");

                    finish();
                    break;
                case "QUERY":
                    if (command.length < 2 || command.length > 3)
                        throw new IllegalArgumentException("The QUERY command must have 1 or 2 arguments! (QUERY;year OR QUERY;year;raceId)");

                    if (command.length == 2) {
                        query(Integer.parseInt(command[1]));
                    } else {
                        query(Integer.parseInt(command[1]), Integer.parseInt(command[2]));
                    }
                    break;
                case "POINT":
                    if (command.length != 2)
                        throw new IllegalArgumentException("The POINT command must have 2 arguments! (POINT;scoring)");

                    point(command[1]);
                    break;
                case "":
                case "EXIT":
                    break;
                default:
                    throw new IllegalArgumentException("Wrong command! (" + line + ")");
            }
        }catch (NumberFormatException e) {
            System.out.println("The given arguments are not correct! (" + e.getMessage() + ")");
        }
        catch (NoSuchElementException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * The implementation of the RACE command. Used to add a race to the database. After that you can only write FASTEST, RESULT or FINISH commands.
     * @param year The year of the race.
     * @param location The name of the Grand Prix.
     * @param raceId The week of the race in the year.
     * @param pointMultiplier The point multiplier. It must be 0, 0.5, 1 or 2.
     */
    public void race(int year, String location, int raceId, double pointMultiplier) {
        if (currentRace != null)  throw new IllegalArgumentException("The previous RACE was not finished!");
        if (raceId < 1) throw new IllegalArgumentException("The race id must be greater than 0!");
        if (!Arrays.asList(new Double[]{0., 0.5, 1., 2.}).contains(pointMultiplier))
            throw new IllegalArgumentException("Point multiplier must be 0, 0.5, 1 or 2!");

        Race race = new Race(year, location,raceId, pointMultiplier);
        races.add(race);
        currentRace = race;
        System.out.println("Race started!");
    }

    /**
     * The implementation of the RESULT command. Used to add a competitor's result to the race in the database.  Can only be executed between RACE and FINISH.
     * @param position The position of the competitor.
     * @param name The name of the competitor.
     * @param teamName The name of the competitor's team.
     */
    public void result(int position, String name, String teamName) {
        if (currentRace == null) throw new IllegalArgumentException("RESULT must be after RACE command!");
        if (position < 1) throw  new IllegalArgumentException("Position must be greater than 0!");

        currentRace.addResult(position, new Result(name, teamName));
        System.out.println("#" + position + " " + name + " ("+ teamName + ") added!");
    }

    /**
     * Implementation of the FASTEST command. Used to enter the competitor with the fastest lap. Can only be executed between RACE and FINISH.
     * @param name The name of the competitor.
     * @param teamName The name of the competitor's team.
     */
    public void fastest(String name, String teamName) {
        if (currentRace == null) throw  new IllegalArgumentException("FASTEST must be after RACE command!");

        List<Result> result =
                currentRace.getResults().values().stream()
                        .filter(res -> res.getName().equals(name) && res.getTeamName().equals(teamName))
                        .collect(Collectors.toList());

        if (result.isEmpty()) throw new NoSuchElementException("No participant found!");

        currentRace.setFastest(result.get(0));
        System.out.println("The fastest is: " + result.get(0).getName() + "!");
    }

    /**
     * The implementation of the FINISH command. Used to complete the RACE input.
     * Can only be executed after a RACE command, if there has been no FINISH command since, and at least 10 RESULT commands have been issued.
     */
    public void finish() {
        if (currentRace == null) throw new IllegalArgumentException("FINISH must be after a RACE command!");
        if (currentRace.getResults().size() < 10)  throw new IllegalArgumentException("Before FINISH must be minimum 10 RESULT command!");

        currentRace = null;
        System.out.println("Race finished!\n");
    }

    /**
     * Checks if the query command can be executed for the given year. If not, throws an exception.
     * @param year The year of the competition.
     */
    private void checkCurrentQuery(int year) {
        if (currentQuery != null)
            throw new IllegalArgumentException("The previous QUERY requires a POINT command! (POINT;CLASSIC/MODERN/NEW/PRESENT)");
        if (races.stream().noneMatch(race -> race.getYear() == year))
            throw new NoSuchElementException("The given year is not in the database! (" + year + ")");
    }

    /**
     * Checks if the query command can be executed for the given race. If not, throws an exception.
     * @param year The year of the competition.
     * @param raceId The week of the last race.
     */
    private void checkCurrentQuery(int year, int raceId) {
        checkCurrentQuery(year);
        int max = Integer.MIN_VALUE;
        Optional<Race> maxRace = races.stream().filter(race -> race.getYear() == year).max(Comparator.comparingInt(Race::getRaceId));
        if (maxRace.isPresent()) max = maxRace.get().getRaceId();
        if (raceId < 1) throw new IllegalArgumentException("The race id must be greater than 0!");
        if (raceId > max) throw new IllegalArgumentException("The id of the last race is " + max + "!");
    }

    /**
     * Implementation of QUERY command. Used to query the annual F1 table.
     * After this command, only the POINT command can be issued.
     * @param year The year of the competition.
     */
    public void query(int year) {
        checkCurrentQuery(year);
        currentQuery = new Query(year);
    }

    /**
     * Implementation of QUERY command. Used to query the F1 table after a given race.
     * After this command, only the POINT command can be issued.
     * @param year The year of the competition.
     * @param raceId The week of the last race.
     */
    public void query(int year, int raceId) {
        checkCurrentQuery(year, raceId);
        currentQuery = new Query(year, raceId);
    }

    /**
     * Implementation of the POINT command. Sets the scoring format and then displays the data based on the previous query.
     * It can only be executed after a QUERY command, if there has been no associated POINT command.
     * @param scoring The method of scoring. It must be CLASSIC, MODERN, NEW or PRESENT.
     */
    public void point(String scoring) {
        if (currentQuery == null) throw new IllegalArgumentException("POINT must be after QUERY command!");

        Scoring s = Scoring.valueOf(scoring);
        currentQuery.writePoints(races, s);
        currentQuery = null;
    }
}
