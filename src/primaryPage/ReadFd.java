package primaryPage;

import java.io.File;
import java.util.Iterator;



import static primaryPage.PictureFlowPane.fileArrayList;
import static primaryPage.PictureFlowPane.PictureSet;
public class ReadFd implements Runnable{
    int fileCount;
    double sizeOfImage;
    public volatile boolean running = true;
    public ReadFd(int fileCount,double sizeOfImage){
        this.fileCount=fileCount;
        this.sizeOfImage=sizeOfImage;
    }
    @Override
    public void run(){
        ReadingData(fileCount,sizeOfImage);
    }
    private void ReadingData(int fileCount,double sizeOfImage){
    //读取目录信息
    Iterator<File> iterator=fileArrayList.iterator();
    while(iterator.hasNext()){
        if(!running){
            break;
        }
        File value=iterator.next();
        if (!value.isDirectory()) {
            String fileName = value.getName();
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            //支持图片的格式
            if (suffix.equals("jpg") || suffix.equals("JPG") || suffix.equals("png") || suffix.equals("gif")
                    || suffix.equals("bmp") || suffix.equals("jpeg")) {
                fileCount++;
                sizeOfImage += value.length() / 1024.0;
                PictureSet.add(value.getAbsolutePath());
                //下方显示该目录的图片项目数
            }else {
                iterator.remove();
            }
        }else {
            iterator.remove();
        }
    }
    this.fileCount=fileCount;
    this.sizeOfImage=sizeOfImage;

}

    public int getFileCount() {
        return fileCount;
    }

    public double getSizeOfImage(){
        return sizeOfImage;
}
}
