package edit.Util;

import edit.controller.EditController;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ResizableRectangle {

    public Rectangle rectangle;

    private boolean dragTop, dragRight, dragBottom, dragLeft;
    double x, y, width, height;


    public ResizableRectangle(EditController editController) {
        x = editController.imageView.getX();
        y = editController.imageView.getY();
        width = editController.imageView.getFitWidth();
        height = editController.imageView.getFitHeight();

        rectangle = new Rectangle(x - 10, y - 10, width + 20, height + 20);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.BLACK);
        rectangle.setId("rec");


        rectangle.setOnMousePressed(event -> {
            dragTop = isOnTopEdge(event);
            dragRight = isOnRightEdge(event);
            dragBottom = isOnBottomEdge(event);
            dragLeft = isOnLeftEdge(event);
            event.consume();
        });

        rectangle.setOnMouseDragged(event -> {
            if (dragTop && dragLeft) {
                moveTop(event);
                moveLeft(event);
            } else if (dragTop && dragRight) {
                moveTop(event);
                moveRight(event);
            } else if (dragBottom && dragLeft) {
                moveBottom(event);
                moveLeft(event);
            } else if (dragBottom && dragRight) {
                moveBottom(event);
                moveRight(event);
            } else if (dragTop) {
                moveTop(event);
            } else if (dragRight) {
                moveRight(event);
            } else if (dragBottom) {
                moveBottom(event);
            } else if (dragLeft) {
                moveLeft(event);
            }
            setCursor(event);
            event.consume();
        });

        rectangle.setOnMouseMoved(this::setCursor);


        editController.showPane.getChildren().add(rectangle);

    }

    private boolean isOnTopEdge(MouseEvent event) {
        return event.getY() < rectangle.getY() + 10;
    }

    private boolean isOnRightEdge(MouseEvent event) {
        return event.getX() > rectangle.getX() + rectangle.getWidth() - 10;
    }

    private boolean isOnBottomEdge(MouseEvent event) {
        return event.getY() > rectangle.getY() + rectangle.getHeight() - 10;
    }

    private boolean isOnLeftEdge(MouseEvent event) {
        return event.getX() < rectangle.getX() + 10;
    }

    private void setCursor(MouseEvent event) {
        if (isOnTopEdge(event) && isOnRightEdge(event) || isOnBottomEdge(event) && isOnLeftEdge(event)) {
            rectangle.setCursor(Cursor.NE_RESIZE);
        } else if (isOnTopEdge(event) && isOnLeftEdge(event) || isOnBottomEdge(event) && isOnRightEdge(event)) {
            rectangle.setCursor(Cursor.NW_RESIZE);
        } else if (isOnTopEdge(event) || isOnBottomEdge(event)) {
            rectangle.setCursor(Cursor.N_RESIZE);
        } else if (isOnLeftEdge(event) || isOnRightEdge(event)) {
            rectangle.setCursor(Cursor.E_RESIZE);
        } else {
            rectangle.setCursor(Cursor.MOVE);
        }
    }

    private void moveTop(MouseEvent event) {
        double newHeight = rectangle.getY() + rectangle.getHeight() - event.getY();
        if (newHeight > 0) {
            rectangle.setY(event.getY());
            rectangle.setHeight(newHeight);
        }
    }

    private void moveRight(MouseEvent event) {
        double newWidth = event.getX() - rectangle.getX();
        if (newWidth > 0) {
            rectangle.setWidth(newWidth);
        }
    }

    private void moveBottom(MouseEvent event) {
        double newHeight = event.getY() - rectangle.getY();
        if (newHeight > 0) {
            rectangle.setHeight(newHeight);
        }
    }

    private void moveLeft(MouseEvent event) {
        double newWidth = rectangle.getX() + rectangle.getWidth() - event.getX();
        if (newWidth > 0) {
            rectangle.setX(event.getX());
            rectangle.setWidth(newWidth);
        }
    }
}
