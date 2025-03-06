
/**
 * Write a description of class Player here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Player {
    private final String name;
    private final int roundNum;
    private final int rowNum;
    private final int vBucksWon;

    public Player(String name, int roundNum, int rowNum, int vBucksWon) {
        this.name = name;
        this.roundNum = roundNum;
        this.rowNum = rowNum;
        this.vBucksWon = vBucksWon;
    }

    public String getName() { return name; }
    public int getRoundNum() { return roundNum; }
    public int getRowNum() { return rowNum; }
    public int getVBucksWon() { return vBucksWon; }
}
