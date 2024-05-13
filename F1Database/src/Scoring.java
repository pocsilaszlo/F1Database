/**
 * Enum representing the scoring methods.
 */
public enum Scoring {
    CLASSIC(new int[]{10, 6, 4, 3, 2, 1, 0, 0, 0, 0}),
    MODERN(new int[]{10, 8, 6, 5, 4, 3, 2, 1, 0, 0}),
    NEW(new int[]{25, 18, 15, 12, 10, 8, 6, 4, 2, 1}),
    PRESENT(new int[]{25, 18, 15, 12, 10, 8, 6, 4, 2, 1});

    /**
     * The array of scores from position 1 to 10.
     */
    private final int[] points;

    /**
     * Constructor of Scoring enum.
     * @param points The array of the points.
     */
    Scoring(int[] points) {
        this.points = points;
    }

    /**
     * Getter of the points field.
     * @return Returns the points of the given scoring method.
     */
    public int[] getPoints() {
        return points;
    }
}
