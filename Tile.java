import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.geometry.Bounds;

/*************************************************
 * Class to create a singular tile
 *
 * @author CS Group Aniekan
 ************************************************/
public class Tile extends ImageView {
    protected int rowNum;
    protected double breakRisk;
    protected boolean hasBroken;

    public Tile(int roundNum, int rowNum, int rowPos, String state, double breakRisk) 
    {
        super();
        this.rowNum = rowNum;
        this.breakRisk = breakRisk;
        setImage(state, rowPos, roundNum);
    }

    public void setImage(String state, int rowPos, int roundNum) 
    {
        String imgUrl = String.format("./assets/images/round_%d/%d_%s.png", roundNum, rowPos, state);
        super.setImage(new Image(imgUrl));
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
}
