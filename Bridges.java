import java.util.ArrayList;
import javafx.scene.layout.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import java.util.Random;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/*******************************************************************************************************************************
 * This is a class places glass tiles ontop of bridges but in other to get to the other side you have to step on the right glass.
 *
 * @author Group Aniekan, Skye and Kings
 * @version 1st March 2025
 ******************************************************************************************************************************/
public class Bridges extends StackPane
{
    protected int glassPerRow;
    protected String bridgeUrl;
    protected double[] scale;
    protected double[] movement;
    protected ArrayList<ArrayList<Tile>> tiles;
    protected EventHandler<MouseEvent> event;
    protected Random gen;
    protected int roundNum;
    protected double spacing;
    protected double width;
    protected double height;
    protected double vBucksChance;
    protected double translate;
    
    public Bridges(double width, double height, int roundNum, int roundPay, EventHandler<MouseEvent> event)
    {   
        // create bridge stack pane
        super();
        super.setPrefWidth(width);
        super.setPrefHeight(height);
        this.width = width;
        this.height = height;
        tiles = new ArrayList<ArrayList<Tile>>();
        this.event = event;
        this.roundNum = 0;
        gen = new Random();
        
        updateBridge(roundNum, roundPay);
    }
    
    public void updateBridge(int roundNum, int roundPay)
    {
        //Make a new bridge for every new round
        switch(roundNum)
        {
            case 1:
                glassPerRow = 4;
                //scale of each of the 3 glass tiles
                scale = new double[]{0.5,0.5,0.5};
                movement = new double[]{-70, -75, -60};
                spacing = -10;
                vBucksChance = 0;
                break;
                
            case 2:
                glassPerRow = 3;
                scale = new double[]{1.02,0.85,0.7};
                movement = new double[]{-74, -20, 41};
                spacing = -28;
                vBucksChance = 0.33;//change
                translate = -1.5;
                break;
            case 3:
                glassPerRow = 2;
                scale = new double[]{1.42,1.12,0.87};
                movement = new double[]{-64, -38, 10};
                spacing = -33;
                vBucksChance = 0;
                translate = -5.4;
                break;
        }
        //get the bridge and glass tile image
        bridgeUrl = String.format("./assets/images/round_%d/bridge.png", roundNum);
        drawBridge(roundNum, roundPay);
    }
    
    private void drawBridge(int roundNum, int roundPay)
    {
        if(this.roundNum != roundNum)
        {
            //remove all the tiles and Bridges
            tiles.clear();
            this.getChildren().clear();

            //make a new bridge
            ImageView bg = new ImageView(new Image("./assets/images/bg.png"));
            bg.setFitHeight(height);
            bg.setFitWidth(width);

            Pane textPane = new Pane();
            textPane.setMouseTransparent(true);
            
            //Display amount of vBucks won 
            Text roundPayText = new Text("+"+roundPay);
            roundPayText.setFont(Font.font("Comic Sans MS", 28));
            roundPayText.setFill(Color.BLACK);
            roundPayText.setLayoutX(width/2 - roundPayText.getLayoutBounds().getWidth()/2 - 2);
            roundPayText.setLayoutY(137);
            
            textPane.getChildren().add(roundPayText);
        
            // Make glass bridge bg
            ImageView bridgeImg = new ImageView(new Image(bridgeUrl));
            bridgeImg.setPreserveRatio(true);
            bridgeImg.setFitWidth(width);
            
            //Make container for glass rows
            VBox glassContainer = new VBox();
            // Make glass rows
            for (int i=3; i>0; i--)
            {
                HBox glassRow = new HBox();   
                ArrayList<Tile> tileRow = new ArrayList<Tile>();
                for(int j=0; j<glassPerRow; j++)
                {
                    int payout = 0;
                    double breakRisk = 0;
                    if (gen.nextDouble() < vBucksChance)
                    {
                        // change the breakrisk after getting broken tiles
                        breakRisk = 0.1; 
                        payout = (gen.nextInt(50) + 50)/10 * 10;
                    }
                    //make a regular tile
                    Tile current = new Tile(roundNum, i-1, j, payout, breakRisk);
                    tileRow.add(current);
                    glassRow.getChildren().add(current);
                }
                
                // generate a tile that will break and add it to the row
                int payout = 0;//change
                if (gen.nextDouble() < vBucksChance)
                {
                    payout = (gen.nextInt(50) + 50)/10 * 10;
                }
                int randomIndex = gen.nextInt(glassPerRow);
                Tile willBreak = new Tile(roundNum, i-1, randomIndex, payout, 1);
                glassRow.getChildren().set(randomIndex, willBreak);
                tileRow.set(randomIndex, willBreak);
                
                glassRow.setAlignment(Pos.BOTTOM_CENTER);
                glassRow.setSpacing(spacing);
                
                // scale down as it goes further
                double scaleFactor = scale[i-1];
                glassRow.setScaleX(scaleFactor);
                glassRow.setScaleY(scaleFactor);
                
                // Adjust spacing dynamically for a perspective effect
                glassRow.setTranslateY(movement[i-1]);
                tiles.add(tileRow);
                glassContainer.getChildren().add(glassRow);
            }
            glassContainer.setAlignment(Pos.BOTTOM_CENTER);
            glassContainer.setTranslateX(translate);
            
            this.setAlignment(bridgeImg, Pos.BOTTOM_CENTER);
            this.setAlignment(glassContainer, Pos.BOTTOM_CENTER);
            this.getChildren().addAll(bg, bridgeImg, textPane, glassContainer);
            this.roundNum = roundNum;
            setFunctions();
        }
    }
    
    private void setFunctions()
    {
        for (int i=0; i<tiles.size(); i++)
        {
            ArrayList<Tile> currentList = tiles.get(i);
            for (int j=0; j<currentList.size(); j++)
            {
                Tile current = currentList.get(j);
                current.setOnMouseClicked(event);
            }
        }
    }
    
    public void revealRows(int rowNum)
    {
        int totalRows = tiles.size();
        for (int i = totalRows - rowNum; i < totalRows; i++)
        {
            ArrayList<Tile> tileRow = tiles.get(i);
            for (int j=0; j<tileRow.size(); j++)
            {
                Tile current = tileRow.get(j);
                if (current.getBreakRisk() == 1)
                {
                    current.setImage("broken");
                }
            }
        }
    }
    
    
    //Restart the gsme for the very beginning
    public void resetRoundNum()
    {
        roundNum = 0;
    }
}
