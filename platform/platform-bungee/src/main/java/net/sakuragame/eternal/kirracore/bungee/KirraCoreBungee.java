package net.sakuragame.eternal.kirracore.bungee;

import lombok.Getter;
import lombok.val;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.sakuragame.eternal.kirracore.bungee.manager.ServerManager;
import net.sakuragame.eternal.kirracore.bungee.network.NetworkHandler;
import net.sakuragame.eternal.kirracore.bungee.util.ClassUtil;
import net.sakuragame.eternal.kirracore.common.annotation.KListener;

import java.util.Collection;

@SuppressWarnings("SpellCheckingInspection")
public class KirraCoreBungee extends Plugin {

    @Getter
    private static KirraCoreBungee instance;

    @Getter
    private ServerManager serverManager;

    @Getter
    private Collection<Class<?>> clazzs;

    @Override
    public void onEnable() {
        instance = this;

        serverManager = new ServerManager(this);

        clazzs = ClassUtil.getClassesInPackage(this, "net.sakuragame.eternal.kirracore.bungee");


        initListeners();

        NetworkHandler.init();
    }

    private void initListeners() {
        clazzs.forEach(clazz -> {
            if (clazz.getAnnotation(KListener.class) == null) {
                return;
            }
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
