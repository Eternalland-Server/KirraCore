package net.sakuragame.eternal.kirracore.bukkit.storage;

import lombok.AllArgsConstructor;
import net.sakuragame.eternal.dragoncore.database.mysql.DatabaseTable;

@SuppressWarnings("SpellCheckingInspection")
@AllArgsConstructor
public enum DataTables {

    PROFILE(new DatabaseTable("kirracore_profile",
            new String[]{
                    "`uid` int NOT NULL PRIMARY KEY",
                    "`online_minutes` int",
                    "`convert_model` varchar(64)",
                    "`convert_minutes` int",
            }));

    private final DatabaseTable table;

    public String getTableName() {
        return table.getTableName();
    }

    public String[] getColumns() {
        return table.getTableColumns();
    }

    public DatabaseTable getTable() {
        return table;
    }

    public void createTable() {
        table.createTable();
    }
}