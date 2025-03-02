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
    
    public LoseScreen(double width, double height, EventHandler<MouseEvent> event)
    {
        super();
        this.setPrefWidth(width);
        this.setPrefHeight(height);
        this.setPickOnBounds(false);
        
        timeline = new Timeline();

        //Let the entire screen be pitch black once player loses(for now)
        overlay = new Rectangle(width, height, Color.BLACK);
        overlay.setOpacity(0);
        overlay.setMouseTransparent(true);

        //make Reset button to start the game from the beginning 
        resetButton = new Button("RESET");
        resetButton.setOnMouseClicked(event);
        resetButton.setVisible(false);
        
        this.getChildren().addAll(overlay, resetButton);
    }
    
    public void updateLose(boolean hasLost)
    {
        if (hasLost)
        {
            timeline.getKeyFrames().clear();
            
            KeyFrame nothing = new KeyFrame(Duration.millis(600), new KeyValue(overlay.opacityProperty(), 0));
            KeyFrame opacityUp = new KeyFrame(Duration.millis(1100), new KeyValue(overlay.opacityProperty(), 1));
            KeyFrame visible = new KeyFrame(Duration.millis(1100), e -> resetButton.setVisible(true));
            overlay.setMouseTransparent(false);
            
            timeline.getKeyFrames().addAll(nothing, opacityUp, visible);
            timeline.play(); 
        }
        else
        {
            overlay.setOpacity(0);
            overlay.setMouseTransparent(true);
            resetButton.setVisible(false);
        }
    }
}
