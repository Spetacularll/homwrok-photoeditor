package edit.Action;

import edit.controller.EditController;
import javafx.scene.Cursor;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;


public class Draw {

    private final EditController editController;

    private Circle styleCircle;
    private Line line;
    static public Pane drawPane = new Pane();

    private Path path;
    private final double preRadius = 2.5;
    private final double v = 16;
    private final Pane choosePane = new Pane();

    public Draw(EditController editController) {
        this.editController = editController;
    }
    public void draw() {
        chooseGraphicsContext(); //选择样式
        drawPane.setOnMousePressed(event -> newPath(event.getX(), event.getY()));
        drawPane.setOnMouseDragged(event -> {
            event.consume();
            path.setStroke(styleCircle.getFill());
            addToPath(event.getX(), event.getY());
        });
        drawPane.setOnMouseReleased(event -> saveData());

    }
    private void saveData() {
        //保存操作信息
        editController.dataIndex.set(editController.dataIndex.get() + 1);
        for (int i = editController.dataIndex.get(); i < editController.editDataArray.size(); i++) {
            editController.editDataArray.remove(i);
        }
        editController.editDataArray.add(editController.dataIndex.get(), path);
        if (editController.editDataArray.size() > 10) {
            editController.editDataArray.remove(0);
            editController.dataIndex.set(editController.dataIndex.get() - 1);
        }
    }
    //选择信息存放于sizeCircle
    private void chooseGraphicsContext() {
        choosePane.setPrefSize(editController.scrollPane.getWidth(), editController.scrollPane.getHeight());
        editController.scrollPane.setContent(choosePane);
        initLine();
        initColor();
        initStyleCircle();
    }
    //颜色生成器
    private Circle colorGenerator(Color color) {
        Circle circle = new Circle(30);
        circle.setFill(color);
        circle.setCursor(Cursor.HAND);
        circle.setOnMouseClicked(event -> {
            styleCircle.setFill(color);
            line.setStroke(color);
        });
        return circle;
    }
    private void newPath(double x, double y) {
        Paint color = styleCircle.getFill();
        double width = styleCircle.getRadius() * 2;
        path = new Path();
        path.setStroke(color);
        path.setStrokeWidth(width);
        path.setStrokeLineCap(StrokeLineCap.ROUND);
        path.setStrokeLineJoin(StrokeLineJoin.ROUND);
        path.getElements().add(new MoveTo(x, y));
        drawPane.getChildren().add(path);
    }
    private void addToPath(double x, double y) {
        path.getElements().add(new LineTo(x, y));
    }

    private void initStyleCircle() {
        styleCircle = new Circle(preRadius);
        choosePane.getChildren().add(styleCircle);
        styleCircle.setCenterY(150);
        styleCircle.setCenterX(line.getStartX() + preRadius);
        styleCircle.setFill(Color.BLACK);
        styleCircle.setCursor(Cursor.HAND);

        Label sizeLabel = new Label(String.format("%.2f", styleCircle.getRadius() * 2));
        sizeLabel.setLayoutY(115);
        choosePane.getChildren().add(sizeLabel);

        styleCircle.centerXProperty().addListener((observable, oldValue, newValue) -> {
            sizeLabel.setLayoutX(styleCircle.getCenterX());
            sizeLabel.setText(String.format("%.2f", styleCircle.getRadius() * 2));
        });
        styleCircle.setOnMouseDragged(event -> {
            double newX = event.getX();
            //左右边界不超线
            if (newX - styleCircle.getRadius() < line.getStartX()) {
                newX = line.getStartX() + styleCircle.getRadius();
            } else if (newX + styleCircle.getRadius() > line.getEndX()) {
                newX = line.getEndX() - styleCircle.getRadius();
            }
            styleCircle.setCenterX(newX);
            styleCircle.setRadius(preRadius + (styleCircle.getCenterX() - line.getStartX() - preRadius) / v);
        });
    }
    private void initLine() {
        line = new Line(20, 150, choosePane.getPrefWidth() - 20, 150);
        choosePane.getChildren().add(line);
        line.setOpacity(0.5);
        line.setStrokeWidth(3.5);
        line.setStroke(Color.BLACK);
        line.setCursor(Cursor.HAND);
        line.setOnMousePressed(event -> {
            styleCircle.setCenterX(event.getX());
            styleCircle.setRadius(preRadius + event.getX() / v);
        });
        line.setOnMouseDragged(event -> {
            double newX = event.getX();
            //左右边界不超线
            if (newX - styleCircle.getRadius() < line.getStartX()) {
                newX = line.getStartX() + styleCircle.getRadius();
            } else if (newX + styleCircle.getRadius() > line.getEndX()) {
                newX = line.getEndX() - styleCircle.getRadius();
            }
            styleCircle.setCenterX(newX);
            styleCircle.setRadius(preRadius + (styleCircle.getCenterX() - line.getStartX() - preRadius) / v);
        });
    }
    private void initColor() {
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setLayoutX(20);
        colorPicker.setLayoutY(170);
        colorPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            styleCircle.setFill(newValue);
            line.setStroke(newValue);

        });
        choosePane.getChildren().add(colorPicker);

        Circle black = colorGenerator(Color.BLACK);
        Circle white = colorGenerator(Color.WHITE);
        Circle green = colorGenerator(Color.GREEN);
        Circle blue = colorGenerator(Color.BLUE);
        Circle red = colorGenerator(Color.RED);
        Circle brown = colorGenerator(Color.BROWN);
        FlowPane flowPane = new FlowPane(black, white, green, blue, red, brown);
        flowPane.setMaxSize(choosePane.getPrefWidth(), choosePane.getPrefHeight());
        flowPane.setLayoutY(300);
        choosePane.getChildren().add(flowPane);
        flowPane.setCursor(Cursor.HAND);
        flowPane.setHgap(10);
        flowPane.setVgap(10);
    }
    public static void cancelDrawPane(){
        Draw.drawPane.setOnMousePressed(null);
        Draw.drawPane.setOnMouseDragged(null);
        Draw.drawPane.setOnMouseReleased(null);
    }
}
