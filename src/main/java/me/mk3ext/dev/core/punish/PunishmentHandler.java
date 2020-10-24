package me.mk3ext.dev.core.punish;

import me.mk3ext.dev.core.Main;
import me.mk3ext.dev.core.profiles.Profile;
import me.mk3ext.dev.core.punish.types.PunishBan;
import me.mk3ext.dev.core.punish.utils.BungeeListener;
import me.mk3ext.dev.core.punish.utils.Format;
import me.mk3ext.dev.core.punish.utils.FormatNumber;
import me.mk3ext.dev.core.rank.RankGroupsUtil;
import me.mk3ext.dev.core.util.Common;
import me.mk3ext.dev.core.util.UtilColour;
import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class PunishmentHandler implements Listener {

    public static void handle(String uuid, Player staff, String reason, int hours, Punish.Punishments type) {
        staff.getOpenInventory().close();
        String formatted = FormatNumber.format(hours);
        String targetName = Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName();

        try {
            long expire = System.currentTimeMillis() + TimeUnit.HOURS.toMillis(hours);
            if (hours == -1) {
                expire = -1L;
            }

            String date = String.valueOf(expire);

            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("PlayerList");
            out.writeUTF("ALL");

            Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);

            player.sendPluginMessage(Main.getInstance(), "BungeeCord", out.toByteArray());
            //chat

            long finalExpire = expire;
            long finalExpire1 = expire;
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (Arrays.asList(BungeeListener.requests.get(1)).contains(targetName)) {
                        DatabaseHandler.writeInto(uuid, staff.getUniqueId().toString(), reason, String.valueOf(System.currentTimeMillis()), date, type.toString(), "true");

                        String[] playerNames = BungeeListener.requests.get(1);

                        for (String name : playerNames) {
                            OfflinePlayer pl = Bukkit.getOfflinePlayer(name);
                            String uuid1 = pl.getUniqueId().toString();

                            if (RankGroupsUtil.isStaff(new Profile(pl).getRank())) {
                                ByteArrayDataOutput out1 = ByteStreams.newDataOutput();
                                out1.writeUTF("Message");
                                out1.writeUTF(name);
                                String message = "";

                                switch (type) {
                                    case BAN:
                                        message = UtilColour.format(Common.PUNISHprefix + " &d" + staff.getName() + "&7 banned &d" + targetName + " &7for &d" + FormatNumber.format(hours) + "&7.");
                                        break;
                                    case TEMPBAN:
                                        message = UtilColour.format(Common.PUNISHprefix + " &d" + staff.getName() + "&7 banned &d" + targetName + " &7for &d" + FormatNumber.format(hours) + "&7.");
                                        break;
                                    case MUTE:
                                        message = UtilColour.format(Common.PUNISHprefix + " &d" + staff.getName() + "&7 muted &d" + targetName + " &7for &d" + FormatNumber.format(hours) + "&7.");
                                        break;
                                    case TEMPMUTE:
                                        message = UtilColour.format(Common.PUNISHprefix + " &d" + staff.getName() + "&7 muted &d" + targetName + " &7for &d" + FormatNumber.format(hours) + "&7.");
                                        break;
                                    case WARN:
                                        message = UtilColour.format(Common.PUNISHprefix + " &d" + staff.getName() + "&7 warned &d" + targetName + " &7for &d" + reason + "&7.");
                                        break;
                                }

                                out1.writeUTF(message);

                                Player player1 = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);

                                player1.sendPluginMessage(Main.getInstance(), "BungeeCord", out1.toByteArray());
                            }
                        }
                        String id = DatabaseHandler.getID(uuid, date, reason);
                        switch (type) {
                            case BAN:
                                PunishBan.ban(id);
                                break;
                            case TEMPBAN:
                                PunishBan.ban(id);
                                break;
                            case MUTE:
                                ByteArrayDataOutput out2 = ByteStreams.newDataOutput();
                                out2.writeUTF("Message");
                                out2.writeUTF(targetName);
                                String message = UtilColour.format(Common.PUNISHprefix + " &7You were permanently muted for &d" + reason + "&7. (ID - " + id + ")");
                                out2.writeUTF(message);
                                Player player2 = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
                                player2.sendPluginMessage(Main.getInstance(), "BungeeCord", out2.toByteArray());
                                break;
                            case TEMPMUTE:
                                long duration = finalExpire - System.currentTimeMillis();
                                ByteArrayDataOutput out3 = ByteStreams.newDataOutput();
                                out3.writeUTF("Message");
                                out3.writeUTF(targetName);
                                String message3 = UtilColour.format(Common.PUNISHprefix + " &7You were temporarily muted for &d" + reason + "&7. This mute will last &d" + Format.MakeStr(duration, 0) + "&7. (ID - " + id + ")");
                                out3.writeUTF(message3);
                                Player player3 = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
                                player3.sendPluginMessage(Main.getInstance(), "BungeeCord", out3.toByteArray());
                                break;
                            case WARN:
                                ByteArrayDataOutput out4 = ByteStreams.newDataOutput();
                                out4.writeUTF("Message");
                                out4.writeUTF(targetName);
                                String message4 = UtilColour.format(Common.PUNISHprefix + " &7You were warned for &d" + reason + "&7. (ID - " + id + ")");
                                out4.writeUTF(message4);
                                Player player4 = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
                                player4.sendPluginMessage(Main.getInstance(), "BungeeCord", out4.toByteArray());
                                break;
                        }
                    } else {
                        DatabaseHandler.writeInto(uuid, staff.getUniqueId().toString(), reason, String.valueOf(System.currentTimeMillis()), date, type.toString(), "false");

                        String[] playerNames = BungeeListener.requests.get(1);

                        for (String name : playerNames) {
                            OfflinePlayer pl = Bukkit.getOfflinePlayer(name);
                            String uuid2 = pl.getUniqueId().toString();

                            if (RankGroupsUtil.isStaff(new Profile(pl).getRank())) {
                                ByteArrayDataOutput out1 = ByteStreams.newDataOutput();
                                out1.writeUTF("Message");
                                out1.writeUTF(name);
                                String message = "";

                                switch (type) {
                                    case BAN:
                                    case TEMPBAN:
                                        message = UtilColour.format(Common.PUNISHprefix + " &d" + staff.getName() + "&7 banned &d" + targetName + " &7for &d" + FormatNumber.format(hours) + "&7.");
                                        break;
                                    case MUTE:
                                    case TEMPMUTE:
                                        message = UtilColour.format(Common.PUNISHprefix + " &d" + staff.getName() + "&7 muted &d" + targetName + " &7for &d" + FormatNumber.format(hours) + "&7.");
                                        break;
                                    case WARN:
                                        message = UtilColour.format(Common.PUNISHprefix + " &d" + staff.getName() + "&7 warned &d" + targetName + " &7for &d" + reason + "&7.");
                                        break;
                                }

                                out1.writeUTF(message);

                                Player player1 = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);

                                player1.sendPluginMessage(Main.getInstance(), "BungeeCord", out1.toByteArray());
                            }

                            String id = DatabaseHandler.getID(uuid, date, reason);
                            switch (type) {
                                case BAN:
                                case TEMPBAN:
                                    PunishBan.ban(id);
                                    break;
                            }

                        }
                    }
                }
            }.runTaskLater(Main.getInstance(), 2L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
