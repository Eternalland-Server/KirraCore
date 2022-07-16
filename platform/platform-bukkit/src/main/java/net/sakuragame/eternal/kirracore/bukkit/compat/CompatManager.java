package net.sakuragame.eternal.kirracore.bukkit.compat;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.onarandombox.MultiverseCore.MultiverseCore;
import lombok.Getter;
import lombok.val;
import net.sakuragame.eternal.kirracore.bukkit.KirraCoreBukkit;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

public class CompatManager {

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
        val ref = new Reflections("net.sakuragame.eternal.kirracore.bukkit");
        val subType = ref.getSubTypesOf(CompatHandler.class);
        subType.forEach(clazz -> {
            try {
                val newInstance = clazz.newInstance();
                if (Bukkit.getPluginManager().getPlugin(newInstance.getPlugin()) == null) {
                    return;
                }
                instance.getLogger().info("跟 " + newInstance.getPlugin() + " 进行挂钩.");
                newInstance.init();
            } catch (Exception exception) {
                instance.getLogger().info("注册兼容性控件时出现了一个错误: ");
                exception.printStackTrace();
            }
        });
    }
}
