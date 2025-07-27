package DataBase;

import java.sql.Connection;

public class CustomerTableDB {

    //Connecting with database
    private static final Connection connection = ConnectDB.getConnection();

    //Table name which handles the customers
    private static final String tableName = "customer";

}
