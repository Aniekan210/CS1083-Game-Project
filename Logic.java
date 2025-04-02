import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileWriter;
import java.io.File;

/***********************************************
 * This class is the brain of the entire game.
 *
 * @author Aniekan Skye Kings
 * @version Update logic version
 *******************************************/
public class Logic
{
    protected int roundNo;
    protected int rowNo;
    protected String name;
    protected boolean hasLost;
    protected boolean hasWon;
    protected int payout;
    protected int[] payouts;
    protected boolean start;
    protected boolean wonRound;
    
    public Logic()
    {
        reset();
    }
    
    // Mutator methods
    public void incRoundNum() 
    { 
        roundNo++;
        rowNo = 0;
    }
    
    public void incRowNum()
    {
        rowNo++;
    }
    
    public void addPayout(int amount)
    {
        payout += amount;
    }
    
    public void lose()
    {
        hasLost = true;
        payout = 0;
    }
    
    public void win()
    {
        hasWon = true;
    }
    
    public void reset()
    {
        roundNo = 1; // change to 1
        rowNo = 0;
        hasLost = false; // change to false
        hasWon = false;
        payout = 0;
        payouts = new int[]{300, 400, 600};
        start = true;
        wonRound = false;
    }
    
    public void save() 
    {
        String filename = "leaderboard.dat";
        
        File file = new File(filename);

        // Create the file if it doesn't exist
        try {
            file.createNewFile();
            // Open the file in append mode and write data
            try (PrintWriter out = new PrintWriter(new FileWriter(file, true))) {
                out.println(name + " " + roundNo + " " + rowNo + " " + payout);
            } catch (IOException e) {
                System.err.println("Error saving player data: " + e.getMessage());
            }

        } catch (IOException e) {
            System.err.println("An error occurred while creating the file: " + e.getMessage());
        }
    }
    
    public void start()
    {
        start = false;
    }
    
    public void setWonRound(boolean value)
    {
        wonRound = value;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    // Accessor methods
    public int getRoundNum() { return roundNo; }
    public int getRowNum() { return rowNo; }
    public int getPayout() { return payout; }
    public int getRoundPayout() { return payouts[roundNo-1]; }
    public boolean getHasLost() { return hasLost; }
    public boolean getHasWon() { return hasWon; }
    public boolean getStart() { return start; }
    public boolean getWonRound() { return wonRound; }
}
