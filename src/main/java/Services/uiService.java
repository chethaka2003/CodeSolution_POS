package Services;

import Controllers.BuisnessSelectorController;
import Controllers.LoginController;
import animatefx.animation.FadeIn;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Path;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

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

    //Ask confirmation from the user
    public static void askConfirmation(String title, String header, String content,Runnable onYes){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.getButtonTypes().add(ButtonType.YES);
        alert.getButtonTypes().add(ButtonType.NO);
        alert.getButtonTypes().remove(ButtonType.OK);
        alert.getButtonTypes().remove(ButtonType.CANCEL);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES){
            onYes.run();
        }

    }

    //Popup window visible
    public static void openPopupWindow(MouseEvent event, String fxmlPath, String cssPath) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(uiService.class.getResource(fxmlPath));
        Stage stage = new Stage();
        stage.setTitle("Enter Discount");
        stage.centerOnScreen();
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(uiService.class.getResource(cssPath).toExternalForm());
        stage.setScene(scene);
        stage.show();

        //Fade in effect
        new FadeIn(scene.getRoot()).play();
    }


    //Crete a method to get FXML controller
    public static <T> T getFxmlController(String fxmlPath){
        //Holding the fxml controller
        FXMLLoader loader = new FXMLLoader(uiService.class.getResource(fxmlPath));
        try {
            Parent root = loader.load();
            return loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

