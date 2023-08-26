package com.github.sanforjr2021.data.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static com.github.sanforjr2021.util.MessageUtil.logError;

public class DAOController {
    private static String url;

    public DAOController(String url) {
        DAOController.url = url;
        buildDatabase();
    }

    protected static Connection openDBConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }

    private void buildDatabase() {
        try {
            Statement statement = openDBConnection().createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS PlayerOrigin(" +
                    "UUID varchar(64)," +
                    "Origin varchar(32)," +
                    "PRIMARY KEY (UUID)" +
                    ")";
            statement.execute(sql);
            sql = "CREATE TABLE IF NOT EXISTS ShulkChest(" +
                    "UUID varchar(64)," +
                    "ShulkData LONGTEXT," +
                    "PRIMARY KEY (UUID)" +
                    ")";
            statement.execute(sql);
            statement.close();
        } catch (SQLException exp) {
            logError("Could not build table PlayerOrigin");
            exp.printStackTrace();
        }

    }
}
