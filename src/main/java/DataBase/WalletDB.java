package DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WalletDB {

    private static final Connection connection = ConnectDB.getConnection();

    //Adding cash into DB
    public static void UpdateCash(int amount , String note,String actionType,String personName){

        String sql = "INSERT INTO wallet (actionType,note,responsiblePerson,changedAmount,DrawerBalance) VALUES (?,?,?,?,(SELECT DrawerBalance FROM wallet ORDER BY logID DESC LIMIT 1) + changedAmount)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, actionType);
            preparedStatement.setString(2, note);
            preparedStatement.setString(3, personName);
            preparedStatement.setInt(4, amount);

            int RowsAffected = preparedStatement.executeUpdate();
            if(RowsAffected == 0){
                System.out.println("No rows affected");
            }
            else{
                System.out.println("Rows affected: " + RowsAffected);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
