/***********************************************
 * This class is the brain of the entire game.
 *
 * @author Anieken Ekarika
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
        roundNo = 3;
        rowNo = 0;
        hasLost = false;
        hasWon = false;
        payout = 0;
        payouts = new int[]{500, 600, 800};
        start = true;
    }
    
    public void setStart()
    {
        start = false;
    }
    
    // Accessor methods
    public int getRoundNum() { return roundNo; }
    public int getRowNum() { return rowNo; }
    public int getPayout() { return payout; }
    public int getRoundPayout() { return payouts[roundNo-1]; }
    public boolean getHasLost() { return hasLost; }
    public boolean getHasWon() { return hasWon; }
}
