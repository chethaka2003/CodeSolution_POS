package Controllers;

import DataBase.ItemTableDB;
import Services.uiService;
import com.codesolution.cs_pos_v1.Item;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TransactionController {

    @FXML
    private Button btnAddItem;

    @FXML
    private Button btnDone;

    @FXML
    private Button btnNext;

    @FXML
    private Button btnPrint;

    @FXML
    private ComboBox<?> cmbPaymentMethod;

    @FXML
    private ScrollPane itemScrollHolder;

    @FXML
    private TextField txtCustomerId;

    @FXML
    private TextField txtDiscount;

    @FXML
    private TextField txtNetAmount;

    @FXML
    private TextField txtNetAmount1;

    @FXML
    private TextField txtTotal;

    @FXML
    private TextField itemCode;


    @FXML
    private VBox container;



    @FXML
    void onDone(ActionEvent event) {

    }

    @FXML
    void onNextCustomer(ActionEvent event) {

    }

    @FXML
    void onPrintInvoice(ActionEvent event) {

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

}
