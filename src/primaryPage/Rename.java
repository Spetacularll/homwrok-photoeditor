package primaryPage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;


public class Rename
{


    Stage stage=new Stage();
    TextField textfield =new TextField();
    TextField start=new TextField();
    TextField bit=new TextField();
    Text text=new Text();
    Tooltip tooltip=new Tooltip("文件名不能包括下列任何字符: ? \\ * | \" < > : /");
    BorderPane borderPane=new BorderPane();
    Button button=new Button("确定");
    public Rename()
    {

        //单重命名的窗口布局
        if(ImageBoxButton.getSelectedPictures().size()==1)
        {
            VBox vbox=new VBox();
            vbox.getChildren().addAll(textfield,text);
            vbox.setAlignment(Pos.CENTER_LEFT);

            textfield.setTooltip(tooltip);
            textfield.setPromptText("请输入名字");
            textfield.setOnAction(e->{
                if(CheckEmpty(textfield))
                {
                    //判断是否成功
                    if(OneRename())
                    {
                        stage.close();
                    } else
                    {
                        text.setText("文件名重复");
                        text.setFill(Color.RED);
                    }

                } else {
                    text.setText("名字未输入");
                    text.setFill(Color.RED);
                }
            });

            HBox hBox=new HBox(button);
            hBox.setAlignment(Pos.BOTTOM_CENTER);
            borderPane.setCenter(vbox);
            borderPane.setBottom(hBox);
            Scene scene=new Scene(borderPane,300,100);


            stage.setResizable(false);
            stage.setTitle("重命名：");
            //stage.initOwner(Main.primarystage);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.show();
        }
        //批量重命名的窗口布局
        else
        {
            VBox vbox1=new VBox();
            VBox vbox2=new VBox();
            VBox vbox3=new VBox();
            textfield.setTooltip(tooltip);
            textfield.setPromptText("名字");
            start.setPromptText("起始数字");
            bit.setPromptText("后缀位数");
            vbox1.getChildren().addAll(new Label("名字       "),textfield);
            vbox2.getChildren().addAll(new Label("起始编号"),start);
            vbox3.getChildren().addAll(new Label("后缀位数"),bit);
            borderPane.setTop(vbox1);
            borderPane.setCenter(new VBox(vbox2, vbox3,text));
            HBox hBox1=new HBox(button);
            hBox1.setAlignment(Pos.BOTTOM_CENTER);
            borderPane.setBottom(hBox1);
            Scene scene=new Scene(borderPane,500,200);
            textfield.setOnAction(e->{
                if (CheckEmpty(textfield)&&CheckEmpty(start)&&CheckEmpty(bit))
                {
                    //判断是否成功
                    if(MutilRename())
                    {

                        stage.close();
                    }
                    else
                    {

                        text.setText("输入有错误");
                        text.setFill(Color.RED);
                    }

                }
                else
                {
                    text.setText("请先输入完整信息");
                    text.setFill(Color.RED);
                }
            });
            start.setOnAction(e->{
                if (CheckEmpty(textfield)&&CheckEmpty(start)&&CheckEmpty(bit))
                {
                    //判断是否成功
                    if(MutilRename())
                    {

                        stage.close();
                    }
                    else
                    {

                        text.setText("输入有错误");
                        text.setFill(Color.RED);
                    }

                }
                else
                {
                    text.setText("请先输入完整信息");
                    text.setFill(Color.RED);
                }
            });
            bit.setOnAction(e->{
                if (CheckEmpty(textfield)&&CheckEmpty(start)&&CheckEmpty(bit))
                {
                    //判断是否成功
                    if(MutilRename())
                    {

                        stage.close();
                    }
                    else
                    {

                        text.setText("输入有错误");
                        text.setFill(Color.RED);
                    }

                }
                else
                {
                    text.setText("请先输入完整信息");
                    text.setFill(Color.RED);
                }
            });


            stage.setResizable(false);
            stage.setTitle("重命名：");
            //stage.initOwner(Main.primarystage);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.show();

        }

        //文件输入名字的限制：不能出现非法字符
        textfield.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {

                //有非法字符
                if(!NameCheck(arg2))
                {
                    tooltip.show(textfield,stage.getX(),stage.getY());
                    textfield.setText(arg1);

                }
                //无非法字符
                if(NameCheck(arg2)&&NameCheck(arg1))
                {
                    tooltip.hide();
                    text.setText(null);
                }

            }
        });
        //确定按钮的动作
        button.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                // 单图片重命名
                if(ImageBoxButton.getSelectedPictures().size()==1)
                {
                    if(CheckEmpty(textfield))
                    {
                        //判断是否成功
                        if(OneRename())
                        {
                            stage.close();
                        }
                        else
                        {
                            text.setText("文件名重复");
                            text.setFill(Color.RED);
                        }

                    }
                    else {
                        text.setText("名字未输入");
                        text.setFill(Color.RED);
                    }
                }
                //批量图片重命名
                else if(ImageBoxButton.getSelectedPictures().size()>1)
                {
                    if (CheckEmpty(textfield)&&CheckEmpty(start)&&CheckEmpty(bit))
                    {
                        //判断是否成功
                        if(MutilRename())
                        {

                            stage.close();
                        }
                        else
                        {

                            text.setText("输入有错误");
                            text.setFill(Color.RED);
                        }

                    }
                    else
                    {
                        text.setText("请先输入完整信息");
                        text.setFill(Color.RED);
                    }
                }
            }
        });
    }
    //判断输入框内容是否为空
    protected boolean CheckEmpty(TextField textfield)
    {
        return (textfield.getText()!=null&&!textfield.getText().isEmpty());

    }
    //检查输入名字是否出现非法字符
    protected boolean NameCheck(String name) {
        // TODO Auto-generated method stub
        if(name.contains("?")||name.contains("/")||name.contains("\\")||name.contains("|")||name.contains("*")||name.contains("\"")||name.contains("<")||name.contains(">")||name.contains(":"))
        {
            return false;
        }
        return true;
    }
    //判断单命名是否成功
    protected boolean OneRename()
    {
        //建立新文件
        ImageBoxButton Label=ImageBoxButton.getSelectedPictures().get(0);
        File file=new File(Main.pictureFlowPane.filePath+File.separator+Label.getName());
        String parent=file.getParent();
        String[] filename=file.getName().split("\\.");
        String suf =filename[filename.length-1];
        File tmp =new File(parent+"\\"+textfield.getText()+"."+suf);
        //比较新文件在该目录下是否有重名
        if(!file.renameTo(tmp))
        {
            return false;//失败
        }

        else
        {
            //成功，同步更新到图片面板显示
            Main.pictureFlowPane.flowPane.getChildren().clear();
            try {
                Main.pictureFlowPane.getPicture(PictureFlowPane.file);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }

        }
        return true;
    }

    //检查重命名是否成功
    protected boolean MutilRename()
    {
        //判断输入的数字与位数是否在范围内
        int startid=Integer.valueOf(start.getText());
        int bits=Integer.valueOf(bit.getText());
        if(startid<0 ||(startid+ImageBoxButton.getSelectedPictures().size())>=(int)Math.pow(10, bits)) {
            return false;
        }


        for(ImageBoxButton Label:ImageBoxButton.getSelectedPictures())
        {
            //建立新文件
            File file=new File(Main.pictureFlowPane.filePath+File.separator+Label.getName());
            String parent=file.getParent();
            String[] filename=file.getName().split("\\.");
            String suf1 =filename[filename.length-1];
            System.out.println("???????????");
            String newName=createNewname(startid,bits);
            File tmp =new File(parent+"\\"+newName+"."+suf1);
            //比较新文件在该目录下是否有重名
            if(!file.renameTo(tmp))
            {

                return false;
            }
            startid++;

        }
        //成功，同步更新到图片面板显示
        Main.pictureFlowPane.flowPane.getChildren().clear();
        try {
            Main.pictureFlowPane.getPicture(PictureFlowPane.file);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }


        return true;
    }
    //根据起始数字与后缀位数创造新名字
    private String createNewname(int id, int bit)
    {
        String newName = textfield.getText();

        int tt = id;
        int cnt=0;
        while(tt!=0) {
            cnt++;
            tt/=10;
        }
        if(id==0) {
            cnt++;
        }
        while(bit>cnt) {
            newName+=0;
            cnt++;
        }
        newName += id;
        return newName;
    }

}