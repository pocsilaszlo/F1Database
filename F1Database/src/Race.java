import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * It represents a race in the database.
 */
public class Race {

    /**
     * The year of the race.
     */
    private final int year;
    /**
     * The name of the Grand Prix.
     */
    private final String location;
    /**
     * The week of the competition.
     */
    private final int raceId;
    /**
     * The point multiplier.
     */
    private final double pointMultiplier;
    /**
     * The map of results.
     */
    private final LinkedHashMap<Integer, Result> results;
    /**
     * Object to store the fastest participant.
     */
    private Result fastest;

    /**
     * The constructor of the Race class.
     * @param year The year of the race.
     * @param location The name of the Grand Prix.
     * @param raceId The week of the competition.
     * @param pointMultiplier The point multiplier.
     */
    public Race(int year, String location, int raceId, double pointMultiplier) {
        this.year = year;
        this.location = location;
        this.raceId = raceId;
        this.pointMultiplier = pointMultiplier;
        this.results = new LinkedHashMap<>();
    }

    /**
     * Getter of year field.
     * @return Returns the year of the race.
     */
    public int getYear() {
        return year;
    }

    /**
     * Getter of location field.
     * @return Returns the name of the Grand Prix.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Getter of raceId field.
     * @return Returns the week of the competition.
     */
    public int getRaceId() {
        return raceId;
    }

    /**
     * Getter of pointMultiplier field.
     * @return Returns the point multiplier..
     */
    public double getPointMultiplier() {
        return pointMultiplier;
    }

    /**
     * Getter of results field.
     * @return Returns the map of results.
     */
    public HashMap<Integer, Result> getResults() {
        return new HashMap<>(results);
    }

    /**
     * Getter of fastest field.
     * @return Returns the fastest competitor.
     */
    public Result getFastest() {
        return fastest;
    }

    /**
     * Adds a record to the results.
     * @param position The position of the competitor.
     * @param result The results of the competitor.
     */
    public void addResult(Integer position, Result result) {
        this.results.put(position, result);
    }

    /**
     * Setter of the fastest field.
     * @param fastest The new value of the fastest field.
     */
    public void setFastest(Result fastest) {
        this.fastest = fastest;
    }
}
