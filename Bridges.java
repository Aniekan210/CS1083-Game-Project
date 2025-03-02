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
    protected String bgUrl;
    protected double[] scale;
    protected double[] movement;
    protected ArrayList<Tile> tiles;
    protected EventHandler<MouseEvent> event;
    protected Random gen;
    protected int roundNum;
    protected double spacing;
    protected double width;
    protected double height;
    protected double vBucksChance;
    
    public Bridges(double width, double height, int roundNum, int roundPay, EventHandler<MouseEvent> event)
    {   
        // create bridge stack pane
        super();
        super.setPrefWidth(width);
        super.setPrefHeight(height);
        this.width = width;
        this.height = height;
        tiles = new ArrayList<Tile>();
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
                //distance the player moves(upward kind of)
                movement = new double[]{-70, -75, -60};
                spacing = -10;
                vBucksChance = 0;
                break;
                
            case 2:
                glassPerRow = 3;
                scale = new double[]{1.2,1,0.65};
                movement = new double[]{-30, -15, 15};
                vBucksChance = 0;
                break;
                
            case 3:
                glassPerRow = 2;
                scale = new double[]{1.42,1.12,0.87};
                movement = new double[]{-65, -38, 10};
                spacing = -35;
                vBucksChance = 0;
                break;
        }
        //get the bridge and glass tile image
        bridgeUrl = String.format("./assets/images/round_%d/bridge.png", roundNum);
        bgUrl = String.format("./assets/images/round_%d/bg.png", roundNum);
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
            ImageView bg = new ImageView(new Image(bgUrl));
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
                HBox glassRow = new HBox(0);    
                for(int j=0; j<glassPerRow; j++)
                {
                    int payout = 1;
                    double breakRisk = 0;
                    if (gen.nextDouble() < vBucksChance)
                    {
                        // change the breakrisk after getting broken tiles
                        breakRisk = 0.2; 
                        payout = gen.nextInt(50) + 50;
                    }
                    //make a regular tile
                    Tile current = new Tile(roundNum, i-1, j, payout, breakRisk);
                    tiles.add(current);
                    glassRow.getChildren().add(current);
                }
                
                // generate a tile that will break and add it to the row
                int payout = 0;
                if (gen.nextDouble() < vBucksChance)
                {
                    payout = (gen.nextInt(50) + 50)/10 * 10;
                }
                int randomIndex = gen.nextInt(glassPerRow);
                Tile willBreak = new Tile(roundNum, i-1, randomIndex, payout, 1);
                glassRow.getChildren().set(randomIndex, willBreak);
                tiles.add(willBreak);
                
                glassRow.setAlignment(Pos.BOTTOM_CENTER);
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
            glassContainer.setTranslateX(-5.4);
            
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
            //get the tile that was clicked
            Tile current = tiles.get(i);
            current.setOnMouseClicked(event);
        }
    }
    //Restart the gsme for the very beginning
    public void resetRoundNum()
    {
        roundNum = 0;
    }
}
