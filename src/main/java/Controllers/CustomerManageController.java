package Controllers;

import DataBase.CustomerTableDB;
import Services.uiService;
import animatefx.animation.Shake;
import com.codesolution.cs_pos_v1.Customer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class CustomerManageController implements Initializable {


    public int intLastDigits;

    @FXML
    private TextField cusAddress;

    @FXML
    private TextField cusEmail;

    @FXML
    private TextField cusName;

    @FXML
    private TextField cusNum;

    public void initialize(URL url, ResourceBundle resourceBundle){

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


}
