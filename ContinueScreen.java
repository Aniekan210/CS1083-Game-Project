import javafx.scene.layout.*;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
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
public class ContinueScreen extends StackPane
{
    protected ImageView bg;
    protected Text payoutText;
    protected ImageView continueBtn;
    protected ImageView stopBtn;
    protected Image contDisabled;
    protected Image contHovered;
    protected Image contDefault;
    protected Image stopHovered;
    protected Image stopDefault;
    protected EventHandler<MouseEvent> clickCont;
    protected EventHandler<MouseEvent> clickStop;
    protected Pane container;
    protected Timeline timeline;
    protected AudioClip backgroundMusic;
    protected AudioClip winAudio;
    protected double height;
    protected double width;
    
    public ContinueScreen(double width, double height, AudioClip backgroundMusic, EventHandler<MouseEvent> clickCont, EventHandler<MouseEvent> clickStop)
    {
        this.setPrefWidth(width);
        this.setPrefHeight(height);
        this.setPickOnBounds(false);
        this.backgroundMusic = backgroundMusic;
        this.height = height;
        this.width = width;
        this.clickCont = clickCont;
        this.clickStop = clickStop;
        timeline = new Timeline();
        winAudio = new AudioClip("file:assets/sounds/win.mp3");

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
        
        double buttonWidth = 250;
        contHovered = new Image("./assets/images/continue_hovered.png");
        contDefault = new Image("./assets/images/continue_default.png");
        contDisabled = new Image("./assets/images/continue_disabled.png");
        continueBtn = new ImageView(contDefault);
        continueBtn.setFitWidth(buttonWidth);
        continueBtn.setPreserveRatio(true);
        continueBtn.setLayoutX(width/2 - buttonWidth/2);        
        continueBtn.setLayoutY(height-60-continueBtn.getLayoutBounds().getHeight()-95);
        
        stopHovered = new Image("./assets/images/stop_hovered.png");
        stopDefault = new Image("./assets/images/stop_default.png");
        stopBtn = new ImageView(stopDefault);
        stopBtn.setFitWidth(buttonWidth);
        stopBtn.setPreserveRatio(true);
        stopBtn.setLayoutX(width/2 - buttonWidth/2);        
        stopBtn.setLayoutY(height-60-stopBtn.getLayoutBounds().getHeight());
        
        setHoversAndClicks();
        
        ImageView winTitle = new ImageView(new Image("./assets/images/you_win.png"));
        winTitle.setFitWidth(450);
        winTitle.setPreserveRatio(true);
        winTitle.setLayoutX(width/2 - 225);
        winTitle.setLayoutY(60);
        
        payoutText = new Text();
        payoutText.setFont(Font.font("Comic Sans MS", 40));
        payoutText.setFill(Color.WHITE);
        payoutText.setStroke(Color.BLACK);
        payoutText.setStrokeWidth(2.2);
        payoutText.setLayoutY(325);
        
        container.getChildren().addAll(continueBtn, winTitle, stopBtn, payoutText);
        this.getChildren().addAll(bg, container);
    }
    
    public void updateContinue(boolean hasWonRound, int roundNum, int reward)
    {   
        String payout = String.format("AMOUNT WON: %d vBucks", reward);
        payoutText.setText(payout);
        payoutText.setLayoutX(width/2 - payoutText.getBoundsInLocal().getWidth()/2);
        if (roundNum == 3)
        {
            continueBtn.setImage(contDisabled);
            removeHoversAndClicks();
        }
        else
        {
            setHoversAndClicks();
            continueBtn.setImage(contDefault);
        }
        if (hasWonRound)
        {
            bg.setMouseTransparent(false);
            container.setMouseTransparent(false);
            timeline.getKeyFrames().clear();
            int startTime = 750;
            KeyFrame nothing = new KeyFrame(Duration.millis(startTime), new KeyValue(bg.opacityProperty(), 0));
            KeyFrame stopAndStartMusic = new KeyFrame(Duration.millis(startTime), e -> {
                backgroundMusic.stop();
                winAudio.play();
            });
            
            KeyFrame show = new KeyFrame(Duration.millis(startTime+300), 
                               new KeyValue(bg.opacityProperty(), 1),
                               new KeyValue(container.opacityProperty(), 0)
            );
            
            KeyFrame showUI = new KeyFrame(Duration.millis(startTime+1000), new KeyValue(container.opacityProperty(), 1));

            
            timeline.getKeyFrames().addAll(nothing, stopAndStartMusic, show, showUI);
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
    
    public void play()
    {
        timeline.getKeyFrames().clear();
        
        KeyFrame playMusic = new KeyFrame(Duration.millis(300), e -> {
            winAudio.stop();
            backgroundMusic.play();
        });
        
        timeline.getKeyFrames().add(playMusic);
        timeline.play();
    }
    
    private void setHoversAndClicks()
    {
        continueBtn.setOnMouseEntered(e -> continueBtn.setImage(contHovered));
        continueBtn.setOnMouseExited(e -> continueBtn.setImage(contDefault));
        continueBtn.setOnMouseClicked(clickCont);
        
        stopBtn.setOnMouseEntered(e -> stopBtn.setImage(stopHovered));
        stopBtn.setOnMouseExited(e -> stopBtn.setImage(stopDefault));
        stopBtn.setOnMouseClicked(clickStop);
    }
    
    private void removeHoversAndClicks()
    {
        continueBtn.setOnMouseEntered(null);
        continueBtn.setOnMouseExited(null);
        continueBtn.setOnMouseClicked(null);
    }
}
