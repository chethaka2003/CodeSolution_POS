package DataBase;

import Services.uiService;
import com.codesolution.cs_pos_v1.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CustomerTableDB {

    //Connecting with database
    private static final Connection connection = ConnectDB.getConnection();

    //Table name which handles the customers
    private static final String tableName = "customer";

    //Getting the last round number of customer
    public static String getLastDigits() throws SQLException {
        String sql = String.format("SELECT LastDigits FROM %s WHERE createDate = CURRENT_DATE ORDER BY LastDigits DESC LIMIT 1;",tableName);
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    int value = rs.getInt(1)+1;
                    String formatted = String.format("%03d", value);
                    return formatted;
                }
                return String.format("%03d", 0);
            }
        }
    }

    //Adding customer into customer database
    public static int addCustomerData(Customer customer)  {

        String sql = "INSERT INTO customer VALUES (?,?,?,?,?,?,?,?);";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1,customer.getCustomer_id());
            preparedStatement.setString(2,customer.getCustomer_name());
            preparedStatement.setInt(3,customer.getCustomer_phone());
            preparedStatement.setString(4,customer.getCustomer_email());
            preparedStatement.setString(5,customer.getCustomer_address());
            preparedStatement.setDate(6,customer.getCreate_Date());
            preparedStatement.setInt(7,customer.getLastDigits());
            preparedStatement.setInt(8,customer.getTotalTransaction());

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted;
        }
        catch(SQLException e){
            System.out.println("Error while inserting user details.");
            e.printStackTrace();
            return 0;
        }


    }

    //Getting the customer form the contact number
    public static Customer findCustomer(int phoneNumber){
        String sql = "SELECT customer_id,customer_name,contact_number,customer_email,customer_address,createDate,totalTransaction,LastDigits FROM customer WHERE contact_number = ?;";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1,phoneNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                Customer customer = new Customer(resultSet.getString("customer_id"),resultSet.getString("customer_name"), resultSet.getString("customer_email"),resultSet.getInt("contact_number"), resultSet.getString("customer_address"),resultSet.getDate("createDate"), resultSet.getInt("totalTransaction"), resultSet.getInt("LastDigits"));
                return customer;
            }
            else {
                System.out.println("Customer is not available");
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Returns all the customers
    public static List<Customer> findAllCustomers(){
        String sql = "SELECT customer_id,customer_name,contact_number,customer_email,customer_address,createDate,totalTransaction,LastDigits FROM customer";
        List<Customer> customers = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Customer customer = new Customer(resultSet.getString("customer_id"),resultSet.getString("customer_name"), resultSet.getString("customer_email"),resultSet.getInt("contact_number"), resultSet.getString("customer_address"),resultSet.getDate("createDate"), resultSet.getInt("totalTransaction"), resultSet.getInt("LastDigits"));
                customers.add(customer);
            }
            return customers;

        } catch (SQLException e) {

            throw new RuntimeException(e);

        }
    }



    //Update the data which are in customer table
    public static void updateCusStringData(String updateTableName, String updateValue ,String whereColumn , int whereValue){

        String sql = "UPDATE customer SET "+updateTableName+" = ? WHERE "+whereColumn+" = ?;";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1,updateValue);
            preparedStatement.setInt(2,whereValue);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated>0){
                System.out.println("Customer data values Updated");
            }
            else {
                System.out.println("No value got updated");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Update integer values in the database
    public static void updateCusIntegerData(String updateTableName, int updateValue ,String whereColumn , int whereValue){

        String sql = "UPDATE customer SET "+updateTableName+" = ? WHERE "+whereColumn+" = ?;";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1,updateValue);
            preparedStatement.setInt(2,whereValue);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated>0){
                System.out.println("Customer data values Updated");
            }
            else {
                System.out.println("No value got updated");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Delete value from customer table
    public static void deleteCustomer(int customerNumber){
        String sql = "DELETE FROM customer WHERE contact_number = ?;";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1,customerNumber);
            preparedStatement.executeUpdate();
            System.out.println("Customer has been removed from the database");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Get the top customer
    public static String getTopCustomer(){

        String sql = "select customer_name FROM customer ORDER by totalTransaction DESC LIMIT 1;";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                String topCustomerName = resultSet.getString("customer_name");
                return topCustomerName;
            }else {
                return  null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public  static String countRowsToday(){

        String sql = "SELECT COUNT(*) AS total_rows FROM customer where createDate = CURRENT_DATE;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                String numOfCustomers = String.valueOf(resultSet.getInt("total_rows"));
                return numOfCustomers;
            }
            else{
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String countAllCustomers(){

        String sql = "SELECT COUNT(*) AS total_rows FROM customer;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                String numOfCustomers = String.valueOf(resultSet.getInt("total_rows"));
                return numOfCustomers;
            }
            else{
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
