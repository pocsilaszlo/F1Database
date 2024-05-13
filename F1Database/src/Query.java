import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class is responsible for storing and executing queries.
 */
public class Query {

    /**
     * The year of the competition.
     */
    private final int year;
    /**
     * The week of the race.
     */
    private final Integer raceId;
    /**
     * The HashMap responsible for storing the competitors' points.
     */
    private final HashMap<String, Double> table;

    /**
     * The constructor of the Query class.
     * @param year The year of the competition.
     */
    public Query(int year) {
        this.year = year;
        this.raceId = null;
        this.table = new HashMap<>();
    }

    /**
     * The constructor of the Query class.
     * @param year The year of the competition.
     * @param raceId The race week of the race.
     */
    public Query(int year, int raceId) {
        this.year = year;
        this.raceId = raceId;
        this.table = new HashMap<>();
    }

    /**
     * Prints the current F1 table according to the given parameters.
     * @param races The list of the races in the database.
     * @param scoring The scoring method.
     */
    public void writePoints(List<Race> races, Scoring scoring) {
        List<Race> r = filterRaces(races);
        countPoints(r, scoring);
        Map<String, Double> sortedMap = sortTable();

        System.out.println("\nThe " + year + " F1 standings" +
                (raceId == null ? "" : " after race " + raceId) +":\n" +
                "---------------------------------------------");
        sortedMap.forEach((k, v) -> System.out.println(k  + " - " + v));
        System.out.println("---------------------------------------------\n");
    }

    /**
     * Filter competitions by year or, where applicable, by week.
     * @param races The list of the races in the database.
     * @return Returns the stream of filtered races.
     */
    private List<Race> filterRaces(List<Race> races) {
        return races.stream()
                .filter(race -> race.getYear() == year && (this.raceId == null || race.getRaceId() <= raceId))
                .collect(Collectors.toList());
    }

    /**
     * Calculate bonus points according to scoring method.
     * @param scoring The scoring method.
     * @param result The competitor's result.
     * @param race The given race.
     * @param position The position of the competitor in the race.
     * @return Returns 1 if the scoring is PRESENT and the competitor was the fastest in the given race, otherwise 0.
     */
    private double countBonus(Scoring scoring, Result result, Race race, int position) {
        double bonus = 0;
        if (scoring == Scoring.PRESENT && race.getFastest() != null && result.getName().equals(race.getFastest().getName()))
            bonus = 1;
        return race.getPointMultiplier() * (scoring.getPoints()[position - 1] + bonus);
    }

    /**
     * Calculates the competitors points and saves them in the repository.
     * @param r The given race.
     * @param scoring The scoring method.
     */
    private void countPoints(List<Race> r, Scoring scoring) {
        r.forEach(race -> race.getResults().forEach((position, result) -> {
            if (!table.containsKey(result.getName())) {
                if (position < 11) {
                    table.put(result.getName(), countBonus(scoring, result, race, position));
                } else {
                    table.put(result.getName(), 0.);
                }
            }
            else if (position < 11) {
                double val = table.get(result.getName());
                table.put(result.getName(), val + countBonus(scoring, result, race, position));
            }
        }));
    }

    /**
     * Used to sort the data in descending order.
     * @return Returns the map of the ordered data.
     */
    private Map<String, Double> sortTable() {
        return table.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(LinkedHashMap::new, (map, entry) -> map.put(entry.getKey(), entry.getValue()), LinkedHashMap::putAll);
    }
}
