import javafx.scene.layout.*;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
/**
 * Write a description of class StartScreen here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class StartScreen extends StackPane
{
    public StartScreen(double width, double height, EventHandler<MouseEvent> event)
    {
        super();
        this.setVisible(true);
        this.setPickOnBounds(false);
        
        ImageView bg = new ImageView(new Image("./assets/images/startBg.png"));
        bg.setFitWidth(width);
        bg.setFitHeight(height);
        bg.setPreserveRatio(true);       
        
        Pane container = new Pane();
        container.setPrefWidth(width);
        container.setPrefHeight(height);
        container.setPickOnBounds(false);

        ImageView logo = new ImageView(new Image("./assets/images/logo.png"));
        logo.setFitWidth(450);
        logo.setPreserveRatio(true);
        logo.setLayoutX(width/2 - 225);
        logo.setLayoutY(75);
        
        ImageView startBtn = new ImageView(new Image("./assets/images/start_button.png"));
        startBtn.setFitWidth(220);
        startBtn.setPreserveRatio(true);
        startBtn.setLayoutX(width/2 - 110);
        startBtn.setLayoutY(height - 200);
        startBtn.setOnMouseClicked(event);
        
        container.getChildren().addAll(logo, startBtn);
        this.getChildren().addAll(bg, container);
    }
    
    public void updateStart(boolean isStart)
    {
        if (isStart)
        {            
            this.setVisible(true);
        }
        else
        {
            this.setVisible(false);
        }
    }
}
