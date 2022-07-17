package net.sakuragame.eternal.kirracore.bukkit.compat.mythicmobs;

import net.sakuragame.eternal.kirracore.bukkit.compat.CompatHandler;
import org.bukkit.Bukkit;

public class CompatMythicMobsHandler implements CompatHandler {

    @Override
    public String getPlugin() {
        return "MythicMobs";
    }

    @Override
    public void init() {
        Bukkit.getPluginManager().registerEvents(new MythicMobsListener(), getInstance());
    }
}