package com.github.sanforjr2021.data.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import static com.github.sanforjr2021.util.MessageUtil.logError;

public class ShulkInventoryDAO {

    public static String getShulkData(UUID uuid) { //We do not return UUID as they know the UUID already.
        String shulkData = null;
        try {
            Connection connection = DAOController.openDBConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT ShulkData FROM ShulkChest WHERE UUID = ?");
            statement.setString(1, uuid.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                shulkData = resultSet.getString(1);
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return shulkData;
    }

    public static boolean write(UUID uuid, String data) {
        int numRowsChanged = 0;
        try {
            Connection connection = DAOController.openDBConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO ShulkChest VALUES (?,?) ON DUPLICATE KEY UPDATE ShulkData = ?;");
            statement.setString(1, uuid.toString());
            statement.setString(2, data);
            statement.setString(3, data);
            numRowsChanged = statement.executeUpdate();
            connection.close();
        } catch (SQLException throwables) {
            logError("Error Logging shulkdata into database for UUID" + uuid);
            throwables.printStackTrace();
        }
        return numRowsChanged == 1;
    }
}
