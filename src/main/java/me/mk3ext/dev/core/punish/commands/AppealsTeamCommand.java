package me.mk3ext.dev.core.punish.commands;

import me.mk3ext.dev.core.Main;
import me.mk3ext.dev.core.database.createAppeals;
import me.mk3ext.dev.core.rank.RankGroupsUtil;
import me.mk3ext.dev.core.rank.RankMethod;
import me.mk3ext.dev.core.util.Common;
import me.mk3ext.dev.core.util.UtilColour;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class AppealsTeamCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }
        Player p = (Player) sender;
        if (!(RankGroupsUtil.isManagement(RankMethod.getRank(p.getUniqueId().toString())))) {
            p.sendMessage(UtilColour.format(Common.NoPermsAdmin));
        } else {
            if (args.length != 2) {
                p.sendMessage(UtilColour.format(Common.AppealTEAMprefix + " &d/appeals <add/remove> <name>"));
            } else {
                String uuid = Bukkit.getOfflinePlayer(args[1]).getUniqueId().toString();
                if (args[0].equalsIgnoreCase("add")) {
                    new createAppeals(uuid);
                    p.sendMessage(UtilColour.format(Common.AppealTEAMprefix + " &7You added &d"+Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName()+" &7to the appeals team database."));
                } else if (args[0].equalsIgnoreCase("remove")) {
                    Main.getInstance().mongoHandler.deleteAppleDoc(uuid);
                    p.sendMessage(UtilColour.format(Common.AppealTEAMprefix + " &7You removed &d"+Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName()+" &7from the appeals team database."));

                }
            }
        }

        return true;
    }
}
