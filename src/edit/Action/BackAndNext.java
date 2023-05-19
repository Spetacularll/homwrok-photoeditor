package edit.Action;

import edit.controller.EditController;
import javafx.beans.binding.Bindings;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Path;

public class BackAndNext {
    EditController editController;

    public BackAndNext(EditController editController) {
        this.editController = editController;

        //禁用按钮，防止越界
        editController.back.disableProperty().bind(editController.dataIndex.isEqualTo(-1));

        editController.back.setOnMouseClicked(event -> {
            //选择回退类型
            Object object = editController.editDataArray.get(editController.dataIndex.get());
            //如果是贴图
            if (object instanceof Sticker.stickerData stickerData) {
                switch (stickerData.op) {//恢复贴图位置
                    case 0 -> deleteSticker();
                    case 1 -> stickerRedo();
                    case 2 -> addSticker();
                }
            }
            //如果是涂鸦
            else if (editController.editDataArray.get(editController.dataIndex.get()) instanceof Path) {
                drawRedo();
            }
            editController.dataIndex.set(editController.dataIndex.get() - 1);
        });



        editController.next.disableProperty().bind(editController.dataIndex.isEqualTo(Bindings.size(editController.editDataArray).subtract(1)));

        editController.next.setOnMouseClicked(event -> {
            editController.dataIndex.set(editController.dataIndex.get() + 1);
            Object object = editController.editDataArray.get(editController.dataIndex.get());
            if (object instanceof Sticker.stickerData stickerData) {
                switch (stickerData.op) {//恢复贴图位置
                    case 0 -> addSticker();
                    case 1 -> stickerUndo();
                    case 2 -> deleteSticker();
                }
            } else if (object instanceof Path) {
                drawUndo();
            }
        });
    }


    //恢复贴图变化量
    private void stickerRedo() {

        Sticker.stickerData data = (Sticker.stickerData) editController.editDataArray.get(editController.dataIndex.get());

        //计算出点击撤销时的缩放倍数
        double timesWhenClicked = editController.imageView.getFitHeight() / (editController.image.getHeight() * editController.n);
        //计算出撤销的应该应用的正确倍数
        double correctTimes = timesWhenClicked / data.times;
        ImageView draggedImage = (ImageView) editController.borderPane.lookup("#" + data.index);
        draggedImage.setX(draggedImage.getX() - data.X * correctTimes);
        draggedImage.setY(draggedImage.getY() - data.Y * correctTimes);
        draggedImage.setFitWidth(draggedImage.getFitWidth() - data.width * correctTimes);
        draggedImage.setFitHeight(draggedImage.getFitHeight() - data.height * correctTimes);
    }

    private void stickerUndo() {
        Sticker.stickerData data = (Sticker.stickerData) editController.editDataArray.get(editController.dataIndex.get());

        double timesWhenClicked = editController.imageView.getFitHeight() / (editController.image.getHeight() * editController.n);
        double correctTimes = timesWhenClicked / data.times;
        ImageView draggedImage = (ImageView) editController.borderPane.lookup("#" + data.index);
        draggedImage.setX(draggedImage.getX() + data.X * correctTimes);
        draggedImage.setY(draggedImage.getY() + data.Y * correctTimes);
        draggedImage.setFitWidth(draggedImage.getFitWidth() + data.width * correctTimes);
        draggedImage.setFitHeight(draggedImage.getFitHeight() + data.height * correctTimes);
    }


    private void addSticker() {
        Sticker.stickerData data = (Sticker.stickerData) editController.editDataArray.get(editController.dataIndex.get());
        editController.showPane.getChildren().add(data.draggedImage);
    }

    private void deleteSticker() {
        Sticker.stickerData data = (Sticker.stickerData) editController.editDataArray.get(editController.dataIndex.get());
        editController.showPane.getChildren().remove(data.draggedImage);
    }

    private void drawRedo() {
        Path path = (Path) editController.editDataArray.get(editController.dataIndex.get());
        Draw.drawPane.getChildren().remove(path);
    }

    private void drawUndo() {
        Path path = (Path) editController.editDataArray.get(editController.dataIndex.get());
        Draw.drawPane.getChildren().add(path);
    }
}
