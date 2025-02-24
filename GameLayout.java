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
        playerPane.setMouseTransparent(true);
        playerPane.setPrefWidth(width);
        playerPane.setPrefHeight(height);
        playerPane.getChildren().add(player);
        
        // create bride pane
        Bridges bridgePane = new Bridges(width, height, gameLogic.getRoundNum());
        bridgePane.setFunctions(new EventHandler<MouseEvent>() 
        {
            @Override
            public void handle(MouseEvent e) 
            {
                Tile clicked = (Tile) e.getSource();
                System.out.println("Tile clicked at: " + clicked.getCenterX() + ", " + clicked.getCenterY());
                player.move(clicked.getCenterX(), clicked.getCenterY());
            }
        });
        
        // add bridge pane and payer pane to contaner
        container.getChildren().addAll(bridgePane, playerPane);
        
        this.getChildren().addAll(container);
    }
    
    private void drawUI()
    {
        
    }
}
