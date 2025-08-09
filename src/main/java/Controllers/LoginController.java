package Controllers;

import Services.uiService;
import animatefx.animation.FadeIn;
import animatefx.animation.Shake;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private PasswordField mainPassword;

    @FXML
    private TextField mainUserName;

    @FXML
    private Button loginBtn;

    @FXML
    private VBox loginVbox;

    @FXML
    void onClickForgetPwd(MouseEvent event) {

    }

    @FXML
    void onClickLogin(MouseEvent event) throws IOException {
        String userName = mainUserName.getText();
        String password = mainPassword.getText();
        if (userName.equals("admin") && password.equals("admin")) {
            uiService.giveConfirmationAlert("Login Successful",null,"Username and password are correct");
            //Switch the scene
            uiService.switchScene(event,"/com/codesolution/cs_pos_v1/fxmls/BuisnessSelector.fxml","/Styles/mainStyle.css");
        }
        else if(userName.isEmpty() || password.isEmpty()) {
            uiService.giveErrorAlert("Login Failed",null,"Username or password is empty");
            if(userName.isEmpty()){
                new Shake(mainUserName).play();
            }
            if(password.isEmpty()){
                new Shake(mainPassword).play();
            }
        }
        else {
            uiService.giveErrorAlert("Login Failed",null,"Username or Password is incorrect");
            //Clears the input fields
            mainUserName.clear();
            mainPassword.clear();

            new Shake(mainUserName).play();
            new Shake(mainPassword).play();
        }



    }

    public void initialize(URL location, ResourceBundle resources) {

        loginVbox.sceneProperty().addListener(new ChangeListener<Scene>() {
            @Override
            public void changed(ObservableValue<? extends Scene> observable,
                                Scene oldScene, Scene newScene) {
                if (newScene != null) {
                    newScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent event) {
                            switch (event.getCode()) {
                                case ENTER:
                                    String userName = mainUserName.getText();
                                    String password = mainPassword.getText();
                                    if (userName.equals("admin") && password.equals("admin")) {
                                        uiService.giveConfirmationAlert("Login Successful",null,"Username and password are correct");
                                        //Switch the scene
                                        try {
                                            uiService.switchSceneKeyboard(event,"/com/codesolution/cs_pos_v1/fxmls/BuisnessSelector.fxml","/Styles/mainStyle.css");
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                    else if(userName.isEmpty() || password.isEmpty()) {
                                        uiService.giveErrorAlert("Login Failed",null,"Username or password is empty");
                                        if(userName.isEmpty()){
                                            new Shake(mainUserName).play();
                                        }
                                        if(password.isEmpty()){
                                            new Shake(mainPassword).play();
                                        }
                                    }
                                    else {
                                        uiService.giveErrorAlert("Login Failed",null,"Username or Password is incorrect");
                                        //Clears the input fields
                                        mainUserName.clear();
                                        mainPassword.clear();

                                        new Shake(mainUserName).play();
                                        new Shake(mainPassword).play();
                                        break;
                                    }
                                case DOWN:
                                    mainPassword.requestFocus();
                                    break;

                            }
                        }
                    });
                    mainUserName.setOnKeyPressed(new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent event) {
                            if (event.getCode() == KeyCode.DOWN) {
                                mainPassword.requestFocus();
                            }
                        }
                    });
                    mainPassword.setOnKeyPressed(new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent event) {
                            if (event.getCode() == KeyCode.UP) {
                                mainUserName.requestFocus();
                            }
                        }
                    });

                    mainUserName.requestFocus();
                }
            }
        });
    }

    //Check weather default passwords and usernames are correct or not
//    public void validatePassowrd(String userName, String password) {
//        if (userName.equals("admin") && password.equals("admin")) {
//            uiService.giveConfirmationAlert("Login Successful",null,"Username and password are correct");
//            //Switch the scene
//            uiService.switchScene(event,"/com/codesolution/cs_pos_v1/fxmls/BuisnessSelector.fxml","/Styles/mainStyle.css");
//        }
//        else if(userName.isEmpty() || password.isEmpty()) {
//            uiService.giveErrorAlert("Login Failed",null,"Username or password is empty");
//            if(userName.isEmpty()){
//                new Shake(mainUserName).play();
//            }
//            if(password.isEmpty()){
//                new Shake(mainPassword).play();
//            }
//        }
//        else {
//            uiService.giveErrorAlert("Login Failed",null,"Username or Password is incorrect");
//            //Clears the input fields
//            mainUserName.clear();
//            mainPassword.clear();
//
//            new Shake(mainUserName).play();
//            new Shake(mainPassword).play();
//        }
//    }

}
