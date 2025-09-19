package Controllers;

import DataBase.ItemTableDB;
import DataBase.SupplierTableDB;
import Services.uiService;
import animatefx.animation.Shake;
import com.codesolution.cs_pos_v1.Item;
import com.codesolution.cs_pos_v1.Supplier;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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

    //Searched item code
    public String out_searchItemCode;

    //Default image path if image path is not available or not set
    public String defaultImagePath = "src/main/resources/Inputs/noPreviewDefault.jpeg";

    @FXML
    private VBox cusUpdtView;

    @FXML
    private VBox itemFindView;

    @FXML
    private VBox itemAddView;

    @FXML
    private TextField itemBarCode;

    @FXML
    private Label itemBarCodeLbl;

    @FXML
    private TextField itemBrand;

    @FXML
    private Label itemBrandLbl;

    @FXML
    private TextField itemCat;

    @FXML
    private Label itemCatLbl;

    @FXML
    private Label itemCodeLbl;

    @FXML
    private TextField itemColor;

    @FXML
    private Label itemColorLbl;

    @FXML
    private Label itemCostLbl;

    @FXML
    private TextField itemCostPrice;

    @FXML
    private Label itemCreateDateLbl;

    @FXML
    private ImageView itemImage;

    @FXML
    private TextField itemName;

    @FXML
    private Label itemNameLbl;

    @FXML
    private TextField itemNbarcode;

    @FXML
    private TextField itemNbrand;

    @FXML
    private TextField itemNcat;

    @FXML
    private TextField itemNcolor;

    @FXML
    private TextField itemNcost;

    @FXML
    private TextField itemNname;

    @FXML
    private TextField itemNsell;

    @FXML
    private TextField itemNsize;

    @FXML
    private TextField itemNstock;

    @FXML
    private TextField itemNsubCat;

    @FXML
    private TextField itemNsupId;

    @FXML
    private TextField itemNsupItemCode;

    @FXML
    private TextField itemNwarrenty;

    @FXML
    private TextField itemNwholeSell;

    @FXML
    private Label itemSellLbl;

    @FXML
    private TextField itemSellPrice;

    @FXML
    private TextField itemSize;

    @FXML
    private Label itemSizeLbl;

    @FXML
    private Label itemSoldLocLbl;

    @FXML
    private TextField itemStock;

    @FXML
    private Label itemStockLbl;

    @FXML
    private TextField itemSubCat;

    @FXML
    private Label itemSubCatLbl;

    @FXML
    private Label itemSupIdLbl;

    @FXML
    private TextField itemSupplierId;

    @FXML
    private Label itemUpdtDateLbl;

    @FXML
    private TextField itemWarrenty;

    @FXML
    private Label itemWholeSellLbl;

    @FXML
    private TextField itemWholeSellPrice;

    @FXML
    private ScrollPane suppliersList;

    @FXML
    private TextField srchItemId;

    @FXML
    private TextField supItemCode;

    @FXML
    private Label supItemCodeLbl;

    @FXML
    private HBox updateValueHbox;

    @FXML
    private ChoiceBox<String> findItemBrand;

    @FXML
    private ChoiceBox<String> findItemCat;

    @FXML
    private ChoiceBox<String> findItemColor;

    @FXML
    private TextField findItemName;

    @FXML
    private ChoiceBox<String> findItemSize;

    @FXML
    private ChoiceBox<String> findItemSubCat;


    public void initialize(URL url , ResourceBundle rb) {

        //Getting previous entered values
        ArrayList<String> suggestedBrands = ItemTableDB.getSuggestList("itemBrand");
        ArrayList<String> suggestedCategories = ItemTableDB.getSuggestList("item_category");
        ArrayList<String> suggestedSubCategories = ItemTableDB.getSuggestList("item_SubCategory");
        ArrayList<String> suggestedColors = ItemTableDB.getSuggestList("itemColor");
        ArrayList<String> suggestedSize = ItemTableDB.getSuggestList("itemSize");


        //Adding suggested list into textField
        TextFields.bindAutoCompletion(itemBrand, suggestedBrands);

        //Adding choiceBox values into ChoiceBox
        findItemBrand.getItems().addAll(suggestedBrands);
        findItemBrand.getItems().add("Any");
        findItemBrand.setValue("Any");

        findItemSize.getItems().addAll(suggestedSize);
        findItemSize.getItems().add("Any");
        findItemSize.setValue("Any");

        findItemColor.getItems().addAll(suggestedColors);
        findItemColor.getItems().add("Any");
        findItemColor.setValue("Any");

        findItemCat.getItems().addAll(suggestedCategories);
        findItemCat.getItems().add("Any");
        findItemCat.setValue("Any");

        findItemSubCat.getItems().addAll(suggestedSubCategories);
        findItemSubCat.getItems().add("Any");
        findItemSubCat.setValue("Any");


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

        checkLength(itemNname,45,"Item new Name");
        checkLength(itemNcat,15,"Item new Category");
        checkLength(itemNsubCat,15,"Item new Sub category");
        checkLength(itemNbrand,15,"Item New brand");
        checkLength(itemNsize,15,"Item new size");
        checkLength(itemNcolor,15,"Item new color");


        //Check the data type
        checkType(itemWarrenty,"\\d*","Item warranty");
        checkType(itemCostPrice,"\\d*","Item cost price");
        checkType(itemSellPrice,"\\d*","Item selling price");
        checkType(itemWholeSellPrice,"\\d*","Item whole sale price");
        checkType(itemStock,"\\d*","Item Stock count");


        checkType(itemNwarrenty,"\\d*","Item New warranty in months");
        checkType(itemNcost,"\\d*","Item New cost Price");
        checkType(itemNsell,"\\d*","Item New selling price");
        checkType(itemNwholeSell,"\\d*","Item New Whole sell price");
        checkType(itemNstock,"\\d*","Item New stock count");






    }



    @FXML
    void onClickPrintBarcode(MouseEvent event) {


    }

    @FXML
    void searchItemClick(MouseEvent event) {

        List<Item> items;

        String itemBrand = findItemBrand.getValue();
        String itemName = findItemName.getText();
        String itemSize = findItemSize.getValue();
        String itemColor = findItemColor.getValue();
        String itemCat = findItemCat.getValue();
        String itemSubCat = findItemSubCat.getValue();

        items = ItemTableDB.searchItem(itemName,itemCat,itemSubCat,itemColor,itemSize,itemBrand);
        if (items.isEmpty()){
            System.out.println("No values found");
        }
        for (Item item :items){
            System.out.println(item.getItemName());
            createItemRow(suppliersList,items);
        }

    }




    @FXML
    void rmveBtnClk(MouseEvent event) {

    }

    @FXML
    void onClickSelectNewImage(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image files","*jpg"),new FileChooser.ExtensionFilter("PNG image","*.png"));
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile.exists()) {
            Image image = new Image(selectedFile.toURI().toString());
            uiService.askConfirmation("Image change confirmation",null,"Are you sure you want to change image",()->ItemTableDB.updateItemStringData("item_image",selectedFile.toString(),out_itemCode));
            itemImage.setImage(image);
            System.out.println("Loaded: " + selectedFile.getAbsolutePath());

        } else {
            uiService.giveErrorAlert("Incorrect File",null,"Please enter correct file path");
        }
    }

    //Getting
    @FXML
    void findItemClick(MouseEvent event) {
        if (srchItemId.getText().isEmpty() ||srchItemId.getText().isBlank()){
            uiService.giveErrorAlert("Empty value",null,"Please Enter valid Barcode ore Item ID");
        }
        else{
            updateValueHbox.setVisible(true);
            String itemCode = srchItemId.getText();
            out_searchItemCode = itemCode;
            Item item = ItemTableDB.getItem(itemCode);
            if (item == null){
                uiService.giveErrorAlert("Incorrect ItemCode or BarCode",null,"Please recheck the  item code or barcode you have entered");
            }else {
                itemCodeLbl.setText(item.getItemCode());
                itemNameLbl.setText(item.getItemName());
                supItemCodeLbl.setText(item.getSupplierItemCode());
                itemCatLbl.setText(item.getCategory());
                itemSubCat.setText(item.getSubCategory());
                itemCostLbl.setText(String.valueOf(item.getCostPrice()));
                itemSellLbl.setText(String.valueOf(item.getSellingPrice()));
                itemWholeSellLbl.setText(String.valueOf(item.getWholeSalePrice()));
                itemStockLbl.setText(String.valueOf(item.getStockCount()));
                itemBarCodeLbl.setText(item.getBarcode());
                itemSoldLocLbl.setText(ItemTableDB.getItemData("item_sold_loc",itemCode));
                itemSupIdLbl.setText(item.getSupplierID());
                itemBrandLbl.setText(item.getBrand());
                itemSizeLbl.setText(item.getSize());
                itemColorLbl.setText(item.getColor());
                itemCreateDateLbl.setText(ItemTableDB.getItemData("item_createdDate",itemCode));
                itemUpdtDateLbl.setText(ItemTableDB.getItemData("item_updateDate",itemCode));
                File file = new File(item.getImagePath());   // e.g. "C:\\Users\\Chethaka\\Pictures\\item.png"
                if (file.exists()) {
                    Image image = new Image(file.toURI().toString());
                    itemImage.setImage(image);
                    System.out.println("Loaded: " + file.getAbsolutePath());
                } else {
                    File newfile = new File(defaultImagePath);
                    Image image = new Image(newfile.toURI().toString());
                    itemImage.setImage(image);
                    System.out.println("Loaded: " + file.getAbsolutePath());
                }


            }

        }
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

        String barcode = firstPart+"-"+datePart+"-"+String.valueOf(out_lastDigits);

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

    @FXML
    void onClickAddBarCode(MouseEvent event) {

    }




    //Adding home click button
    @FXML
    void onItemHomeClicked(MouseEvent event) {

    }

    @FXML
    void onItemAddClicked(MouseEvent event) {

        Node clickedNode = (Node) event.getSource();

        Parent parent = clickedNode.getParent();

        for (Node child : parent.getChildrenUnmodifiable()) {
            child.getStyleClass().remove("setActive");
        }

        clickedNode.getStyleClass().add("setActive");

        System.out.println("Clicked: " + clickedNode);

        cusUpdtView.setVisible(false);
        updateValueHbox.setVisible(false);
        itemAddView.setVisible(true);
        itemFindView.setVisible(false);



    }

    @FXML
    void onItemFindClicked(MouseEvent event) {
        Node clickedNode = (Node) event.getSource();

        Parent parent = clickedNode.getParent();

        for (Node child : parent.getChildrenUnmodifiable()) {
            child.getStyleClass().remove("setActive");
        }

        clickedNode.getStyleClass().add("setActive");

        System.out.println("Clicked: " + clickedNode);

        cusUpdtView.setVisible(false);
        updateValueHbox.setVisible(false);
        itemAddView.setVisible(false);
        itemFindView.setVisible(true);

    }


    @FXML
    void onItemUpdtClicked(MouseEvent event) {
        Node clickedNode = (Node) event.getSource();

        Parent parent = clickedNode.getParent();

        for (Node child : parent.getChildrenUnmodifiable()) {
            child.getStyleClass().remove("setActive");
        }

        clickedNode.getStyleClass().add("setActive");

        System.out.println("Clicked: " + clickedNode);
        cusUpdtView.setVisible(true);
        updateValueHbox.setVisible(false);
        itemAddView.setVisible(false);
        itemFindView.setVisible(false);
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
            if (itemBarCode.getText().isEmpty() || itemBarCode.getText().isBlank()){
                out_barCode = genBarcode();
            }else {
                out_barCode = itemBarCode.getText();
            }





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

    @FXML
    void onClickUpdtItemBarCode(MouseEvent event) {
        if (itemNbarcode.getText().isBlank() || itemNbarcode.getText().isEmpty()){
            uiService.giveErrorAlert("Empty value",null,"Please enter the values you want to update");
        }
        else{
            String itemNewBarcode = itemNbarcode.getText();
            uiService.askConfirmation("Confirmation",null,"Are you sure you want to update this value",() -> ItemTableDB.updateItemStringData("item_barcode",itemNewBarcode,out_searchItemCode));
        }
    }

    @FXML
    void onClickUpdtItemBrand(MouseEvent event) {
        if (itemNbrand.getText().isBlank() || itemNbrand.getText().isEmpty()){
            uiService.giveErrorAlert("Empty value",null,"Please enter the values you want to update");
        }
        else{
            String itemUpdateValue = itemNbrand.getText();
            uiService.askConfirmation("Confirmation",null,"Are you sure you want to update this value",() -> ItemTableDB.updateItemStringData("itemBrand",itemUpdateValue,out_searchItemCode));
        }
    }

    @FXML
    void onClickUpdtItemCat(MouseEvent event) {
        updateStringValue(itemNcat,"item_category");
    }

    @FXML
    void onClickUpdtItemColor(MouseEvent event) {
        updateStringValue(itemNcolor,"itemColor");
    }

    @FXML
    void onClickUpdtItemCost(MouseEvent event) {
        updateIntValue(itemNcost,"item_CostPrice");
    }

    @FXML
    void onClickUpdtItemName(MouseEvent event) {
        updateStringValue(itemNname,"item_name");
    }

    @FXML
    void onClickUpdtItemSell(MouseEvent event) {
        updateIntValue(itemNsell,"item_SellPrice");
    }

    @FXML
    void onClickUpdtItemSize(MouseEvent event) {
        updateStringValue(itemNsize,"itemSize");
    }

    @FXML
    void onClickUpdtItemStock(MouseEvent event) {
        updateIntValue(itemNstock,"item_stock_count");
    }

    @FXML
    void onClickUpdtItemSubCat(MouseEvent event) {
        updateStringValue(itemNsubCat,"item_SubCategory");
    }

    @FXML
    void onClickUpdtItemSupId(MouseEvent event) {
        if (itemNsupId.getText().isBlank() || itemNsupId.getText().isEmpty()){
            uiService.giveErrorAlert("Empty value",null,"Please enter the values you want to update");
        }
        else{
            String itemUpdateValue = itemNsupId.getText();
            if (SupplierTableDB.isSupAvailable(itemUpdateValue)){
                uiService.askConfirmation("Confirmation",null,"Are you sure you want to update this value",() -> ItemTableDB.updateItemStringData("itemSupplier_id",itemUpdateValue,out_searchItemCode));
            }else {
                uiService.giveErrorAlert("Supplier not avaialable",null,"Supplier number you entered is not available in the database");
            }

        }
    }

    @FXML
    void onClickUpdtItemWarrenty(MouseEvent event) {
        updateIntValue(itemNwarrenty,"item_warrentyMonths");
    }

    @FXML
    void onClickUpdtItemWholeSell(MouseEvent event) {
        updateIntValue(itemNwholeSell,"item_WholeSellPrice");
    }

    @FXML
    void onClickUpdtSupItemCode(MouseEvent event) {
        updateStringValue(itemNsupItemCode,"supplierItem_code");
    }

    public void updateStringValue(TextField textField,String columnName){
        if (textField.getText().isBlank() || textField.getText().isEmpty()){
            uiService.giveErrorAlert("Empty value",null,"Please enter the values you want to update");
        }
        else{
            String itemUpdateValue = textField.getText();
            uiService.askConfirmation("Confirmation",null,"Are you sure you want to update this value",() -> ItemTableDB.updateItemStringData(columnName,itemUpdateValue,out_searchItemCode));
        }
    }

    public void updateIntValue(TextField textField,String columnName){
        if (textField.getText().isBlank() || textField.getText().isEmpty()){
            uiService.giveErrorAlert("Empty value",null,"Please enter the values you want to update");
        }
        else{
            int itemUpdateValue = Integer.parseInt(textField.getText());
            uiService.askConfirmation("Confirmation",null,"Are you sure you want to update this value",() -> ItemTableDB.updateItemIntData(columnName,itemUpdateValue,out_searchItemCode));
        }
    }

    //Creating rows
    public void createItemRow(ScrollPane scrollPane,List<Item> items){

        // Create a VBox to hold all the rows
        VBox container = new VBox();
        container.setSpacing(2); // space between rows
        container.setAlignment(Pos.TOP_LEFT);

        for(Item item : items){

            //Creating each rows
            HBox hBox = new HBox();
            hBox.setPrefSize(1345.00,35.00);
            hBox.setAlignment(Pos.CENTER_LEFT);

            //Creating ItemID column
            Label ID =new Label(item.getItemName());
            ID.setPrefSize(160.00,23.00);
            ID.getStyleClass().add("cusListRowData");

            //Creating Item column
            Label name =new Label(item.getItemCode());
            name.setPrefSize(221.00,23.00);
            name.getStyleClass().add("cusListRowData");

            //Creating Item Price column
            Label Price =new Label("Rs."+String.valueOf(item.getSellingPrice())+" /=");
            Price.setPrefSize(193.00,23.00);
            Price.getStyleClass().add("cusListRowData");

            //Creating Item WholeSalePrice column
            Label wholeSalePrice =new Label("Rs."+String.valueOf(item.getWholeSalePrice())+" /=");
            wholeSalePrice.setPrefSize(191.00,23.00);
            wholeSalePrice.getStyleClass().add("cusListRowData");

            //Creating Item Color column
            Label color =new Label(item.getColor());
            color.setPrefSize(132.00,23.00);
            color.getStyleClass().add("cusListRowData");

            //Creating Item Size column
            Label size =new Label(item.getSize());
            size.setPrefSize(155.00,23.00);
            size.getStyleClass().add("cusListRowData");

            //Creating Item Brand column
            Label brand =new Label(item.getBrand());
            brand.setPrefSize(162.00,23.00);
            brand.getStyleClass().add("cusListRowData");

            //Creating Item Stock column
            int stockCount = item.getStockCount();
            Label stock;
            if (stockCount>0){
                stock =new Label("Available");
                stock.setPrefSize(139.00,23.00);
                stock.getStyleClass().add("itemAvailable");
            } else {
                stock =new Label("Out of Stock");
                stock.setPrefSize(139.00,23.00);
                stock.getStyleClass().add("itemNotAvailable");
            }


            //Adding the children into Hbox
            hBox.getChildren().addAll(ID,name,Price,wholeSalePrice,color,size,brand,stock);

            //adding into vbox
            container.getChildren().add(hBox);
        }
        scrollPane.setContent(container);

    }

    @FXML
    void b2Dboard(MouseEvent event) throws IOException {
        uiService.switchScene(event,"/com/codesolution/cs_pos_v1/fxmls/dashboard.fxml","/Styles/mainStyle.css");
    }



}
