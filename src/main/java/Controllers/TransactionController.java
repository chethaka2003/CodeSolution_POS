package Controllers;

import DataBase.CustomerTableDB;
import DataBase.ItemTableDB;
import DataBase.TransactionTableDB;
import Services.BillPrintService;
import Services.uiService;
import com.codesolution.cs_pos_v1.CartRow;
import com.codesolution.cs_pos_v1.Item;
import com.codesolution.cs_pos_v1.Transaction;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class TransactionController implements Initializable {

    public double totalAmountToPay = 0.00;
    public int discountedAmount = 0;
    public double netTotalToPay = 0.00;

    public String paymentMethod;
    public  int intLastDigits;

    public boolean isPaid = false;


    public static TransactionController GlobetransactionController;

    //Final Item details
    List<CartRow> cartRows = new ArrayList<>();


    @FXML
    private Button btnDone;

    @FXML
    private Button btnNext;

    @FXML
    private Button btnPrint;

    @FXML
    private TextField cashInAmount;

    @FXML
    private ComboBox<String> cmbPaymentMethod;

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
    void b2Dboard(MouseEvent event) throws IOException {
        uiService.switchScene(event,"/com/codesolution/cs_pos_v1/fxmls/dashboard.fxml","/Styles/mainStyle.css");
    }

    @FXML
    void onAddItem(ActionEvent event) {
        if (itemCode.getText().isEmpty()||itemCode.getText().isBlank()){
            uiService.giveErrorAlert("Empty Value",null,"Please enter ItemCode or Barcode");
        }else{
            Item item = ItemTableDB.getItem(itemCode.getText());
            if (item == null){
                uiService.giveErrorAlert("Incorrect ID",null,"Please check the Item Code or Barcode");
            }
            else {
                //Creating each rows
                addItem(item);

            }
        }
    }

    @FXML
    void onNextCustomer(MouseEvent event) {
        isPaid = false;     //allowing to add next customer
    }

    @FXML
    void onPay(MouseEvent event) {
        if(!isPaid) {
            updateCart();
            String transactionID = genTransaction_id();
            Transaction transaction = new Transaction(transactionID, null, null, cmbPaymentMethod.getValue(), totalAmountToPay, discountedAmount, netTotalToPay, intLastDigits,"test");
            TransactionTableDB.addTransactionData(transaction);
            isPaid = true;
        } else {
            uiService.giveErrorAlert("Already paid",null,"This Transaction is already paid. PLease press next Customer");
        }
    }

    @FXML
    void onPrintInvoice(MouseEvent event) throws Exception {
        if (cartRows.isEmpty()||cartRows == null){
            uiService.giveErrorAlert("Empty Value",null,"Please enter ItemCode or Barcode");
        }
        BillPrintService.printReceipt(cartRows,"Shalika Super","No 52 ,Katukurunda","Moratuwa","070 50 67 377","038 22 44 172",totalAmountToPay,discountedAmount,netTotalToPay );
    }


    //Adding items into Vbox container
    public void addItem(Item item){
        HBox hBox = new HBox();
        hBox.setPrefSize(787.00,43.00);
        hBox.setAlignment(Pos.CENTER_LEFT);

        Label itemName = new Label(item.getItemName());
        itemName.setPrefSize(240.00,17.00);
        itemName.setAlignment(Pos.CENTER_LEFT);
        itemName.setId("itemName");

        Label itemCode = new Label(item.getItemCode());
        itemCode.setPrefSize(181.00,17.00);
        itemCode.setAlignment(Pos.CENTER_LEFT);

        Label itemWarrenty = new Label(String.valueOf(item.getWarrentyMonths()));
        itemWarrenty.setPrefSize(100.00,17.00);
        itemWarrenty.setAlignment(Pos.CENTER_LEFT);

        Label itemPrice = new Label("Rs."+item.getSellingPrice()+"/=");
        itemPrice.setPrefSize(139.00,17.00);
        itemPrice.setAlignment(Pos.CENTER_LEFT);
        itemPrice.setId("itemPrice");

        Spinner qty = new Spinner(1,999,1);
        qty.setPrefSize(54.00,25.00);
        qty.valueProperty().addListener((observable,oldValue,newValue)-> {
            int total = Integer.parseInt(newValue.toString()) * item.getSellingPrice();
            itemPrice.setText("Rs." + total + "/=");
            updateGrandTotal();
            updateNetTotal();
        });
        qty.setId("qty");

        File newfile = new File("src/main/resources/Icons/remove.png");
        Image image = new Image(newfile.toURI().toString());
        System.out.println(newfile.toURI());
        ImageView closeButton = new ImageView();
        closeButton.setImage(image);
        closeButton.setFitWidth(20.00);
        closeButton.setFitHeight(20.00);
        closeButton.setOnMouseClicked(event -> {
            container.getChildren().remove(hBox);
            updateGrandTotal();
        });

        //adding item into HBox
        hBox.getChildren().addAll(itemCode,itemName,itemWarrenty,qty,itemPrice,closeButton);
        hBox.setStyle("-fx-border-color: red;");
        hBox.setSpacing(10);
        container.getChildren().add(hBox);

        //Updating the grand total
        updateGrandTotal();
        updateNetTotal();

        //Adding Price into total values

    }

    //This updates the grand total
    public void updateGrandTotal(){
        
        totalAmountToPay = 000.00;
        for (Node node : container.getChildren()) {
            if (node instanceof HBox hbox) {
                for (Node child : hbox.getChildren()) {
                    if (child instanceof Label label) {
                        String text = label.getText();
                        if (text.startsWith("Rs.") && text.endsWith("/=")) {
                            String amountStr = text.replace("Rs.", "").replace("/=", "").trim();
                            try {
                                totalAmountToPay += Double.parseDouble(amountStr);
                            } catch (NumberFormatException ignored) {}
                        }
                    }
                }
            }
        }

        String formattedTotal = String.format("%.2f", totalAmountToPay);
        totalAmount.setText("Rs."+formattedTotal+"/=");

    }

    public void updateNetTotal(){
        netTotalToPay = totalAmountToPay - discountedAmount;
        netAmount.setText("Rs."+netTotalToPay+"0/=");
    }

    //Adding a setter to discount
    public void setDiscountAmount(int discountAmount) {
        discountedAmount = discountAmount;
        discount.setText("- Rs."+String.valueOf(discountAmount)+"/=");
        updateGrandTotal();
        updateNetTotal();
    }

    @FXML
    void onClickAddDiscountBtn(MouseEvent event) throws IOException {
        uiService.openPopupWindow(event, "/com/codesolution/cs_pos_v1/fxmls/Popups/AddDiscountPopup.fxml","/Styles/mainStyle.css");

    }

    //Adding items into cart
    public List<CartRow> updateCart(){

        cartRows.clear();

        for (Node node : container.getChildren()) {
            if (node instanceof HBox hbox) {

                //Initializing variables
                String itemName = null;
                String itemPrice = null;
                String qty = null;
                for (Node child : hbox.getChildren()) {
                    if (child instanceof Label label && "itemName".equals(label.getId())) {
                        itemName = label.getText();
                    }
                    if (child instanceof Label label && "itemPrice".equals(label.getId())) {
                        itemPrice = label.getText();
                    }
                    if (child instanceof Spinner spinner && "qty".equals(spinner.getId())) {
                        qty = String.valueOf(spinner.getValue());
                    }
                }

                CartRow cartRow = new CartRow(itemName,qty,itemPrice);
                cartRows.add(cartRow);

            }
        }

        return cartRows;

    }

    //Generating transaction ID
    public String genTransaction_id() {

        //Getting the date part
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String datePart = formatter.format(today);

        //Getting the round part
        String lastDigits;
        try {
            lastDigits = TransactionTableDB.getLastDigits();
            intLastDigits = Integer.parseInt(lastDigits);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String transactionID = datePart+lastDigits;
        return transactionID;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        GlobetransactionController = this ;
        cmbPaymentMethod.getItems().addAll("Card","Cash","Card + Cash");
        cmbPaymentMethod.setValue("Cash");

    }
}

