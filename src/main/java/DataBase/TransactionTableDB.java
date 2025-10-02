package DataBase;

import com.codesolution.cs_pos_v1.Customer;
import com.codesolution.cs_pos_v1.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionTableDB {

    //Connecting with database
    private static final Connection connection = ConnectDB.getConnection();

    //Table name which handles the customers
    private static final String tableName = "transactions";

    //Getting the last round number of customer
    public static String getLastDigits() throws SQLException {
        String sql = String.format("SELECT last_digits FROM %s WHERE transaction_date = CURRENT_DATE ORDER BY last_digits DESC LIMIT 1;",tableName);
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    int value = rs.getInt(1)+1;
                    String formatted = String.format("%05d", value);
                    return formatted;
                }
                return String.format("%05d", 0);
            }
        }
    }

    //Adding transaction into transaction database
    public static int addTransactionData(Transaction transaction)  {

        String sql = "INSERT INTO transactions(transaction_id,customer_id,total_amount,discount_amount,net_amount,payment_method,last_digits) VALUES (?,?,?,?,?,?,?);";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1,transaction.getTransaction_id());
            preparedStatement.setString(2,transaction.getCustomer_id());
            preparedStatement.setDouble(3,transaction.getTotal_amount());
            preparedStatement.setDouble(4,transaction.getDiscount_amount());
            preparedStatement.setDouble(5,transaction.getNet_amount());
            preparedStatement.setString(6,transaction.getPayment_type());
            preparedStatement.setInt(7,transaction.getLastDigits());

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted;
        }
        catch(SQLException e){
            System.out.println("Error while inserting user details.");
            e.printStackTrace();
            return 0;
        }


    }
}
