package Controllers;

import DataBase.ItemTableDB;
import DataBase.SupplierTableDB;
import Services.uiService;
import animatefx.animation.Shake;
import com.codesolution.cs_pos_v1.Item;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ItemController implements Initializable {

    //Adding out Variables
    public int out_lastDigits;
    public String out_itemCode;
    public String out_itemName;
    public String out_category;
    public String out_subCategory;
    public int out_warranty;
    public int out_costPrice;
    public int out_sellPrice;
    public int out_wholeSellPrice;
    public int out_stockCount;
    public String out_Brand;
    public String out_itemSize;
    public String out_itemColor;
    public String out_imagePath;
    public String out_supItemCode;
    public  String out_supId;
    public String out_barCode;

    //Default image path if image path is not available or not set
    public String defaultImagePath = "src/main/resources/Inputs/noPreviewDefault.jpeg";

    @FXML
    private VBox itemAddView;

    @FXML
    private TextField itemBrand;

    @FXML
    private TextField itemCat;

    @FXML
    private TextField itemColor;

    @FXML
    private TextField itemCostPrice;

    @FXML
    private TextField itemName;

    @FXML
    private TextField itemSellPrice;

    @FXML
    private TextField itemSize;

    @FXML
    private TextField itemStock;

    @FXML
    private TextField itemSubCat;

    @FXML
    private TextField itemSupplierId;

    @FXML
    private TextField itemWarrenty;

    @FXML
    private TextField itemWholeSellPrice;

    @FXML
    private TextField supItemCode;

    public void initialize(URL url , ResourceBundle rb) {

        //Getting previous entered values
        ArrayList<String> suggestedBrands = ItemTableDB.getSuggestList("itemBrand");


        //Adding suggested list into textField
        TextFields.bindAutoCompletion(itemBrand, suggestedBrands);

        //Adding validations
        itemName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.length() > 45) {
                    itemName.setText(oldValue);
                    new Shake(itemName).play();
                    uiService.giveErrorAlert("Limit exceeded", null, "Item name is too long. Keep it under 45 characters.");
                } else {
                    itemName.setText(newValue);
                }
            }
        });

        checkLength(itemCat, 15, "Item Category name");
        checkLength(itemSubCat, 15, "Item sub Category name");
        checkLength(itemBrand,15,"Item brand name");
        checkLength(itemSize,15,"Item size");
        checkLength(itemColor,15,"Item color");

        //Check the data type
        checkType(itemWarrenty,"\\d*","Item warranty");
        checkType(itemCostPrice,"\\d*","Item cost price");
        checkType(itemSellPrice,"\\d*","Item selling price");
        checkType(itemWholeSellPrice,"\\d*","Item whole sale price");
        checkType(itemStock,"\\d*","Item Stock count");





    }


    @FXML
    void backItemAdd(MouseEvent event) {

    }

    @FXML
    void onClickPrintBarcode(MouseEvent event) {


    }

    @FXML
    void onItemAddClicked(MouseEvent event) {



    }

    public String genItemCode(){
        //Getting the date part
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String datePart = formatter.format(today);

        String firstPart = "KAS";
        //Getting the round part
        String lastDigits;
        try {
            lastDigits = ItemTableDB.getLastDigits();
            out_lastDigits = Integer.parseInt(lastDigits);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String itemCode = firstPart+datePart+lastDigits;
        return itemCode;
    }

    public String genBarcode(){

        String firstPart = "KAS";

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
        String datePart = formatter.format(today);

        String catPart = out_category.toUpperCase().substring(0,3);

        String barcode = firstPart+"-"+datePart+"-"+catPart+"-"+String.valueOf(out_lastDigits);

        return barcode;
    }

    //Checks the length of the textField
    public void checkLength(TextField textField, int length,String inputFieldName){
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.length()>length){
                    textField.setText(oldValue);
                    new Shake(textField).play();
                    uiService.giveErrorAlert("Limit exceeded",null,inputFieldName+" is too long. Keep it under "+length+" characters.");
                }
                else {
                    textField.setText(newValue);
                }
            }
        });
    }

    //Checking the type of inputField values
    public void checkType(TextField textField , String Regex ,String inputFieldName){
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches(Regex)){
                    textField.setText(oldValue);
                    new Shake(textField).play();
                    uiService.giveErrorAlert("Incorrect data type",null,inputFieldName+" Can't have String data values");
                }
                else {
                    textField.setText(newValue);
                }
            }
        });
    }

