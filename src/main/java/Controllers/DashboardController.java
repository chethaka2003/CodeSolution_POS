package Controllers;

import Services.uiService;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class DashboardController {

    @FXML
    void onCusClick(MouseEvent event) throws IOException {
        uiService.switchScene(event,"/com/codesolution/cs_pos_v1/fxmls/CustomerManage.fxml","/Styles/mainStyle.css");
    }

    @FXML
    void onSupplierClick(MouseEvent event) throws IOException {
        uiService.switchScene(event,"/com/codesolution/cs_pos_v1/fxmls/SupplierManage.fxml","/Styles/mainStyle.css");
    }
}
