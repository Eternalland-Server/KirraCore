package net.sakuragame.eternal.kirracore.bukkit;

import lombok.Getter;
import lombok.val;
import net.sakuragame.eternal.kirracore.bukkit.function.FunctionPacket;
import net.sakuragame.eternal.kirracore.bukkit.util.Utils;
import net.sakuragame.eternal.kirracore.common.packet.impl.b2c.sub.TResult;
import net.sakuragame.eternal.kirracore.common.packet.impl.c2b.sub.SwitchType;
import net.sakuragame.eternal.kirracore.common.packet.impl.sub.AssignType;
import net.sakuragame.eternal.kirracore.common.util.CC;
import net.sakuragame.eternal.kirracore.common.util.Numbers;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("SpellCheckingInspection")
public class KirraCoreBukkitAPI {

    protected static final HashMap<UUID, BukkitTask> LOADING_TITLE_TASK_MAP = new HashMap<>();

    @Getter
    private static final HashMap<UUID, CompletableFuture<TResult>> TELEPORTING_MAP = new HashMap<>();

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
     * @param assignType  指定类型。
     * @param assignValue 指定数据。
     * @return 传送结果。
     */
    public static CompletableFuture<TResult> teleportPlayerToAnotherServer(@NotNull String serverID,
                                                                           @Nullable AssignType assignType,
                                                                           @Nullable String assignValue,
                                                                           @NotNull UUID[] uuids
    ) {
        val future = new CompletableFuture<TResult>();
        FunctionPacket.sendC2BPacket(serverID, uuids, assignType, assignValue, SwitchType.DIRECT);
        FunctionPacket.handleC2BFuture(uuids, future);
        return future;
    }

    /**
     * 将玩家根据负载均衡规则传送到一个服务器。
     *
     * @param serverPrefix 目标服务器 ID 前缀。
     * @param uuids        玩家 UUID。
     * @return 传送结果。
     */
    public static CompletableFuture<TResult> teleportPlayerToServerByBalancing(@NotNull String serverPrefix,
                                                                               @NotNull UUID... uuids
    ) {
        System.out.println("reached balance");
        val future = new CompletableFuture<TResult>();
        FunctionPacket.sendC2BPacket(serverPrefix, uuids, null, null, SwitchType.DIRECT);
        FunctionPacket.handleC2BFuture(uuids, future);
        return future;
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