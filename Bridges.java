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
    protected Tile[][] tiles;
    protected int[] possiblePay;
    protected EventHandler<MouseEvent> event;
    protected Random gen;
    protected int roundNum;
    protected double spacing;
    protected double width;
    protected double height;
    protected double vBucksChance;
    protected double translate;
    protected double breakNum;
    
    public Bridges(double width, double height, int roundNum, int roundPay, EventHandler<MouseEvent> event)
    {   
        super();
        super.setPrefWidth(width);
        super.setPrefHeight(height);
        this.width = width;
        this.height = height;
        this.event = event;
        this.roundNum = 0;
        
        gen = new Random();
        // list of possibel amounts vBucks could have
        possiblePay = new int[]{50, 100, 150};
            
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
                scale = new double[]{0.716, 0.57, 0.442};
                movement = new double[]{-62, -20, 40};
                spacing = -40;
                vBucksChance = 0.4;// change to 0.4
                translate = 0;
                break;
                
            case 2:
                glassPerRow = 3;
                scale = new double[]{1.02,0.85,0.7};
                movement = new double[]{-74, -20, 41};
                spacing = -28;
                vBucksChance = 0.25;
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
            // Remove all the tiles and bridge if the round number has changed
            tiles = new Tile[3][glassPerRow];
            VBox glassContainer = new VBox();
            this.getChildren().clear();
            
            // Add background
            // Add the background
            ImageView bg = new ImageView(new Image("./assets/images/bg.png"));
            bg.setFitHeight(height);
            bg.setFitWidth(width);

            //Display amount of vBucks to win 
            Pane textPane = new Pane();
            textPane.setMouseTransparent(true);
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
            for (int i=3; i>0; i--)
            {
                HBox glassRow = new HBox();   
                Tile[] tileRow = new Tile[glassPerRow];
                for(int j=0; j<glassPerRow; j++)
                {
                    int payout = 0;
                    double breakRisk = 0;
                    if (gen.nextDouble() < vBucksChance)
                    {
                        int payIndex = gen.nextInt(possiblePay.length);
                        payout = possiblePay[payIndex];
                        breakRisk = 0.03; 
                    }
                    //make a regular tile
                    Tile current = new Tile(roundNum, i-1, j, payout, breakRisk);
                    tileRow[j] = current;
                    glassRow.getChildren().add(current);
                }
                
                // generate a tile that will break and add it to the row
                int payout = 0; //change to 0
                if (gen.nextDouble() < vBucksChance)
                {
                    int payIndex = gen.nextInt(possiblePay.length);
                    payout = possiblePay[payIndex];
                }
                int randomIndex = gen.nextInt(glassPerRow);
                Tile willBreak = new Tile(roundNum, i-1, randomIndex, payout, 1);
                glassRow.getChildren().set(randomIndex, willBreak);
                tileRow[randomIndex] = willBreak;
                
                glassRow.setAlignment(Pos.BOTTOM_CENTER);
                glassRow.setSpacing(spacing);
                
                // scale down as it goes further
                double scaleFactor = scale[i-1];
                glassRow.setScaleX(scaleFactor);
                glassRow.setScaleY(scaleFactor);
                
                // Adjust spacing dynamically for a perspective effect
                glassRow.setTranslateY(movement[i-1]);
                tiles[i-1] = tileRow;
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
        for (int i=0; i<tiles.length; i++)
        {
            Tile[] currentList = tiles[i];
            for (int j=0; j<currentList.length; j++)
            {
                Tile current = currentList[j];
                current.setOnMouseClicked(event);
            }
        }
    }
    
    public void revealRows(int rowNum)
    {
        for (int i=0; i<rowNum; i++)
        {
            Tile[] currentList = tiles[i];
            for (int j=0; j<currentList.length; j++)
            {
                Tile current = currentList[j];
                current.reveal();
            }
        }
    }
    
    // Helper method to handle the reset of the game
    public void resetRoundNum()
    {
        roundNum = 0;
    }
}
