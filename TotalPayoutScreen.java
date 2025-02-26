import javafx.scene.layout.*;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
/**
 * Write a description of class TotalPayoutScreen here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TotalPayoutScreen extends StackPane
{    
    protected Text payoutText;
    
    public TotalPayoutScreen()
    {
        super();
        payoutText = new Text("0");
        
        ImageView bg = new ImageView(new Image("./assets/images/payout_screen.png"));
        bg.setPreserveRatio(true);
        bg.setFitWidth(100);
        this.getChildren().addAll(bg, payoutText);
    }
    
    public void setPayout(int payout)
    {
        payoutText.setText(""+payout);
    }
}
