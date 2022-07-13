package net.sakuragame.eternal.kirracore.bungee;

import lombok.Getter;
import lombok.val;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.sakuragame.eternal.kirracore.bungee.annotation.KListener;
import net.sakuragame.eternal.kirracore.bungee.manager.ServerManager;
import org.reflections.Reflections;

@SuppressWarnings("SpellCheckingInspection")
public class KirraCoreBungee extends Plugin {

    @Getter
    private static KirraCoreBungee instance;

    @Getter
    private static ServerManager serverManager;

    @Getter
    private Reflections ref;

    @Override
    public void onEnable() {
        instance = this;

        serverManager = new ServerManager(this);


        ref = new Reflections("net.sakuragame.eternal.kirracore.bungee");

        initListeners();
    }

    private void initListeners() {
        val annotated = ref.getTypesAnnotatedWith(KListener.class);
        annotated.forEach(clazz -> {
            if (Listener.class.isAssignableFrom(clazz)) {
                try {
                    val listener = (Listener) clazz.newInstance();
                    getProxy().getPluginManager().registerListener(this, listener);
                } catch (Exception ignored) {
                }
            }
        });
    }
}
