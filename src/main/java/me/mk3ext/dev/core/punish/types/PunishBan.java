package me.mk3ext.dev.core.punish.types;

import me.mk3ext.dev.core.Main;
import me.mk3ext.dev.core.util.Common;
import me.mk3ext.dev.core.util.UtilColour;
import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.mk3ext.dev.core.punish.DatabaseHandler;
import me.mk3ext.dev.core.punish.utils.Format;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class PunishBan implements Listener {

    public static void ban(String id) {
        new BukkitRunnable() {
            @Override
            public void run() {
                System.out.println(id);
                String punisherName = Bukkit.getOfflinePlayer(UUID.fromString(DatabaseHandler.getPunisherUUID(id))).getName();
                String targetName = Bukkit.getOfflinePlayer(UUID.fromString(DatabaseHandler.getTargetUUID(id))).getName();
                String reason = DatabaseHandler.getReason(id);
                String datesent = DatabaseHandler.getLongSent(id);
                String dateexpire = DatabaseHandler.getLongExpire(id);
                String seen = DatabaseHandler.getSeen(id).replace("t", "T").replace("f", "F");

                String formattedsent = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(Long.parseLong(datesent)));
                String formattedexpire = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(Long.parseLong(dateexpire)));

                long duration = Long.parseLong(dateexpire) - Long.parseLong(datesent);
                String format = Format.MakeStr(duration, 0);

                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeUTF("KickPlayer");
                out.writeUTF(targetName);
                String message;                                  // reason    id   formattedSent formattedexpire
                if (dateexpire.equals("-1")) {
                    message = UtilColour.format(Common.PERMban.replace("%reason%", reason)
                            .replace("%id%", id).replace("%formatSent%", formattedsent));
                } else {
                    message = UtilColour.format(Common.TEMPban.replace("%reason%", reason)
                            .replace("%id%", id).replace("%formatSent%", formattedsent).replace("%formatExpire%", formattedexpire));
                }
                out.writeUTF(message);
                Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
                player.sendPluginMessage(Main.getInstance(), "BungeeCord", out.toByteArray());
            }
        }.runTaskLater(Main.getInstance(), 3);

    }
}
