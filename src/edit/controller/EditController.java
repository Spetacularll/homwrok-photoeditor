package edit.controller;

import edit.Action.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import static edit.Action.Draw.drawPane;

public class EditController implements Initializable {
    public static String filePath;//图片路径
    public int MAX_WIDTH = 1000;
    public int MAX_HEIGHT = 650;
    public int page = 0; //当前功能页面 0初始页面 1贴图 2裁剪 3涂鸦
    public Pane showPane = new Pane();
    public final ScrollPane scrollPaneImage = new ScrollPane();

    public final IntegerProperty dataIndex = new SimpleIntegerProperty(-1); //操作信息的下标
    public double times = 1; //缩放倍数
    public double n = 1;//加载时缩放图片的倍数
    public ImageView imageView;
    public Image image;
    public String imageFormat;
    public String imageName;

    public static String savePath = "outputFile"; //图片默认保存路径

    public final ObservableList<Object> editDataArray = FXCollections.observableArrayList(); //操作信息

    @FXML
    public Button setSavePath, sticker, back, next, cut, save, draw;
    @FXML
    public BorderPane borderPane;
    @FXML
    public ScrollPane scrollPane;
    @FXML
    Pane rootPane;
    private Stage stage;
    private double deltaX, deltaY;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scrollPaneImage.setMaxSize(MAX_WIDTH, MAX_HEIGHT);
        scrollPaneImage.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPaneImage.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        initImageData();

        initImageView();
        initShowPane();
        initButton();

