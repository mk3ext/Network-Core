package me.mk3ext.dev.core.support;

import me.mk3ext.dev.core.currency.Bytes;
import me.mk3ext.dev.core.rank.RankGroupsUtil;
import me.mk3ext.dev.core.util.Common;
import me.mk3ext.dev.core.profiles.Profile;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ByteCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            if (args[0].equals("add")) {
                Integer i = Integer.parseInt(args[2]);
                OfflinePlayer targetProf = Bukkit.getOfflinePlayer(args[1]);
                String target = targetProf.getUniqueId().toString();
                Bytes.addBytes(target, i);
                sender.sendMessage(Common.BYTEprefix + " &d" + targetProf.getName() + "&7 now has &d" + Bytes.getBytes(target) + " bytes.");
            }
            return true;
        } else {
            Player p = (Player) sender;
            Profile prof = new Profile(p);

            if (!(RankGroupsUtil.isManagement(prof.getRank()))) {
                prof.sendMessage(Common.NoPermsAdmin);
            } else {
                if (args.length == 2) {
                    if (args[0].equals("get")) {
                        Player targetProf = (Player) Bukkit.getOfflinePlayer(args[1]);
                        String target = targetProf.getUniqueId().toString();
                        if (Bytes.checkifhasBytes(target)) {
                            prof.sendMessage(Common.BYTEprefix + " &d" + targetProf.getName() + "&7 has &d" + Bytes.getBytes(target) + " bytes.");
                        } else {
                            prof.sendMessage(Common.BYTEprefix + " &d" + targetProf.getName() + "&7 has &d0 bytes.");
                        }
                    } else {
                        prof.sendMessage(Common.BYTEprefix + " &7Listing commands...");
                        prof.sendMessage(Common.BYTEprefix + " &d/bytes get <player> &7- Gets a player's bytes.");
                        prof.sendMessage(Common.BYTEprefix + " &d/bytes set <player> <amount> &7- Sets a player's bytes.");
                        prof.sendMessage(Common.BYTEprefix + " &d/bytes add <player> <amount> &7- Adds an amount of bytes.");
                        prof.sendMessage(Common.BYTEprefix + " &d/bytes remove <player> <amount> &7- Removes an amount of bytes.");
                    }
                } else if (args.length == 3) {
                    if (args[0].equals("set")) {
                        try {
                            Integer i = Integer.parseInt(args[2]);
                            Player targetProf = (Player) Bukkit.getOfflinePlayer(args[1]);
                            String target = targetProf.getUniqueId().toString();
                            Bytes.setBytes(target, i);
                            prof.sendMessage(Common.BYTEprefix + " &d" + targetProf.getName() + "&7 now has &d" + Bytes.getBytes(target) + " bytes.");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (args[0].equals("add")) {
                        try {
                            Integer i = Integer.parseInt(args[2]);
                            Player targetProf = (Player) Bukkit.getOfflinePlayer(args[1]);
                            String target = targetProf.getUniqueId().toString();
                            Bytes.addBytes(target, i);
                            prof.sendMessage(Common.BYTEprefix + " &d" + targetProf.getName() + "&7 now has &d" + Bytes.getBytes(target) + " bytes.");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (args[0].equals("remove")) {
                        try {
                            Integer i = Integer.parseInt(args[2]);
                            Player targetProf = (Player) Bukkit.getOfflinePlayer(args[1]);
                            String target = targetProf.getUniqueId().toString();
                            Bytes.removeBytes(target, i);
                            prof.sendMessage(Common.BYTEprefix + " &d" + targetProf.getName() + "&7 now has &d" + Bytes.getBytes(target) + " bytes.");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        prof.sendMessage(Common.BYTEprefix + " &7Listing commands...");
                        prof.sendMessage(Common.BYTEprefix + " &d/bytes get <player> &7- Gets a player's bytes.");
                        prof.sendMessage(Common.BYTEprefix + " &d/bytes set <player> <amount> &7- Sets a player's bytes.");
                        prof.sendMessage(Common.BYTEprefix + " &d/bytes add <player> <amount> &7- Adds an amount of bytes.");
                        prof.sendMessage(Common.BYTEprefix + " &d/bytes remove <player> <amount> &7- Removes an amount of bytes.");
                    }
                } else {
                    prof.sendMessage(Common.BYTEprefix + " &7Listing commands...");
                    prof.sendMessage(Common.BYTEprefix + " &d/bytes get <player> &7- Gets a player's bytes.");
                    prof.sendMessage(Common.BYTEprefix + " &d/bytes set <player> <amount> &7- Sets a player's bytes.");
                    prof.sendMessage(Common.BYTEprefix + " &d/bytes add <player> <amount> &7- Adds an amount of bytes.");
                    prof.sendMessage(Common.BYTEprefix + " &d/bytes remove <player> <amount> &7- Removes an amount of bytes.");
                }
            }
        }
        return true;
    }
}
