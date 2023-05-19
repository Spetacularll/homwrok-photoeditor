package primaryPage;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    public static PictureFlowPane pictureFlowPane;
    public static TextPane myTextPane;
    public static ImageMenuItem imagemenuitem=new ImageMenuItem();


    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("图片管理系统");
        primaryStage.getIcons().add(new Image("file:file/图标1.jpg"));
        primaryStage.setMaximized(true);//能够最大化
        primaryStage.setResizable(true);//能够改变窗口大小

        BorderPane borderPane = new BorderPane();


        //附加功能栏
        Function function=new Function();
        borderPane.setTop(function.hBox);

        //具体信息栏
        PicInfPane myPane = new PicInfPane();
        borderPane.setRight(myPane.scrollPane);

        //目录树
        FileTreeView myTreeView = new FileTreeView();
        borderPane.setLeft(myTreeView.rootpane);

        //图片缩放pane
        pictureFlowPane=new PictureFlowPane();
        borderPane.setCenter(pictureFlowPane);



        //底部信息pane
        myTextPane=new TextPane();
        borderPane.setBottom(myTextPane);


        //建立拖动控件
        MouseController mouseController = new MouseController();

        //程序关闭后清空粘贴版内容
        primaryStage.setOnCloseRequest(event->{
            Clipboard.getSystemClipboard().clear();
        });


        Scene scene=new Scene(borderPane,1300,780);
        primaryStage.setScene(scene);
        primaryStage.show();
    }




    public static void main(String[] args) {
        launch(args);
    }
}
