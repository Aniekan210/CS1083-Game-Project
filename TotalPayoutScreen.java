import javafx.scene.layout.*;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
/**
 * Write a description of class TotalPayoutScreen here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TotalPayoutScreen extends Pane
{    
    protected Text payoutText;
    
    public TotalPayoutScreen()
    {
        super();
        payoutText = new Text("0");
        payoutText.setFont(Font.font("Comic Sans MS", 30));
        payoutText.setFill(Color.WHITE);
        payoutText.setStroke(Color.BLACK);
        payoutText.setStrokeWidth(1);
        payoutText.setLayoutX(15);
        payoutText.setLayoutY(57);
        
        ImageView bg = new ImageView(new Image("./assets/images/payout_screen.png"));
        bg.setPreserveRatio(true);
        bg.setFitWidth(150);
        this.getChildren().addAll(bg, payoutText);
    }
    
    public void setPayout(int payout)
    {
        payoutText.setText(""+payout);
    }
}
