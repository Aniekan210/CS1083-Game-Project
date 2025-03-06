import javafx.scene.layout.*;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.util.Duration;
import javafx.scene.media.AudioClip;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
/**
 * Write a description of class ContinueScreen here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class EndScreen extends StackPane
{
    protected ImageView bg;
    protected Text payoutText;
    protected Pane container;
    protected Timeline timeline;
    protected AudioClip winAudio;
    protected double height;
    protected double width;
    
    public EndScreen(double width, double height)
    {
        this.setPrefWidth(width);
        this.setPrefHeight(height);
        this.setPickOnBounds(false);
        this.height = height;
        this.width = width;
        timeline = new Timeline();
        winAudio = new AudioClip("file:assets/sounds/earn.mp3"); //CHANGE TO EARN.MP3 WHEN YOU AVE TEH AUDIO
        
        bg = new ImageView(new Image("/assets/images/startBg.png"));
        bg.setFitWidth(width);
        bg.setFitHeight(height);
        bg.setOpacity(0);
        bg.setMouseTransparent(true);
        
        container =  new Pane();
        container.setPrefWidth(width);
        container.setPrefHeight(height);
        container.setOpacity(0);
        container.setMouseTransparent(true);
        
        ImageView winTitle = new ImageView(new Image("./assets/images/earned.png"));
        winTitle.setFitWidth(500);
        winTitle.setPreserveRatio(true);
        winTitle.setLayoutX(width/2 - 250);
        winTitle.setLayoutY(height/2 - winTitle.getLayoutBounds().getHeight()/2);
        
        payoutText = new Text();
        payoutText.setFont(Font.font("Comic Sans MS", 70));
        payoutText.setFill(Color.WHITE);
        payoutText.setStroke(Color.BLACK);
        payoutText.setStrokeWidth(3);
        payoutText.setLayoutY(369);
        payoutText.setLayoutX(180);
        
        container.getChildren().addAll(winTitle, payoutText);
        this.getChildren().addAll(bg, container);
    }
    
    public void updateEnd(boolean hasWon, int reward)
    {   
        payoutText.setText(""+reward);
        if (hasWon)
        {
            bg.setMouseTransparent(false);
            container.setMouseTransparent(false);
            timeline.getKeyFrames().clear();
            
            //KeyFrame nothing = new KeyFrame(Duration.millis(300), new KeyValue(bg.opacityProperty(), 0));
            
            KeyFrame startMusic = new KeyFrame(Duration.millis(500), e -> {
                winAudio.play();
            });
            
            KeyFrame show = new KeyFrame(Duration.millis(500), 
                               new KeyValue(bg.opacityProperty(), 1),
                               new KeyValue(container.opacityProperty(), 0)
            );
            
            KeyFrame showUI = new KeyFrame(Duration.millis(1000), new KeyValue(container.opacityProperty(), 1));

            
            timeline.getKeyFrames().addAll(startMusic, show, showUI);
            timeline.play();
        }
        else
        {
            container.setOpacity(0);
            container.setMouseTransparent(true);
            bg.setOpacity(0);
            bg.setMouseTransparent(true);
        }
    }
}
