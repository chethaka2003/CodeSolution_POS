package DataBase;

import com.codesolution.cs_pos_v1.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemTableDB {

    private static final Connection connection = ConnectDB.getConnection();

    private static final String tableName = "item";

    //Getting autoSuggestion
    public static ArrayList<String> getSuggestList(String columnName){

        ArrayList<String> suggestList = new ArrayList<>();

        String sql = "SELECT "+columnName+" from item;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                suggestList.add(resultSet.getString(columnName));
                System.out.println(resultSet.getString(columnName));
            }
            return suggestList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Getting the last round number of item
    public static String getLastDigits() throws SQLException {
        String sql = String.format("SELECT lastDigits FROM %s WHERE item_createdDate = CURRENT_TIMESTAMP ORDER BY lastDigits DESC LIMIT 1;",tableName);
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

    public static int addItem(Item item,int lastDigits){

        String sql = "INSERT INTO item(item_code,item_name,item_category,item_SubCategory,item_warrentyMonths,item_CostPrice,item_SellPrice,item_WholeSellPrice,item_stock_count,itemBrand,itemSize,itemColor,item_image,item_barcode,supplierItem_code,itemSupplier_id,lastDigits) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1,item.getItemCode());
            preparedStatement.setString(2,item.getItemName());
            preparedStatement.setString(3,item.getCategory());
            preparedStatement.setString(4, item.getSubCategory());
            preparedStatement.setInt(5,item.getWarrentyMonths());
            preparedStatement.setInt(6,item.getCostPrice());
            preparedStatement.setInt(7,item.getSellingPrice());
            preparedStatement.setInt(8,item.getWholeSalePrice());
            preparedStatement.setInt(9,item.getStockCount());
            preparedStatement.setString(10,item.getBrand());
            preparedStatement.setString(11,item.getSize());
            preparedStatement.setString(12,item.getColor());
            preparedStatement.setString(13,item.getImagePath());
            preparedStatement.setString(14,item.getBarcode());
            preparedStatement.setString(15,item.getSupplierItemCode());
            preparedStatement.setString(16,item.getSupplierID());
            preparedStatement.setInt(17,lastDigits);

            int rowsInserted = preparedStatement.executeUpdate();

            return rowsInserted;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
