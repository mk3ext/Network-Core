package me.mk3ext.dev.core.reports;

import me.mk3ext.dev.core.enums.CoreCommands;
import me.mk3ext.dev.core.util.Common;
import me.mk3ext.dev.core.profiles.Profile;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReportCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }

        Profile prof = new Profile((Player)sender);
        if (args.length < 2) {
            prof.sendMessage(Common.REPORTprefix + " &6" + CoreCommands.REPORT.getUsage());
            return true;
        }

        String uuid = Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString();
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            if (i > 1) sb.append(" ");
            sb.append(args[i]);
        }

        ReportsManagement.newReport(uuid, prof.getUuid(), sb.toString());
        prof.sendMessage(Common.REPORTprefix + " &7You filed a report against &6" + Bukkit.getOfflinePlayer(args[0]).getName() + "&7.");

        return true;
    }
}
