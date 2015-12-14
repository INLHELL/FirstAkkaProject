import akka.actor.UntypedActor;

import java.sql.*;

/**
 * Created by vfedotov on 12/14/2015.
 */
public class MasterActor extends UntypedActor {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/test";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "toor";
    private ResultSet resultSet;
    private Connection connection;


    @Override
    public void preStart() {
        super.preStart();
        //STEP 3: Open a connection
        System.out.println("Connecting to database...");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onReceive(Object message) throws Exception {
//        if (message instanceof String) {
//        } else

        if (message instanceof Result) {
            Result result = (Result) message;
            String sqlQuery = result.getSqlQuery();
            System.out.println("SQL received: " + sqlQuery);
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);
            System.out.println("Result required!");
            System.out.println(super.getSender());
            super.getSender().tell(resultSet);
        } else {
            super.unhandled(message);
        }

    }
}
