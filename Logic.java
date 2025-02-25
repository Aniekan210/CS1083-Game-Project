
/**
 * Write a description of class Player here.
 *
 * @author Anieken Ekarika
 * @version (a version number or a date)
 */
public class Logic
{
    protected int roundNo;
    protected int rowNo;
    protected boolean hasLost;
    protected boolean hasWon;
    protected int payout;
    
    public Logic()
    {
        reset();
    }
    
    // Mutator methods
    public void incRoundNum() 
    { 
        roundNo++;
        rowNo = 0;
        if (roundNo == 4) 
        { 
            hasWon = true;
            reset();
        }
    }
    
    public void incRowNum()
    {
        rowNo++;
        if (rowNo == 4)
        {
            rowNo = 0;
            incRoundNum();
        }
    }
    
    public void addPayout(int amount)
    {
        payout += amount;
    }
    
    public void lose()
    {
        hasLost = true;
    }
    
    public void reset()
    {
        roundNo = 1;
        rowNo = 0;
        hasLost = false;
        hasWon = false;
        payout = 0;
    }
    
    // Accessor methods
    public int getRoundNum() { return roundNo; }
    public int getRowNum() { return rowNo; }
    public int getPayout() { return payout; }
}
