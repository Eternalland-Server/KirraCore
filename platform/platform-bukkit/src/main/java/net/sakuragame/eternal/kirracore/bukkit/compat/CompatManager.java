package net.sakuragame.eternal.kirracore.bukkit.compat;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.onarandombox.MultiverseCore.MultiverseCore;
import lombok.Getter;
import net.sakuragame.eternal.kirracore.bukkit.KirraCoreBukkit;
import net.sakuragame.eternal.kirracore.bukkit.compat.mythicmobs.CompatMythicMobsHandler;
import net.sakuragame.eternal.kirracore.bukkit.compat.skript.CompatSkriptHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class CompatManager {

    protected final ArrayList<CompatHandler> HANDLERS = new ArrayList<CompatHandler>() {{
        add(new CompatMythicMobsHandler());
        add(new CompatSkriptHandler());
    }};

    @Getter
    private final KirraCoreBukkit instance;

    @Getter
    private final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

    @Getter
    private final MultiverseCore multiverseCore = JavaPlugin.getPlugin(MultiverseCore.class);

    public CompatManager(KirraCoreBukkit instance) {
        this.instance = instance;
        initCompat();
    }

    private void initCompat() {
        HANDLERS.forEach(handler -> {
            if (!Bukkit.getPluginManager().isPluginEnabled(handler.getPlugin())) {
                return;
            }
            this.instance.getLogger().info("跟 " + handler.getPlugin() + " 进行挂钩.");
            handler.init();
        });
    }
}
