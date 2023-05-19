package primaryPage;


import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.input.Clipboard;
import javafx.scene.input.DataFormat;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class PictureFlowPane extends StackPane {
    public final FlowPane flowPane = new FlowPane();
    public static TreeItem<File> file = new TreeItem<>();
    public static int fileCount = 0;
    public static int i = 0;
    static int i1, i2;
    public static int num = 5;//The static int used for loading.

    public static int getI(int num) {
        i += num;
        return i;
    }

    public ScrollPane scrollPane = new ScrollPane();
    public static ImageMenuItem imagemenuitem = new ImageMenuItem();
    public static String filePath;

    public static ArrayList<File> fileArrayList = new ArrayList<>();
    public static double sizeOfImage = 0;
    public static String sizeofimage;
    public static int v = 0;    //判断图片总大小是否大于1MB
    public static ArrayList<String> imageArrayList = new ArrayList<>();
    public static final ArrayList<String> PitureFile = new ArrayList<>();


    public static void setFile(TreeItem<File> file) {
        PictureFlowPane.file = file;
    }

    public static void setFilePath(TreeItem<File> file) {
        PictureFlowPane.filePath = file.getValue().getAbsolutePath();
    }

    public PictureFlowPane() throws IOException {
        super();
        flowPane.setPadding(new Insets(10, 20, 20, 20));
        flowPane.setOrientation(Orientation.HORIZONTAL);
        flowPane.setHgap(20);
        flowPane.setVgap(25);
        flowPane.setStyle("-fx-background-color: rgb(245,245,245)");
        flowPane.setPrefSize(670, 760);
        //加入用于显示图片的flowPane与用于鼠标拖拽的界面
        scrollPane.vvalueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.doubleValue() >= scrollPane.getVmax()) {

                if (fileCount > 0) {
                    if (i1 > 0) {
                        i1--;
                        ReadThread.numThread = 4;
                        new ReadThread(5).start();

                    } else if (i2 > 0) {
                        ReadThread.numThread = i2;
                        new ReadThread(1).start();

                        i2 = 0;
                    }
                }
            }
        });
        scrollPane.setContent(flowPane);
        scrollPane.setFitToWidth(true);
        this.getChildren().addAll(scrollPane, MouseController.pane);
        setEvents();

    }

    //三个面板都设置事件
    private void setEvents() {

        //两者设置点击事件,分别放入该图片界面的上方
        MouseController.pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                getChildren().clear();
                getChildren().add(scrollPane);
            }
        });
        flowPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                getChildren().clear();
                getChildren().addAll(scrollPane, MouseController.pane);
            }
        });
        //ScrollPane:
        //图片面板设置事件
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) ->
        {
            Node clickedPane = e.getPickResult().getIntersectedNode();
            // 鼠标点击非图片节点,即图片面板的空白区域
            if (clickedPane instanceof Pane) {
                //按两下左键取消选中
                if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() > 1) {
                    ImageBoxButton.clearSelected();// 取消被选择图片的选择状态
                }

                // 鼠标右键，弹出菜单栏
                if (e.getButton() == MouseButton.SECONDARY && e.getClickCount() == 1) {
                    ImageBoxButton.clearSelected();// 取消被选择图片的选择状态
                    Clipboard clipboard = Clipboard.getSystemClipboard();
                    List<File> files = (List<File>) (clipboard.getContent(DataFormat.FILES));
                    if (files.size() == 0) {
                        imagemenuitem.getPaste().setDisable(true);//系统剪切版为空时，显示不可点击状态
                    } else {
                        imagemenuitem.getPaste().setDisable(false);//显示可点击状态
                    }
                    imagemenuitem.maincontextMenu.show(this, e.getScreenX(), e.getScreenY());//实现在鼠标坐标位置
                }
                //菜单栏隐藏
                else {
                    //当再次点击空白区域且菜单栏已显示时，取消显示
                    if (imagemenuitem.maincontextMenu.isShowing()) {
                        imagemenuitem.maincontextMenu.hide();
                        ImageBoxButton.clearSelected();
                    }
                }
            }
            //点击图片的任意操作都会将菜单隐藏
            else if (clickedPane instanceof ImageBoxButton) {
                if (imagemenuitem.maincontextMenu.isShowing()) {

                    imagemenuitem.maincontextMenu.hide();
                }
                if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2) {

                }
            }
        });


    }

    public void getPicture(TreeItem<File> file) throws MalformedURLException {
        //清空和重新设置成员变量


        PitureFile.clear();
        Main.pictureFlowPane.flowPane.getChildren().clear();
        imageArrayList.clear();
        setFile(file);
        setFilePath(file);
        i = 0;
        fileCount = 0;
        fileArrayList.clear();

        fileArrayList.addAll(List.of(Objects.requireNonNull(file.getValue().listFiles())));


        //读取目录信息

        Thread thread = new Thread(() -> {
            try {
                Thread getDataThread = new GetDataThread();
                getDataThread.start();
                getDataThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Platform.runLater(() -> {
                if (v == 1) {
                    Main.myTextPane.text1.setText("总共" + fileCount + "个项目" + "             大小：" + sizeofimage + "MB");
                } else {
                    Main.myTextPane.text1.setText("总共" + fileCount + "个项目" + "            大小：" + sizeofimage + "KB");
                }
                sizeOfImage = 0;
            });

            if (fileCount != 0) {
                i1 = fileCount / 20;
                i2 = fileCount % 20;
                if (i1 >= 2) {
                    i1 -= 2;
                    //先读取40张
                    ReadThread.numThread = 4;
                    ReadThread readThread = new ReadThread(10);
                    readThread.start();
                } else {
                    ReadThread.numThread = i1 * 20 + i2;
                    ReadThread readThread = new ReadThread(1);
                    readThread.start();
                    i1 = i2 = 0;
                }
            }
        });
        thread.start();
    }
}
