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
    }
    
    public void win()
    {
        hasWon = true;
    }
    
    public void reset()
    {
        roundNo = 2; // change to 1
        rowNo = 0;
        hasLost = false; // change to false
        hasWon = false;
        payout = 0;
        payouts = new int[]{500, 600, 800};
        start = true; // change to true
        wonRound = false;
    }
    
    public void start()
    {
        start = false;
    }
    
    public void setWonRound(boolean value)
    {
        wonRound = value;
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
