package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//This class is for connect the specific database
public class ConnectDB {

    //Use this variable to change the database name
    private static final String dataBaseName = "kasceromic";

    //Connection string
    private static final String URL = "jdbc:mysql://localhost:3306/"+dataBaseName;

    //User
    private static final String USER = "root";

    //Password
    private static final String PASS = "";

    //Connection which holds the connection to the database
    private static Connection connection;

    //This method help to create a connection with database
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL,USER,PASS);
            System.out.println("Connected to the database.");
            return connection;
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error: Unable to connect to the database.");
            e.printStackTrace();
        }

        return null;
    }

}
