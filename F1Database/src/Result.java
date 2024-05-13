import java.util.Objects;

/**
 * Represents the results of a competitor.
 */
public class Result {

    /**
     * The name of the competitor.
     */
    private final String name;
    /**
     * The name of the competitor's team.
     */
    private final String teamName;

    /**
     * Constructor of the Result class.
     * @param name The name of the competitor.
     * @param teamName The name of the competitor's team.
     */
    public Result(String name, String teamName) {
        this.name = name;
        this.teamName = teamName;
    }

    /**
     * Getter of the name field.
     * @return Returns the competitor's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter of the teamName field.
     * @return Returns the name of competitor's team.
     */
    public String getTeamName() {
        return teamName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;
        return Objects.equals(name, result.name) && Objects.equals(teamName, result.teamName);
    }


    @Override
    public int hashCode() {
        return Objects.hash(name, teamName);
    }
}
