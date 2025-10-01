package Controllers;

import Services.uiService;
import animatefx.animation.Shake;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddDiscountPopupController implements Initializable {

    @FXML
    private TextField popDisAmount;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        popDisAmount.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                uiService.giveErrorAlert("Incorrect Discount",null,"Please enter only digits");
                popDisAmount.setText(oldValue);
                new Shake(popDisAmount).play();
            }
            else {
                popDisAmount.setText(newValue);
            }
        });
    }

    @FXML
    void popAddDisClick(MouseEvent event) {
        if (popDisAmount.getText().isBlank()||popDisAmount.getText().isEmpty()) {
            uiService.giveErrorAlert("Incorrect Discount",null,"Please enter a discount amount");
        } else if (Integer.parseInt(popDisAmount.getText())<=0) {
            uiService.giveErrorAlert("Please enter a positive number",null,"Please enter a discount amount");
        }
        else {
            TransactionController.GlobetransactionController.setDiscountAmount(Integer.parseInt(popDisAmount.getText()));
        }
    }

    @FXML
    void popCancelClick(MouseEvent event) {
        Stage stage = (Stage) popDisAmount.getScene().getWindow();
        stage.close();
    }

}
