import javafx.scene.layout.*;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.util.Random;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import java.util.concurrent.TimeUnit;

/**
 * Write a description of class Round here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class GameLayout extends StackPane
{
    protected Logic gameLogic;
    private Random gen;
    protected Character player;
    protected Bridges bridgePane;
    
    public GameLayout(double width, double height)
    {
        super();
        this.setPrefWidth(width);
        this.setPrefHeight(height);
        gen = new Random();
        player = new Character();
        gameLogic = new Logic();

        drawBridgeAndPlayer(width, height);
        drawUI();
    }
    
    private void drawBridgeAndPlayer(double width, double height)
    {
        // Brdige and player container
        StackPane container = new StackPane();
        
        // create player pane
        Pane playerPane = new Pane();
        playerPane.setPickOnBounds(false);
        playerPane.setPrefWidth(width);
        playerPane.setPrefHeight(height);        
        playerPane.getChildren().add(player);        
        
        // create bride pane
        bridgePane = new Bridges(width, height, gameLogic.getRoundNum(), 
            new EventHandler<MouseEvent>() 
            {
                @Override
                public void handle(MouseEvent e) 
                {
                    // On every button click
                    Tile clicked = (Tile) e.getSource();
                    
                    // Check the position of the tile
                    boolean allowMovement = gameLogic.getRowNum() == clicked.getRowNum();
                    
                    if (allowMovement)
                    {
                        // move player to tile
                        player.move(clicked.getCenterX(), clicked.getCenterY());                      
                        
                        // check if tile is broken
                        boolean isBroken = gen.nextDouble() < clicked.getBreakRisk();
                        if (isBroken)
                        {
                            // if yes 
                            
                            // update the tile image
                        
                            // fail the player
                        }
                        else
                        {
                            // if not
          
                            // check how many vbucks it has and add it to the payout
                            
                            // update the image
                        }
                    
                        // update the rowNum
                        gameLogic.incRowNum();
                        if (gameLogic.getRowNum() == 3)
                        {
                            player.move(width/2, height-10);
                            
                            gameLogic.incRowNum();
                        }
                    }
                                        
                    // Update the rest of the game
                    updateAll();
                }
            }
        );
        
        // add bridge pane and payer pane to contaner
        container.getChildren().addAll(bridgePane, playerPane);
        
        this.getChildren().addAll(container);
    }
    
    private void drawUI()
    {
        
    }
     
    private void updateAll()
    {
        bridgePane.updateBridge(gameLogic.getRoundNum());
    }
}
