package primaryPage;


import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;


public class ImageMenuItem{
    private final   ContextMenu contextMenu = new ContextMenu();//每张图片的菜单项组


    public final  ContextMenu maincontextMenu =new ContextMenu();//PicturePane的菜单组
    MenuItem selectedAll=new MenuItem("全选");
    MenuItem paste=new MenuItem("粘贴");
    MenuItem delete = new MenuItem("删除");
    MenuItem copy = new MenuItem("复制");
    MenuItem reName = new MenuItem("重命名");



    ImageMenuItem(){
        delete.setStyle("-fx-text-fill:RED");
        //复制
        copy.setOnAction(e->{
            new Copy();
        });
        //粘贴
        paste.setOnAction(e->{
            new Paste();
        });
        //全选
        selectedAll.setOnAction(e->{
            for (Node node :  Main.pictureFlowPane.flowPane.getChildren())
                ((ImageBoxButton)node).setSelected(true);
        });
        //重命名
        reName.setOnAction(e->{
            //当没有被选择的图片被重命名，弹出警告
            if(ImageBoxButton.getSelectedPictures().size()==0)
            {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.titleProperty().set("警告");
                alert.headerTextProperty().set("请先选择一张图片");
                alert.showAndWait();
            }
            else
            {

                new Rename();
            }
        });
        //删除
        delete.setOnAction(e->{
            new Delete();
        });

        maincontextMenu.getItems().addAll(selectedAll,paste);
        contextMenu.getItems().addAll(copy,delete,reName);
        contextMenu.setStyle("-fx-background-color:rgb(255, 255, 255, .85)");    //透明设置


    }

    public ContextMenu getContextMenu(){
        return contextMenu;
    }

    public MenuItem getPaste()
    {
        return paste;
    }

    // 获取不带扩展名的文件名
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }
}