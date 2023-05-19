package edit.Action;

import edit.Util.ResizableRectangle;
import edit.controller.EditController;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

public class Cut {
    private double x;
    private double y;
    private double width;
    private double height;

    public Cut(EditController editController) {
        //创建截图框
        Rectangle rectangle = new ResizableRectangle(editController).rectangle;

        //保存矩形框内的部分
        editController.save.setOnMouseClicked(event -> {
            x = rectangle.getX();
            y = rectangle.getY();
            width = rectangle.getWidth();
            height = rectangle.getHeight();

            editController.showPane.getChildren().remove(rectangle);
            Node rec = new Rectangle(x, y, width, height);

            new Save(rec,editController);


            //居中
            editController.showPane.setTranslateX((editController.scrollPaneImage.getMaxWidth() - editController.imageView.getFitWidth()) / 2);
            editController.showPane.setTranslateY((editController.scrollPaneImage.getMaxHeight() - editController.imageView.getFitHeight()) / 2);
        });
    }

}



