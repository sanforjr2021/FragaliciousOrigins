package com.github.sanforjr2021.data.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static com.github.sanforjr2021.util.MessageUtil.logError;

public class DAOController {
    private static String url;

    public DAOController(String url) {
        loadJDBC();
        DAOController.url = url;
        buildDatabase();
    }

    protected static Connection openDBConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }

    private void buildDatabase() {
        try {
            Statement statement = openDBConnection().createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS ORIGINS_PlayerOrigin(" +
                    "UUID varchar(64)," +
                    "Origin varchar(32)," +
                    "PRIMARY KEY (UUID)" +
                    ")";
            statement.execute(sql);
            sql = "CREATE TABLE IF NOT EXISTS ORIGINS_ShulkChest(" +
                    "UUID varchar(64)," +
                    "ShulkData LONGTEXT," +
                    "PRIMARY KEY (UUID)" +
                    ")";
            statement.execute(sql);
            sql = "CREATE TABLE IF NOT EXISTS ORIGINS_BlazebornHeat(" +
                    "UUID varchar(64)," +
                    "Heat INT(10)," +
                    "PRIMARY KEY (UUID)" +
                    ")";
            statement.execute(sql);
            statement.close();
        } catch (SQLException exp) {
            logError("Could not build table PlayerOrigin");
            exp.printStackTrace();
        }

    }
    private void loadJDBC(){
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
