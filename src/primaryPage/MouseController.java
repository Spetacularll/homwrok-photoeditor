package primaryPage;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

import static primaryPage.Main.pictureFlowPane;

//鼠标拖拽控制器
public class MouseController {
    private double width, height;

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    private double setX, setY;
    public Rectangle rectangle = new Rectangle();//拖拽形成一个矩形

    public static Pane pane = new Pane();
    private boolean isDragged;//判断是否拖拽

    public boolean getDragged() {
        return isDragged;
    }


    public MouseController() {

        rectangle.setArcHeight(6);
        rectangle.setArcWidth(10);
        rectangle.setFill(Color.rgb(135, 206, 250, 0.3));
        pane.getChildren().add(rectangle);
        MouseDraggedOnShow();
    }

    public void MouseDraggedOnShow() {
        pane.setOnMousePressed(e -> {
            this.MousePressed(e);
        });
        pane.setOnMouseDragged(e -> {
            this.MouseDragged(e);
        });
        pane.setOnMouseReleased(e -> {
            this.MouseReleased(e);
        });
}

    //鼠标按下获得鼠标坐标设置给矩形
    private void MousePressed(MouseEvent mouseEvent) {
        isDragged = false;
        setX = mouseEvent.getX();
        setY = mouseEvent.getY();
        rectangle.setX(setX);
        rectangle.setY(setY);

    }


    //拖拽时,根据初始坐标与矩形长和宽显示矩阵
    private void MouseDragged(MouseEvent mouseEvent) {
        isDragged = true;
        //选择初次按压的坐标与拖拽时的坐标的最小值作为矩形的新的坐标，使得鼠标从下往上也能形成矩形
        rectangle.setX(Math.min(setX, mouseEvent.getX()));
        rectangle.setY(Math.min(setY, mouseEvent.getY()));
        //设置长宽(取绝对值)
        rectangle.setWidth(Math.abs(mouseEvent.getX() - setX));
        rectangle.setHeight(Math.abs(mouseEvent.getY() - setY));
        //处于拖拽时
        if (this.isDragged) {
            ImageBoxButton.clearSelected();//清空之前的图片选择状态
            for (Node node : pictureFlowPane.flowPane.getChildren()) {
                //获得图片面板的图片节点
                if (node instanceof ImageBoxButton) {
                    //判断矩形区域是否与图片节点有重合,有则让图片节点设为选择状态
                    if (RectOnButton((ImageBoxButton) node)) {
                        ((ImageBoxButton) node).setSelected(true);
                    }
                }
            }
        }
    }

    //松开后，矩形坐标与长宽设置为0,不显示矩形
    private void MouseReleased(MouseEvent mouseEvent) {
        rectangle.setX(0);
        rectangle.setY(0);
        rectangle.setHeight(0);
        rectangle.setWidth(0);
    }

    //判断矩形区域是否与图片节点有重合
    private boolean RectOnButton(ImageBoxButton node) {
        double imageNodeCenterPointX = node.getLayoutX() + node.getWidth() / 2.0;
        double imageNodeCenterPointY = node.getLayoutY() + node.getHeight() / 2.0;
        double selectRectangleCenterPointX = rectangle.getX() + rectangle.getWidth() / 2.0;
        double selectRectangleCenterPointY = rectangle.getY() + rectangle.getHeight() / 2.0;
        return Math.abs(imageNodeCenterPointX - selectRectangleCenterPointX) <= (node.getWidth() / 2.0 + rectangle.getWidth() / 2.0) &&
                Math.abs(imageNodeCenterPointY - selectRectangleCenterPointY) <= (node.getHeight() / 2.0 + rectangle.getHeight() / 2.0);
    }
}