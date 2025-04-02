import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.geometry.Bounds;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.layout.*;

/*************************************************
 * Class to create a singular tile
 *
 * @author Group Aniekan, Skye and Kings
 ************************************************/
public class Tile extends StackPane
{
    protected int rowNum;
    protected int rowPos;
    protected int roundNum;
    protected ImageView tileImg;
    protected Text tilePay;
    protected int pay;
    protected double breakRisk;
    protected boolean hasBroken;
    protected AudioClip brokenSound;
    protected AudioClip moneySound;
    protected boolean shouldReveal;

    public Tile(int roundNum, int rowNum, int rowPos, int pay, double breakRisk) 
    {
        super();
        this.rowNum = rowNum;
        this.rowPos = rowPos;
        this.roundNum = roundNum;
        this.breakRisk = breakRisk;
        this.pay = pay;
        shouldReveal = true;
        String state = pay > 0 ? "money" : "regular";
        brokenSound = new AudioClip("file:assets/sounds/glassBreak.mp3");
        moneySound = new AudioClip("file:assets/sounds/money.mp3");
        
        tileImg = new ImageView();
        tilePay = new Text(""+pay);
        tilePay.setFont(Font.font("Comic Sans MS", 42));
        tilePay.setFill(Color.WHITE);
        tilePay.setStroke(Color.BLACK);
        tilePay.setStrokeWidth(2.2);
        tilePay.setOpacity(0);
        
        setImage(state);
        this.getChildren().addAll(tileImg, tilePay);
    }     

    public void setImage(String state) 
    {
        String imgUrl = String.format("./assets/images/round_%d/%d_%s.png", roundNum, rowPos, state);
        tileImg.setImage(new Image(imgUrl));
        tileImg.setPreserveRatio(true);
        tileImg.setFitWidth(200);
        this.setPickOnBounds(false);
    }
    
    public void reveal()
    {
        if(breakRisk == 1)
        {
            setImage("broken");
        }
        else if (pay > 0)
        {
            if (shouldReveal)
            {
                tilePay.setOpacity(1);
            }
            setImage("regular");
        }
    }
    
    public void notReveal()
    {
        shouldReveal = false;
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
        Bounds bounds = tileImg.localToScene(tileImg.getBoundsInLocal());
        return bounds.getMinX() + bounds.getWidth() / 2;
    }

    public double getCenterY() 
    {
        Bounds bounds = tileImg.localToScene(tileImg.getBoundsInLocal());
        return bounds.getMinY() + bounds.getHeight() / 2;
    }
    
    public int getRowNum() { return rowNum; }
    
    public int getPayout() { return pay; }
}
