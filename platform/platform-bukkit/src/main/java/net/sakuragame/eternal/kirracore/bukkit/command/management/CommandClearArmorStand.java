package net.sakuragame.eternal.kirracore.bukkit.command.management;

import com.qrakn.honcho.command.CommandMeta;
import lombok.val;
import net.sakuragame.eternal.kirracore.common.util.Lang;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.util.concurrent.atomic.AtomicInteger;

@CommandMeta(label = {"ClearAS", "ClearArmorStand", "ClearArmorStands"}, permission = "admin")
public class CommandClearArmorStand {

    public void execute(Player player) {
        val count = new AtomicInteger(0);
        player.getWorld().getEntities()
                .stream()
                .filter(entity -> entity instanceof ArmorStand)
                .map(entity -> (ArmorStand) entity)
                .forEach(entity -> {
                    count.getAndIncrement();
                    entity.remove();
                });
        Lang.sendMessage(player, Lang.MANAGEMENT_MSG_PREFIX, "清理了 " + count.get() + " 个盔甲架.", Lang.BukkitMessageType.TEXT);
    }
}
