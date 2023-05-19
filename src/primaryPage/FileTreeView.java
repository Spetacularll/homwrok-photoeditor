package primaryPage;

import javafx.scene.control.Label;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;


public class FileTreeView extends TreeView<File> {
    public StackPane rootpane = new StackPane();


    FileTreeView() throws IOException {
        setPrefSize(300, 800);
        setTreeView();
    }

    public void setTreeView() throws IOException {


        ImageView folderIcon = new ImageView();
        folderIcon.setPreserveRatio(true);
        folderIcon.setFitWidth(16);
        folderIcon.setFitHeight(16);


        File[] items = File.listRoots();

        TreeItem<File> mainTreeItem = new TreeItem<>();

        FileTreeViewController myTreeViewController = new FileTreeViewController();

        for (File item : items) {
            if(item.isDirectory()) {
                TreeItem<File> treeItem = new TreeItem<>(item);
                mainTreeItem.getChildren().add(treeItem);
                addItems(treeItem, 0);
            }
        }


        TreeView<File> treeView = new TreeView<>(mainTreeItem);
        rootpane.getChildren().add(treeView);

        treeView.setRoot(mainTreeItem);
        treeView.setShowRoot(false);



        //在这里判断点击了哪一个文件夹newValue
        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String path = newValue.getValue().getAbsolutePath();

            myTreeViewController.initEnterFolder(path);
            try {
                addItems(newValue, 0);

                addItems(newValue,  0);
                Main.pictureFlowPane.getPicture(newValue);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });





        //树状试图设置显示
        treeView.setCellFactory(new Callback<javafx.scene.control.TreeView<File>,TreeCell<File>>() {
            @Override
            public TreeCell<File> call(javafx.scene.control.TreeView<File> param) {
                return new TreeCell<File>() {
                    @Override
                    protected void updateItem(File item, boolean empty) {

                        if (!empty) {
                            super.updateItem(item, false);
                            Label label = new Label(isListRoots(item));
                            this.setStyle("-fx-border-color: rgb(244,244,244)");
                            this.setGraphic(label);
                        } else {
                            this.setGraphic(null);
                        }
                    }
                };
            }
        });






    }

    /*
    注意这里不能将文件夹全部放入到File[],否者内存会爆
     */

    public void addItems(TreeItem<File> in, int flag) throws IOException {
        File[] fileList = in.getValue().listFiles();
        if (fileList != null) {
            if (flag == 0) {
                in.getChildren().remove(0, in.getChildren().size());
            }

            if (fileList.length > 0) {
                for (File file : fileList) {
                    if (file.isDirectory() && !file.isHidden()) {
                        TreeItem<File> newItem = new TreeItem<>(file);
                        if (flag < 1) {
                            //flag小于1后我们就一共调用两次addItems函数，就层数只有2
                            addItems(newItem, flag + 1);
                        }
                        in.getChildren().add(newItem);
                    }
                }
            }





        }
    }

    public String isListRoots(File item) {
        File[] rootlist = File.listRoots();
        for (File isListRoots : rootlist) {
            if (item.toString().equals(isListRoots.toString())) {
                return item.toString();
            }
        }
        return item.getName();
    }




}