package edit.Action;

import edit.controller.EditController;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;

import static edit.Action.Draw.drawPane;

public class Scale {

    //放缩
    public static void scale(ScrollEvent eventScroll, EditController editController){
        double deltaY = eventScroll.getDeltaY();
        double v = deltaY / Math.abs(deltaY) / 1000 * 99 + 1;
        editController.times = editController.times * v;
        editController.imageView.setFitWidth(editController.imageView.getFitWidth() * v);
        editController.imageView.setFitHeight(editController.imageView.getFitHeight() * v);
        drawPane.setScaleX(editController.times);
        drawPane.setScaleY(editController.times);
        //居中
        drawPane.setTranslateX((editController.times - 1) * drawPane.getWidth() / 2);
        drawPane.setTranslateY((editController.times - 1) * drawPane.getHeight() / 2);
        editController.showPane.setPrefSize(editController.imageView.getFitWidth(),editController.imageView.getFitHeight());

        for (Node node : editController.showPane.getChildren()) {
            if ((node instanceof ImageView imageViewTemp) && !node.equals(editController.imageView)) {
                imageViewTemp.setFitWidth(imageViewTemp.getFitWidth() * v);
                imageViewTemp.setFitHeight(imageViewTemp.getFitHeight() * v);

                imageViewTemp.setX(imageViewTemp.getX() * v);
                imageViewTemp.setY(imageViewTemp.getY() * v);
            }
        }
    }
}
