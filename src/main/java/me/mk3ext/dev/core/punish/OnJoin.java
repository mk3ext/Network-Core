package me.mk3ext.dev.core.punish;

import me.mk3ext.dev.core.Main;
import me.mk3ext.dev.core.punish.utils.Format;
import me.mk3ext.dev.core.util.Common;
import me.mk3ext.dev.core.util.UtilColour;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OnJoin implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLogin(PlayerLoginEvent e) {
        String UUID = e.getPlayer().getUniqueId().toString();
        if (DatabaseHandler.isPerm(UUID,"BAN").getKey()) {
            String id = DatabaseHandler.isPerm(UUID,"BAN").getValue();
            String reason = DatabaseHandler.getReason(id);
            String datesent = DatabaseHandler.getLongSent(id);
            String formattedsent = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(Long.parseLong(datesent)));
            String dateexpire = DatabaseHandler.getLongExpire(id);
            e.setKickMessage(UtilColour.format(Common.PERMban.replace("%reason%", reason)
                    .replace("%id%", id).replace("%formatSent%", formattedsent)));
            e.setResult(PlayerLoginEvent.Result.KICK_BANNED);
            if (DatabaseHandler.getSeen(id).equals("false")) {
                DatabaseHandler.setSeenTrue(id);
            }
        } else if (DatabaseHandler.isTemp(UUID,"TEMPBAN").getKey()) {
            String id = DatabaseHandler.isTemp(UUID,"TEMPBAN").getValue();
            String reason = DatabaseHandler.getReason(id);
            String datesent = DatabaseHandler.getLongSent(id);
            String formattedsent = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(Long.parseLong(datesent)));
            String dateexpire = DatabaseHandler.getLongExpire(id);
            String formattedexpire = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(Long.parseLong(dateexpire)));
            e.setKickMessage(UtilColour.format(Common.TEMPban.replace("%reason%", reason)
                    .replace("%id%", id).replace("%formatSent%", formattedsent).replace("%formatExpire%", formattedexpire)));
            e.setResult(PlayerLoginEvent.Result.KICK_BANNED);
            if (DatabaseHandler.getSeen(id).equals("false")) {
                DatabaseHandler.setSeenTrue(id);
            }
        }
        for (String id : DatabaseHandler.getAllIdsForPlayer(UUID)) {
            if (DatabaseHandler.getSeen(id).equals("false")) {
                if (DatabaseHandler.getForcedRemoved(id).equals("false")) {
                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            DatabaseHandler.setSeenTrue(id);
                            switch (DatabaseHandler.getType(id)) {
                                case "WARN":
                                    e.getPlayer().sendMessage(UtilColour.format(Common.PUNISHprefix + " &7You were recently warned for &d" + DatabaseHandler.getReason(id) + "&7. (ID - " + id + ")"));
                                    break;
                                case "TEMPMUTE":
                                    String expirylong = DatabaseHandler.getLongExpire(id);
                                    String durleft = Format.MakeStr((Long.parseLong(expirylong) - System.currentTimeMillis()), 0);
                                    e.getPlayer().sendMessage(UtilColour.format(Common.PUNISHprefix + " &7You were recently muted for &d" + DatabaseHandler.getReason(id) + "&7. This will expire in &d" + durleft + "&7. (ID - " + id + ")"));
                                    break;
                                case "MUTE":
                                    e.getPlayer().sendMessage(UtilColour.format(Common.PUNISHprefix + " &7You were recently permanently muted for &d" + DatabaseHandler.getReason(id) + "&7. (ID - " + id + ")"));
                                    break;
                            }
                        }
                    }.runTaskLater(Main.getInstance(), 3);
                }
            }
        }
    }

}
