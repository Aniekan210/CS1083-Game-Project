
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
        //roundNo = 1;
        roundNo = 2;
        rowNo = 1;
        hasLost = false;
        hasWon = false;
        payout = 0;
    }
    
    // Mutator methods
    public void incRoundNum() 
    { 
        roundNo++;
        rowNo = 1;
        if (roundNo == 4) 
        { 
            roundNo = 3;
        }
    }
    public void incRowNo()
    {
        rowNo++;
        if (rowNo == 4)
        {
            rowNo = 3;
        }
    }
    public void addPayout(int amount)
    {
        payout += amount;
    }
    public void setHasLost(boolean value)
    {
        hasLost = value;
    }
    
    // Accessor methods
    public int getRoundNum() { return roundNo; }
    public int getRowNum() { return rowNo; }
    public int getPayout() { return payout; }
}
