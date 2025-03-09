import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.util.Duration;
import javafx.scene.media.AudioClip;

/**
 * Class to create visual character object
 *
 * @author Aniekan Skye Kings 
 */
public class Character extends ImageView 
{
    //get the character stance and character jump
    protected Timeline timeline;
    protected Image defaultImg;
    protected Image jumpingImg;
    protected double originalW;
    protected double originalH;
    protected double[] scales;
    protected AudioClip jumpSound;
    protected AudioClip landSound;

    public Character() 
    {
        super();
        super.setFitWidth(150);
        //keeps the original ratio of the image
        super.setPreserveRatio(true);
        // add the stances
        defaultImg = new Image("./assets/images/character.png");
        jumpingImg = new Image("./assets/images/character_jump.png");
        this.setImage(defaultImg);
        
        jumpSound = new AudioClip("file:assets/sounds/jump.mp3");
        landSound = new AudioClip("file:assets/sounds/land.mp3");
        
        jumpSound.setVolume(0.6);
        landSound.setVolume(0.2);
        
        scales = new double[]{1, 0.93, 0.86};
        originalW = this.getLayoutBounds().getWidth();
        originalH = this.getLayoutBounds().getHeight();
        
        timeline = new Timeline();
    }

    public void move(double x, double y, int rowNum, boolean notReset) 
    {
        //clear previous movement
        timeline.getKeyFrames().clear();
    
        double scale = scales[rowNum];
        double pivotX = (originalW * scale) / 2;  
        double pivotY = originalH * scale;  

        //translate the character to the position which has been clicked
        KeyFrame xMovement = new KeyFrame(Duration.millis(600), new KeyValue(this.translateXProperty(), x - pivotX));
        KeyFrame yMovement = new KeyFrame(Duration.millis(600), new KeyValue(this.translateYProperty(), y - pivotY));
        
        KeyFrame xScale = new KeyFrame(Duration.millis(600), new KeyValue(this.scaleXProperty(), scale));
        KeyFrame yScale = new KeyFrame(Duration.millis(600), new KeyValue(this.scaleYProperty(), scale));

        KeyFrame jumping = new KeyFrame(Duration.ZERO, e -> {
            this.setImage(jumpingImg);
            if(notReset)
            {
                jumpSound.play();
            }
        });
        KeyFrame defaultS = new KeyFrame(Duration.millis(600), e -> {
            this.setImage(defaultImg);
            if(notReset)
            {
                landSound.play();
            }
        });

        timeline.getKeyFrames().addAll(xMovement, yMovement, xScale, yScale, jumping, defaultS);
        timeline.play();
    }
}
