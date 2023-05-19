package primaryPage;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class TextPane extends HBox{
    public final HBox hBox=new HBox();

    public final Label text1=new Label();
    public  final Label text2=new Label();
    public Button button=new Button("播放");
    public TextPane() {
        // TODO Auto-generated constructor stub
        hBox.getChildren().addAll(new HBox(40,text1,text2));
        this.getChildren().addAll(hBox);
        this.setAlignment(Pos.CENTER_LEFT);
    }
}
