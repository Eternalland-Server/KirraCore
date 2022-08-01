package net.sakuragame.eternal.kirracore.bukkit.listener.profile;

import ink.ptms.zaphkiel.ZaphkielAPI;
import lombok.val;
import net.sakuragame.eternal.kirracore.bukkit.KirraCoreBukkit;
import net.sakuragame.eternal.kirracore.bukkit.util.LocationUtil;
import net.sakuragame.eternal.kirracore.bukkit.util.Scheduler;
import net.sakuragame.eternal.kirracore.common.annotation.KListener;
import net.sakuragame.eternal.kirracore.common.util.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("SpellCheckingInspection")
@KListener
public class ListenerGem implements Listener {

    public static final Boolean ZAPHKIEL_ENABLED = Bukkit.getPluginManager().isPluginEnabled("Zaphkiel");

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.PHYSICAL) {
            return;
        }
        if (!ZAPHKIEL_ENABLED) {
            return;
        }
        val item = event.getItem();
        val player = event.getPlayer();
        val profile = KirraCoreBukkit.getInstance().getProfileManager().getProfile(player);
        if (profile == null) {
            return;
        }
        if (item == null || item.getType() == Material.AIR) {
            return;
        }
        val convertTo = getToConvert(item);
        if (convertTo == null) {
            return;
        }
        if (profile.convertModel != null && !profile.convertModel.equals("null")) {
            Lang.sendMessage(player, Lang.WARN_MSG_PREFIX, "您当前的模型已经存在了!", Lang.BukkitMessageType.ACTION_BAR);
            return;
        }
        item.setAmount(item.getAmount() - 1);
        Lang.sendMessage(player, Lang.NORMAL_MSG_PREFIX, "少女祈祷中...", Lang.BukkitMessageType.ACTION_BAR);
        LocationUtil.sparkyThings(player);
        Scheduler.runLaterAsync(() -> {
            player.getWorld().strikeLightningEffect(player.getLocation());
            profile.setConvertModel(convertTo);
            profile.setConvertMinutes(10);
            profile.loadConvertData();
            Lang.sendMessage(player, Lang.NOTICE_MSG_PREFIX, "您变身成为了: &b&l" + convertTo + "!", Lang.BukkitMessageType.ACTION_BAR);
        }, 144);
    }

    @Nullable
    private String getToConvert(ItemStack item) {
        val itemStream = ZaphkielAPI.INSTANCE.read(item);
        if (itemStream.isVanilla()) {
            return null;
        }
        return KirraCoreBukkit.getInstance().getConfig().getString("settings.convert." + itemStream.getZaphkielName());
    }
}
