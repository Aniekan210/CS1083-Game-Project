import javafx.scene.layout.*;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.util.Random;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;

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
    protected UILayer ui;
    protected double width;
    protected double height;
    
    public GameLayout(double width, double height)
    {
        super();
        this.setPrefWidth(width);
        this.setPrefHeight(height);
        gen = new Random();
        player = new Character();
        gameLogic = new Logic();

        this.width = width;
        this.height = height;
        
        drawBridgeAndPlayer();
        drawUI();
    }
    
    private void drawBridgeAndPlayer()
    {
        // Brdige and player container
        StackPane container = new StackPane();
        
        // create player pane
        Pane playerPane = new Pane();
        playerPane.setMouseTransparent(true);
        playerPane.setPrefWidth(width);
        playerPane.setPrefHeight(height);        
        playerPane.getChildren().add(player);        
        
        // create bride pane
        bridgePane = new Bridges(width, height, gameLogic.getRoundNum(), gameLogic.getRoundPayout(), 
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
                            // update the tile image
                            clicked.setImage("broken");
                        
                            // fail the player
                            gameLogic.lose();
                        }
                        else
                        {
                            // check how many vbucks it has and add it to the payout
                            gameLogic.addPayout(clicked.getPayout());
                            
                            // update the image of the tile
                            clicked.setImage("regular");
                        }
                    
                        // update the rowNum
                        gameLogic.incRowNum();
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
        ui = new UILayer(width, height);
        this.getChildren().add(ui);
    }
     
    private void updateAll()
    {
        ui.updateUI(gameLogic.getPayout());
        
        // add 
        bridgePane.updateBridge(gameLogic.getRoundNum(), gameLogic.getRoundPayout());
    }
}
