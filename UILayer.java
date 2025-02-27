import javafx.scene.layout.*;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.util.Duration;
import javafx.animation.Interpolator;
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
        this.setPrefWidth(width);
        this.setPrefHeight(height);
        this.setPickOnBounds(false);
        
        timeline = new Timeline();
        
        isOpened = false;
        modal = new ImageView(new Image("./assets/images/hint.png"));
        modal.setScaleX(0);
        modal.setScaleY(0);
        modal.setPreserveRatio(true);
        modal.setFitWidth(600);
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
        payoutScreen.setLayoutX(10);
        payoutScreen.setLayoutY(20);
        
        InstructionsBar instructions = new InstructionsBar();
        instructions.setXByPivot(width/2);
        instructions.setLayoutY(10);
        
        HintButton hint = new HintButton();
        hint.setXByPivot(width-40);
        hint.setLayoutY(32);
        hint.setOnMouseClicked(e -> {
            hint.toggle();
            this.toggleModal();
        });
        
        container.getChildren().addAll(payoutScreen, instructions, hint);
        this.getChildren().add(container);
    }
    
    public void updateUI(int payout)
    {
        payoutScreen.setPayout(payout);
    }
    
    
    public void toggleModal()
    {
        timeline.getKeyFrames().clear();
        KeyFrame first, second, third;
    
        if (!isOpened) 
        {
            // Opening the modal with scaling and bouncy effect
            first = new KeyFrame(Duration.millis(100),
                new KeyValue(modal.scaleXProperty(), 1.05, Interpolator.EASE_OUT),
                new KeyValue(modal.scaleYProperty(), 1.05, Interpolator.EASE_OUT)
            );
    
            second = new KeyFrame(Duration.millis(300),
                new KeyValue(modal.scaleXProperty(), 0.95, Interpolator.EASE_IN),
                new KeyValue(modal.scaleYProperty(), 0.95, Interpolator.EASE_IN)
            );
    
            third = new KeyFrame(Duration.millis(500),
                new KeyValue(modal.scaleXProperty(), 1, Interpolator.EASE_BOTH),
                new KeyValue(modal.scaleYProperty(), 1, Interpolator.EASE_BOTH)
            );
    
            isOpened = true;
        } 
        else 
        {
            // Closing the modal with bouncy scaling to 0
            first = new KeyFrame(Duration.millis(100),
                new KeyValue(modal.scaleXProperty(), 0.95, Interpolator.EASE_OUT),
                new KeyValue(modal.scaleYProperty(), 0.95, Interpolator.EASE_OUT)
            );
    
            second = new KeyFrame(Duration.millis(300),
                new KeyValue(modal.scaleXProperty(), 1.05, Interpolator.EASE_IN),
                new KeyValue(modal.scaleYProperty(), 1.05, Interpolator.EASE_IN)
            );
    
            third = new KeyFrame(Duration.millis(500),
                new KeyValue(modal.scaleXProperty(), 0, Interpolator.EASE_BOTH),
                new KeyValue(modal.scaleYProperty(), 0, Interpolator.EASE_BOTH)
            );
    
            isOpened = false;
        }
    
        timeline.getKeyFrames().addAll(first, second, third);
        timeline.play();
}

}
