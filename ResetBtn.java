import javafx.scene.layout.*;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
/**
 * Write a description of class ResetBtn here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ResetBtn extends Pane
{
    public ResetBtn(double width, double height, EventHandler<MouseEvent> event)
    {
        super();
        this.setPrefWidth(width);
        this.setPrefHeight(height);
        this.setPickOnBounds(false);
        
        Image resetDefault = new Image("./assets/images/reset_button.png");
        Image resetHovered = new Image("./assets/images/reset_hovered.png");
        ImageView reset = new ImageView(resetDefault);
        reset.setOnMouseEntered(e -> reset.setImage(resetHovered));
        reset.setOnMouseExited(e -> reset.setImage(resetDefault));
        reset.setOnMouseClicked(event);
        reset.setFitHeight(70);
        reset.setPreserveRatio(true);
        reset.setLayoutY(height - 70);
        reset.setLayoutX(0);
        
        this.getChildren().addAll(reset);
    }
}
