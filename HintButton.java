import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
/***************************************************************
 * This is just a button that show the the rules and how to play
 *
 * @author Aniekan
 * @version new feature
 ***************************************************************/
public class HintButton extends ImageView
{
    protected boolean isOff;
    protected Image offState;
    protected Image onState;
    
    public HintButton()
    {        
        super();
        
        onState = new Image("./assets/images/light_on.png");
        offState = new Image("./assets/images/light_off.png");
        
        isOff = false;
        this.setImage(onState);
        this.setPreserveRatio(true);
        this.setFitWidth(35);
    }
    
    public void setXByPivot(double x)
    {
        double pivotX = this.getLayoutBounds().getWidth();
        this.setLayoutX(x - pivotX);
    }
    
    public void toggle()
    {
        if (isOff)
        {
            isOff = false;
            this.setImage(onState);
        }
        else
        {
            isOff = true;
            this.setImage(offState);
        }
    }
}
