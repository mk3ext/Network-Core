package me.mk3ext.dev.core.punish;

import me.mk3ext.dev.core.punish.utils.Format;
import me.mk3ext.dev.core.util.Common;
import me.mk3ext.dev.core.util.UtilColour;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class OnChat implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onChat(AsyncPlayerChatEvent e) {
        String uuid = e.getPlayer().getUniqueId().toString();
        if (DatabaseHandler.isPerm(uuid,"MUTED").getKey()) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(UtilColour.format(Common.PUNISHprefix + " &7You're permanently muted for &d" + DatabaseHandler.getReason(DatabaseHandler.isPerm(uuid,"MUTED").getValue()) + "&7. (ID - "+DatabaseHandler.isPerm(uuid,"MUTED").getValue()+")"));
        } else if (DatabaseHandler.isTemp(uuid,"MUTED").getKey()) {
            e.setCancelled(true);
            String id = DatabaseHandler.isTemp(uuid,"MUTED").getValue();
            String reason = DatabaseHandler.getReason(id);
            String expirylong = DatabaseHandler.getLongExpire(id);
            String durleft = Format.MakeStr((Long.parseLong(expirylong) - System.currentTimeMillis()),0);
            e.getPlayer().sendMessage(UtilColour.format(Common.PUNISHprefix + " &7You're temporarily muted for &d" + reason + "&7. This will expire in &d" + durleft + "&7."));
        }
    }
}
