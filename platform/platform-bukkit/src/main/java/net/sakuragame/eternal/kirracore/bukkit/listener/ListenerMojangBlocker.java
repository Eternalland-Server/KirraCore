package net.sakuragame.eternal.kirracore.bukkit.listener;

import com.alibaba.dcm.DnsCacheManipulator;
import net.sakuragame.eternal.kirracore.bukkit.KirraCoreBukkit;
import net.sakuragame.eternal.kirracore.bukkit.util.Utils;
import net.sakuragame.eternal.kirracore.common.annotation.KListener;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;

import java.util.Objects;

@SuppressWarnings("SpellCheckingInspection")
@KListener
public class ListenerMojangBlocker implements Listener {

    public ListenerMojangBlocker() {
        if (Bukkit.getOnlineMode()) {
            Utils.info("Mojang 全局屏蔽服务无法在正版模式下启用.");
        } else {
            Utils.info("正在开启 Mojang 全局屏蔽服务.");
            DnsCacheManipulator.setDnsCache("www.minecraft.net", "127.0.0.1");
            DnsCacheManipulator.setDnsCache("www.mojang.com", "127.0.0.1");
            DnsCacheManipulator.setDnsCache("status.mojang.com", "127.0.0.1");
            DnsCacheManipulator.setDnsCache("authserver.mojang.com", "127.0.0.1");
            DnsCacheManipulator.setDnsCache("session.minecraft.net", "127.0.0.1");
            DnsCacheManipulator.setDnsCache("account.mojang.com", "127.0.0.1");
            DnsCacheManipulator.setDnsCache("auth.mojang.com", "127.0.0.1");
            DnsCacheManipulator.setDnsCache("skins.minecraft.net", "127.0.0.1");
            DnsCacheManipulator.setDnsCache("sessionserver.mojang.com", "127.0.0.1");
            DnsCacheManipulator.setDnsCache("api.mojang.com", "127.0.0.1");
            DnsCacheManipulator.setDnsCache("textures.minecraft.net", "127.0.0.1");
            DnsCacheManipulator.setDnsCache("mojang.com", "127.0.0.1");
            Utils.info("Mojang 全局屏蔽服务已启用.");
        }
    }

    @EventHandler
    public void onServerClose(PluginDisableEvent event) {
        if (Bukkit.getOnlineMode()) {
            return;
        }
        if (Objects.equals(event.getPlugin().getName(), KirraCoreBukkit.getInstance().getName())) {
            DnsCacheManipulator.clearDnsCache();
        }
    }
}
