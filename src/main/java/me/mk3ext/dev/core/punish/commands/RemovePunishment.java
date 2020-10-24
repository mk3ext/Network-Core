package me.mk3ext.dev.core.punish.commands;

import me.mk3ext.dev.core.rank.RankGroupsUtil;
import me.mk3ext.dev.core.rank.RankMethod;
import me.mk3ext.dev.core.util.Common;
import me.mk3ext.dev.core.util.UtilColour;
import me.mk3ext.dev.core.punish.DatabaseHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemovePunishment implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player p = (Player) sender;
        if (!(RankGroupsUtil.isManagement(RankMethod.getRank(p.getUniqueId().toString())))) {
            p.sendMessage(UtilColour.format(Common.NoPermsAdmin));
        } else {
            if (args.length != 1) {
                p.sendMessage(UtilColour.format(Common.PUNISHprefix + " &d/removepunishment <id>"));
            } else {
                DatabaseHandler.remove(args[0]);
                p.sendMessage(UtilColour.format(Common.PUNISHprefix + " &7You fully removed &dPunishment #" + args[0] + "&7."));
            }
        }
        return true;

    }
}
