package Controllers;

import DataBase.CustomerTableDB;
import Services.uiService;
import animatefx.animation.FadeIn;
import animatefx.animation.Shake;
import com.codesolution.cs_pos_v1.Customer;
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

    @FXML
    private VBox cusAddView;

    @FXML
    private VBox cusHomeView;

    @FXML
    private VBox cusUpdtView;

    @FXML
    private ScrollPane customersList;

    @FXML
    private Label topCusName;

    @FXML
    private Label cusCount;

    @FXML
    private Label newCusCount;


    public void initialize(URL url, ResourceBundle resourceBundle){

        //Default value for update /delete customer
        updateValueHbox.setVisible(false);

        //Adding keyboard shortcuts
        cusUpdtView.sceneProperty().addListener(new ChangeListener<Scene>() {
            @Override
            public void changed(ObservableValue<? extends Scene> observable, Scene oldValue, Scene newValue) {
                if(newValue != null){
                   newValue.setOnKeyPressed(new EventHandler<KeyEvent>() {
                       @Override
                       public void handle(KeyEvent event) {
                           if (event.getCode()== KeyCode.ENTER){
                                findCusClick();
                           } else if (event.getCode() == KeyCode.BACK_SPACE) {
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

        cusAddView.sceneProperty().addListener(new ChangeListener<Scene>() {
            @Override
            public void changed(ObservableValue<? extends Scene> observable, Scene oldValue, Scene newValue) {
                newValue.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if (event.getCode() == KeyCode.ENTER){
                            addCusBtn();
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

        cusHomeView.sceneProperty().addListener(new ChangeListener<Scene>() {
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
                if (!newValue.matches("[a-zA-Z ]*")){
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
                if (!newValue.matches("[a-zA-Z ]*")){
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

        //Creating default view for customer dashboard
        createCusListRow(customersList);

        //Setting uo the dashboard values
        topCusName.setText(CustomerTableDB.getTopCustomer());
        newCusCount.setText(CustomerTableDB.countRowsToday());
        cusCount.setText(CustomerTableDB.countAllCustomers());



    }

    //Adding actions to customer button
    @FXML
    void addCusBtn() {
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
            int rowsInserted = CustomerTableDB.addCustomerData(customer);
            if (rowsInserted > 0){
                uiService.giveConfirmationAlert("Successful",null,"Customer "+customerName+" has added successfully");

                //Clear the input fields
                cusEmail.clear();
                cusAddress.clear();
                cusNum.clear();
                cusName.clear();

            }
            else {
                uiService.giveErrorAlert("Database upload has failed",null,"This Phone number is already in use");
            }

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
    void findCusClick() {
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
        uiService.askConfirmation("Delete customer",null,"Are you sure you want to delete current customer",()->CustomerTableDB.deleteCustomer(out_customerNum));
        updateValueHbox.setVisible(false);
    }


    @FXML
    void updtCusAdrs(MouseEvent event) {
        if (!cusNadres.getText().isEmpty()){
            String newCusAddress = cusNadres.getText();
            uiService.askConfirmation("Customer Address Update",null,"Are you want to change Customer address",() ->CustomerTableDB.updateCusStringData("customer_address",newCusAddress,"contact_number",out_customerNum));
            cusNadres.clear();
            cusAdrsLbl.setText(newCusAddress);
        }
        else{
            uiService.giveErrorAlert("Empty value",null,"Please enter a valid customer name");
        }
    }

    @FXML
    void updtCusMail(MouseEvent event) {
        if (!cusNmail.getText().isEmpty()){
            String newCustomerEmail = cusNmail.getText();
            uiService.askConfirmation("Customer Email Update",null,"Are you want to change Customer Email",() ->CustomerTableDB.updateCusStringData("customer_email",newCustomerEmail,"contact_number",out_customerNum));
            cusNmail.clear();
            cusEmail.setText(newCustomerEmail);
        }
        else {
            uiService.giveErrorAlert("Empty value",null,"Please enter a valid customer email");
        }
    }

    //Update customer name
    @FXML
    void updtCusName(MouseEvent event) {
        if (!cusNname.getText().isEmpty()){
            String newCustomerName = cusNname.getText();
            uiService.askConfirmation("Customer name Update",null,"Are you want to change Customer name",() ->CustomerTableDB.updateCusStringData("customer_name",newCustomerName,"contact_number",out_customerNum));
            cusNname.clear();
            cusNameLbl.setText(newCustomerName);
            ;

        }
        else {
           uiService.giveErrorAlert("Empty value",null,"Please enter a valid customer name");
        }

    }

    @FXML
    void updtCusNum(MouseEvent event) {
        if (cusNnum.getText().matches("0\\d{9}")&& !cusNnum.getText().isEmpty()){
            int newCusNumber = Integer.parseInt(cusNnum.getText());
            uiService.askConfirmation("Customer Phone number Update",null,"Are you want to change Customer Phone number",() ->CustomerTableDB.updateCusIntegerData("contact_number",newCusNumber,"contact_number",out_customerNum));
            out_customerNum = newCusNumber;
            cusNnum.clear();
            cusNumLbl.setText(String.valueOf(out_customerNum));
        }
        else {
            uiService.giveErrorAlert("Empty value or incorrect phone number",null,"Please enter a valid customer Phone number");
        }

    }

    @FXML
    void onCusAddClicked(MouseEvent event) {

        Node clickedNode = (Node) event.getSource();

        Parent parent = clickedNode.getParent();

        for (Node child : parent.getChildrenUnmodifiable()) {
            child.getStyleClass().remove("setActive");
        }

        clickedNode.getStyleClass().add("setActive");

        System.out.println("Clicked: " + clickedNode);

        cusHomeView.setVisible(false);
        cusUpdtView.setVisible(false);
        cusAddView.setVisible(true);
        new FadeIn(cusAddView).play();
    }

    @FXML
    void onCusHomeClicked(MouseEvent event) {
        Node clickedNode = (Node) event.getSource();

        Parent parent = clickedNode.getParent();

        for (Node child : parent.getChildrenUnmodifiable()) {
            child.getStyleClass().remove("setActive");
        }

        clickedNode.getStyleClass().add("setActive");

        System.out.println("Clicked: " + clickedNode);
        cusHomeView.setVisible(true);
        new FadeIn(cusHomeView).play();
        cusUpdtView.setVisible(false);
        cusAddView.setVisible(false);

        createCusListRow(customersList);

        //Updating the values again
        topCusName.setText(CustomerTableDB.getTopCustomer());
        newCusCount.setText(CustomerTableDB.countRowsToday());
        cusCount.setText(CustomerTableDB.countAllCustomers());
    }

    @FXML
    void onCusUpdtClicked(MouseEvent event) {
        Node clickedNode = (Node) event.getSource();

        Parent parent = clickedNode.getParent();

        for (Node child : parent.getChildrenUnmodifiable()) {
            child.getStyleClass().remove("setActive");
        }

        clickedNode.getStyleClass().add("setActive");

        updateValueHbox.setVisible(false);
        System.out.println("Clicked: " + clickedNode);
        cusHomeView.setVisible(false);
        cusUpdtView.setVisible(true);
        new FadeIn(cusUpdtView).play();
        cusAddView.setVisible(false);
    }

    public void CustomerDefHomePage(){



    }

    //Creating rows
    public void createCusListRow(ScrollPane scrollPane){

        // Create a VBox to hold all the rows
        VBox container = new VBox();
        container.setSpacing(2); // space between rows
        container.setAlignment(Pos.TOP_LEFT);

        List<Customer> customers= new ArrayList<>();
        customers = CustomerTableDB.findAllCustomers();
        for(Customer customer : customers){

            //Creating each rows
            HBox hBox = new HBox();
            hBox.setPrefSize(1345.00,35.00);
            hBox.setAlignment(Pos.CENTER_LEFT);

            //Creating customerID column
            Label ID =new Label(customer.getCustomer_id());
            ID.setPrefSize(181.00,23.00);
            ID.getStyleClass().add("cusListRowData");

            //Creating name column
            Label name =new Label(customer.getCustomer_name());
            name.setPrefSize(194.00,23.00);
            name.getStyleClass().add("cusListRowData");

            //Creating phone number column
            Label number =new Label(String.valueOf(customer.getCustomer_phone()));
            number.setPrefSize(195.00,23.00);
            number.getStyleClass().add("cusListRowData");

            //Creating email column
            Label email =new Label(String.valueOf(customer.getCustomer_email()));
            email.setPrefSize(289.00,23.00);
            email.getStyleClass().add("cusListRowData");

            //Creating address column
            Label address =new Label(String.valueOf(customer.getCustomer_address()));
            address.setPrefSize(373.00,23.00);
            address.getStyleClass().add("cusListRowData");

            //Creating address column
            Label date =new Label(String.valueOf(String.valueOf(customer.getCreate_Date())));
            date.setPrefSize(111.00,23.00);
            date.getStyleClass().add("cusListRowData");

            //Adding the children into Hbox
            hBox.getChildren().addAll(ID,name,number,email,address,date);

            //adding into vbox
            container.getChildren().add(hBox);
        }
        scrollPane.setContent(container);

    }

    //Creating back buttons
    @FXML
    void backCusAdd(MouseEvent event) throws IOException {
        uiService.switchScene(event,"/com/codesolution/cs_pos_v1/fxmls/dashboard.fxml","/Styles/mainStyle.css");
    }

    @FXML
    void backCusDash(MouseEvent event) throws IOException {
        uiService.switchScene(event,"/com/codesolution/cs_pos_v1/fxmls/dashboard.fxml","/Styles/mainStyle.css");
    }

    @FXML
    void backCusUpdt(MouseEvent event) throws IOException {
        uiService.switchScene(event,"/com/codesolution/cs_pos_v1/fxmls/dashboard.fxml","/Styles/mainStyle.css");
    }



}
