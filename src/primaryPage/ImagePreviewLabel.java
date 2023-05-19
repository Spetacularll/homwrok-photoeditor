package primaryPage;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImagePreviewLabel {
    private final Label imageLabeled = new Label();

    ImagePreviewLabel(String imagePath,int width,int height){
        PicInfPane.imagePane.getChildren().clear();

        imageLabeled.setPrefSize(width,height);
        imageLabeled.setWrapText(true);
        imageLabeled.setAlignment(Pos.BASELINE_CENTER);
        Image image = new Image(imagePath);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);

        imageView.setPreserveRatio(true);//保持缩放比例

        imageLabeled.setGraphic(imageView);
    }


    public Label getImageLabeled() {
        return imageLabeled;
    }

    public void addLabelOnPane(Label label){
        PicInfPane.imagePane.getChildren().add(label);
    }
}