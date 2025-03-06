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
 *The is the setup and view of the game
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
        gen = new Random();
        gameLogic = new Logic();
        player = new Character();
        
        this.backgroundMusic = backgroundMusic;
        this.backgroundMusic.setCycleCount(AudioClip.INDEFINITE);
        this.backgroundMusic.play();
        
        this.width = width;
        this.height = height;
        
        this.startPosX = width/2;
        this.startPosY = height - 10;
        
        timeline = new Timeline();
        
        drawBridgeAndPlayer();
        drawScreens();
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
        player.move(startPosX, startPosY, 0, false);
        
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
                        player.move(clicked.getCenterX(), clicked.getCenterY(), clicked.getRowNum(), true);                      
                        
                        // check if tile is broken
                        boolean isBroken = gen.nextDouble() < clicked.getBreakRisk();
                        if (isBroken)
                        {                            
                            // update the tile image
                            timeline.getKeyFrames().clear();
                            KeyFrame setBroke = new KeyFrame(Duration.millis(600), frame -> {
                                clicked.setImage("broken");
                                clicked.playBreak();
                            });
                            timeline.getKeyFrames().add(setBroke);
                            timeline.play();
                        
                            // fail the player
                            gameLogic.lose();
                        }
                        else
                        {
                            timeline.getKeyFrames().clear();
                            
                            // check how many vbucks it has and add it to the payout
                            int payout = clicked.getPayout();
                            if (payout != 0)
                            {
                                gameLogic.addPayout(payout);
                                
                                KeyFrame setBroke = new KeyFrame(Duration.millis(600), frame -> {
                                    clicked.setImage("regular");
                                    clicked.playMoney();
                                });
                                timeline.getKeyFrames().add(setBroke);
                            }
                            
                            KeyFrame reveal = new KeyFrame(Duration.millis(600), frame -> {
                                bridgePane.revealRows(gameLogic.getRowNum());
                            });
                            timeline.getKeyFrames().add(reveal);
                            timeline.play();

                            // update the rowNum
                            gameLogic.incRowNum();
                        }
                    }
                    
                    if(gameLogic.getRowNum() == 3)
                    {
                        gameLogic.setRowNum(2);
                        gameLogic.setWonRound(true);
                    }
                     // Update the rest of the game
                    updateAll();
                }
            }
        );
        
        // add bridge pane and player pane to contaner
        container.getChildren().addAll(bridgePane, playerPane);
        
        this.getChildren().addAll(container);
    }
    
    private void drawScreens()
    {
        ui = new UILayer(width, height);
        
        gameOverScreen = new LoseScreen(width, height, backgroundMusic);
        
        EventHandler<MouseEvent> clickCont = new EventHandler<MouseEvent>() 
        {
            @Override
            public void handle(MouseEvent e) 
            {
                continueScreen.flash();
                gameLogic.addPayout(gameLogic.getRoundPayout());
                gameLogic.incRoundNum();
                gameLogic.setWonRound(false);
                player.move(startPosX, startPosY, 0, false);
                updateAll();
            }
        };
        
        EventHandler<MouseEvent> clickStop = new EventHandler<MouseEvent>() 
        {
            @Override
            public void handle(MouseEvent e) 
            {
                gameLogic.win();
                gameLogic.addPayout(gameLogic.getRoundPayout());
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
                    
                    if (!hasCharacters || hasSpaces || emptyInput)
                    {
                        
                    }
                    else
                    {
                        input.flash();          
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
        
        this.getChildren().addAll(ui, gameOverScreen, continueScreen, end, rst, start, input);        
    }
     
    private void updateAll()
    {
        end.updateEnd(gameLogic.getHasWon(), gameLogic.getPayout());
        start.updateStart(gameLogic.getStart());
        gameOverScreen.updateLose(gameLogic.getHasLost());
        continueScreen.updateContinue(gameLogic.getWonRound(), gameLogic.getRoundNum(), gameLogic.getRoundPayout());
        ui.updateUI(gameLogic.getPayout());
                
        // add 
        bridgePane.updateBridge(gameLogic.getRoundNum(), gameLogic.getRoundPayout());
    }
    
    private void reset()
    {
        gameLogic.save();
        gameLogic.reset();
        player.move(startPosX, startPosY, 0, false);
        bridgePane.resetRoundNum();
        updateAll();
        backgroundMusic.stop();
        backgroundMusic.play(0.2);
    }
}
