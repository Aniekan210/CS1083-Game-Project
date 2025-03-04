import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.geometry.Bounds;
import javafx.scene.media.AudioClip;

/*************************************************
 * Class to create a singular tile
 *
 * @author Group Aniekan, Skye and Kings
 ************************************************/
public class Tile extends ImageView
{
    protected int rowNum;
    protected int rowPos;
    protected int roundNum;
    protected int pay;
    protected double breakRisk;
    protected boolean hasBroken;
    protected AudioClip brokenSound;
    protected AudioClip moneySound;

    public Tile(int roundNum, int rowNum, int rowPos, int pay, double breakRisk) 
    {
        super();
        this.rowNum = rowNum;
        this.rowPos = rowPos;
        this.roundNum = roundNum;
        this.breakRisk = breakRisk;
        this.pay = pay;
        String state = pay > 0 ? "money" : "regular";
        setImage(state);
        brokenSound = new AudioClip("file:assets/sounds/glassBreak.mp3");
        moneySound = new AudioClip("file:assets/sounds/money.mp3");
    }     

    public void setImage(String state) 
    {
        String imgUrl = String.format("./assets/images/round_%d/%d_%s.png", roundNum, rowPos, state);
        super.setImage(new Image(imgUrl));
        super.setPreserveRatio(true);
        super.setFitWidth(200);
        this.setPickOnBounds(false);
    }
    
    public void playBreak()
    {
        brokenSound.play(0.4);
    }
    
    public void playMoney()
    {
        moneySound.play(0.19);
    }

    // Accessor methods
    public double getBreakRisk() 
    {
        return breakRisk;
    }

    public double getCenterX() 
    {
        Bounds bounds = this.localToScene(this.getBoundsInLocal());
        return bounds.getMinX() + bounds.getWidth() / 2;
    }

    public double getCenterY() 
    {
        Bounds bounds = this.localToScene(this.getBoundsInLocal());
        return bounds.getMinY() + bounds.getHeight() / 2;
    }
    
    public int getRowNum() { return rowNum; }
    
    public int getPayout() { return pay; }
}
