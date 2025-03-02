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
/**
 * Write a description of class LoseScreen here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class LoseScreen extends StackPane
{
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
        
        overlay = new Rectangle(width, height, Color.BLACK);
        overlay.setOpacity(0);
        overlay.setMouseTransparent(true);
        
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
            
            KeyFrame nothing = new KeyFrame(Duration.millis(700), new KeyValue(overlay.opacityProperty(), 0));
            KeyFrame opacityUp = new KeyFrame(Duration.millis(1200), new KeyValue(overlay.opacityProperty(), 1));
            KeyFrame visible = new KeyFrame(Duration.millis(1200), e -> resetButton.setVisible(true));
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
