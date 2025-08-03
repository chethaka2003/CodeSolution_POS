package Controllers;

import Services.uiService;
import animatefx.animation.Shake;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginController {

    @FXML
    private PasswordField mainPassword;

    @FXML
    private TextField mainUserName;

    @FXML
    void onClickForgetPwd(MouseEvent event) {

    }

    @FXML
    void onClickLogin(MouseEvent event) {
        String userName = mainUserName.getText();
        String password = mainPassword.getText();
        validatePassowrd(userName,password);
    }

    //Check weather default passwords and usernames are correct or not
    public void validatePassowrd(String userName, String password) {
        if (userName.equals("admin") && password.equals("admin")) {
            uiService.giveConfirmationAlert("Login Successful",null,"Username and password are correct");
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

}
