package Controllers;

import DataBase.CustomerTableDB;
import Services.uiService;
import animatefx.animation.FadeIn;
import animatefx.animation.Shake;
import com.codesolution.cs_pos_v1.Customer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class CustomerManageController implements Initializable {


    public int intLastDigits;
    public String out_customerName;
    public String out_customerAddress;
    public int out_customerNum;
    public String out_customerEmail;

    @FXML
    private TextField cusAddress;

    @FXML
    private TextField cusEmail;

    @FXML
    private TextField cusName;

    @FXML
    private TextField cusNum;

    @FXML
    private Label cusAdrsLbl;

    @FXML
    private Label cusIdLbl;

    @FXML
    private TextField cusNadres;

    @FXML
    private Label cusNameLbl;

    @FXML
    private TextField cusNmail;

    @FXML
    private TextField cusNname;

    @FXML
    private TextField cusNnum;

    @FXML
    private Label cusNumLbl;

    @FXML
    private Label cusRegDatelbl;

    @FXML
    private Label cusTotalLbl;

    @FXML
    private TextField srchCusNumbr;

    @FXML
    private HBox updateValueHbox;

    public void initialize(URL url, ResourceBundle resourceBundle){

        //Default value for update /delete customer
        updateValueHbox.setVisible(false);

        //Adding listeners to monitor the value changes
        cusNum.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")){
                    uiService.giveErrorAlert("Incorrect number",null,"Please enter a valid phone number");
                    cusNum.setText(oldValue);
                    new Shake(cusNum).play();
                }
                else{
                    cusNum.setText(newValue);
                }
            }
        });

        cusNnum.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")){
                    uiService.giveErrorAlert("Incorrect number",null,"Please enter a valid phone number");
                    cusNnum.setText(oldValue);
                    cusNumLbl.setText(oldValue);
                    new Shake(cusNnum).play();
                    new Shake(cusNumLbl).play();
                }
                else{
                    cusNnum.setText(newValue);
                    cusNumLbl.setText(newValue);
                }
                if (newValue.isEmpty()){
                    cusNumLbl.setText(String.valueOf(out_customerNum));
                }
            }
        });

        srchCusNumbr.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")){
                    uiService.giveErrorAlert("Numbers only",null,"Please enter a valid phone number");
                    srchCusNumbr.setText(oldValue);
                    new Shake(srchCusNumbr).play();
                }
                else{
                    srchCusNumbr.setText(newValue);
                }
            }
        });

        cusAddress.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.length() > 35){
                    uiService.giveErrorAlert("Limit exceeded",null,"Please enter address within 35 characters");
                    cusAddress.setText(oldValue);
                    new Shake(cusAddress).play();
                }
                else {
                    cusAddress.setText(newValue);
                }
            }
        });

        cusNadres.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.length() > 35){
                    uiService.giveErrorAlert("Limit exceeded",null,"Please enter address within 35 characters");
                    cusNadres.setText(oldValue);
                    cusAdrsLbl.setText(oldValue);
                    new Shake(cusNadres).play();
                    new Shake(cusAdrsLbl).play();

                }
                else {
                    cusNadres.setText(newValue);
                    cusAdrsLbl.setText(newValue);

                }
                if (newValue.isEmpty()){
                    cusAdrsLbl.setText(out_customerAddress);
                }

            }
        });


        cusName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.length() > 20){
                    uiService.giveErrorAlert("Limit exceeded",null,"Please enter name within 20 characters");
                    cusName.setText(oldValue);
                    new Shake(cusName).play();
                }
                else {
                    cusName.setText(newValue);
                }
                if (!newValue.matches("[a-zA-Z]*")){
                    uiService.giveErrorAlert("Letters only",null,"Please enter a valid name without digits");
                    cusName.setText(oldValue);
                    new Shake(cusName).play();
                }
                else{
                    cusName.setText(newValue);
                }
            }
        });

        cusNname.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.length() > 20){
                    uiService.giveErrorAlert("Limit exceeded",null,"Please enter name within 20 characters");
                    cusNname.setText(oldValue);
                    cusNameLbl.setText(oldValue);
                    new Shake(cusNname).play();
                    new Shake(cusNameLbl).play();
                }
                else {
                    cusNname.setText(newValue);
                    cusNameLbl.setText(newValue);
                }
                if (newValue.isEmpty()){
                    cusNameLbl.setText(out_customerName);
                }
                if (!newValue.matches("[a-zA-Z]*")){
                    uiService.giveErrorAlert("Letters only",null,"Please enter a valid name without digits");
                    cusNameLbl.setText(oldValue);
                    cusNname.setText(oldValue);
                    new Shake(cusNameLbl).play();
                    new Shake(cusNname).play();
                }
                else{
                    cusNname.setText(newValue);
                    cusNameLbl.setText(newValue);
                }
            }
        });

        cusEmail.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.length() > 60){
                    uiService.giveErrorAlert("Limit exceeded",null,"Please enter email within 60 characters");
                    cusEmail.setText(oldValue);
                    new Shake(cusEmail).play();
                }
                else {
                    cusEmail.setText(newValue);
                }
            }
        });

        cusNmail.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.length() > 60){
                    uiService.giveErrorAlert("Limit exceeded",null,"Please enter email within 60 characters");
                    cusNmail.setText(oldValue);
                    cusEmail.setText(oldValue);
                    new Shake(cusNmail).play();
                    new Shake(cusEmail).play();
                }
                else {
                    cusNmail.setText(newValue);
                    cusEmail.setText(newValue);
                }
                if (newValue.isEmpty()){
                    cusEmail.setText(out_customerEmail);
                }
            }
        });

    }

    //Adding actions to customer button
    @FXML
    void addCusBtn(MouseEvent event) {
        if (cusName.getText().isEmpty()||cusAddress.getText().isEmpty()||cusEmail.getText().isEmpty()||cusNum.getText().isEmpty()) {
            if (cusName.getText().isEmpty()) {
                uiService.giveErrorAlert("Empty value", null, "Please enter the customer name");
                new Shake(cusName).play();
            } else if (cusAddress.getText().isEmpty()) {
                uiService.giveErrorAlert("Empty value", null, "Please enter the customer address");
                new Shake(cusAddress).play();
            } else if (cusEmail.getText().isEmpty()) {
                uiService.giveErrorAlert("Empty value", null, "Please enter the customer email");
                new Shake(cusEmail).play();
            } else if (cusNum.getText().isEmpty()) {
                uiService.giveErrorAlert("Empty value", null, "Please enter the customer phone number");
                new Shake(cusNum).play();
            }
        }
        else if (!cusEmail.getText().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")){
          uiService.giveErrorAlert("Incorrect mail",null,"PLease enter a valid email address");
          new Shake(cusEmail).play();
        } else if (!cusNum.getText().matches("0\\d{9}")) {
            uiService.giveErrorAlert("Incorrect Phone number",null,"Please enter a valid phone number with 10 digits starting from 0");
        } else {
            //Getting values from text fields
            String customerAddress = cusAddress.getText();
            String customerEmail = cusEmail.getText();
            String customerName = cusName.getText();
            int customerPhoneNumber = Integer.parseInt(cusNum.getText());
            String customerID = genCustomer_id();
            Date createDate = getToday();
            int totalTransaction = 0;
            int lastDigits = intLastDigits;


            Customer customer = new Customer(customerID,customerName,customerEmail,customerPhoneNumber,customerAddress,createDate,totalTransaction,lastDigits);
            CustomerTableDB.addCustomerData(customer);
            uiService.giveConfirmationAlert("Successful",null,"Customer "+customerName+" has added successfully");

            //Clear the input fields
            cusEmail.clear();
            cusAddress.clear();
            cusNum.clear();
            cusName.clear();
        }



    }

    //Generating today customer ID
    public String genCustomer_id() {

        //Getting the date part
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String datePart = formatter.format(today);

        //Getting the round part
        String lastDigits;
        try {
            lastDigits = CustomerTableDB.getLastDigits();
            intLastDigits = Integer.parseInt(lastDigits);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String customerID = datePart+lastDigits;
        return customerID;
    }

    //Generating today date
    public java.sql.Date getToday() {
        return java.sql.Date.valueOf(LocalDate.now());
    }

    //Search customer from database
    @FXML
    void findCusClick(MouseEvent event) {
        if (srchCusNumbr.getText().isEmpty()){
            uiService.giveErrorAlert("Empty value",null,"Please Enter phone number and search");
        }
        else {
            int customerPhone = Integer.parseInt(srchCusNumbr.getText());
            Customer foundCustomer = CustomerTableDB.findCustomer(customerPhone);
            if (foundCustomer != null){
                System.out.println(foundCustomer.getCustomer_name());
                srchCusNumbr.clear();

                //Assigning old values into variables
                String customerID = foundCustomer.getCustomer_id();
                String customerName = foundCustomer.getCustomer_name();;
                String customerAddress = foundCustomer.getCustomer_address();
                int totalAmount = foundCustomer.getTotalTransaction();
                Date regDate = foundCustomer.getCreate_Date();
                String customerMail = foundCustomer.getCustomer_email();

                //Assigning variables into out variables
                out_customerAddress = customerAddress;
                out_customerEmail = customerMail;
                out_customerNum = customerPhone;
                out_customerName = customerName;

                //Assigning variables into labels
                cusNameLbl.setText(customerName);
                cusAdrsLbl.setText(customerAddress);
                cusEmail.setText(customerMail);
                cusNumLbl.setText(String.valueOf(customerPhone));
                cusIdLbl.setText(customerID);
                cusTotalLbl.setText(String.valueOf(totalAmount));
                cusRegDatelbl.setText(String.valueOf(regDate));

                updateValueHbox.setVisible(true);
                new FadeIn(updateValueHbox).play();
            }


        }

    }

    @FXML
    void rmveBtnClk(MouseEvent event) {

    }


    //When click on update customer button
    @FXML
    void updtBtnClk(MouseEvent event) {

        //Holding variables temporary


        String newCustomerEmail = cusNmail.getText();
        int newCusNumber = Integer.parseInt(cusNnum.getText());
        String newCusAddress = cusNadres.getText();
        System.out.println(String.valueOf(newCusNumber));


    }


    @FXML
    void updtCusAdrs(MouseEvent event) {

    }

    @FXML
    void updtCusMail(MouseEvent event) {

    }

    @FXML
    void updtCusName(MouseEvent event) {
        if (!cusNname.getText().isEmpty()){
            String newCustomerName = cusNname.getText();
            CustomerTableDB.updateCusStringData("customer_name",newCustomerName,"contact_number",out_customerNum);

        }
    }

    @FXML
    void updtCusNum(MouseEvent event) {

    }



}
