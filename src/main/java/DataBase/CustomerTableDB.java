package DataBase;

import com.codesolution.cs_pos_v1.Customer;

import java.sql.*;


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
    public static void addCustomerData(Customer customer)  {

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
            if (rowsInserted > 0){
                System.out.println("User added successfully");
            }
        }
        catch(SQLException e){
            System.out.println("Error while inserting user details.");
            e.printStackTrace();
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

}
