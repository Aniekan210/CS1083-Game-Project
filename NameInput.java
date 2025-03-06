import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.util.Duration;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.animation.Interpolator;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.shape.Rectangle;
/**
 * Write a description of class NameInput here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class NameInput extends Pane
{
    protected TextField input;
    protected ImageView okButton;
    protected ImageView bg;
    protected Text question;
    protected boolean isShowing;
    protected Timeline timeline;
    protected Rectangle flashBang;
    public NameInput(double width, double height, EventHandler<MouseEvent> event)
    {
        super();
        this.setPrefHeight(height);  
        this.setPrefWidth(width);
        this.setMouseTransparent(true);
        
        isShowing = false;
        timeline = new Timeline();
        
        flashBang = new Rectangle(width, height, Color.WHITE);
        flashBang.setMouseTransparent(true);
        flashBang.setOpacity(0);
        
        bg = new ImageView(new Image("./assets/images/container.png"));
        bg.setFitWidth(450);
        bg.setPreserveRatio(true);
        bg.setLayoutX(width/2 - 225);
        bg.setLayoutY(height/2 - bg.getLayoutBounds().getHeight()/2);
        bg.setScaleX(0);
        bg.setScaleY(0);
        
        input = new TextField();
        input.setPromptText("Enter your name here...");
        input.setStyle("-fx-font-size: 16px; -fx-padding: 10px;");
        input.setPrefWidth(300); 
        input.setPrefHeight(40);
        input.setLayoutX(width/2 - 150);
        input.setLayoutY(255);
        input.setOpacity(0);
        
        question = new Text("What is your name?");
        question.setFont(Font.font("Comic Sans MS", 25));
        question.setFill(Color.WHITE);
        question.setStroke(Color.BLACK);
        question.setStrokeWidth(1);
        question.setLayoutX(width/2 - question.getBoundsInLocal().getWidth()/2);
        question.setLayoutY(240);
        question.setOpacity(0);
        
        okButton = new ImageView(new Image("./assets/images/ok_button.png"));
        okButton.setFitWidth(70);
        okButton.setPreserveRatio(true);
        okButton.setLayoutX(width/2 - 35);
        okButton.setLayoutY(310);
        okButton.setOnMouseClicked(event);
        okButton.setOpacity(0);
        
        this.getChildren().addAll(bg, input, okButton, question, flashBang);
    }
    
    public void toggle()
    {    
        if (!isShowing) 
        {
            this.setMouseTransparent(false);
            timeline.getKeyFrames().clear();
            KeyFrame first, second, third;
            
            first = new KeyFrame(Duration.millis(100),
                new KeyValue(bg.scaleXProperty(), 1.05, Interpolator.EASE_OUT),
                new KeyValue(bg.scaleYProperty(), 1.05, Interpolator.EASE_OUT)
            );
    
            second = new KeyFrame(Duration.millis(300),
                new KeyValue(bg.scaleXProperty(), 0.95, Interpolator.EASE_IN),
                new KeyValue(bg.scaleYProperty(), 0.95, Interpolator.EASE_IN)
            );
    
            third = new KeyFrame(Duration.millis(500),
                new KeyValue(bg.scaleXProperty(), 1, Interpolator.EASE_BOTH),
                new KeyValue(bg.scaleYProperty(), 1, Interpolator.EASE_BOTH),
                new KeyValue(input.opacityProperty(), 1),
                new KeyValue(okButton.opacityProperty(), 1),
                new KeyValue(question.opacityProperty(), 1)
            );
                
            timeline.getKeyFrames().addAll(first, second, third);
            timeline.play();
    
            isShowing = true;
        } 
        else 
        {
            okButton.setOpacity(0);
            bg.setScaleX(0);
            bg.setScaleY(0);
            input.setOpacity(0);
            question.setOpacity(0);
            this.setMouseTransparent(true);

            isShowing = false;
        }
    }
    
    public void flash()
    {
        timeline.getKeyFrames().clear();
        
        KeyFrame flash = new KeyFrame(Duration.millis(200),new KeyValue(flashBang.opacityProperty(), 1));
        KeyFrame dim = new KeyFrame(Duration.millis(800),new KeyValue(flashBang.opacityProperty(), 0));
        
        timeline.getKeyFrames().addAll(flash, dim);
        timeline.play();
    }
    
    public String getText()
    {
        return input.getText();
    }

    public void clear()
    {
          // Clear the text field when the image is clicked
            input.clear();
    }
}
