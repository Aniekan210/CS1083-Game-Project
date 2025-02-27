import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
/**
 * Write a description of class Instructions here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class InstructionsBar extends ImageView
{        
    public InstructionsBar()
    {        
        super(new Image("./assets/images/instruction.png"));
        this.setPreserveRatio(true);
        this.setFitWidth(300);
    }
    
    public void setXByPivot(double x)
    {
        double pivotX = this.getLayoutBounds().getWidth() / 2;
        this.setLayoutX(x - pivotX);
    }
}
