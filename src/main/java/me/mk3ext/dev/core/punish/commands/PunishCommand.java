package me.mk3ext.dev.core.punish.commands;

import me.mk3ext.dev.core.profiles.Profile;
import me.mk3ext.dev.core.rank.Rank;
import me.mk3ext.dev.core.rank.RankGroupsUtil;
import me.mk3ext.dev.core.rank.RankMethod;
import me.mk3ext.dev.core.util.Common;
import me.mk3ext.dev.core.util.UtilColour;
import me.mk3ext.dev.core.punish.gui.PunishGUIMainDev;
import me.mk3ext.dev.core.punish.gui.PunishMainGUI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PunishCommand implements CommandExecutor {


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player p = (Player) sender;
        if (!(RankGroupsUtil.isStaff(RankMethod.getRank(p.getUniqueId().toString())))) {
            p.sendMessage(UtilColour.format(Common.NoPermsHelper));
        } else {
            if (args.length != 1) {
                p.sendMessage(UtilColour.format(Common.PUNISHprefix + " &d/punish <player>"));
            } else {
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                p.sendMessage(UtilColour.format(Common.PUNISHprefix + " &7Punishing &e" + target.getName() + "&7..."));
                if (new Profile(p).getRank().equals(Rank.DEVELOPER)) {
                    PunishGUIMainDev.openGUI(p, target);
                } else {
                    PunishMainGUI.openGUI(p, target);
                }

            }
        }

        return true;
    }
}
