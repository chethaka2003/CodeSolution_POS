package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;

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
    void onDone(ActionEvent event) {

    }

    @FXML
    void onNextCustomer(ActionEvent event) {

    }

    @FXML
    void onPrintInvoice(ActionEvent event) {

    }

}
