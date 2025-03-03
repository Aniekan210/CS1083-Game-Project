import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.util.Duration;
import javafx.scene.media.AudioClip;

/**********************************************
 * The screen output for anyone who loses
 *
 * @author Group Project (Aniekan, Skye, Kings)
 * @version Latest version of losing screen
 *********************************************/
public class LoseScreen extends StackPane
{
    //Instance data
    protected Rectangle overlay;
    protected Button resetButton;
    protected Timeline timeline;
    protected AudioClip backgroundMusic;
    
    public LoseScreen(double width, double height, AudioClip backgroundMusic)
    {
        super();
        this.setPrefWidth(width);
        this.setPrefHeight(height);
        this.setPickOnBounds(false);
        this.backgroundMusic = backgroundMusic;
        
        timeline = new Timeline();

        //Let the entire screen be pitch black once player loses(for now)
        overlay = new Rectangle(width, height, Color.BLACK);
        overlay.setOpacity(0);
        overlay.setMouseTransparent(true);
        
        this.getChildren().addAll(overlay);
    }
    
    public void updateLose(boolean hasLost)
    {
        if (hasLost)
        {
            timeline.getKeyFrames().clear();
            
            KeyFrame nothing = new KeyFrame(Duration.millis(650), new KeyValue(overlay.opacityProperty(), 0));
            KeyFrame opacityUp = new KeyFrame(Duration.millis(1200), new KeyValue(overlay.opacityProperty(), 1));
            KeyFrame stopMusic = new KeyFrame(Duration.millis(1200), e -> backgroundMusic.stop());
            overlay.setMouseTransparent(false);
            
            timeline.getKeyFrames().addAll(nothing, opacityUp, stopMusic);
            timeline.play(); 
        }
        else
        {
            overlay.setOpacity(0);
            overlay.setMouseTransparent(true);
        }
    }
}
