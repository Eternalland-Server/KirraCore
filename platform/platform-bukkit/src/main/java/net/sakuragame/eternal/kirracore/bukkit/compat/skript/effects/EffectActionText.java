package net.sakuragame.eternal.kirracore.bukkit.compat.skript.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.sakuragame.eternal.justmessage.api.MessageAPI;
import net.sakuragame.eternal.kirracore.common.util.CC;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

@SuppressWarnings("unchecked")
public class EffectActionText extends Effect {

    private Expression<Player> player;
    private Expression<String> str;

    public boolean init(Expression<?>[] exp, int argA, @NotNull Kleenean argB, @NotNull SkriptParser.ParseResult argC) {
        player = (Expression<Player>) exp[1];
        str = (Expression<String>) exp[0];
        return true;
    }

    @NotNull
    @Override
    public String toString(@Nullable Event arg0, boolean arg1) {
        return "";
    }

    @Override
    protected void execute(@NotNull Event event) {
        if (player == null || str == null) {
            return;
        }
        Player[] players = this.player.getAll(event);
        String str = this.str.getSingle(event);
        Arrays.stream(players).forEach(player -> MessageAPI.sendActionTip(player, CC.toColored(str)));
    }
}
