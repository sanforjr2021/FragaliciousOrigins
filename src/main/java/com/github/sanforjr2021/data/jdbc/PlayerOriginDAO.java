package com.github.sanforjr2021.data.jdbc;

import com.github.sanforjr2021.origins.Origin;
import com.github.sanforjr2021.origins.OriginType;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.github.sanforjr2021.util.MessageUtil.logError;

public class PlayerOriginDAO {
    public PlayerOriginDAO(){
        //This lives just to prevent crashes.
    }
    public static OriginType getOrigin(UUID uuid) throws SQLException { //We do not return UUID as they know the UUID already.
        OriginType type;
        Connection connection = DAOController.openDBConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT Origin FROM ORIGINS_PlayerOrigin WHERE UUID = ?");
        statement.setString(1, uuid.toString());
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            type = OriginType.valueOf(resultSet.getString(1));
        } else {
            type = null;
        }
        connection.close();
        return type;
    }

    public static boolean write(UUID uuid, OriginType type) {
        int numRowsChanged = 0;
        try {
            Connection connection = DAOController.openDBConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO ORIGINS_PlayerOrigin VALUES ( ?,?) ON DUPLICATE KEY UPDATE Origin = ?;");
            statement.setString(1, uuid.toString());
            statement.setString(2, type.toString());
            statement.setString(3, type.toString());
            numRowsChanged = statement.executeUpdate();
            connection.close();
        } catch (SQLException throwables) {
            logError("Error Logging player into database " + uuid);
            throwables.printStackTrace();
        }
        return numRowsChanged == 1;
    }

    public static boolean write(HashMap<UUID, Origin> map) {
        //If no players are logged in, don't update
        if(map.size() < 1){
            return false;
        }
        try {
            Connection connection = DAOController.openDBConnection();
            try {
                connection.setAutoCommit(false); //Commits are delayed so if there are any issues we don't run into issues and can safely rollback DB
                for (Map.Entry<UUID, Origin> mapElement : map.entrySet()) {
                    PreparedStatement statement = connection.prepareStatement("INSERT INTO ORIGINS_PlayerOrigin VALUES ( ?,?) ON DUPLICATE KEY UPDATE Origin = ?;");
                    String origin =  mapElement.getValue().getOriginType().toString();
                    statement.setString(1, mapElement.getKey().toString());
                    statement.setString(2, origin);
                    statement.setString(3, origin);
                    statement.execute();
                }
                connection.commit();
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException throwables) {
                logError("Could not save hashmap of all players");
                throwables.printStackTrace();
                connection.rollback();
                return false;
            }
        } catch (SQLException throwables) {
            logError("Could not establish connection to SQL server");
            return false;
        }
        return true;
    }
    private long getUnixTimestamp(){
        return System.currentTimeMillis() / 1000L;
    }
    public static boolean getExpiredTempOrigins(){
        return true;
    }
}
