package net.sakuragame.eternal.kirracore.common.packet;

import lombok.AllArgsConstructor;
import lombok.Getter;

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