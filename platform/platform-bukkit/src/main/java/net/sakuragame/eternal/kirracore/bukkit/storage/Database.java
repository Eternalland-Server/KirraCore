package net.sakuragame.eternal.kirracore.bukkit.storage;

import lombok.val;
import lombok.var;
import net.sakuragame.eternal.kirracore.bukkit.profile.Profile;
import net.sakuragame.eternal.kirracore.common.util.Pair;
import net.sakuragame.serversystems.manage.api.database.DataManager;
import net.sakuragame.serversystems.manage.api.database.DatabaseQuery;
import net.sakuragame.serversystems.manage.client.api.ClientManagerAPI;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Database {

    private final DataManager dataManager = ClientManagerAPI.getDataManager();

    private final Pair<String, Integer> defaultPair = Pair.createPair("null", 0);

    static {
        for (DataTables table : DataTables.values()) {
            table.createTable();
        }
    }

    public boolean hasData(UUID uuid) {
        val uid = ClientManagerAPI.getUserID(uuid);
        var hasData = false;
        try (DatabaseQuery query = dataManager.createQuery(DataTables.PROFILE.getTableName(), "uid", uid)) {
            ResultSet result = query.getResultSet();
            while (result.next()) {
                hasData = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hasData;
    }

    public void createData(UUID uuid) {
        val uid = ClientManagerAPI.getUserID(uuid);
        dataManager.executeInsert(
                DataTables.PROFILE.getTableName(),
                new String[]{"uid", "online_minutes", "convert_model", "convert_minutes"},
                new Object[]{uid, 0, "null", 0}
        );
    }

    public void update(UUID uuid, Profile profile) {
        val uid = ClientManagerAPI.getUserID(uuid);
        if (uid == -1) return;
        dataManager.executeReplace(
                DataTables.PROFILE.getTableName(),
                new String[]{"uid", "online_minutes", "convert_model", "convert_minutes"},
                new Object[]{uid, profile.onlineMinutes, profile.convertModel == null ? "null" : profile.convertModel, profile.convertMinutes}
        );
    }

    public int getOnlineMinutes(UUID uuid) {
        val uid = ClientManagerAPI.getUserID(uuid);
        if (uid == -1) return 0;
        var toReturn = -1;
        try (DatabaseQuery query = dataManager.createQuery(DataTables.PROFILE.getTableName(), "uid", uid)) {
            ResultSet result = query.getResultSet();
            while (result.next()) {
                toReturn = result.getInt("online_minutes");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    public Pair<String, Integer> getModelPair(UUID uuid) {
        val uid = ClientManagerAPI.getUserID(uuid);
        if (uid == -1) return defaultPair;
        val pair = Pair.createPair("default", 0);
        try (DatabaseQuery query = dataManager.createQuery(DataTables.PROFILE.getTableName(), "uid", uid)) {
            val result = query.getResultSet();
            while (result.next()) {
                pair.setA(result.getString("convert_model"));
                pair.setB(result.getInt("convert_minutes"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pair;
    }
}
