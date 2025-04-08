import javafx.scene.layout.*;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.util.Random;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.util.Duration;
import javafx.scene.media.AudioClip;

/*******************************************
 * This is a class that controls the main window of the game
 *
 * @author Group Aniekan, Skye, Kings
 * @version Group project (Game)
 *******************************************/
public class GameLayout extends StackPane
{
    //Instance data
    protected Logic gameLogic;
    private Random gen;
    protected Character player;
    protected Bridges bridgePane;
    protected UILayer ui;
    protected LoseScreen gameOverScreen;
    protected ContinueScreen continueScreen;
    protected StartScreen start;
    protected EndScreen end;
    protected NameInput input;
    protected FlashBang flash;
    protected double width;
    protected double height;
    protected double startPosX;
    protected double startPosY;
    protected Timeline timeline;
    protected AudioClip backgroundMusic;
    
    public GameLayout(double width, double height, AudioClip backgroundMusic)
    {
        super();
        this.setPrefWidth(width);
        this.setPrefHeight(height);
        
        timeline = new Timeline();
        gen = new Random();
        gameLogic = new Logic();
        player = new Character();
        flash = new FlashBang(width, height);
        
        this.width = width;
        this.height = height;
        this.startPosX = width/2;
        this.startPosY = height - 10;
        
        this.backgroundMusic = backgroundMusic;
        this.backgroundMusic.setCycleCount(AudioClip.INDEFINITE);
        this.backgroundMusic.play();
                
        drawBridgeAndPlayer();
        drawScreens();
    }
    
    
    /*********************************************************
     * A method to draw and add the bridge and player objects
     *********************************************************/
    private void drawBridgeAndPlayer()
    {
        // Brdige and player container
        StackPane container = new StackPane();
        
        // create the pane the player will move in and add the player
        Pane playerPane = new Pane();
        playerPane.setMouseTransparent(true);
        playerPane.setPrefWidth(width);
        playerPane.setPrefHeight(height);        
        playerPane.getChildren().add(player); 
        player.move(startPosX, startPosY, 0, false);
        
        // create the bridges
        bridgePane = new Bridges(width, height, gameLogic.getRoundNum(), gameLogic.getRoundPayout(), 
        // function that runs on every glass pane click
            new EventHandler<MouseEvent>() 
            {
                @Override
                public void handle(MouseEvent e) 
                {
                    timeline.getKeyFrames().clear();
                    // get the tile
                    Tile clicked = (Tile) e.getSource();
                    
                    // allow the movement of the player if the row position of teh tile matches te logic row pos
                    boolean allowMovement = gameLogic.getRowNum() == clicked.getRowNum();
                    
                    if (allowMovement)
                    {
                        // move player to tile
                        player.move(clicked.getCenterX(), clicked.getCenterY(), clicked.getRowNum(), true);                      
                        
                        // check if tile is broken
                        boolean isBroken = gen.nextDouble() < clicked.getBreakRisk();
                        if (isBroken)
                        {                            
                            // update the tile image and play glass breaking sound and fail the player
                            KeyFrame setBroke = new KeyFrame(Duration.millis(600), frame -> {
                                clicked.setImage("broken");
                                clicked.playBreak();
                                gameLogic.incRowNum();
                                gameLogic.lose();
                                updateAll();
                            });
                            timeline.getKeyFrames().add(setBroke);
                        }
                        else
                        {                            
                            // check how many vbucks it has and add it to the payout
                            int payout = clicked.getPayout();
                            if (payout != 0)
                            {
                                // add the payout to teh logic and play the coins sound
                                KeyFrame setBroke = new KeyFrame(Duration.millis(600), frame -> {
                                    gameLogic.addPayout(payout);
                                    clicked.setImage("regular");
                                    clicked.playMoney();
                                });
                                timeline.getKeyFrames().add(setBroke);
                            }
                            
                            // update the rowNum
                            gameLogic.incRowNum();
                            
                            // reveal the bad tiles on the row
                            KeyFrame reveal = new KeyFrame(Duration.millis(600), frame -> {
                                clicked.notReveal();
                                bridgePane.revealRows(gameLogic.getRowNum());
                                
                                // if they have sucessfully moved to teh third row then the win that round
                                if(gameLogic.getRowNum() == 3)
                                {
                                    gameLogic.setWonRound(true);
                                    gameLogic.addPayout(gameLogic.getRoundPayout());
                                }
                                updateAll();
                            });
                            timeline.getKeyFrames().add(reveal);
                        }
                        timeline.play();
                    }
                }
            }
        );
        
        // add bridge pane and player pane to contaner
        container.getChildren().addAll(bridgePane, playerPane);
        
        this.getChildren().addAll(container);
    }
    
