package net.sakuragame.eternal.kirracore.bukkit;

import com.qrakn.honcho.Honcho;
import com.qrakn.honcho.command.CommandMeta;
import lombok.Getter;
import lombok.val;
import net.sakuragame.eternal.kirracore.bukkit.annotation.KListener;
import net.sakuragame.eternal.kirracore.bukkit.compat.CompatManager;
import net.sakuragame.eternal.kirracore.bukkit.profile.ProfileManager;
import net.sakuragame.eternal.kirracore.bukkit.storage.Database;
import net.sakuragame.serversystems.manage.client.api.util.SchedulerUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

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
    private Reflections ref;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        reloadConfig();

        compatManager = new CompatManager(this);
        profileManager = new ProfileManager(this);

        honcho = new Honcho(this);
        kConfiguration = new KConfiguration();
        database = new Database();

        ref = new Reflections("net.sakuragame.eternal.kirracore.bukkit");

        initListeners();
        initCommands();
    }

    private void initCommands() {
        val annotated = ref.getTypesAnnotatedWith(CommandMeta.class);
        annotated.forEach(clazz -> {
            try {
                honcho.registerCommand(clazz);
            } catch (Exception ignored) {
            }
        });
    }

    private void initListeners() {
        val annotated = ref.getTypesAnnotatedWith(KListener.class);
        annotated.forEach(clazz -> {
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