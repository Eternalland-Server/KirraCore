package net.sakuragame.eternal.kirracore.bukkit;

import lombok.val;
import net.sakuragame.eternal.kirracore.bukkit.network.NetworkHandler;
import net.sakuragame.eternal.kirracore.bukkit.util.Utils;
import net.sakuragame.eternal.kirracore.common.packet.impl.c2b.C2BPacketPlayerSwitchServer;
import net.sakuragame.eternal.kirracore.common.packet.impl.c2b.sub.SwitchType;
import net.sakuragame.eternal.kirracore.common.util.CC;
import net.sakuragame.eternal.kirracore.common.util.Numbers;
import net.sakuragame.serversystems.manage.client.api.ClientManagerAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@SuppressWarnings("SpellCheckingInspection")
public class KirraCoreBukkitAPI {

    protected static final HashMap<UUID, BukkitTask> LOADING_TITLE_TASK_MAP = new HashMap<>();

    /**
     * 检查玩家是否拥有管理员权限。
     *
     * @param player 玩家。
     * @return 结果。
     */
    public static boolean isAdminPlayer(@NotNull Player player) {
        return Utils.hasAdminPermission(player);
    }

    /**
     * 将玩家直接传送到一个服务器。
     *
     * @param serverID    目标服务器 ID。
     * @param uuids       玩家 UUID。
     * @param assignWorld 指定世界。
     * @param assignCoord 指定点位。 (与 KirraCoord 挂钩)
     */
    public static void teleportPlayerToAnotherServer(@NotNull String serverID,
                                                     @NotNull UUID[] uuids,
                                                     @Nullable String assignWorld,
                                                     @Nullable String assignCoord
    ) {
        val packet = new C2BPacketPlayerSwitchServer();
        packet.setPlayerIDs(
                Arrays.stream(uuids)
                        .map(ClientManagerAPI::getUserID)
                        .filter(uid -> uid != -1)
                        .collect(Collectors.toList())
        );
        packet.setServerFrom(Utils.getCURRENT_SERVER_NAME());
        packet.setServerTo(serverID);
        packet.setAssignWorld(assignWorld == null ? "null" : assignWorld);
        packet.setAssignCoord(assignCoord == null ? "null" : assignCoord);
        packet.setSwitchType(SwitchType.DIRECT);
        NetworkHandler.sendPacket(packet, true);
        Arrays.stream(uuids)
                .map(Bukkit::getPlayer)
                .filter(Objects::nonNull)
                .forEach(KirraCoreBukkitAPI::showDefaultLoadingAnimation);
    }

    /**
     * 将玩家根据负载均衡规则传送到一个服务器。
     *
     * @param serverPrefix 目标服务器 ID 前缀。
     * @param uuids        玩家 UUID。
     */
    public static void teleportPlayerToServerByBalancing(@NotNull String serverPrefix,
                                                         @NotNull UUID... uuids
    ) {
        val packet = new C2BPacketPlayerSwitchServer();
        packet.setPlayerIDs(
                Arrays.stream(uuids)
                        .map(ClientManagerAPI::getUserID)
                        .filter(uid -> uid != -1)
                        .collect(Collectors.toList())
        );
        packet.setServerFrom(Utils.getCURRENT_SERVER_NAME());
        packet.setServerTo(serverPrefix);
        packet.setAssignWorld("null");
        packet.setAssignCoord("null");
        packet.setSwitchType(SwitchType.BY_BALANCING);
        NetworkHandler.sendPacket(packet, true);
        Arrays.stream(uuids)
                .map(Bukkit::getPlayer)
                .filter(Objects::nonNull)
                .forEach(KirraCoreBukkitAPI::showDefaultLoadingAnimation);
    }

    /**
     * 向玩家展示加载动画 (副标题)，
     * 玩家退出服务器后自动结束。
     *
     * @param player    玩家。
     * @param str       副标题内容，
     *                  以 "@" 代表加载符号。
     *                  例：正在前往副本：副本名 (@)
     * @param forceShow 是否强制展示，
     *                  当玩家已经开始了一个加载动画，是否替换掉原有任务。
     */
    public static void showLoadingAnimation(
            @NotNull Player player,
            @NotNull String str,
            boolean forceShow
    ) {
        val currentTask = LOADING_TITLE_TASK_MAP.get(player.getUniqueId());
        if (currentTask != null) {
            if (forceShow) {
                currentTask.cancel();
            } else {
                return;
            }
        }
        AtomicInteger i = new AtomicInteger(0);
        BukkitTask task = new BukkitRunnable() {

            @Override
            public void run() {
                if (!player.isOnline()) {
                    cancel();
                    LOADING_TITLE_TASK_MAP.remove(player.getUniqueId());
                    return;
                }
                i.set(i.get() + 1);
                if (i.get() >= 4) {
                    i.set(0);
                }
                String coloredStr = CC.toColored(str.replace("@", Utils.getLoadingSymbolByNumber(i.get())));
                player.sendTitle("", coloredStr, 0, 40, 0);
            }
        }.runTaskTimerAsynchronously(KirraCoreBukkit.getInstance(), 0, 10);
        LOADING_TITLE_TASK_MAP.put(player.getUniqueId(), task);
    }

    /**
     * 向玩家展示默认样式加载动画。
     *
     * @param player 玩家。
     */
    public static void showDefaultLoadingAnimation(Player player) {
        String str;
        switch (Numbers.getRandomInteger(1, 5)) {
            case 1: {
                str = "&e正在进行时空跃迁.. &7@";
                break;
            }
            case 2: {
                str = "&e少女祈祷中.. &7@";
                break;
            }
            case 3: {
                str = "&e正在命令时空管理人.. &7@";
                break;
            }
            case 4: {
                str = "&e正在唤醒灵魂.. &7@";
                break;
            }
            default: {
                str = "&e正在前往该坐标.. &7@";
                break;
            }
        }
        showLoadingAnimation(player, "&6&l➱ " + str, false);
    }

    /**
     * 取消玩家的加载动画任务。
     *
     * @param player 玩家。
     */
    public static void cancelLoadingAnimation(@NotNull Player player) {
        val task = LOADING_TITLE_TASK_MAP.get(player.getUniqueId());
        if (task == null) {
            return;
        }
        task.cancel();
        player.sendTitle("", "", 0, 1, 0);
        LOADING_TITLE_TASK_MAP.remove(player.getUniqueId());
    }
}