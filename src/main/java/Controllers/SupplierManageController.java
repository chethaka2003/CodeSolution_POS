package Controllers;

import DataBase.CustomerTableDB;
import DataBase.SupplierTableDB;
import Services.uiService;
import animatefx.animation.FadeIn;
import animatefx.animation.Shake;
import com.codesolution.cs_pos_v1.Customer;
import com.codesolution.cs_pos_v1.Supplier;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SupplierManageController implements Initializable {

    @FXML
    private VBox supAddView;

    @FXML
    private TextField supAddress;

    @FXML
    private Label supAdrsLbl;

    @FXML
    private TextField supCompanyName;

    @FXML
    private Label supCompanyNameLbl;

    @FXML
    private TextField supEmail;

    @FXML
    private VBox supHomeView;

    @FXML
    private Label supIdLbl;

    @FXML
    private TextField supNadres;

    @FXML
    private TextField supName;

    @FXML
    private Label supNameLbl;

    @FXML
    private Label supEmailLbl;

    @FXML
    private TextField supNcompany;

    @FXML
    private TextField supNmail;

    @FXML
    private TextField supNname;

    @FXML
    private TextField supNnum;

    @FXML
    private TextField supNote;

    @FXML
    private Label supNoteLbl;

    @FXML
    private TextField supNnote;

    @FXML
    private TextField supNum;

    @FXML
    private Label supNumLbl;

    @FXML
    private TextField supSrchNumbr;

    @FXML
    private VBox supUpdtView;

    @FXML
    private ScrollPane suppliersList;

    @FXML
    private HBox updateValueHbox;

    public int intLastDigits;

    public String out_supplierName;
    public String out_supplierEmail;
    public String out_supplierCompanyName;
    public String out_supplierAddress;
    public String out_supplierNote;
    public int out_supplierPhoneNumber;

    public void initialize(URL url, ResourceBundle resourceBundle){

        //Default value for update /delete customer
        updateValueHbox.setVisible(false);

        //Adding keyboard shortcuts
        supUpdtView.sceneProperty().addListener(new ChangeListener<Scene>() {
            @Override
            public void changed(ObservableValue<? extends Scene> observable, Scene oldValue, Scene newValue) {
                if(newValue != null){
                    newValue.setOnKeyPressed(new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent event) {
                            if (event.getCode()== KeyCode.ENTER){

                            }
                            else if (event.getCode() == KeyCode.BACK_SPACE) {
                                try {
                                    uiService.switchSceneKeyboard(event,"/com/codesolution/cs_pos_v1/fxmls/dashboard.fxml","/Styles/mainStyle.css");
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    });
                }
            }
        });

        supAddView.sceneProperty().addListener(new ChangeListener<Scene>() {
            @Override
            public void changed(ObservableValue<? extends Scene> observable, Scene oldValue, Scene newValue) {
                newValue.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if (event.getCode() == KeyCode.ENTER){
                            addSupBtn();
                        }else if (event.getCode() == KeyCode.BACK_SPACE) {
                            try {
                                uiService.switchSceneKeyboard(event,"/com/codesolution/cs_pos_v1/fxmls/dashboard.fxml","/Styles/mainStyle.css");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });
            }
        });

        supHomeView.sceneProperty().addListener(new ChangeListener<Scene>() {
            @Override
            public void changed(ObservableValue<? extends Scene> observable, Scene oldValue, Scene newValue) {
                newValue.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if (event.getCode() == KeyCode.BACK_SPACE){
                            try {
                                uiService.switchSceneKeyboard(event,"/com/codesolution/cs_pos_v1/fxmls/dashboard.fxml","/Styles/mainStyle.css");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });
            }
        });

        //Adding listeners to monitor the value changes
        supNum.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")){
                    uiService.giveErrorAlert("Incorrect number",null,"Please enter a valid phone number");
                    supNum.setText(oldValue);
                    new Shake(supNum).play();
                }
                else{
                    supNum.setText(newValue);
                }
            }
        });

        supNnum.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")){
                    uiService.giveErrorAlert("Incorrect number",null,"Please enter a valid phone number");
                    supNnum.setText(oldValue);
                    supNumLbl.setText(oldValue);
                    new Shake(supNnum).play();
                    new Shake(supNumLbl).play();
                }
                else{
                    supNnum.setText(newValue);
                    supNumLbl.setText(newValue);
                }
                if (newValue.isEmpty()){
                    supNumLbl.setText(String.valueOf(out_supplierPhoneNumber));
                }
            }
        });

        supSrchNumbr.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")){
                    uiService.giveErrorAlert("Numbers only",null,"Please enter a valid phone number");
                    supSrchNumbr.setText(oldValue);
                    new Shake(supSrchNumbr).play();
                }
                else{
                    supSrchNumbr.setText(newValue);
                }
            }
        });

        supAddress.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.length() > 35){
                    uiService.giveErrorAlert("Limit exceeded",null,"Please enter address within 35 characters");
                    supAddress.setText(oldValue);
                    new Shake(supAddress).play();
                }
                else {
                    supAddress.setText(newValue);
                }
            }
        });

        supNadres.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.length() > 35){
                    uiService.giveErrorAlert("Limit exceeded",null,"Please enter address within 35 characters");
                    supNadres.setText(oldValue);
                    supAdrsLbl.setText(oldValue);
                    new Shake(supNadres).play();
                    new Shake(supAdrsLbl).play();

                }
                else {
                    supNadres.setText(newValue);
                    supAdrsLbl.setText(newValue);

                }
                if (newValue.isEmpty()){
                    supAdrsLbl.setText(out_supplierAddress);
                }

            }
        });

        supCompanyName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.length()>25){
                    uiService.giveErrorAlert("Limit exceeded",null,"Please enter name within 20 characters");
                    supCompanyName.setText(oldValue);
                    new Shake(supCompanyName).play();

                }
                else{
                    supName.setText(newValue);
                }
            }
        });

        supNcompany.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.length()>25){
                    uiService.giveErrorAlert("Limit exceeded",null,"Please enter name within 20 characters");
                    supNcompany.setText(oldValue);
                    supCompanyNameLbl.setText(oldValue);
                    new Shake(supNcompany).play();
                    new Shake(supCompanyNameLbl).play();

                }
                else{
                    supNcompany.setText(newValue);
                    supCompanyNameLbl.setText(newValue);
                }
            }
        });

        supNote.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.length()>35){
                    uiService.giveErrorAlert("Limit exceeded",null,"Please enter note within 20 characters");
                    supNote.setText(oldValue);
                    new Shake(supNote).play();

                }
                else{
                    supNote.setText(newValue);
                }
            }
        });

        supNnote.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.length()>35){
                    uiService.giveErrorAlert("Limit exceeded",null,"Please enter note within 20 characters");
                    supNnote.setText(oldValue);
                    supNoteLbl.setText(oldValue);
                    new Shake(supNnote).play();
                    new Shake(supNoteLbl).play();

                }
                else{
                    supNnote.setText(newValue);
                    supNoteLbl.setText(newValue);
                }
            }
        });


        supName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.length() > 20){
                    uiService.giveErrorAlert("Limit exceeded",null,"Please enter name within 20 characters");
                    supName.setText(oldValue);
                    new Shake(supName).play();
                }
                else {
                    supName.setText(newValue);
                }
                if (!newValue.matches("[a-zA-Z ]*")){
                    uiService.giveErrorAlert("Letters only",null,"Please enter a valid name without digits");
                    supName.setText(oldValue);
                    new Shake(supName).play();
                }
                else{
                    supName.setText(newValue);
                }
            }
        });

        supNname.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.length() > 20){
                    uiService.giveErrorAlert("Limit exceeded",null,"Please enter name within 20 characters");
                    supNname.setText(oldValue);
                    supNameLbl.setText(oldValue);
                    new Shake(supNname).play();
                    new Shake(supNameLbl).play();
                }
                else {
                    supNname.setText(newValue);
                    supNameLbl.setText(newValue);
                }
                if (newValue.isEmpty()){
                    supNameLbl.setText(out_supplierName);
                }
                if (!newValue.matches("[a-zA-Z ]*")){
                    uiService.giveErrorAlert("Letters only",null,"Please enter a valid name without digits");
                    supNameLbl.setText(oldValue);
                    supNname.setText(oldValue);
                    new Shake(supNameLbl).play();
                    new Shake(supNname).play();
                }
                else{
                    supNname.setText(newValue);
                    supNameLbl.setText(newValue);
                }
                if (newValue.isEmpty()){
                    supEmail.setText(out_supplierEmail);
                }
            }
        });

        supEmail.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.length() > 60){
                    uiService.giveErrorAlert("Limit exceeded",null,"Please enter email within 60 characters");
                    supEmail.setText(oldValue);
                    new Shake(supEmail).play();
                }
                else {
                    supEmail.setText(newValue);
                }
            }
        });

        supNmail.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.length() > 60){
                    uiService.giveErrorAlert("Limit exceeded",null,"Please enter email within 60 characters");
                    supNmail.setText(oldValue);
                    supEmail.setText(oldValue);
                    new Shake(supNmail).play();
                    new Shake(supEmail).play();
                }
                else {
                    supNmail.setText(newValue);
                    supEmail.setText(newValue);
                }
                if (newValue.isEmpty()){
                    supEmailLbl.setText(out_supplierEmail);
                }
            }
        });
    createSupListRow(suppliersList);




    }


    //Creating back buttons
    @FXML
    void backSupAdd(MouseEvent event) throws IOException {
        uiService.switchScene(event,"/com/codesolution/cs_pos_v1/fxmls/dashboard.fxml","/Styles/mainStyle.css");
    }

    @FXML
    void backSupDash(MouseEvent event) throws IOException {
        uiService.switchScene(event,"/com/codesolution/cs_pos_v1/fxmls/dashboard.fxml","/Styles/mainStyle.css");
    }

    @FXML
    void backSupUpdt(MouseEvent event) throws IOException {
        uiService.switchScene(event,"/com/codesolution/cs_pos_v1/fxmls/dashboard.fxml","/Styles/mainStyle.css");
    }

    @FXML
    void findSupClick() {
        if (supSrchNumbr.getText().isEmpty() || !(supSrchNumbr.getText().length() ==10)){
            uiService.giveErrorAlert("Empty value",null,"Please Enter valid phone number and search");

        }
        else {
            int supplierPhone = Integer.parseInt(supSrchNumbr.getText());
            out_supplierPhoneNumber = supplierPhone;
            Supplier foundSupplier = SupplierTableDB.findSupplier(supplierPhone);
            if (foundSupplier != null){
                System.out.println(foundSupplier.getSupplierName());
                supSrchNumbr.clear();

                //Assigning old values into variables
                int supplierPhoneNumber = foundSupplier.getSupContactNumber();
                String supplierID = foundSupplier.getSupplierID();
                String supplierName = foundSupplier.getSupplierName();
                String supplierAddress = foundSupplier.getSupAddress();
                String supplierMail = foundSupplier.getSupEmail();
                String supplierCompanyName = foundSupplier.getSupCompanayName();
                String supplierNote = foundSupplier.getSupNote();

                //Assigning variables into out variables
                out_supplierPhoneNumber = supplierPhoneNumber;
                out_supplierCompanyName = supplierCompanyName;
                out_supplierEmail = supplierMail;
                out_supplierName = supplierName;
                out_supplierNote = supplierNote;
                out_supplierAddress = supplierAddress;

                //Assigning variables into labels
                supNameLbl.setText(supplierName);
                supAdrsLbl.setText(supplierAddress);
                supEmailLbl.setText(supplierMail);
                supNumLbl.setText(String.valueOf(supplierPhoneNumber));
                supIdLbl.setText(supplierID);
                supNoteLbl.setText(supplierNote);
                supCompanyNameLbl.setText(supplierCompanyName);

                updateValueHbox.setVisible(true);
                new FadeIn(updateValueHbox).play();
            }


        }
    }

    @FXML
    void onSupAddClicked(MouseEvent event) {
        Node clickedNode = (Node) event.getSource();

        Parent parent = clickedNode.getParent();

        for (Node child : parent.getChildrenUnmodifiable()) {
            child.getStyleClass().remove("setActive");
        }

        clickedNode.getStyleClass().add("setActive");

        System.out.println("Clicked: " + clickedNode);
        supHomeView.setVisible(false);
        new FadeIn(supHomeView).play();
        supUpdtView.setVisible(false);
        supAddView.setVisible(true);

    }

    @FXML
    void onSupHomeClicked(MouseEvent event) {
        Node clickedNode = (Node) event.getSource();

        Parent parent = clickedNode.getParent();

        for (Node child : parent.getChildrenUnmodifiable()) {
            child.getStyleClass().remove("setActive");
        }

        clickedNode.getStyleClass().add("setActive");

        System.out.println("Clicked: " + clickedNode);
        supHomeView.setVisible(true);
        new FadeIn(supHomeView).play();
        supUpdtView.setVisible(false);
        supAddView.setVisible(false);

        createSupListRow(suppliersList);

    }

    //Customer update button is clicked
    @FXML
    void onSupUpdtClicked(MouseEvent event) {
        Node clickedNode = (Node) event.getSource();

        Parent parent = clickedNode.getParent();

        for (Node child : parent.getChildrenUnmodifiable()) {
            child.getStyleClass().remove("setActive");
        }

        clickedNode.getStyleClass().add("setActive");

        updateValueHbox.setVisible(false);
        System.out.println("Clicked: " + clickedNode);
        supHomeView.setVisible(false);
        new FadeIn(supHomeView).play();
        supUpdtView.setVisible(true);
        supAddView.setVisible(false);

    }

    @FXML
    void rmveBtnClk(MouseEvent event) {
        uiService.askConfirmation("Delete Supplier",null,"Are you sure you want to delete current Supplier",()->SupplierTableDB.deleteSupplier(out_supplierPhoneNumber));
        updateValueHbox.setVisible(false);
    }

    @FXML
    void updtSupName(MouseEvent event) {
        if (!supNname.getText().isEmpty()){
            String newSupplierName = supNname.getText();
            uiService.askConfirmation("Supplier name Update",null,"Are you want to change Supplier name",() ->SupplierTableDB.updateSupStringData("supplier_name",newSupplierName,"supplier_phoneNumber",out_supplierPhoneNumber));
            supNname.clear();
            supNameLbl.setText(newSupplierName);
            ;

        }
        else {
            uiService.giveErrorAlert("Empty value",null,"Please enter a valid Supplier name");
            supNameLbl.setText(out_supplierName);

        }
    }

    @FXML
    void updtSupNote(MouseEvent event) {
        if (!supNnote.getText().isEmpty()){
            String newSupplierNote = supNnote.getText();
            uiService.askConfirmation("Supplier note Update",null,"Are you want to change Supplier note",() ->SupplierTableDB.updateSupStringData("supplier_note",newSupplierNote,"supplier_phoneNumber",out_supplierPhoneNumber));
            supNnote.clear();
            supNoteLbl.setText(newSupplierNote);
            ;

        }
        else {
            uiService.giveErrorAlert("Empty value",null,"Please enter a valid Supplier note");
            supNoteLbl.setText(out_supplierNote);

        }
    }

    @FXML
    void updtSupCmpnyName(MouseEvent event) {
        if (!supNcompany.getText().isEmpty()){
            String newSupplierCompany = supNcompany.getText();
            uiService.askConfirmation("Supplier Company Update",null,"Are you want to change Supplier note",() ->SupplierTableDB.updateSupStringData("supplier_company",newSupplierCompany,"supplier_phoneNumber",out_supplierPhoneNumber));
            supNcompany.clear();
            supCompanyNameLbl.setText(newSupplierCompany);
            ;

        }
        else {
            uiService.giveErrorAlert("Empty value",null,"Please enter a valid Supplier company");
            supCompanyNameLbl.setText(out_supplierCompanyName);

        }
    }

    @FXML
    void updtSupMail(MouseEvent event) {
        if (!supNmail.getText().isEmpty()){
            String newSupplierEmail= supNmail.getText();
            uiService.askConfirmation("Supplier Email Update",null,"Are you want to change Supplier Email",() ->SupplierTableDB.updateSupStringData("supplier_email",newSupplierEmail,"supplier_phoneNumber",out_supplierPhoneNumber));
            supNmail.clear();
            supEmailLbl.setText(newSupplierEmail);
            ;

        }
        else {
            uiService.giveErrorAlert("Empty value",null,"Please enter a valid Supplier Email");
            supEmailLbl.setText(out_supplierEmail);

        }
    }

    @FXML
    void updtSupAdrs (MouseEvent event) {
        if (!supNadres.getText().isEmpty()){
            String newSupplierAddress= supNadres.getText();
            uiService.askConfirmation("Supplier Address Update",null,"Are you want to change Supplier Address",() ->SupplierTableDB.updateSupStringData("supplier_address",newSupplierAddress,"supplier_phoneNumber",out_supplierPhoneNumber));
            supNadres.clear();
            supAdrsLbl.setText(newSupplierAddress);
            ;

        }
        else {
            uiService.giveErrorAlert("Empty value",null,"Please enter a valid Supplier Address");
            supAdrsLbl.setText(out_supplierAddress);

        }
    }

    @FXML
    void updtSupNum(MouseEvent event) {
        if (!supNnum.getText().isEmpty()){
            int newSupplierNumber= Integer.parseInt(supNnum.getText());
            uiService.askConfirmation("Supplier Number Update",null,"Are you want to change Supplier Number",() ->SupplierTableDB.updateSupIntegerData("supplier_phoneNumber",newSupplierNumber,"supplier_phoneNumber",out_supplierPhoneNumber));
            supNnum.clear();
            supNumLbl.setText(String.valueOf(newSupplierNumber));
            ;

        }
        else {
            uiService.giveErrorAlert("Empty value",null,"Please enter a valid Supplier Number");
            supNumLbl.setText(String.valueOf(out_supplierPhoneNumber));

        }
    }




    //Adding actions to supplier add button
    @FXML
    void addSupBtn() {
        if (supName.getText().isEmpty()||supAddress.getText().isEmpty()||supEmail.getText().isEmpty()||supNum.getText().isEmpty()||supCompanyName.getText().isEmpty()) {
            if (supName.getText().isEmpty()) {
                uiService.giveErrorAlert("Empty value", null, "Please enter the Supplier name");
                new Shake(supName).play();
            } else if (supAddress.getText().isEmpty()) {
                uiService.giveErrorAlert("Empty value", null, "Please enter the Supplier address");
                new Shake(supAddress).play();
            } else if (supEmail.getText().isEmpty()) {
                uiService.giveErrorAlert("Empty value", null, "Please enter the Supplier email");
                new Shake(supEmail).play();
            } else if (supNum.getText().isEmpty()) {
                uiService.giveErrorAlert("Empty value", null, "Please enter the Supplier phone number");
                new Shake(supNum).play();
            } else if (supCompanyName.getText().isEmpty()) {
                uiService.giveErrorAlert("Empty value", null, "Please enter the Supplier Company name");
                new Shake(supCompanyName).play();
        }

        }
        else if (!supEmail.getText().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")){
            uiService.giveErrorAlert("Incorrect mail",null,"PLease enter a valid email address");
            new Shake(supEmail).play();
        } else if (!supNum.getText().matches("0\\d{9}")) {
            uiService.giveErrorAlert("Incorrect Phone number",null,"Please enter a valid phone number with 10 digits starting from 0");
        } else {
            //Getting values from text fields
            String supplierAddress = supAddress.getText();
            String supplierEmail = supEmail.getText();
            String supplierName = supName.getText();
            int supplierPhoneNumber = Integer.parseInt(supNum.getText());
            String supplierID = genSupplier_id();
            int lastDigits = intLastDigits;
            String supplierCompanyName = supCompanyName.getText();
            String supplierNote = supNote.getText();


            Supplier supplier = new Supplier(supplierID,supplierName,supplierCompanyName,supplierPhoneNumber,supplierAddress,supplierEmail,supplierNote,lastDigits);
            int rowsInserted = SupplierTableDB.addSupplierData(supplier);
            if (rowsInserted > 0){
                uiService.giveConfirmationAlert("Successful",null,"Supplier "+supplierName+" has added successfully");

                //Clear the input fields
                supEmail.clear();
                supAddress.clear();
                supNum.clear();
                supName.clear();
                supNote.clear();
                supCompanyName.clear();

            }
            else {
                uiService.giveErrorAlert("Database upload has failed",null,"This Phone number is already in use");
            }

        }



    }

    //Generating today customer ID
    public String genSupplier_id() {

        //Getting the date part
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String datePart = formatter.format(today);

        //Getting the round part
        String lastDigits;
        try {
            lastDigits = SupplierTableDB.getLastDigits();
            intLastDigits = Integer.parseInt(lastDigits);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String supplierID = datePart+lastDigits;
        return supplierID;
    }

    //Generating today date
    public java.sql.Date getToday() {
        return java.sql.Date.valueOf(LocalDate.now());
    }

    //Creating rows
    public void createSupListRow(ScrollPane scrollPane){

        // Create a VBox to hold all the rows
        VBox container = new VBox();
        container.setSpacing(2); // space between rows
        container.setAlignment(Pos.TOP_LEFT);

        List<Supplier> suppliers= new ArrayList<>();
        suppliers = SupplierTableDB.findAllSuppliers();
        for(Supplier supplier : suppliers){

            //Creating each rows
            HBox hBox = new HBox();
            hBox.setPrefSize(1345.00,35.00);
            hBox.setAlignment(Pos.CENTER_LEFT);

            //Creating customerID column
            Label ID =new Label(supplier.getSupplierID());
            ID.setPrefSize(174.00,23.00);
            ID.getStyleClass().add("cusListRowData");

            //Creating name column
            Label name =new Label(supplier.getSupplierName());
            name.setPrefSize(198.00,23.00);
            name.getStyleClass().add("cusListRowData");

            //Creating phone number column
            Label number =new Label(String.valueOf(supplier.getSupContactNumber()));
            number.setPrefSize(194.00,23.00);
            number.getStyleClass().add("cusListRowData");

            //Creating email column
            Label email =new Label(String.valueOf(supplier.getSupEmail()));
            email.setPrefSize(284.00,23.00);
            email.getStyleClass().add("cusListRowData");

            //Creating address column
            Label address =new Label(String.valueOf(supplier.getSupAddress()));
            address.setPrefSize(324.00,23.00);
            address.getStyleClass().add("cusListRowData");

            //Creating address column
            Label date =new Label(supplier.getSupCompanayName());
            date.setPrefSize(165.00,23.00);
            date.getStyleClass().add("cusListRowData");

            //Adding the children into Hbox
            hBox.getChildren().addAll(ID,name,number,email,address,date);

            //adding into vbox
            container.getChildren().add(hBox);
        }
        scrollPane.setContent(container);

    }





}
