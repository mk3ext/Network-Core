package me.mk3ext.dev.core.player;

import me.mk3ext.dev.core.profiles.Profile;
import me.mk3ext.dev.core.rank.RankGroupsUtil;
import me.mk3ext.dev.core.util.Common;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Teleport implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }
        Player p = (Player) sender;
        Profile prof = new Profile(p);

        if (!(RankGroupsUtil.isStaff(prof.getRank()))) {
            prof.sendMessage(Common.NoPermsHelper);
            return true;
        }
        if (args.length < 1) {
            listUsages(prof);
        } else if (args.length == 1) {
            if (Bukkit.getPlayer(args[0]) == null) {
                prof.sendMessage(Common.TPprefix + " &7That player is not online.");
            } else {
                prof.getOnlinePlayerObject().teleport(Bukkit.getPlayer(args[0]));
                prof.sendMessage(Common.TPprefix + " &7You teleported to &d" + Bukkit.getPlayer(args[0]).getName() + "&7.");
            }
        } else if (args.length == 2) {
            if (args[0].equals("here")) {
                if (args[1].equals("all")) {
                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        pl.teleport(prof.getOnlinePlayerObject());
                    }
                    prof.sendMessage(Common.TPprefix + " &7You teleported &deveryone &7to your location.");
                    return true;
                } else {
                    if (Bukkit.getPlayer(args[1]) == null) {
                        prof.sendMessage(Common.TPprefix + " &7That player is not online.");
                    } else {
                        Bukkit.getPlayer(args[1]).teleport(prof.getOnlinePlayerObject());
                        prof.sendMessage(Common.TPprefix + " &7You teleported &d" + Bukkit.getPlayer(args[1]).getName() + "&7 to yourself.");
                    }
                    return true;
                }
            } else {
                listUsages(prof);
            }
        } else {
            listUsages(prof);
        }
        return true;
    }

    public void listUsages(Profile p) {
        String prefix = Common.TPprefix + " &7";
        p.sendMessage(prefix + "Listing usages...");
        p.sendMessage(prefix + "&d/tp <player> &7- Teleports you to a player.");
        p.sendMessage(prefix + "&d/tp here all &7- Teleports all players to yourself.");
        p.sendMessage(prefix + "&d/tp here <player> &7- Teleports a player to you.");
    }
}
