package net.sakuragame.eternal.kirracore.common.packet.function;

import net.sakuragame.eternal.kirracore.common.annotation.KComingPacketHandler;
import net.sakuragame.eternal.kirracore.common.packet.IPacket;
import net.sakuragame.eternal.kirracore.common.packet.PacketListenerData;

import java.lang.reflect.Method;
import java.util.List;

@SuppressWarnings("rawtypes")
public class FunctionPacketRegister {

    public static void registerListener(Object clazz, List<PacketListenerData> listeners) {
        for (Method method : clazz.getClass().getDeclaredMethods()) {
            if (method.getDeclaredAnnotation(KComingPacketHandler.class) != null) {
                Class packetClass = null;
                if (method.getParameters().length > 0) {
                    if (IPacket.class.isAssignableFrom(method.getParameters()[0].getType())) {
                        packetClass = method.getParameters()[0].getType();
                    }
                }
                if (packetClass != null) {
                    listeners.add(new PacketListenerData(clazz, method, packetClass));
                }
            }
        }
    }
}
