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
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

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
    protected ImageView character;
    protected Pane animationPane;
    protected Button resetButton;
    protected Timeline timeline;
    protected AudioClip backgroundMusic;
    protected AudioClip scream;
    protected AudioClip lose;
    protected double height;
    protected double width;
    protected double charHeight;
    protected ImageView loseTitle;
    
    public LoseScreen(double width, double height, AudioClip backgroundMusic)
    {
        super();
        this.setPrefWidth(width);
        this.setPrefHeight(height);
        this.setPickOnBounds(false);
        this.backgroundMusic = backgroundMusic;
        this.height = height;
        this.width = width;
        charHeight = 350;
        
        timeline = new Timeline();
        scream = new AudioClip("file:assets/sounds/scream.mp3");
        lose = new AudioClip("file:assets/sounds/lose.mp3");
        
        //Let the entire screen be pitch black once player loses(for now)
        overlay = new Rectangle(width, height, Color.BLACK);
        overlay.setOpacity(0);
        overlay.setMouseTransparent(true);
        
        animationPane = new Pane();
        animationPane.setPrefWidth(width);
        animationPane.setPrefHeight(height);
        animationPane.setMouseTransparent(true);
        animationPane.setVisible(false);
        
        character = new ImageView(new Image("./assets/images/falling.png"));
        character.setFitHeight(charHeight);
        character.setPreserveRatio(true);
        character.setLayoutX(width/2 - character.getLayoutBounds().getWidth()/2);
        character.setLayoutY(-charHeight - 10);
        
        
        loseTitle = new ImageView(new Image("./assets/images/you_lose.png"));
        loseTitle.setFitWidth(350);
        loseTitle.setPreserveRatio(true);
        loseTitle.setLayoutX(width/2 - 175);
        loseTitle.setLayoutY(height/2 - loseTitle.getLayoutBounds().getHeight()/2);
        loseTitle.setScaleX(0);
        loseTitle.setScaleY(0);
        
        animationPane.getChildren().addAll(character, loseTitle);
        this.getChildren().addAll(overlay, animationPane);
    }
    
    public void updateLose(boolean hasLost)
    {
        if (hasLost)
        {
            animationPane.setMouseTransparent(false);
            animationPane.setVisible(true);
            overlay.setMouseTransparent(false);
            character.setOpacity(1);
            
            timeline.getKeyFrames().clear();
            
            
            KeyFrame nothing = new KeyFrame(Duration.millis(650), new KeyValue(overlay.opacityProperty(), 0));
            KeyFrame opacityUp = new KeyFrame(Duration.millis(1200), new KeyValue(overlay.opacityProperty(), 1));
            KeyFrame stopMusic = new KeyFrame(Duration.millis(1200), e -> backgroundMusic.stop());

            // character animations
            KeyFrame noFall = new KeyFrame(Duration.millis(1200), new KeyValue(character.translateYProperty(), -charHeight - 10));
            KeyFrame playScream = new KeyFrame(Duration.millis(1200), e -> scream.play(1));
            KeyFrame fall = new KeyFrame(Duration.millis(2000), new KeyValue(character.translateYProperty(), height + charHeight));
            KeyFrame dissapear = new KeyFrame(Duration.millis(2000), e -> character.setOpacity(0));
            KeyFrame goBack = new KeyFrame(Duration.millis(2100), new KeyValue(character.translateYProperty(), -charHeight - 10));
            
            // title key frames
            KeyFrame noShow = new KeyFrame(Duration.millis(2200), 
                              new KeyValue(loseTitle.scaleXProperty(), 0),
                              new KeyValue(loseTitle.scaleYProperty(), 0)
            );
            KeyFrame playLose = new KeyFrame(Duration.millis(2200), e -> lose.play(1));
            KeyFrame show = new KeyFrame(Duration.millis(3200), 
                              new KeyValue(loseTitle.scaleXProperty(), 1),
                              new KeyValue(loseTitle.scaleYProperty(), 1)
            );
            
            timeline.getKeyFrames().addAll(nothing, opacityUp, stopMusic, noFall, playScream, fall, dissapear, goBack, noShow, playLose, show);
            timeline.setAutoReverse(false);
            timeline.setCycleCount(1);
            timeline.play(); 
        }
        else
        {
            scream.stop();
            lose.stop();
            loseTitle.setScaleX(0);
            loseTitle.setScaleY(0);
            overlay.setOpacity(0);
            overlay.setMouseTransparent(true);
            animationPane.setMouseTransparent(true);
            animationPane.setVisible(false);
        }
    }
}
