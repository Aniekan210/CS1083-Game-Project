import javafx.scene.layout.*;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.util.Duration;
/**
 * Write a description of class ClosingScreen here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class UILayer extends StackPane
{
    protected Timeline timeline;
    protected boolean isOpened;
    protected ImageView modal;
    protected TotalPayoutScreen payoutScreen;
    
    public UILayer(double width, double height)
    {
        super();
        super.setPrefWidth(width);
        super.setPrefHeight(height);
        super.setPickOnBounds(false);
        
        timeline = new Timeline();
        
        isOpened = false;
        modal = new ImageView(new Image("./assets/images/hint.png"));        
        modal.setPreserveRatio(true);
        modal.setFitWidth(300);
        modal.setScaleY(0);
        modal.setScaleX(0);
        this.getChildren().add(modal);
        drawUI(width, height);
    }
    
    private void drawUI(double width, double height)
    {
        // create container
        Pane container = new Pane();
        container.setPrefWidth(width);
        container.setPrefHeight(height);
        container.setPickOnBounds(false);
        
        payoutScreen = new TotalPayoutScreen();
        payoutScreen.setLayoutX(30);
        payoutScreen.setLayoutY(30);
        
        
        container.getChildren().addAll(payoutScreen);
        this.getChildren().add(container);
    }
    
    public void updateUI(int payout)
    {
        payoutScreen.setPayout(payout);
    }
    
    
    public void toggleModal()
    {
        timeline.getKeyFrames().clear();
        KeyFrame first;
        KeyFrame second;
        if (!isOpened)
        {
            first = new KeyFrame(Duration.millis(400),
                new KeyValue(modal.scaleXProperty(), 1.1),
                new KeyValue(modal.scaleYProperty(), 1.1)
            );
            
            second = new KeyFrame(Duration.millis(200),
                new KeyValue(modal.scaleXProperty(), 1),
                new KeyValue(modal.scaleYProperty(), 1)
            );
            isOpened = true;
        }
        else
        {
            first = new KeyFrame(Duration.millis(200),
                new KeyValue(modal.scaleXProperty(), 1.1),
                new KeyValue(modal.scaleYProperty(), 1.1)
            );
            
            second = new KeyFrame(Duration.millis(400),
                new KeyValue(modal.scaleXProperty(), 0),
                new KeyValue(modal.scaleYProperty(), 0)
            );
            
            isOpened = false;
        }
        
        timeline.getKeyFrames().addAll(first, second);
    }
}
