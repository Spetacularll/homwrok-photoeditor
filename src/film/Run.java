package film;

import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import static primaryPage.PictureFlowPane.imageArrayList;

public class Run {
    Media backgroundMusic;
    String imagePath;
    ImageSliderPane imageSliderPane;

    public Run(){
        if(!imageArrayList.isEmpty()){
            Stage stage = new Stage();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Music File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Music Files", "*.mp3", "*.wav", "*.aac"),
                    new FileChooser.ExtensionFilter("All Files", "*.*"));
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                // Do something with the selected file
                backgroundMusic = new Media(selectedFile.toURI().toString());

            }else{
                backgroundMusic = new Media(new File("BGM/02 Levitate.mp3").toURI().toString());
            }
            imageSliderPane = new ImageSliderPane(imageArrayList,backgroundMusic);
            Scene scene = new Scene(imageSliderPane,600,800);
            scene.setFill(Color.DIMGRAY);
            stage.setScene(scene);
            stage.show();
            imageSliderPane.getScene().getWindow().setOnCloseRequest(windowEvent -> {
                MediaPlayer BGM = imageSliderPane.getMediaPlayer();
                BGM.stop();
                Timeline timeline = imageSliderPane.getTimeline();
                timeline.stop();
            });
        }
    }
    //exchange the index with image.get(0)
    public void arrange(String url){
        if(!imageArrayList.isEmpty()){
            for (String value :
                    imageArrayList) {
                if(value==url){
                    int index = imageArrayList.indexOf(value);
                    imageArrayList.remove(index);
                }
            }
        }

    }
    public Run(Image image){

        //TODO Adjust the imageArrayList
        arrange(image.getUrl());
        imageArrayList.add(0,image.getUrl());

        if(!imageArrayList.isEmpty()){
            Stage stage = new Stage();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Music File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Music Files", "*.mp3", "*.wav", "*.aac"),
                    new FileChooser.ExtensionFilter("All Files", "*.*"));
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                // Do something with the selected file
                backgroundMusic = new Media(selectedFile.toURI().toString());

            }else{
                backgroundMusic = new Media(new File("BGM/02 Levitate.mp3").toURI().toString());
            }
            imageSliderPane = new ImageSliderPane(imageArrayList,backgroundMusic);
            Scene scene = new Scene(imageSliderPane,600,800);
            scene.setFill(Color.DIMGRAY);
            stage.setScene(scene);
            stage.show();
            imageSliderPane.getScene().getWindow().setOnCloseRequest(windowEvent -> {
                MediaPlayer BGM = imageSliderPane.getMediaPlayer();
                BGM.stop();
                Timeline timeline = imageSliderPane.getTimeline();
                timeline.stop();
            });
        }


    }
}
