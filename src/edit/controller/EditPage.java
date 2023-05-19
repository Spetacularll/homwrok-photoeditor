package edit.controller;

import edit.Util.DataClear;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class EditPage {
    public EditPage(){


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edit/view/edit.fxml"));
        Stage editStage=new Stage();

        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        EditController editController=fxmlLoader.getController();
        editController.setStage(editStage);

        editController.borderPane.prefWidthProperty().bind(editStage.widthProperty());
        editController.borderPane.prefHeightProperty().bind(editStage.heightProperty());
        editStage.setOnCloseRequest(windowEvent -> {
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("EXIT");
            alert.setHeaderText("Are you sure to exit?");
            Optional<ButtonType> choice=alert.showAndWait();
            if(choice.get()==ButtonType.OK){
                new DataClear();
                editStage.close();
            }
            else {
                windowEvent.consume();
            }
        });
        editStage.setScene(scene);
        editStage.show();

    }
}
