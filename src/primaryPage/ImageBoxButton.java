package primaryPage;

import film.Run;import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

//图片界面中缩略图节点
public class ImageBoxButton extends Button {
    private final String imageName;
    private final String imagePath;
    private final Labeled imageLabel = new Label();
    private final Button button = this;
    private final VBox vBox = new VBox();
    private final ImageView imageView;
    private final Image image;
    private final File imageFile;
    private final ImageMenuItem item = new ImageMenuItem();
    //判断是否被选中
    public BooleanProperty selected = new SimpleBooleanProperty();
    //三个数组，用于将被选中的图片进行复制粘贴重命名等处理
    public static ArrayList<File> selectedPictureFiles = new ArrayList<>();
    public static ArrayList<ImageBoxButton> selectedPictures = new ArrayList<>();
    public static ArrayList<ImageBoxButton> CopyPictures = new ArrayList<>();

    //该节点由
    public ImageBoxButton(String imagePath, String imageName) {
        this.imagePath = imagePath;
        this.imageName = imageName;
        imageFile = new File(imagePath);

        Image imageTemp=new Image(imagePath);
        if (Math.max(imageTemp.getWidth(), imageTemp.getHeight()) <= 100) {
            image = new Image(imagePath);
        } else {
            double time = Math.max(imageTemp.getWidth(), imageTemp.getHeight()) / 100;
            image = new Image(imagePath,imageTemp.getWidth()/time,imageTemp.getHeight()/time,false,false);
        }

        imageView = new ImageView(image);
        switch (Function.v1){
            case 0:imageView.setFitWidth(110);
                imageView.setFitHeight(130);break;
            case 1:imageView.setFitWidth(150);
                imageView.setFitHeight(170);break;
            case 2:imageView.setFitWidth(80);
                imageView.setFitHeight(100);break;
            case 3:imageView.setFitWidth(60);
                imageView.setFitHeight(80);break;
        }
        imageView.setPreserveRatio(true);//保持缩放比例
        imageLabel.setGraphic(imageView);
        Label ImageName = new Label();
        ImageName.setText(imageName);
        imageLabel.setAlignment(Pos.BASELINE_CENTER);
        //Thumbnail size:
        switch (Function.v1){
            case 0:imageLabel.setPrefSize(110, 110);break;
            case 1:imageLabel.setPrefSize(150, 150);break;
            case 2:imageLabel.setPrefSize(80, 80);break;
            case 3:imageLabel.setPrefSize(60, 60);break;
        }
        vBox.getChildren().addAll(imageLabel, ImageName);
        //button由缩小的图片与图片名字组成
        button.setGraphic(vBox);
        button.setStyle("-fx-background-color:transparent");

        //点击图片事件
        setButtonEvents();
        //右击图片事件
        imageLabel.setContextMenu(item.getContextMenu());


    }

    //
    public String getImagePath() {
        return this.imagePath;
    }

    public File getImageFile() {
        return this.imageFile;
    }

    public Image getImage() {
        return this.image;
    }

    public ImageView getImageView() {
        return this.imageView;
    }

    public String getName() {
        return imageName;
    }

    //选择状态的设置
    public void setSelected(boolean value) {
        boolean istrue = selected.get();
        selected.set(value);
        if (selected.get() && !istrue){
            selectedPictures.add(this);
        } else if (istrue && !selected.get()) {
            selectedPictures.remove(this);
        }
        Main.myTextPane.text2.setText("已选中" + selectedPictures.size() + " 张图片");
        Main.myTextPane.text2.setTextFill(Color.RED);
    }

    public static void clearSelected() {
        for (ImageBoxButton imageboxlabel : selectedPictures) {
            imageboxlabel.selected.set(false);
        }
        selectedPictures.removeAll(selectedPictures);
        Main.myTextPane.text2.setText("已选中" + selectedPictures.size() + " 张图片");
        Main.myTextPane.text2.setTextFill(Color.RED);
    }

    //
    public static ArrayList<File> getSelectedPictureFiles() {
        return selectedPictureFiles;
    }

    public static ArrayList<ImageBoxButton> getSelectedPictures() {
        return selectedPictures;
    }

    public static void setCopyPictures(ArrayList<ImageBoxButton> cutedPictures) {
        ImageBoxButton.CopyPictures = cutedPictures;
    }

    public static void addCopyPictures(ImageBoxButton pNode) {
        ImageBoxButton.CopyPictures.add(pNode);
    }

    public static ArrayList<ImageBoxButton> getCopyPictures() {
        return CopyPictures;
    }

    public static void clearCutedPictures() {
        CopyPictures.clear();
    }

    //
    public void setButtonEvents() {
        //根据该节点选中状态改变颜色
        selected.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                if (selected.get()) {
                    button.setStyle("-fx-background-color:rgb(211,211,211)");
                } else {
                    button.setStyle("-fx-background-color:transparent;");
                }
            }
        });

        //鼠标进入该节点
        button.setOnMouseEntered(mouseEvent -> {
            if (!selected.get()) {
                button.setStyle("-fx-background-color: rgb(211,211,211)");

            }
        });
        //鼠标退出该节点
        button.setOnMouseExited(mouseEvent -> {
            if (!selected.get()) {
                button.setStyle("-fx-background-color:transparent ");
            }
        });
        button.setAccessibleHelp(imageName);
        //设置点击事件
        button.setOnMouseClicked(mouseEvent -> {

            PictureFlowPane.imagemenuitem.maincontextMenu.hide();
            //没有按control键时
            if (!mouseEvent.isControlDown()) {
                if (mouseEvent.getButton() != MouseButton.SECONDARY || !(this.selected.getValue())) {
                    ImageBoxButton.clearSelected();
                }
                this.setSelected(true);

                //双击左键创建一个舞台
                if (mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getClickCount() == 2) {
                    PictureFlowPane.imageArrayList.clear();
                    if (this.selected.getValue()) {
                        this.setSelected(true);
                        for (File value:
                                PictureFlowPane.fileArrayList) {
                            if (!value.isDirectory()) {
                                String fileName = value.getName();
                                String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
                                //支持图片的格式
                                if (suffix.equals("jpg") || suffix.equals("JPG") || suffix.equals("png") || suffix.equals("gif")
                                        || suffix.equals("bmp") || suffix.equals("jpeg")) {
                                    String FilePath = value.getAbsolutePath();
                                    PictureFlowPane.imageArrayList.add(FilePath);
                                }
                            }
                        }

                        Run run =new Run(image);
                    }
                }

                //单击显示缩略图
                if (mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getClickCount() == 1) {

                    ImageBoxButton.clearSelected();
                    this.setSelected(true);
                    ImagePreviewLabel imagePreviewLabel = new ImagePreviewLabel(imagePath, 300, 300);
                    imagePreviewLabel.addLabelOnPane(imagePreviewLabel.getImageLabeled());
                    ImagePreviewInformation imagePreviewInformation = null;
                    try {
                        imagePreviewInformation = new ImagePreviewInformation(imagePath, imageName);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    imagePreviewInformation.addInformationOfImageOnPane(imagePreviewInformation.getImageInformationLabel());
                }

            }

        });

    }

    public Button getImageLabel() {
        return button;
    }
}