package primaryPage;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;


public class PicInfPane extends Pane {
    public static FlowPane imagePane = new FlowPane();
    public ScrollPane scrollPane = new ScrollPane();     //滚动面板
    PicInfPane(){
        super();
        setPrefSize(330,760);
        setStyle("-fx-background-color:rgb(255,255,255)");
        setStyle("-fx-border-color: rgb(216,216,216)");


        imagePane.setPadding(new Insets(12,13,14,15));
        imagePane.setOrientation(Orientation.VERTICAL);
        imagePane.setHgap(8);
        imagePane.setVgap(5);

        imagePane.setPrefSize(330,718);
        getChildren().add(imagePane);
        scrollPane.setContent(imagePane);
        scrollPane.setFitToHeight(true);
    }



}