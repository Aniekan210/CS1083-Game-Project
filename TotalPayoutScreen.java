import javafx.scene.layout.*;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
/*****************************************************************
 * This class shows the total number of vbucks won by the player
 *
 * @author CS Group Aniekan
 * @version new feature
 ****************************************************************/
public class TotalPayoutScreen extends Pane
{    
    protected Text payoutText;
    
    public TotalPayoutScreen()
    {
        //Text showing the amount of vbucks won by the player so far
        super();
        payoutText = new Text("0");
        payoutText.setFont(Font.font("Comic Sans MS", 30));
        payoutText.setFill(Color.WHITE);
        payoutText.setStroke(Color.BLACK);
        payoutText.setStrokeWidth(1);
        payoutText.setLayoutX(15);
        payoutText.setLayoutY(57);

        //place the text in the box
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
