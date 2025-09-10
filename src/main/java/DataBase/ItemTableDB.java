package DataBase;

import com.codesolution.cs_pos_v1.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemTableDB {

    private static final Connection connection = ConnectDB.getConnection();

    private static final String tableName = "item";

    //Getting autoSuggestion
    public static ArrayList<String> getSuggestList(String columnName) {

        ArrayList<String> suggestList = new ArrayList<>();

        String sql = "SELECT " + columnName + " from item;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                suggestList.add(resultSet.getString(columnName));
            }
            return suggestList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Getting the last round number of item
    public static String getLastDigits() throws SQLException {
        String sql = String.format("SELECT lastDigits FROM %s WHERE item_createdDate = CURRENT_TIMESTAMP ORDER BY lastDigits DESC LIMIT 1;", tableName);
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int value = rs.getInt(1) + 1;
                    String formatted = String.format("%03d", value);
                    return formatted;
                }
                return String.format("%03d", 0);
            }
        }
    }

    public static int addItem(Item item, int lastDigits) {

        String sql = "INSERT INTO item(item_code,item_name,item_category,item_SubCategory,item_warrentyMonths,item_CostPrice,item_SellPrice,item_WholeSellPrice,item_stock_count,itemBrand,itemSize,itemColor,item_image,item_barcode,supplierItem_code,itemSupplier_id,lastDigits) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, item.getItemCode());
            preparedStatement.setString(2, item.getItemName());
            preparedStatement.setString(3, item.getCategory());
            preparedStatement.setString(4, item.getSubCategory());
            preparedStatement.setInt(5, item.getWarrentyMonths());
            preparedStatement.setInt(6, item.getCostPrice());
            preparedStatement.setInt(7, item.getSellingPrice());
            preparedStatement.setInt(8, item.getWholeSalePrice());
            preparedStatement.setInt(9, item.getStockCount());
            preparedStatement.setString(10, item.getBrand());
            preparedStatement.setString(11, item.getSize());
            preparedStatement.setString(12, item.getColor());
            preparedStatement.setString(13, item.getImagePath());
            preparedStatement.setString(14, item.getBarcode());
            preparedStatement.setString(15, item.getSupplierItemCode());
            preparedStatement.setString(16, item.getSupplierID());
            preparedStatement.setInt(17, lastDigits);

            int rowsInserted = preparedStatement.executeUpdate();

            return rowsInserted;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    //Getting the item
    public static Item getItem(String inputID) {
        String sql = "SELECT * FROM item WHERE item_code = ? OR item_barcode = ? ;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, inputID);
            preparedStatement.setString(2, inputID);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Item item = new Item(resultSet.getString("item_code"), resultSet.getString("item_name"), resultSet.getString("supplierItem_code"), resultSet.getString("item_category"), resultSet.getString("item_SubCategory"), resultSet.getInt("item_warrentyMonths"), resultSet.getInt("item_CostPrice"), resultSet.getInt("item_SellPrice"), resultSet.getInt("item_WholeSellPrice"), resultSet.getInt("item_stock_count"), resultSet.getString("itemSupplier_id"), resultSet.getString("item_barcode"), resultSet.getString("itemBrand"), resultSet.getString("itemSize"), resultSet.getString("itemColor"), resultSet.getString("item_image"));
                return item;
            } else
                return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getItemData(String columnName, String inputID) {
        String sql = "SELECT " + columnName + " FROM item WHERE item_code = ? OR item_barcode = ? ;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, inputID);
            preparedStatement.setString(2, inputID);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (columnName.endsWith("Date")) {
                if (resultSet.next()) {
                    String value = String.valueOf(resultSet.getDate(columnName));
                    return value;
                } else return null;
            } else {
                if (resultSet.next()) {
                    String value = resultSet.getString(columnName);
                    return value;
                } else return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Update the data which are in customer table
    public static void updateItemStringData(String updateColName, String updateValue, String itemCode) {

        String sql = "UPDATE item SET " + updateColName + " = ? WHERE item_code = ? OR item_barcode = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, updateValue);
            preparedStatement.setString(2, itemCode);
            preparedStatement.setString(3, itemCode);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Item data values Updated");
            } else {
                System.out.println("No value got updated");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateItemIntData(String updateColName, int updateValue, String itemCode) {

        String sql = "UPDATE item SET " + updateColName + " = ? WHERE item_code = ? OR item_barcode = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, updateValue);
            preparedStatement.setString(2, itemCode);
            preparedStatement.setString(3, itemCode);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Item data values Updated");
            } else {
                System.out.println("No value got updated");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Searching item
    public static List<Item> searchItem(String itemName, String itemCat, String itemSubCat, String itemColor, String itemSize, String itemBrand) {
        String sqlPart1;
        String sqlPart2;
        String sqlPart3;
        String sqlPart4;
        String sqlPart5;
        String sqlPart6;
        if (itemName.equals("Any")) {
            sqlPart1 = " WHERE item_name LIKE '%' AND";

        } else {
            sqlPart1 = " WHERE item_name = '"+itemName+"' AND";
        }
        if (itemCat.equals("Any")){
            sqlPart2 = " item_category LIKE '%' AND";
        } else {
            sqlPart2 = " item_category = '"+itemCat+"' AND";
        }
        if (itemSubCat.equals("Any")){
            sqlPart3 = " item_SubCategory LIKE '%' AND";
        } else {
            sqlPart3 = " item_SubCategory = '"+itemSubCat+"' AND";
        }
        if (itemColor.equals("Any")){
            sqlPart4 = " itemColor LIKE '%' AND";
        } else {
            sqlPart4 = " itemColor = '"+itemColor+"' AND";
        }
        if (itemSize.equals("Any")){
            sqlPart5 = " itemSize LIKE '%' AND";
        } else {
            sqlPart5 = " itemSize = '"+itemSize+"' AND";
        }
        if (itemSize.equals("Any")){
            sqlPart6 = " itemBrand LIKE '%' ;";
        } else {
            sqlPart6 = " itemBrand = '"+itemBrand+"' ;";
        }

        String sql = "SELECT * FROM item"+sqlPart1+sqlPart2+sqlPart3+sqlPart4+sqlPart5+sqlPart6;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Item> items = new ArrayList<>();
            while (resultSet.next()){
                Item item = new Item(resultSet.getString("item_code"), resultSet.getString("item_name"), resultSet.getString("supplierItem_code"), resultSet.getString("item_category"), resultSet.getString("item_SubCategory"), resultSet.getInt("item_warrentyMonths"), resultSet.getInt("item_CostPrice"), resultSet.getInt("item_SellPrice"), resultSet.getInt("item_WholeSellPrice"), resultSet.getInt("item_stock_count"), resultSet.getString("itemSupplier_id"), resultSet.getString("item_barcode"), resultSet.getString("itemBrand"), resultSet.getString("itemSize"), resultSet.getString("itemColor"), resultSet.getString("item_image"));
                items.add(item);
            }

            return items;

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }
}
