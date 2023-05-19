package primaryPage;

import javafx.application.Platform;

import java.io.File;

import static primaryPage.PictureFlowPane.fileArrayList;

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
        File value;
        for (int t = 0; t < 50; t++) {
 System.out.println(Loaded);
            if(!running||t==fileArrayList.size()){
                break;
            }

            this.Loaded = t+1;

            value = fileArrayList.get(t);
            String fileName = value.getName();
            ImageBoxButton imageBoxLabel = new ImageBoxButton("File:" + value.getAbsolutePath(), fileName);
            //Loading.
            Platform.runLater(()->{
                 Main.pictureFlowPane.flowPane.getChildren().add(imageBoxLabel.getImageLabel());
            });


        }
    }
    }
}
