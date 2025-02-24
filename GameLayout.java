import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.util.Random;

/**
 * Write a description of class Round here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class GameLayout extends Pane
{
    protected Logic gameLogic;
    private Random gen;
    protected Character player;
    
    public GameLayout(double width, double height)
    {
        super();
        this.setPrefWidth(width);
        this.setPrefHeight(height);
        gen = new Random();
        player = new Character();
        gameLogic = new Logic();

        drawBridges(width, height);
        this.getChildren().add(player);
        drawUI();
    }
    
    private void drawBridges(double width, double height)
    {
        // create bridge stack pane
        StackPane bridges = new StackPane();
        bridges.setPrefWidth(width);
        bridges.setPrefHeight(height);
        
        // Get round number
        int roundNum = gameLogic.getRoundNum();
        int glassPerRow=0;
        String bridgeUrl = "";
        double[] scale = {0,0,0};
        double[] movement = {0,0,0};
        
        switch(roundNum)
        {
            case 1:
                glassPerRow = 4;
                break;
            case 2:
                glassPerRow = 3;
                bridgeUrl = "./assets/images/round_2/bridge.png";
                scale = new double[]{1.2,1,0.65};
                movement = new double[]{-30, -15, 15};
                break;
            case 3:
                glassPerRow = 2;
                break;
        }
        
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
                current.setOnMouseClicked(e -> {
                    // check break risk
                    boolean isBroken = gen.nextDouble() < current.getBreakRisk();
                    
                    gameLogic.setHasLost(isBroken);
                    
                    // move the character
                    player.move(current.getCenterX(), current.getCenterY());
                });
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
        
        bridges.setAlignment(bridgeImg, Pos.BOTTOM_CENTER);
        bridges.setAlignment(glassContainer, Pos.BOTTOM_CENTER);
        bridges.getChildren().addAll(bridgeImg, glassContainer);
        this.getChildren().add(bridges);
    }
    
    private void drawUI()
    {
        
    }
}
