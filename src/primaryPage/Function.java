package primaryPage;

import film.Run;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.MalformedURLException;

public class Function extends HBox {


    HBox hBox = new HBox();
    MenuBar menuBar = new MenuBar();
    Button button = new Button("播放");
    public static int v1 = 0;


    Function() {

        Menu contact = new Menu("联系我们");
        MenuItem phone = new MenuItem("电话");
        MenuItem Wechat = new MenuItem("微信");
        Image image1 = new Image("file:file/客服.png");
        ImageView imageView1 = new ImageView(image1);
        imageView1.setFitHeight(16);
        imageView1.setFitWidth(16);
        contact.setGraphic(imageView1);
        phone.setOnAction(actionEvent -> {
            Stage stage = new Stage();
            stage.setAlwaysOnTop(true);
            stage.initStyle(StageStyle.UTILITY);
            stage.setTitle("客服电话:");
            Text text1 = new Text("客服1:12345657");
            Text text2 = new Text("客服2:99999999");
            VBox vBox = new VBox(text1, text2);
            Scene scene = new Scene(vBox, 200, 50);
            stage.setScene(scene);
            stage.show();
        });
        Wechat.setOnAction(actionEvent -> {
            Stage stage = new Stage();
            stage.setAlwaysOnTop(true);
            stage.initStyle(StageStyle.UTILITY);
            stage.setTitle("客服微信:");
            Text text1 = new Text("客服1:kefu12345657");
            Text text2 = new Text("客服2:kefu99999999");
            VBox vBox = new VBox(text1, text2);
            Scene scene = new Scene(vBox, 200, 50);
            stage.setScene(scene);
            stage.show();
        });
        contact.getItems().addAll(phone, Wechat);

        Menu setup = new Menu("设置");
        Image image2 = new Image("file:file/设置.png");
        ImageView imageView2 = new ImageView(image2);
        imageView2.setFitHeight(16);
        imageView2.setFitWidth(16);
        Menu size = new Menu("缩略图大小");
        MenuItem superbig = new MenuItem("超大图");
        MenuItem big = new MenuItem("大图(默认)");
        MenuItem mid = new MenuItem("中等大小");
        MenuItem small = new MenuItem("小图");
        superbig.setOnAction(e -> {
            v1 = 1;
            ImageBoxButton.getSelectedPictures().clear();

            try {
                Main.pictureFlowPane.getPicture(PictureFlowPane.file);
            } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
            }

        });
        big.setOnAction(e -> {
            v1 = 0;
            ImageBoxButton.getSelectedPictures().clear();

            try {
                Main.pictureFlowPane.getPicture(PictureFlowPane.file);
            } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
            }

        });
        mid.setOnAction(e -> {
            v1 = 2;
            ImageBoxButton.getSelectedPictures().clear();

            try {
                Main.pictureFlowPane.getPicture(PictureFlowPane.file);
            } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
            }

        });
        small.setOnAction(e -> {
            v1 = 3;
            ImageBoxButton.getSelectedPictures().clear();

            try {
                Main.pictureFlowPane.getPicture(PictureFlowPane.file);
            } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
            }

        });
        size.getItems().addAll(superbig, big, mid, small);
        MenuItem our = new MenuItem("关于我们");
        setup.getItems().addAll(size, our);
        setup.setGraphic(imageView2);
        menuBar.getMenus().addAll(contact, setup);
        Image image3 = new Image("file:file/播放.png");
        ImageView imageView4 = new ImageView(image3);
        imageView4.setFitHeight(16);
        imageView4.setFitWidth(16);
        button.setGraphic(imageView4);

        button.setOnAction(e -> {
            PictureFlowPane.imageArrayList.clear();
            for (File value :
                    PictureFlowPane.fileArrayList) {

                String FilePath = value.getAbsolutePath();
                PictureFlowPane.imageArrayList.add(FilePath);
            }
            Run run = new Run();
        });

        hBox.getChildren().addAll(menuBar, button);
        hBox.setSpacing(430);
    }
}