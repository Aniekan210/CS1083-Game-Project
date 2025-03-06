import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.util.Duration;
/**
 * Write a description of class FlashBang here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class FlashBang extends Rectangle
{
    protected Timeline timeline;
    
    public FlashBang(double width, double height)
    {
        super(width, height, Color.WHITE);
        this.setMouseTransparent(true);
        this.setOpacity(0);
        timeline = new Timeline();
    }
    
    public void flash()
    {
        timeline.getKeyFrames().clear();
        
        KeyFrame flash = new KeyFrame(Duration.millis(200),new KeyValue(this.opacityProperty(), 1));
        KeyFrame dim = new KeyFrame(Duration.millis(800),new KeyValue(this.opacityProperty(), 0));
        
        timeline.getKeyFrames().addAll(flash, dim);
        timeline.play();
    }
}