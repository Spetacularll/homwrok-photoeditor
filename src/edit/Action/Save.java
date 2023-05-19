package edit.Action;

import edit.controller.EditController;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.image.WritableImage;
import javafx.scene.shape.Rectangle;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static edit.Action.Draw.drawPane;

public class Save {
    EditController editController;

    public Save(EditController editController) {

        this.editController=editController;
        save(new Rectangle(0,0,editController.imageView.getFitWidth(),editController.imageView.getFitHeight()));

    }
    public Save(Node rec,EditController editController){
        this.editController=editController;
        save(rec);
    }
    private void save(Node rec){

        editController.showPane.setClip(rec);

        //恢复原图片大小再保存，防止变模糊
        double t=editController.image.getWidth()/editController.imageView.getFitWidth();
        editController.showPane.setScaleX(t);
        editController.showPane.setScaleY(t);


        WritableImage writableImage = editController.showPane.snapshot(null, null);
        BufferedImage awtImage=new BufferedImage((int)writableImage.getWidth(),(int)writableImage.getHeight(),BufferedImage.TYPE_INT_RGB);
        editController.showPane.setClip(null);

        //恢复视图大小
        editController.showPane.setScaleX(1);
        editController.showPane.setScaleY(1);
        editController.image = writableImage;
        editController.imageView.setImage(writableImage);


        File outputFile = new File(EditController.savePath+"/"+editController.imageName+"."+editController.imageFormat);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(writableImage, awtImage), editController.imageFormat, outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        editController.n=1/t;

        //清除节点，重置数据
        editController.showPane.getChildren().clear();
        editController.imageView.setFitWidth(writableImage.getWidth()/t);
        editController.imageView.setFitHeight(writableImage.getHeight()/t);
        Draw.drawPane.getChildren().clear();
        drawPane.setScaleX(1);
        drawPane.setScaleY(1);
        Draw.drawPane.setPrefSize(editController.imageView.getFitWidth(), editController.imageView.getFitHeight());

        editController.showPane.setPrefSize(editController.imageView.getFitWidth(),editController.imageView.getFitHeight());
        editController.showPane.getChildren().addAll(editController.imageView,Draw.drawPane);


        editController.editDataArray.clear();
        editController.dataIndex.set(-1);
        editController.scrollPane.setContent(null);
        editController.page=0;

        Draw.cancelDrawPane();
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Save success");
        alert.show();

        editController.save.setOnMouseClicked(event -> {
            new Save(editController);
        });
    }

}
