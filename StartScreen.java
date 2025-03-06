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
        
        double buttonWidth = 250;
        
        Image startDefault = new Image("./assets/images/start_button.png");
        Image startHovered = new Image("./assets/images/start_hovered.png");
        ImageView startBtn = new ImageView(startDefault);
        startBtn.setOnMouseEntered(e -> startBtn.setImage(startHovered));
        startBtn.setOnMouseExited(e -> startBtn.setImage(startDefault));
        startBtn.setFitWidth(buttonWidth);
        startBtn.setPreserveRatio(true);
        startBtn.setLayoutX(width/2 - buttonWidth/2);
        startBtn.setLayoutY(height - 270);
        startBtn.setOnMouseClicked(event);
        
        Image leaderDefault = new Image("./assets/images/leaderboard_default.png");
        Image leaderHovered = new Image("./assets/images/leaderboard_hovered.png");
        ImageView leader = new ImageView(leaderDefault);
        leader.setOnMouseEntered(e -> leader.setImage(leaderHovered));
        leader.setOnMouseExited(e -> leader.setImage(leaderDefault));
        leader.setFitWidth(buttonWidth);
        leader.setPreserveRatio(true);
        leader.setLayoutX(width/2 - buttonWidth/2);
        leader.setLayoutY(height - 170);
        leader.setOnMouseClicked(event); // will change this later
        
        container.getChildren().addAll(logo, startBtn, leader);
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
