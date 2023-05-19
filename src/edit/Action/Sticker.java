package edit.Action;

import edit.Util.DataClear;
import edit.controller.EditController;
import javafx.scene.Cursor;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.io.File;

public class Sticker {
    static class stickerData {
        final int op;
        double X, Y, height, width, times;
        String index;

        ImageView draggedImage;

        protected stickerData(int op, String index, double times, double X, double Y, double height, double width) {
            this.op = op;
            this.index = index;
            this.X = X;
            this.Y = Y;
            this.height = height;
            this.width = width;
            this.times = times;
        }

        protected stickerData(int op, ImageView draggedImage) {
            this.op = op;
            this.draggedImage = draggedImage;
        }
    }

    private stickerData stickerTemp;
    public static int draggedImageID = 0;
    private double mouseClickedXLen;
    private double mouseClickedYLen;
    private final EditController editController;


    public Sticker(EditController editController) {
        this.editController = editController;
        File file = new File("patterns");
        File[] files = file.listFiles();
        GridPane content = new GridPane();
        content.setHgap(20);
        content.setVgap(20);
        int row = 0, col = 0;
        for (File filePatten : files) {
            //加载样式
            Image imagePatten = new Image(filePatten.toURI().toString());
            ImageView imageViewPattern = new ImageView(imagePatten);

            //缩放存入
            if (Math.max(imagePatten.getWidth(), imagePatten.getHeight()) <= 100) {
                imageViewPattern.setFitHeight(imagePatten.getHeight());
                imageViewPattern.setFitWidth(imagePatten.getWidth());
            } else {
                double time = Math.max(imagePatten.getWidth(), imagePatten.getHeight()) / 100;
                imageViewPattern.setFitHeight(imagePatten.getHeight() / time);
                imageViewPattern.setFitWidth(imagePatten.getWidth() / time);
            }


            Tooltip tooltip=new Tooltip(filePatten.getName().split("\\.")[0]);
            tooltip.setShowDelay(Duration.seconds(0.2));
            Tooltip.install(imageViewPattern,tooltip);
            //放入样式区
            content.add(imageViewPattern, col, row);
            col++;
            if (col == 2) {
                col = 0;
                row++;
            }

            //实现点击可拖拉效果


            imageViewPattern.setCursor(Cursor.HAND);
            imageViewPattern.setOnMouseClicked(eventClicked -> {
                ImageView draggedImage = new ImageView(imageViewPattern.getImage());
                initDraggedImage(draggedImage, imageViewPattern);

                editController.dataIndex.set(editController.dataIndex.get() + 1);

                for (int i = editController.dataIndex.get(); i < editController.editDataArray.size(); i++) {
                    editController.editDataArray.remove(i);
                }
                editController.editDataArray.add(editController.dataIndex.get(), new stickerData(0, draggedImage));

                if (editController.editDataArray.size() > 10) {
                    editController.editDataArray.remove(0);
                    editController.dataIndex.set(editController.dataIndex.get() - 1);
                }
            });


        }
        //一次性添加进带滑动条的容器里
        editController.scrollPane.setContent(content);
        editController.save.setOnMouseClicked(eventClicked -> {
            new Save(editController);
            new DataClear();
        });
    }
    private void initDraggedImage(ImageView draggedImage, ImageView imageViewPattern) {
        draggedImage.setId(Integer.toString(draggedImageID));
        draggedImageID++;
        draggedImage.setFitWidth(imageViewPattern.getFitWidth());
        draggedImage.setFitHeight(imageViewPattern.getFitHeight());

        editController.showPane.getChildren().add(draggedImage);

        //保存XY和width height

        draggedImage.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.DELETE || keyEvent.getCode() == KeyCode.BACK_SPACE) {

                //贴图移动前的信息

                editController.dataIndex.set(editController.dataIndex.get() + 1);

                for (int i = editController.dataIndex.get(); i < editController.editDataArray.size(); i++) {
                    editController.editDataArray.remove(i);
                }
                editController.editDataArray.add(editController.dataIndex.get(), new stickerData(2, draggedImage));

                if (editController.editDataArray.size() > 10) {
                    editController.editDataArray.remove(0);
                    editController.dataIndex.set(editController.dataIndex.get() - 1);
                }

                editController.showPane.getChildren().remove(draggedImage);
            }
        });

        draggedImage.setOnMouseEntered(event -> {
            draggedImage.requestFocus();
        });
        draggedImage.setOnMouseClicked(event -> {
            draggedImage.requestFocus();
        });
        draggedImage.setCursor(Cursor.HAND);

        draggedImage.setOnScroll(eventScroll -> {

            eventScroll.consume();

            double deltaY = eventScroll.getDeltaY();
            //v=0.099+1=1.099或v=1/1.099=0.909=-0.099+1
            double v = deltaY / Math.abs(deltaY) * 0.099 + 1;

            draggedImage.setFitWidth(draggedImage.getFitWidth() * v);
            draggedImage.setFitHeight(draggedImage.getFitHeight() * v);

        });


        draggedImage.setOnMousePressed(eventMousePressed -> {
            //保存移动前的信息，方便求出变化量
            stickerTemp = new stickerData(-1, draggedImage.getId(), editController.times, draggedImage.getX(), draggedImage.getY(), draggedImage.getFitHeight(), draggedImage.getFitWidth());

            mouseClickedXLen = eventMousePressed.getSceneX() - draggedImage.getX();
            mouseClickedYLen = eventMousePressed.getSceneY() - draggedImage.getY();
        });


        draggedImage.setOnMouseDragged(eventMouseDragged -> {
            eventMouseDragged.consume();
            draggedImage.setX(eventMouseDragged.getSceneX() - mouseClickedXLen);
            draggedImage.setY(eventMouseDragged.getSceneY() - mouseClickedYLen);
        });


        //记录操作
        draggedImage.setOnMouseReleased(event -> {
            double deltaWidth = draggedImage.getFitWidth() - stickerTemp.width;
            double deltaHeight = draggedImage.getFitHeight() - stickerTemp.height;
            double deltaX = draggedImage.getX() - stickerTemp.X;
            double deltaY = draggedImage.getY() - stickerTemp.Y;


            editController.dataIndex.set(editController.dataIndex.get() + 1);

            for (int i = editController.dataIndex.get(); i < editController.editDataArray.size(); i++) {
                editController.editDataArray.remove(i);
            }
            editController.editDataArray.add(editController.dataIndex.get(), new stickerData(1, draggedImage.getId(), editController.times, deltaX, deltaY, deltaHeight, deltaWidth));

            if (editController.editDataArray.size() > 10) {
                editController.editDataArray.remove(0);
                editController.dataIndex.set(editController.dataIndex.get() - 1);
            }
        });
    }

}
