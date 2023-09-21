package com.github.sanforjr2021.data.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import static com.github.sanforjr2021.util.MessageUtil.logError;

public class BlazeHeatDao {
    public static int getBlazeHeat(UUID uuid) { //We do not return UUID as they know the UUID already.
    int heat = -1;
    try {
        Connection connection = DAOController.openDBConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT Heat FROM BlazebornHeat WHERE UUID = ?");
        statement.setString(1, uuid.toString());
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            heat = resultSet.getInt(1);
        }
        connection.close();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    return heat;
}

    public static boolean write(UUID uuid, int heat) {
        int numRowsChanged = 0;
        try {
            Connection connection = DAOController.openDBConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO BlazebornHeat VALUES (?,?) ON DUPLICATE KEY UPDATE Heat = ?;");
            statement.setString(1, uuid.toString());
            statement.setInt(2, heat);
            statement.setInt(3, heat);
            numRowsChanged = statement.executeUpdate();
            connection.close();
        } catch (SQLException throwables) {
            logError("Error Logging shulkdata into database for UUID" + uuid);
            throwables.printStackTrace();
        }
        return numRowsChanged == 1;
    }
}
