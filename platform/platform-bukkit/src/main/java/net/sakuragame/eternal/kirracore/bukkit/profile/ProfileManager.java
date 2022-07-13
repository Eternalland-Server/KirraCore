package net.sakuragame.eternal.kirracore.bukkit.profile;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import me.skymc.kirracoord.Coord;
import me.skymc.kirracoord.Loader;
import net.sakuragame.eternal.kirracore.bukkit.KirraCoreBukkit;
import net.sakuragame.eternal.kirracore.bukkit.util.Scheduler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.UUID;

@SuppressWarnings("SpellCheckingInspection")
@RequiredArgsConstructor
public class ProfileManager {

    private final KirraCoreBukkit instance;

    @Getter
    private final HashMap<UUID, Profile> PROFILES = new HashMap<>();

    @Getter
    private final HashMap<UUID, String> ASSIGN_WORLD = new HashMap<>();
    @Getter
    private final HashMap<UUID, String> ASSIGN_COORD = new HashMap<>();

    public void read(Player player) {
        Scheduler.runAsync(() -> {
            if (!instance.getDatabase().hasData(player.getUniqueId())) {
                instance.getDatabase().createData(player.getUniqueId());
                PROFILES.put(player.getUniqueId(), new Profile(player, 0, "default", 0));
                return;
            }
            val onlineMinutes = instance.getDatabase().getOnlineMinutes(player.getUniqueId());
            val pair = instance.getDatabase().getModelPair(player.getUniqueId());
            val profile = new Profile(player, onlineMinutes, pair.getA(), pair.getB());
            PROFILES.put(player.getUniqueId(), profile);
            profile.loadConvertData();
        });
    }

    public void save(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(KirraCoreBukkit.getInstance(), () -> {
            val profile = getProfileByPlayerUUID(player.getUniqueId());
            if (profile == null) return;
            KirraCoreBukkit.getInstance().getDatabase().update(player.getUniqueId(), profile);
            dataRecycle(player);
        });
    }

    public void dataRecycle(Player player) {
        val profile = getProfileByPlayerUUID(player.getUniqueId());
        if (profile == null) return;
        profile.drop();
    }

    @Nullable
    public Profile getProfile(Player player) {
        return getProfileByPlayerUUID(player.getUniqueId());
    }

    @Nullable
    public Profile getProfileByPlayerUUID(UUID playerUUID) {
        return PROFILES.getOrDefault(playerUUID, null);
    }

    public Location getAssignWorld(Player player) {
        val worldID = ASSIGN_WORLD.get(player.getUniqueId());
        if (worldID == null) return null;

        val dfactory = KirraCoreBukkit.getInstance().getCompatManager().getMultiverseCore().getDestFactory();
        val destination = dfactory.getDestination(worldID);
        if (destination == null) return null;

        ASSIGN_WORLD.remove(player.getUniqueId());
        return destination.getLocation(player);
    }

    public Coord getAssignCoord(Player player) {
        val coordID = ASSIGN_COORD.get(player.getUniqueId());
        if (coordID == null) return null;

        ASSIGN_COORD.remove(player.getUniqueId());

        return Loader.INSTANCE.getCoordByName(coordID);
    }
}
