package Controllers;

import DataBase.ItemTableDB;
import Services.uiService;
import com.codesolution.cs_pos_v1.Item;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TransactionController {

    @FXML
    private Button btnDone;

    @FXML
    private Button btnNext;

    @FXML
    private Button btnPrint;

    @FXML
    private TextField cashInAmount;

    @FXML
    private ComboBox<?> cmbPaymentMethod;

    @FXML
    private VBox container;

    @FXML
    private Label currentDate;

    @FXML
    private Label currentRP;

    @FXML
    private Label currentTime;

    @FXML
    private Label discount;

    @FXML
    private TextField itemCode;

    @FXML
    private ScrollPane itemScrollHolder;

    @FXML
    private Label netAmount;

    @FXML
    private Label totalAmount;

    @FXML
    private TextField txtCustomerId;

    @FXML
    void b2Dboard(MouseEvent event) {

    }

    @FXML
    void onAddItem(ActionEvent event) {
        if (itemCode.getText().isEmpty()||itemCode.getText().isBlank()){
            uiService.giveErrorAlert("Empty Value",null,"Please enter ItemCode or Barcode");
        }else{
            Item item = ItemTableDB.getItem(itemCode.getText());
            if (item == null){
                uiService.giveErrorAlert("Incorrect ID",null,"Please check the Item Code or Barcode");
            }else {
                //Creating each rows
                HBox hBox = new HBox();
                hBox.setPrefSize(752.00,43.00);
                hBox.setAlignment(Pos.CENTER_LEFT);
            }
        }
    }

    @FXML
    void onNextCustomer(MouseEvent event) {

    }

    @FXML
    void onPay(MouseEvent event) {

    }

    @FXML
    void onPrintInvoice(MouseEvent event) {

    }

}

