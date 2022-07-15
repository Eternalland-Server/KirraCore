package net.sakuragame.eternal.kirracore.bukkit.listener.protocol;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerOptions;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import lombok.val;
import net.sakuragame.eternal.kirracore.bukkit.KirraCoreBukkit;
import net.sakuragame.eternal.kirracore.common.annotation.KListener;
import org.bukkit.event.Listener;

@KListener
public class ListenerProtocol implements Listener {

    static {
        PacketAdapter packetAdapter = new PacketAdapter(PacketAdapter
                .params()
                .plugin(KirraCoreBukkit.getInstance())
                .serverSide()
                .listenerPriority(ListenerPriority.HIGH)
                .optionAsync()
                .options(ListenerOptions.SKIP_PLUGIN_VERIFIER)
                .types(PacketType.Play.Server.WORLD_PARTICLES)) {
            @Override
            public void onPacketSending(PacketEvent event) {
                val container = event.getPacket();
                if (!container.getParticles().getValues().contains(EnumWrappers.Particle.DAMAGE_INDICATOR)) return;
                event.setCancelled(true);
            }
        };
        KirraCoreBukkit.getInstance().getCompatManager().getProtocolManager().addPacketListener(packetAdapter);
    }
}
