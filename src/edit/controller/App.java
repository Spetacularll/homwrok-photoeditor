package edit.controller;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage editStage) {
        new EditPage();
    }

    public static void main(String[] args) {
        launch();
    }
}