package primaryPage;

import javafx.application.Platform;

import java.io.File;

import static primaryPage.PictureFlowPane.fileArrayList;
import static primaryPage.PictureFlowPane.loaded;

public class LoadTb implements Runnable{

    public volatile boolean running=true;
    public int fileCount;
    public int Loaded;
    public LoadTb(int fileCount){
        this.fileCount=fileCount;
    }

    @Override
    public void run(){
        Loading();
    }
    private void Loading(){
    if (fileCount != 0) {
        int t;
        File value;
        for (t = loaded; t < loaded+50; t++) {
            if(!running||t==fileArrayList.size()){
                break;
            }
            value = fileArrayList.get(t);
            String fileName = value.getName();
            ImageBoxButton imageBoxLabel = new ImageBoxButton("File:" + value.getAbsolutePath(), fileName);
            //Loading.
            Platform.runLater(()->{
                 Main.pictureFlowPane.flowPane.getChildren().add(imageBoxLabel.getImageLabel());
            });
        }
        System.out.println(t);
        loaded =  t ;
    }
    }
}
