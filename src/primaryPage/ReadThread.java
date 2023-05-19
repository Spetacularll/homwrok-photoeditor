package primaryPage;

import javafx.application.Platform;

import java.io.File;
import java.net.MalformedURLException;

import static primaryPage.PictureFlowPane.*;

//读取图片的线程
public class ReadThread extends Thread {

    int num;
    int i;
    public static int numThread;

    public ReadThread() {
        num = PictureFlowPane.num;
    }

    public ReadThread(int num) {
        this.num = num;
    }

    @Override
    public void run() {
        File value;
        i = getI(num);

        for (int t = i - num; t < i; t++) {
            value = fileArrayList.get(t);
            String fileName = value.getName();
            //支持图片的格式
            ImageBoxButton imageBoxLabel = new ImageBoxButton("File:" + value.getAbsolutePath(), fileName);

            Platform.runLater(() -> {
                Main.pictureFlowPane.flowPane.getChildren().add(imageBoxLabel.getImageLabel());
            });

            try {
                PitureFile.add(value.toURI().toURL().toString());
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
        if (numThread > 1) {
            numThread--;
            new ReadThread(num).start();
        }
    }
}