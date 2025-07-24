package DataBase;

import java.sql.Connection;

public class CustomerTableDB {

    //Connecting with database
    private static final Connection connection = ConnectDB.getConnection();
    private static final String tableName = "customer";

}
