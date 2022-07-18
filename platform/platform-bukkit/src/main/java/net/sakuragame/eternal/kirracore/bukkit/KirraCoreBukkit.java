package net.sakuragame.eternal.kirracore.bukkit;

import com.qrakn.honcho.Honcho;
import com.qrakn.honcho.command.CommandMeta;
import lombok.Getter;
import lombok.val;
import net.sakuragame.eternal.kirracore.bukkit.compat.CompatManager;
import net.sakuragame.eternal.kirracore.bukkit.network.NetworkHandler;
import net.sakuragame.eternal.kirracore.bukkit.profile.ProfileManager;
import net.sakuragame.eternal.kirracore.bukkit.storage.Database;
import net.sakuragame.eternal.kirracore.bukkit.util.ClassUtil;
import net.sakuragame.eternal.kirracore.common.annotation.KListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;

@SuppressWarnings("SpellCheckingInspection")
public class KirraCoreBukkit extends JavaPlugin {

    @Getter
    private static KirraCoreBukkit instance;

    @Getter
    private CompatManager compatManager;

    @Getter
    private ProfileManager profileManager;

    @Getter
    private Honcho honcho;

    @Getter
    private KConfiguration kConfiguration;

    @Getter
    private Database database;

    @Getter
    private Collection<Class<?>> clazzs;

    @Override
    public void onEnable() {
        instance = this;

        clazzs = ClassUtil.getClassesInPackage(this, "net.sakuragame.eternal.kirracore.bukkit");

        saveDefaultConfig();
        reloadConfig();

        compatManager = new CompatManager(this);
        profileManager = new ProfileManager(this);

        honcho = new Honcho(this);
        kConfiguration = new KConfiguration();
        database = new Database();

        initListeners();
        initCommands();

        NetworkHandler.init();
    }

    private void initCommands() {
        clazzs.forEach(clazz -> {
            try {
                if (clazz.getAnnotation(CommandMeta.class) == null) {
                    return;
                }
                System.out.println("not null!");
                System.out.println("clazz: " + clazz.getName());
                honcho.registerCommand(clazz.newInstance());
            } catch (Exception ignored) {
            }
        });
    }

    private void initListeners() {
        clazzs.forEach(clazz -> {
            if (clazz.getAnnotation(KListener.class) == null) {
                return;
            }
            if (Listener.class.isAssignableFrom(clazz)) {
                try {
                    val listener = (Listener) clazz.newInstance();
                    Bukkit.getPluginManager().registerEvents(listener, this);
                } catch (Exception ignored) {
                }
            }
        });
    }
}