package Controllers;

import DataBase.ItemTableDB;
import Services.uiService;
import com.codesolution.cs_pos_v1.Item;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;

public class TransactionController {

    private int totalAmountToPay = 0;

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
            }else {
                //Creating each rows
                addItem(item);

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


    //Adding items into Vbox container
    public void addItem(Item item){
        HBox hBox = new HBox();
        hBox.setPrefSize(787.00,43.00);
        hBox.setAlignment(Pos.CENTER_LEFT);

        Label itemName = new Label(item.getItemName());
        itemName.setPrefSize(240.00,17.00);
        itemName.setAlignment(Pos.CENTER_LEFT);

        Label itemCode = new Label(item.getItemCode());
        itemCode.setPrefSize(181.00,17.00);
        itemCode.setAlignment(Pos.CENTER_LEFT);

        Label itemWarrenty = new Label(String.valueOf(item.getWarrentyMonths()));
        itemWarrenty.setPrefSize(100.00,17.00);
        itemWarrenty.setAlignment(Pos.CENTER_LEFT);

        Label itemPrice = new Label("Rs."+item.getSellingPrice()+"/=");
        itemPrice.setPrefSize(139.00,17.00);
        itemPrice.setAlignment(Pos.CENTER_LEFT);

        Spinner qty = new Spinner(1,999,1);
        qty.setPrefSize(54.00,25.00);
        qty.valueProperty().addListener((observable,oldValue,newValue)-> {
            int total = Integer.parseInt(newValue.toString()) * item.getSellingPrice();
            itemPrice.setText("Rs." + total + "/=");
            updateGrandTotal();
        });

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

        //Adding Price into total values

    }

    //This updates the grand total
    public void updateGrandTotal(){
        
        double total = 000.00;
        for (Node node : container.getChildren()) {
            if (node instanceof HBox hbox) {
                for (Node child : hbox.getChildren()) {
                    if (child instanceof Label label) {
                        String text = label.getText();
                        if (text.startsWith("Rs.") && text.endsWith("/=")) {
                            String amountStr = text.replace("Rs.", "").replace("/=", "").trim();
                            try {
                                total += Double.parseDouble(amountStr);
                            } catch (NumberFormatException ignored) {}
                        }
                    }
                }
            }
        }

        String formattedTotal = String.format("%.2f", total);
        totalAmount.setText("Rs."+formattedTotal+"/=");

    }



}

