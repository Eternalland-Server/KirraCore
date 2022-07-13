package net.sakuragame.eternal.kirracore.bukkit.profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import net.sakuragame.eternal.dragoncore.api.CoreAPI;
import net.sakuragame.eternal.dragoncore.network.PacketSender;
import net.sakuragame.eternal.kirracore.bukkit.KirraCoreBukkit;
import net.sakuragame.eternal.kirracore.bukkit.util.Utils;
import org.bukkit.entity.Player;

@AllArgsConstructor
@Data
public class Profile {

    @Getter
    public final Player player;

    public int onlineMinutes;

    public String convertModel;
    public int convertMinutes;

    public void drop() {
        KirraCoreBukkit.getInstance().getProfileManager().getPROFILES().remove(player.getUniqueId());
    }

    public void loadConvertData() {
        if (!Utils.isSpawnServer()) {
            return;
        }
        if (convertModel == null) {
            CoreAPI.setEntityModel(player.getUniqueId(), null);
            PacketSender.setEntityModel(player, player.getUniqueId(), null);
            return;
        }
        CoreAPI.setEntityModel(player.getUniqueId(), convertModel);
    }
}
