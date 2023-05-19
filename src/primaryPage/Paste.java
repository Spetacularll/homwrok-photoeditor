package primaryPage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;


import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.Clipboard;
import javafx.scene.input.DataFormat;


public class Paste {


    public Paste()
    {
        Clipboard clipboard = Clipboard.getSystemClipboard();//获得系统剪贴板
        @SuppressWarnings("unchecked")
        List<File> files = (List<File>) (clipboard.getContent(DataFormat.FILES));//获得剪贴板的文件
        if (files.size() <= 0)
        {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.titleProperty().set("警告");
            alert.headerTextProperty().set("粘贴失败");
            alert.showAndWait();
        }
        if (ImageBoxButton.getCopyPictures().size() > 0)
        {
            File first = files.get(0);
            //检查剪切板内容是否不是要复制的内容
            if(first.getParentFile().getAbsolutePath().compareTo(PictureFlowPane.filePath) == 0)
            {
                //清空所有选择的内容
                ImageBoxButton.clearSelected();
                ImageBoxButton.getCopyPictures().clear();
                ImageBoxButton.getSelectedPictureFiles().clear();
                clipboard.clear();
                return;
            }
        }

        for(File oldFile : files)
        {
            File oldFiles=new File(Copy.dir+File.separator+oldFile.getName());
            String newName = Pasterename(PictureFlowPane.filePath,oldFiles.getName());
            File newFile = new File(PictureFlowPane.filePath+File.separator+newName);
            try
            {
                //创建新文件到文件系统
                newFile.createNewFile();
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if(newFile.exists())
            {
                try
                {
                    //图片内容复制
                    copyFile(oldFiles,newFile);
                }
                catch (IOException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
            try
            {
                //同步加入加入到图片界面中
                Main.pictureFlowPane.flowPane.getChildren().add(new ImageBoxButton("file:"+newFile.getAbsolutePath(),newFile.getName()).getImageLabel() );

            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println("错误");
            }
            if(ImageBoxButton.getCopyPictures().size()>0)
            {
                oldFile.delete();
            }
        }
        //重新刷新图片界面
        try {
            Main.pictureFlowPane.getPicture(PictureFlowPane.file);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
    //用字节流传输
    private void copyFile(File fromFile, File toFile) throws IOException
    {
        FileInputStream inputStream = new FileInputStream(fromFile);
        FileOutputStream outputStream = new FileOutputStream(toFile);
        byte[] b = new byte[1024];
        int byteRead;
        while ((byteRead = inputStream.read(b)) > 0) {
            outputStream.write(b, 0, byteRead);
        }
        inputStream.close();
        outputStream.close();

    }
    //粘贴文件名字修改：+"副本"
    private String Pasterename(String theFilePath, String name)
    {
        String newName = name;
        File fatherPathFile = new File(theFilePath);
        File[] filesInFatherPath = fatherPathFile.listFiles();
        for (File fileInFatherPath : filesInFatherPath) {
            String fileName = fileInFatherPath.getName();
            int cmp = newName.compareTo(fileName);
            if (cmp == 0) {
                String str = null;
                int end = newName.lastIndexOf("."), start = newName.lastIndexOf("_副本");
                if (start != -1) {
                    str = newName.substring(start, end);
                    int num = 1;
                    try {
                        num = Integer.parseInt(str.substring(str.lastIndexOf("_副本") + 3)) + 1;
                        int cnt = 0, d = num - 1;
                        while (d != 0) {
                            d /= 10;
                            cnt++;
                        }
                        newName = newName.substring(0, end - cnt) + num + newName.substring(end);
                    } catch (Exception e) {
                        newName = newName.substring(0, end) + "_副本1" + newName.substring(end);
                    }

                } else {
                    newName = newName.substring(0, end) + "_副本1" + newName.substring(end);
                }
            }
        }
        return newName;
    }
}