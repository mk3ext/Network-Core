package me.mk3ext.dev.core.util;

import org.bukkit.Bukkit;

public class UtilConsole {

    public static void debug(String str) {
        Bukkit.getConsoleSender().sendMessage(UtilColour.format("&e[Debug] &7" + str));
    }

    public static void send(String str) {
        Bukkit.getConsoleSender().sendMessage(UtilColour.format("&a[Message from CORE] &7" + str));
    }
}
