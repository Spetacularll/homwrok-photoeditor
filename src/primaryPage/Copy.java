package primaryPage;


import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

import java.io.File;
import java.text.DecimalFormat;


public class Copy {
    public static String dir;

    public Copy()
    {
        dir= PictureFlowPane.filePath;//获得要复制区域的目录
        ImageBoxButton.getSelectedPictureFiles().clear();//清空选择图片文件数组
        //没有选择到图片进行复制操作时弹出警告
        if(ImageBoxButton.getSelectedPictures().size()<=0)
        {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.titleProperty().set("警告");
            alert.headerTextProperty().set("请先选择一张图片");
            alert.showAndWait();

        }
        if(ImageBoxButton.getSelectedPictures().size() > 0) {

            ImageBoxButton.getCopyPictures().clear();//清空上次复制图片数组
        }
        Clipboard clipboard = Clipboard.getSystemClipboard();//获取系统剪贴板
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboard.clear();

        //选择图片数组的图片依次将对应的文件加入到选择图片文件数组
        for(ImageBoxButton label : ImageBoxButton.getSelectedPictures()) {
            ImageBoxButton.getSelectedPictureFiles().add(label.getImageFile());
        }
        //选择图片文件数组导入到剪切板
        clipboardContent.putFiles(ImageBoxButton.getSelectedPictureFiles());
        clipboard.setContent(clipboardContent);
        clipboard = null;
        clipboardContent = null;


    }

}
