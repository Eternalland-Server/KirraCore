package net.sakuragame.eternal.kirracore.bungee.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Event;

@AllArgsConstructor
public class ServerOnlineEvent extends Event {

    @Getter
    private final String serverID;
}