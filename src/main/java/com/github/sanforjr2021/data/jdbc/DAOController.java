package com.github.sanforjr2021.data.jdbc;
import java.sql.*;

import static com.github.sanforjr2021.util.MessageUtil.logError;

public class DAOController {
    private static String url;
    public DAOController(String url) {
        this.url = url;
        buildDatabase();
    }

    protected static Connection openDBConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }
    private void buildDatabase(){
        try {
            Statement statement = openDBConnection().createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS PlayerOrigin(" +
                    "UUID varchar(64)," +
                    "Orign varchar(32)," +
                    "PRIMARY KEY (UUID)" +
                    ")";
            statement.execute(sql);
            statement.close();
        }catch (SQLException exp) {
            logError("Could not build table PlayerOrigin");
            exp.printStackTrace();
        }

    }
}