//"\\d*"



    @FXML
    void onItemHomeClicked(MouseEvent event) {

    }

    @FXML
    void onItemUpdtClicked(MouseEvent event) {

    }

    @FXML
    void fileSelectClick(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image files","*jpg"),new FileChooser.ExtensionFilter("PNG image","*.png"));
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        out_imagePath = selectedFile.toString();
        uiService.giveConfirmationAlert("File selected",null,"You have selected a item image successfully");

    }

    @FXML
    void onClickAdditem(MouseEvent event) {

        if(itemName.getText().isEmpty()||itemCat.getText().isEmpty()||itemWarrenty.getText().isEmpty()||itemCostPrice.getText().isEmpty()||itemSellPrice.getText().isEmpty()||itemStock.getText().isEmpty()||supItemCode.getText().isEmpty()||itemSupplierId.getText().isEmpty()){
            uiService.giveErrorAlert("Missing values",null,"Please enter all the compulsory values");
        } else if (itemCat.getText().length()<4) {
            uiService.giveErrorAlert("Incorrect value",null,"Category should have at least 4 characters");
        } else {
            //Assigning all values
            out_itemName = itemName.getText();
            out_category = itemCat.getText();
            out_subCategory = itemSubCat.getText();
            out_warranty = Integer.parseInt(itemWarrenty.getText());
            out_costPrice = Integer.parseInt(itemCostPrice.getText());
            out_sellPrice = Integer.parseInt(itemSellPrice.getText());
            if(itemWholeSellPrice.getText().isEmpty()){
                out_wholeSellPrice = 0;
            }else {
                out_wholeSellPrice = Integer.parseInt(itemWholeSellPrice.getText());
            }
            out_stockCount = Integer.parseInt(itemStock.getText());
            out_Brand = itemBrand.getText();
            out_itemSize = itemSize.getText();
            out_itemColor = itemColor.getText();
            out_supItemCode = supItemCode.getText();
            out_supId = itemSupplierId.getText();
            out_itemCode = genItemCode();
            out_barCode = genBarcode();




            //Assigning optional values
            if (itemSubCat.getText().isEmpty()){
                out_subCategory = "Not specified";
            }
            if (itemWholeSellPrice.getText().isEmpty()){
                out_wholeSellPrice = 0;

            }
            if (itemBrand.getText().isEmpty()){
                out_Brand = "Not specified";
            }
            if (itemSize.getText().isEmpty()){
                out_itemSize = "Not specified";
            }
            if (itemColor.getText().isEmpty()){
                out_itemColor = "Not specified";
            }
            if(out_imagePath == null){
                out_imagePath = defaultImagePath;
            }

            Item item = new Item(out_itemCode,out_itemName,out_supItemCode,out_category,out_subCategory,out_warranty,out_costPrice,out_sellPrice,out_wholeSellPrice,out_stockCount,out_supId,out_barCode,out_Brand,out_itemSize,out_itemColor,out_imagePath);

            int rowsInserted = ItemTableDB.addItem(item,out_lastDigits);
            if (rowsInserted>0){
                uiService.giveConfirmationAlert("Item added successfully",null,"Item "+out_itemName+" added successfully");
            }
            else{
                uiService.giveErrorAlert("Item failed to add database",null,"Please check the supplier iD is available in the suppliers list");
            }

        }

    }


}
