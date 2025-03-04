import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

/***************************************************
 * this class starts up the game
 *
 * @author Aniekan , Skye, Kings
 * @version Latest version of start
 ***************************************************/
public class StartGame extends Application
{
    private AudioClip backgroundMusic;
    
    @Override
    public void start(Stage stage)
    {
        // Create a new grid pane
        Pane pane = new Pane();
        
        // PLEASE DO NOT CHANGE THESE VALUES!!!!!!!
        double width = 750;
        double height = 600;
        backgroundMusic = new AudioClip("file:assets/sounds/background.mp3");
        backgroundMusic.setVolume(0.2);
        
        // Create Game Logic
        GameLayout layout = new GameLayout(width, height, backgroundMusic);
        
        pane.getChildren().add(layout);
         
        Image icon = new Image("./assets/images/icon.png");
        Scene scene = new Scene(pane, width, height);
        stage.setTitle("Tread Lightly");
        stage.setScene(scene);
        stage.getIcons().add(icon);
        stage.setResizable(false);

        // Show the Stage (window)
        stage.show();
    }
    
    @Override
    public void stop()
    {
        backgroundMusic.stop();
    }
}
