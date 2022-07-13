package net.sakuragame.eternal.kirracore.bukkit.network;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.sakuragame.eternal.kirracore.common.packet.IPacket;

import java.lang.reflect.Method;

@SuppressWarnings("rawtypes")
@AllArgsConstructor
@Getter
public class PacketListenerData {

    private Object instance;
    private Method method;
    private Class packetClass;

    public boolean matches(IPacket packet) {
        return this.packetClass == packet.getClass();
    }
}