    /*********************************************************************************
     * A method to draw the various screens of the game and layers them on the window
     *********************************************************************************/
    private void drawScreens()
    {
        ui = new UILayer(width, height);
        
        gameOverScreen = new LoseScreen(width, height, backgroundMusic);
        
        EventHandler<MouseEvent> clickCont = new EventHandler<MouseEvent>() 
        {
            @Override
            public void handle(MouseEvent e) 
            {
                flash.flash();
                player.move(startPosX, startPosY, 0, false);
                continueScreen.play();
                gameLogic.incRoundNum();
                gameLogic.setWonRound(false);
                updateAll();
            }
        };
        
        EventHandler<MouseEvent> clickStop = new EventHandler<MouseEvent>() 
        {
            @Override
            public void handle(MouseEvent e) 
            {
                gameLogic.setWonRound(false);
                gameLogic.win();
                updateAll();
            }
        };
        continueScreen = new ContinueScreen(width, height, backgroundMusic, clickCont, clickStop);
        
        end = new EndScreen(width, height);
        
        ResetBtn rst = new ResetBtn(width, height,
            new EventHandler<MouseEvent>() 
            {
                @Override
                public void handle(MouseEvent e) 
                {
                    reset();
                }
            }
        );
        
        
        input = new NameInput(width, height,
            new EventHandler<MouseEvent>() 
            {
                @Override
                public void handle(MouseEvent e) 
                {
                    String name = input.getText().trim();
                    boolean hasCharacters = !name.isEmpty();
                    boolean hasSpaces = name.contains(" ");
                    boolean emptyInput = name == null;
                    
                    if (hasCharacters && !hasSpaces && !emptyInput)
                    {
                        flash.flash();          
                        gameLogic.setName(name);
                        input.toggle();
                        gameLogic.start();
                        updateAll();
                    }
                }
            }
        );
                
        start = new StartScreen(width, height,
            new EventHandler<MouseEvent>() 
            {
                @Override
                public void handle(MouseEvent e) 
                {
                    input.toggle();
                }
            }
        ); 
        
        this.getChildren().addAll(ui, gameOverScreen, end, rst, continueScreen, start, input, flash);        
    }
    
    
    /*************************************************************************
     * A method to update all screens based off the current logic of the game
     **************************************************************************/
    private void updateAll()
    {
        end.updateEnd(gameLogic.getHasWon(), gameLogic.getPayout());
        start.updateStart(gameLogic.getStart());
        gameOverScreen.updateLose(gameLogic.getHasLost());
        continueScreen.updateContinue(gameLogic.getWonRound(), gameLogic.getRoundNum(), gameLogic.getPayout());
        ui.updateUI(gameLogic.getPayout());
        bridgePane.updateBridge(gameLogic.getRoundNum(), gameLogic.getRoundPayout());
    }
    
    /*********************************************************
     * A method to reset the game
     *********************************************************/
    private void reset()
    {
        gameLogic.save();
        gameLogic.reset();
        player.move(startPosX, startPosY, 0, false);
        bridgePane.resetRoundNum();
        updateAll();
        backgroundMusic.stop();
        backgroundMusic.play(0.2);
        input.clear();
    }
}
