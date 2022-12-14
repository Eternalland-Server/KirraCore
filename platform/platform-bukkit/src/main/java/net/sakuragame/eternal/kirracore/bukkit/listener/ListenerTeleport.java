package net.sakuragame.eternal.kirracore.bukkit.listener;

import lombok.val;
import net.sakuragame.eternal.kirracore.bukkit.KirraCoreBukkit;
import net.sakuragame.eternal.kirracore.bukkit.KirraCoreBukkitAPI;
import net.sakuragame.eternal.kirracore.bukkit.event.PlayerTeleportServerEvent;
import net.sakuragame.eternal.kirracore.common.annotation.KListener;
import net.sakuragame.eternal.kirracore.common.packet.impl.b2c.sub.TResult;
import net.sakuragame.eternal.kirracore.common.util.CC;
import net.sakuragame.eternal.kirracore.common.util.Lang;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@KListener
public class ListenerTeleport implements Listener {

    @EventHandler
    public void onTeleportFailed(PlayerTeleportServerEvent event) {
        if (!event.isFailed()) {
            return;
        }
        val player = event.getPlayer();
        KirraCoreBukkit.getInstance().getChainFactory().newChain()
                .delayedTask(() -> KirraCoreBukkitAPI.cancelLoadingAnimation(player), 5, true)
                .delay(10)
                .task(() -> {
                    player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1.0f, 1.5f);
                    player.sendTitle("", CC.toColored("&4&l传送失败!"), 0, 60, 0);
                    Lang.sendMessage(player, Lang.WARN_MSG_PREFIX, "传送失败, 请稍后再试.", Lang.BukkitMessageType.ACTION_BAR);
                })
                .execute();
    }
}