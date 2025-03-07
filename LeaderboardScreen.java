import javafx.scene.layout.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.geometry.Pos;
/**
 * Write a description of class LeaderboardScreen here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class LeaderboardScreen extends Pane
{
    protected boolean visible;
    protected LeaderBoardTable table;
    
    public LeaderboardScreen(double width, double height)
    {
        this.setPrefWidth(width);
        this.setPrefHeight(height);
        this.setVisible(false);
        this.setMouseTransparent(true);
        visible = false;
        
        ImageView bg = new ImageView(new Image("./assets/images/startBg.png"));
        bg.setFitWidth(width);
        bg.setFitHeight(height);
        
        Image backDefault = new Image("./assets/images/back_default.png");
        Image backHovered = new Image("./assets/images/back_hovered.png");
        ImageView backBtn = new ImageView(backDefault);
        backBtn.setFitWidth(70);
        backBtn.setPreserveRatio(true);
        backBtn.setLayoutX(10);
        backBtn.setLayoutY(10);
        backBtn.setOnMouseClicked(e -> this.toggle());
        backBtn.setOnMouseEntered(e -> backBtn.setImage(backHovered));
        backBtn.setOnMouseExited(e -> backBtn.setImage(backDefault));
        
        VBox container = new VBox(20);
        container.setAlignment(Pos.CENTER);
        container.setPrefWidth(width);
        container.setPrefHeight(height);
        
        Text title = new Text("Leaderboard");
        title.setFont(Font.font("Comic Sans MS", 30));
        title.setFill(Color.WHITE);
        title.setStroke(Color.BLACK);
        title.setStrokeWidth(1);
        
        table = new LeaderBoardTable(400,400);
        
        container.getChildren().addAll(title, table);        
        this.getChildren().addAll(bg, container, backBtn);
    }
    
    public void toggle()
    {
        if (visible)
        {
            this.setVisible(false);
            this.setMouseTransparent(true);
            visible = false;
        }
        else
        {
            this.setVisible(true);
            this.setMouseTransparent(false);
            visible = true;
        }
    }
    
    public void load()
    {
        table.load();
    }
}
