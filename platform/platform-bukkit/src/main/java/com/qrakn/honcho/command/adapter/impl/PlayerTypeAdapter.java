package com.qrakn.honcho.command.adapter.impl;

import com.qrakn.honcho.command.adapter.CommandTypeAdapter;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class PlayerTypeAdapter implements CommandTypeAdapter {

    @Override
    public <T> T convert(String string, Class<T> type) {
        return type.cast(Bukkit.getPlayer(string));
    }

    @Override
    public <T> List<String> tabComplete(String string, Class<T> type) {
        List<String> completed = new ArrayList<>();
        Bukkit.getOnlinePlayers()
                .stream()
                .filter(player -> player.getName().toLowerCase().startsWith(string.toLowerCase()))
                .forEach(player -> completed.add(player.getName()));
        return completed;
    }
}
