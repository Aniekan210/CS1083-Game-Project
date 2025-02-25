import java.util.ArrayList;
import javafx.scene.layout.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;

/**
 * Write a description of class Bridges here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Bridges extends StackPane
{
    protected int glassPerRow;
    protected String bridgeUrl;
    protected double[] scale;
    protected double[] movement;
    protected ArrayList<Tile> tiles;
    protected EventHandler<MouseEvent> event;
    
    public Bridges(double width, double height, int roundNum, EventHandler<MouseEvent> event)
    {   
        // create bridge stack pane
        super();
        super.setPrefWidth(width);
        super.setPrefHeight(height);
        tiles = new ArrayList<Tile>();
        this.event = event;
        
        updateBridge(roundNum);       
    }
    
    public void updateBridge(int roundNum)
    {
        switch(roundNum)
        {
            case 1:
                glassPerRow = 4;
                scale = new double[]{1.1,0.9,0.7};
                movement = new double[]{-70, -75, -60};
                break;
            case 2:
                glassPerRow = 3;
                scale = new double[]{1.2,1,0.65};
                movement = new double[]{-30, -15, 15};
                break;
            case 3:
                glassPerRow = 2;
                scale = new double[]{1.2,1,0.65};
                movement = new double[]{-30, -15, 15};
                break;
        }
        bridgeUrl = String.format("./assets/images/round_%d/bridge.png", roundNum);
        drawBridge(roundNum);
        setFunctions();
    }
    
    private void drawBridge(int roundNum)
    {
        this.getChildren().clear();
        
        // Make glass bridge bg
        ImageView bridgeImg = new ImageView(new Image(bridgeUrl));
        
        //Make container for glass rows
        VBox glassContainer = new VBox();
        
        // Make glass rows
        for (int i=3; i>0; i--)
        {
            HBox glassRow = new HBox(0);
            for(int j=0; j<glassPerRow; j++)
            {
                Tile current = new Tile(roundNum, i-1, j, "regular", 0);
                tiles.add(current);
                glassRow.getChildren().add(current);
            }
            
            glassRow.setAlignment(Pos.CENTER);
            
            // scale down as it goes further
            double scaleFactor = scale[i-1];
            glassRow.setScaleX(scaleFactor);
            glassRow.setScaleY(scaleFactor);
            
            // Adjust spacing dynamically for a perspective effect
            glassRow.setTranslateY(movement[i-1]);

            glassContainer.getChildren().add(glassRow);
        }
        glassContainer.setAlignment(Pos.BOTTOM_CENTER);
        
        this.setAlignment(bridgeImg, Pos.BOTTOM_CENTER);
        this.setAlignment(glassContainer, Pos.BOTTOM_CENTER);
        this.getChildren().addAll(bridgeImg, glassContainer);
    }
    
    private void setFunctions()
    {
        for (int i=0; i<tiles.size(); i++)
        {
            Tile current = tiles.get(i);
            current.setOnMouseClicked(event);
        }
    }
}
