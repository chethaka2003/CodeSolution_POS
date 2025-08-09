package Services;

import Controllers.BuisnessSelectorController;
import Controllers.LoginController;
import animatefx.animation.FadeIn;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;

public class uiService {

    //This methods loads thu UI

    public static void switchScene(MouseEvent event, String fxmlPath, String cssPath) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(uiService.class.getResource(fxmlPath));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(uiService.class.getResource(cssPath).toExternalForm());
        stage.setScene(scene);
        stage.show();

        //Fade in effect
        new FadeIn(scene.getRoot()).play();
    }
    public static void switchSceneKeyboard(KeyEvent event, String fxmlPath, String cssPath) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(uiService.class.getResource(fxmlPath));
        Stage stage = (Stage) ((Scene) event.getSource()).getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(uiService.class.getResource(cssPath).toExternalForm());
        stage.setScene(scene);
        stage.show();

        //Fade in effect
        new FadeIn(scene.getRoot()).play();
    }


    //Gives information about current status of the system
    public static void giveConfirmationAlert(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();


    }

    //Gives error message to user
    public static void giveErrorAlert(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

