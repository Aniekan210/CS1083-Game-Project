import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.util.Random;

/**
 * Write a description of JavaFX class Game here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class StartGame extends Application
{
    @Override
    public void start(Stage stage)
    {
        // Create a new grid pane
        Pane pane = new Pane();
        double width = 600;
        double height = 600;
        
        // Create Game Logic
        GameLayout layout = new GameLayout(width, height);
        
        pane.getChildren().add(layout);
        
        Scene scene = new Scene(pane, width, height);
        stage.setTitle("Tread Lightly");
        stage.setScene(scene);

        // Show the Stage (window)
        stage.show();
    }
}
