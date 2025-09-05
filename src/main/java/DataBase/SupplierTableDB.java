package DataBase;

import Services.uiService;
import com.codesolution.cs_pos_v1.Customer;
import com.codesolution.cs_pos_v1.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class SupplierTableDB {

    //Connecting with database
    private static final Connection connection = ConnectDB.getConnection();

    //Table name which handles the customers
    private static final String tableName = "supplier";

    //Getting the last round number of customer
    public static String getLastDigits() throws SQLException {
        String sql = String.format("SELECT lastDigits FROM %s WHERE supplier_createDate = CURRENT_DATE ORDER BY lastDigits DESC LIMIT 1;",tableName);
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
    public static int addSupplierData(Supplier supplier)  {

        String sql = "INSERT INTO supplier(supplier_id,supplier_name,supplier_company,supplier_phoneNumber,supplier_address,supplier_email,supplier_note,lastDigits) VALUES (?,?,?,?,?,?,?,?);";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1,supplier.getSupplierID());
            preparedStatement.setString(2,supplier.getSupplierName());
            preparedStatement.setString(3,supplier.getSupCompanayName());
            preparedStatement.setInt(4,supplier.getSupContactNumber());
            preparedStatement.setString(5,supplier.getSupAddress());
            preparedStatement.setString(6,supplier.getSupEmail());
            preparedStatement.setString(7,supplier.getSupNote());
            preparedStatement.setInt(8,supplier.getLastDigits());

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted;
        }
        catch(SQLException e){
            System.out.println("Error while inserting supplier details.");
            e.printStackTrace();
            return 0;
        }


    }

    //Returns all the suppliers
    public static List<Supplier> findAllSuppliers(){
        String sql = "SELECT supplier_id,supplier_name,supplier_phoneNumber,supplier_email,supplier_address,supplier_company,supplier_note,lastDigits FROM supplier;";

        List<Supplier> suppliers = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Supplier supplier = new Supplier(resultSet.getString("supplier_id"),resultSet.getString("supplier_name"),resultSet.getString("supplier_company"),resultSet.getInt("supplier_phoneNumber"), resultSet.getString("supplier_address"),resultSet.getString("supplier_email"),resultSet.getString("supplier_note"), resultSet.getInt("lastDigits"));
                suppliers.add(supplier);
            }
            return suppliers;

        } catch (SQLException e) {

            throw new RuntimeException(e);

        }
    }

    //Getting the supplier form the contact number
    public static Supplier findSupplier(int phoneNumber){
        String sql = "SELECT supplier_id,supplier_name,supplier_phoneNumber,supplier_email,supplier_address,supplier_company,supplier_note,lastDigits FROM supplier WHERE supplier_phoneNumber = ?;";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1,phoneNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                Supplier supplier = new Supplier(resultSet.getString("supplier_id"),resultSet.getString("supplier_name"),resultSet.getString("supplier_company"),resultSet.getInt("supplier_phoneNumber"), resultSet.getString("supplier_address"),resultSet.getString("supplier_email"),resultSet.getString("supplier_note"), resultSet.getInt("lastDigits"));
                return supplier;
            }
            else {
                uiService.giveErrorAlert("Invalid Phone number",null,"This phone number is not available in the database");
                System.out.println("Supplier is not available");
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    //Delete value from customer table
    public static void deleteSupplier(int supplierPhoneNumber){
        String sql = "DELETE FROM supplier WHERE supplier_phoneNumber = ?;";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1,supplierPhoneNumber);
            preparedStatement.executeUpdate();
            System.out.println("Supplier has been removed from the database");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Update the data which are in customer table
    public static void updateSupStringData(String updateColumnName, String updateValue ,String whereColumn , int whereValue){

        String sql = "UPDATE supplier SET "+updateColumnName+" = ? WHERE "+whereColumn+" = ?;";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1,updateValue);
            preparedStatement.setInt(2,whereValue);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated>0){
                System.out.println("Supplier data values Updated");
            }
            else {
                System.out.println("No value got updated");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Update integer values in the database
    public static void updateSupIntegerData(String updateTableName, int updateValue ,String whereColumn , int whereValue){

        String sql = "UPDATE supplier SET "+updateTableName+" = ? WHERE "+whereColumn+" = ?;";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1,updateValue);
            preparedStatement.setInt(2,whereValue);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated>0){
                System.out.println("Supplier data values Updated");
            }
            else {
                System.out.println("No value got updated");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Boolean isSupAvailable(String supCode){
        String sql = "SELECT * FROM `supplier` WHERE supplier_id = ?;";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1,supCode);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                return true;
            }
            else return false;
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }



}
