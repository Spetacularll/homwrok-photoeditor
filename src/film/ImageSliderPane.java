package film;

import edit.controller.EditController;
import edit.controller.EditPage;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.ArrayList;

import static primaryPage.PictureFlowPane.imageArrayList;

public class ImageSliderPane extends BorderPane {

    private final ArrayList<Image> images;
    private final ImageView imageView;
    private final Timeline timeline;
    private final MediaPlayer mediaPlayer;
    Button previousButton;
    Button playButton;

    Button pauseButton;
    Button nextButton;
    Button editButton;
    Button zoomButton;
    Button resetButton;
    HBox buttonsPane;
    int currentIndex;


    public void initButton() {
        previousButton = new Button("Previous");
        previousButton.setOnAction(event -> previousImage());
        playButton = new Button("Play");
        playButton.setOnAction(event -> play());
        pauseButton = new Button("Pause");
        pauseButton.setOnAction(event -> pause());
        nextButton = new Button("Next");
        nextButton.setOnAction(event -> nextImage());
        editButton = new Button("Edit");
        zoomButton = new Button("Zoom~");
        resetButton = new Button("Reset");
        editButton.setOnAction(event ->{
            currentIndex = images.indexOf(imageView.getImage());
            if(currentIndex<=imageArrayList.size()&&currentIndex!=-1){
                if(imageArrayList.get(currentIndex).endsWith(".gif"))
                {
                    pause();
                    mediaPlayer.pause();pause();
                    mediaPlayer.pause();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.titleProperty().set("提示");
                    alert.headerTextProperty().set("不能编辑gif！");
                    alert.show();
                }
                else {
                    //? ?这段代码使得第一张图片无法编辑
//                    if(currentIndex==0){
//                        String str = imageArrayList.get(currentIndex);
//                        EditController.filePath = str.substring(5);
//                    }else {
//                        EditController.filePath = imageArrayList.get(currentIndex);
//                    }
                    EditController.filePath = imageArrayList.get(currentIndex);
                    new EditPage();
                }
            }


        });
        resetButton.setOnAction(event->{
            imageView.setTranslateX(0);
            imageView.setTranslateY(0);
            imageView.setScaleX(1);
            imageView.setScaleY(1);
            imageView.setOnScroll(null);
            imageView.setOnMouseMoved(null);
        });
        zoomButton.setOnAction(e ->{
            imageView.setOnMouseMoved(eventstart -> {
                double mouseX = eventstart.getSceneX();
                double mouseY = eventstart.getSceneY();
                imageView.setOnScroll(event -> {
                    double SCALE_DELTA = 1.1;
                    double deltaY = event.getDeltaY();
                    double zoomFactor = deltaY > 0 ? SCALE_DELTA : 1 / SCALE_DELTA;
                    imageView.setScaleX(imageView.getScaleX() * zoomFactor);
                    imageView.setScaleY(imageView.getScaleY() * zoomFactor);

                    // Move the viewport so that the user remains centered on the image
                    Bounds bounds = imageView.localToScene(imageView.getBoundsInLocal());
                    double dx = (mouseX - (bounds.getWidth() / 2 + bounds.getMinX()));
                    double dy = (mouseY - (bounds.getHeight() / 2 + bounds.getMinY()));
                    imageView.setTranslateX(imageView.getTranslateX() - dx * (zoomFactor - 1));
                    imageView.setTranslateY(imageView.getTranslateY() - dy * (zoomFactor - 1));
                    event.consume();
                });


                });
            });



        buttonsPane = new HBox(previousButton, nextButton, playButton, pauseButton,zoomButton,resetButton,editButton);
        buttonsPane.setAlignment(Pos.CENTER);
        buttonsPane.setSpacing(10);
        buttonsPane.setId("buttonsPane");
        setBottom(buttonsPane);

    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public ArrayList<Image> ConvertList(ArrayList<String> images){
        ArrayList<Image> ans = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            ans.add(new Image(images.get(i)));
        }
        return ans;
    }

    public ImageSliderPane(ArrayList<String> images, Media backgroundMusic) {
        this.images = ConvertList(images);
        imageView = new ImageView(images.get(0));
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);
        timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> nextImage()));
        mediaPlayer = new MediaPlayer(backgroundMusic);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(0.5);
        initButton();
        play();
        setCenter(imageView);
        setBottom(buttonsPane);

    }

    private void previousImage() {
         currentIndex = images.indexOf(imageView.getImage());
        if (currentIndex == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.titleProperty().set("提示");
            alert.headerTextProperty().set("已经到顶了！");
            alert.show();
            alert.setOnHidden(evt -> resizeWindow());
        } else {
            imageView.setImage(images.get(currentIndex - 1));
            resizeWindow();
        }

    }

    private void nextImage() {
         currentIndex = images.indexOf(imageView.getImage());
        if (currentIndex == images.size() - 1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText("到底了！");
            alert.show();
            alert.setOnHidden(evt -> resizeWindow());
            timeline.pause();
        } else {
            imageView.setImage(images.get(currentIndex + 1));
            resizeWindow();
        }
    }



    private void resizeWindow() {
        imageView.fitWidthProperty().bind(widthProperty());
        Node bottomNode = lookup("#buttonsPane");
        if (bottomNode != null) {
            imageView.fitHeightProperty().bind(heightProperty().subtract(((HBox) bottomNode).getHeight()));
        }
    }

    private void play() {
        mediaPlayer.play();
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        resizeWindow();
    }

    private void pause() {
        mediaPlayer.pause();
        timeline.pause();
    }

    public Timeline getTimeline() {
        return timeline;
    }
}