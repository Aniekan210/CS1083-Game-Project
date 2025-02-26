import java.util.ArrayList;
import javafx.scene.layout.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import java.util.Random;

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
    protected Random gen;
    protected int roundNum;
    protected double spacing;
    
    public Bridges(double width, double height, int roundNum, int roundPay, EventHandler<MouseEvent> event)
    {   
        // create bridge stack pane
        super();
        super.setPrefWidth(width);
        super.setPrefHeight(height);
        tiles = new ArrayList<Tile>();
        this.event = event;
        this.roundNum = 0;
        gen = new Random();
        
        updateBridge(roundNum, roundPay);
    }
    
    public void updateBridge(int roundNum, int roundPay)
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
                scale = new double[]{0.814,0.62,0.48};
                movement = new double[]{-49, 0, 67};
                spacing = 4.2;
                break;
        }
        bridgeUrl = String.format("./assets/images/round_%d/bridge.png", roundNum);
        drawBridge(roundNum);
    }
    
    private void drawBridge(int roundNum)
    {
        if(this.roundNum != roundNum)
        {
            tiles.clear();
            this.getChildren().clear();
        
            // Make glass bridge bg
            ImageView bridgeImg = new ImageView(new Image(bridgeUrl));
            bridgeImg.setPreserveRatio(true);
            bridgeImg.setFitWidth(500);
            
            //Make container for glass rows
            VBox glassContainer = new VBox();
            
            // 20% chance to be a vbucks 
            double vBucksChance = 0.2; // cange to acc vbucks cance after testing
            
            // Make glass rows
            for (int i=3; i>0; i--)
            {
                HBox glassRow = new HBox(0);    
                for(int j=0; j<glassPerRow; j++)
                {
                    int payout = 0;
                    double breakRisk = 0;
                    if (gen.nextDouble() < vBucksChance)
                    {
                        breakRisk = 0.5;
                        payout = gen.nextInt(200) + 200;
                    }
                    Tile current = new Tile(roundNum, i-1, j, payout, breakRisk);
                    tiles.add(current);
                    glassRow.getChildren().add(current);
                }
                
                // generate a tile that will break and add it to the row
                int payout = 0;
                if (gen.nextDouble() < vBucksChance)
                {
                    payout = gen.nextInt(200) + 200;
                }
                int randomIndex = gen.nextInt(glassPerRow);
                Tile willBreak = new Tile(roundNum, i-1, randomIndex, payout, 1);
                glassRow.getChildren().set(randomIndex, willBreak);
                tiles.add(willBreak);
                
                glassRow.setAlignment(Pos.CENTER);
                glassRow.setSpacing(spacing);
                
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
            this.roundNum = roundNum;
            setFunctions();
        }
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
