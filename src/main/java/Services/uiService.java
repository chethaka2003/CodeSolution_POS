package Services;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;

public class uiService {

    //This methods loads thu UI

    public static Stage getUi(String fxmlFile,String cssFile,String title,Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(uiService.class.getResource(fxmlFile));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(uiService.class.getResource(cssFile).toExternalForm());
        stage.setScene(scene);
        stage.setTitle(title);
        return stage;
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

