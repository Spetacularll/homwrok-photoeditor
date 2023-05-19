package primaryPage;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Iterator;

import static primaryPage.PictureFlowPane.*;


//获取总文件大小和数目的线程


public class GetDataThread extends Thread {
    @Override
    public void run() {
        Iterator<File> iterator=fileArrayList.iterator();
        while(iterator.hasNext()){
            File value=iterator.next();
            if (!value.isDirectory()) {
                String fileName = value.getName();
                String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
                //支持图片的格式
                if (suffix.equals("jpg") || suffix.equals("JPG") || suffix.equals("png") || suffix.equals("gif")
                        || suffix.equals("bmp") || suffix.equals("jpeg")) {
                    fileCount++;
                    sizeOfImage += value.length() / 1024.0;
                    //下方显示该目录的图片项目数
                }else {
                    iterator.remove();
                }
            }else {
                iterator.remove();
            }
        }
        if (sizeOfImage >= 1024) {
            sizeOfImage /= 1024;
            v = 1;
            DecimalFormat format = new DecimalFormat("0.00");
            sizeofimage = format.format(sizeOfImage);
        } else {
            v = 0;
            DecimalFormat format = new DecimalFormat("0.00");
            sizeofimage = format.format(sizeOfImage);
        }
    }
}