        borderPane.setCenter(scrollPaneImage);
    }

    private void initImageData() {
        File picture = new File(filePath);
        image = new Image("file:" + filePath);
        String fileName = picture.getName();
        imageFormat = image.getUrl();
        imageFormat = imageFormat.split("\\.")[1];
        imageName = fileName.substring(0, fileName.lastIndexOf('.'));
    }

    private void initImageView() {
        //求出缩放比例
        while (image.getHeight() * n > MAX_HEIGHT || image.getWidth() * n > MAX_WIDTH) {
            n = n - 0.1;
        }
        imageView = new ImageView(image);
        imageView.setFitWidth(image.getWidth() * n);
        imageView.setFitHeight(image.getHeight() * n);
        showPane.getChildren().add(imageView);
    }

    private void initShowPane() {
        showPane.setPrefSize(imageView.getFitWidth(), imageView.getFitHeight());
        showPane.setOnMousePressed(event -> {
            deltaX = event.getX();
            deltaY = event.getY();
        });
        showPane.setOnMouseDragged(event -> {
            double offsetX = event.getX() - deltaX;
            double offsetY = event.getY() - deltaY;
            showPane.setTranslateX(showPane.getTranslateX() + offsetX);
            showPane.setTranslateY(showPane.getTranslateY() + offsetY);
        });
        showPane.setOnScroll(event -> {
            Scale.scale(event, this);
        });

        //居中
        showPane.setTranslateX((scrollPaneImage.getMaxWidth() - imageView.getFitWidth()) / 2);
        showPane.setTranslateY((scrollPaneImage.getMaxHeight() - imageView.getFitHeight()) / 2);
        drawPane.setPrefSize(imageView.getFitWidth(), imageView.getFitHeight());
        showPane.getChildren().add(drawPane);
        scrollPaneImage.setContent(showPane);
    }

    private void initButton() {
        back.setGraphic(new ImageView("file:Buttons/back.png"));
        back.setStyle("-fx-background-color: transparent;");
        back.setCursor(Cursor.HAND);
        back.setOnMouseEntered(event -> back.setStyle("-fx-background-color:rgb(211,211,211)"));
        back.setOnMouseExited(event -> back.setStyle("-fx-background-color: transparent;"));

        next.setGraphic(new ImageView("file:Buttons/next.png"));
        next.setStyle("-fx-background-color: transparent;");
        next.setCursor(Cursor.HAND);
        next.setOnMouseEntered(event -> next.setStyle("-fx-background-color:rgb(211,211,211)"));
        next.setOnMouseExited(event -> next.setStyle("-fx-background-color: transparent;"));
        new BackAndNext(this);


        sticker.setGraphic(new ImageView("file:Buttons/sticker.png"));
        sticker.setStyle("-fx-background-color: transparent;");
        sticker.setCursor(Cursor.HAND);
        sticker.setOnMouseEntered(event -> sticker.setStyle("-fx-background-color:rgb(211,211,211)"));
        sticker.setOnMouseExited(event -> sticker.setStyle("-fx-background-color: transparent;"));
        sticker.setOnMouseClicked(event -> {
            if (page == 2) {
                showPane.getChildren().remove(showPane.lookup("#" + "rec"));
            }
            if (page != 1) {
                Draw.cancelDrawPane();
                new Sticker(this);
                page = 1;
            } else {
                page = 0;
                scrollPane.setContent(null);
            }
        });

        Tooltip tooltipSticker = new Tooltip("贴图");
        tooltipSticker.setShowDelay(Duration.seconds(0.2));
        Tooltip.install(sticker, tooltipSticker);


        cut.setGraphic(new ImageView("file:Buttons/cut.png"));
        cut.setStyle("-fx-background-color: transparent;");
        cut.setCursor(Cursor.HAND);
        cut.setOnMouseEntered(event -> cut.setStyle("-fx-background-color:rgb(211,211,211)"));
        cut.setOnMouseExited(event -> cut.setStyle("-fx-background-color: transparent;"));

        cut.setOnMouseClicked(event -> {
            if (page != 2) {
                Draw.cancelDrawPane();
                scrollPane.setContent(null);
                new Cut(this);
                page = 2;
            } else {
                page = 0;
                showPane.getChildren().remove(showPane.lookup("#" + "rec"));
            }
        });

        Tooltip tooltipCut = new Tooltip("裁剪");
        tooltipCut.setShowDelay(Duration.seconds(0.2));
        Tooltip.install(cut, tooltipCut);

        draw.setGraphic(new ImageView("file:Buttons/draw.png"));
        draw.setStyle("-fx-background-color: transparent;");
        draw.setCursor(Cursor.HAND);
        draw.setOnMouseEntered(event -> draw.setStyle("-fx-background-color:rgb(211,211,211)"));
        draw.setOnMouseExited(event -> draw.setStyle("-fx-background-color: transparent;"));

        draw.setOnMouseClicked(event -> {
            if (page == 2) {
                showPane.getChildren().remove(showPane.lookup("#" + "rec"));
            }
            if (page != 3) {
                page = 3;
                new Draw(this).draw();
            } else {
                Draw.cancelDrawPane();
                scrollPane.setContent(null);
                page = 0;
            }
        });

        Tooltip tooltipDraw = new Tooltip("涂鸦");
        tooltipDraw.setShowDelay(Duration.seconds(0.2));
        Tooltip.install(draw, tooltipDraw);

        save.setGraphic(new ImageView("file:Buttons/save.png"));
        save.setStyle("-fx-background-color: transparent;");
        save.setCursor(Cursor.HAND);
        save.setOnMouseEntered(event -> save.setStyle("-fx-background-color:rgb(211,211,211)"));
        save.setOnMouseExited(event -> save.setStyle("-fx-background-color: transparent;"));

        save.setOnMouseClicked(event -> new Save(this));

        setSavePath.setGraphic(new ImageView("file:Buttons/path.png"));
        setSavePath.setStyle("-fx-background-color: transparent;");
        setSavePath.setCursor(Cursor.HAND);
        setSavePath.setOnMouseEntered(event -> setSavePath.setStyle("-fx-background-color:rgb(211,211,211)"));
        setSavePath.setOnMouseExited(event -> setSavePath.setStyle("-fx-background-color: transparent;"));

        setSavePath.setOnMouseClicked(event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File file=directoryChooser.showDialog(stage);
            if (file != null) {
                EditController.savePath = file.toString();
            }
        });

        Tooltip tooltipSet = new Tooltip("保存路径");
        tooltipSet.setShowDelay(Duration.seconds(0.2));
        Tooltip.install(setSavePath, tooltipSet);
    }
}

