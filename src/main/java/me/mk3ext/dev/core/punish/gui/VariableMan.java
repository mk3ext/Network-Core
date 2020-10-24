package me.mk3ext.dev.core.punish.gui;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class VariableMan {

    public static HashMap<Player, OfflinePlayer> stafftarget = new HashMap<>();
    public static HashMap<Player, String> staffreason = new HashMap<>();

    public static void addBadPlayer(Player staff, OfflinePlayer target) {
        if (stafftarget.containsKey(staff)) {
            stafftarget.replace(staff, target);
        } else {
            stafftarget.put(staff, target);
        }
    }

    public static void addReason(Player staff, String reason) {
        if (staffreason.containsKey(staff)) {
            staffreason.replace(staff, reason);
        } else {
            staffreason.put(staff, reason);
        }
    }
}
