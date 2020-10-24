package me.mk3ext.dev.core.punish.commands;

import me.mk3ext.dev.core.profiles.Profile;
import me.mk3ext.dev.core.rank.Rank;
import me.mk3ext.dev.core.rank.RankGroupsUtil;
import me.mk3ext.dev.core.rank.RankMethod;
import me.mk3ext.dev.core.util.Common;
import me.mk3ext.dev.core.util.UtilColour;
import me.mk3ext.dev.core.punish.gui.PunishCustomGUI;
import me.mk3ext.dev.core.punish.gui.PunishCustomGUIDev;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PunishCustomCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }
        Player p = (Player) sender;
        if (!(RankGroupsUtil.isManagement(RankMethod.getRank(p.getUniqueId().toString())))) {
            p.sendMessage(UtilColour.format(Common.NoPermsAdmin));
        } else {
            if (args.length < 2) {
                p.sendMessage(UtilColour.format(Common.PUNISHprefix + " &d/punishcustom <player> <reason>"));
            } else {
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

                StringBuilder str = new StringBuilder();
                for (int i = 1; i < args.length; i++) {
                    str.append(args[i]).append(" ");
                }

                if (new Profile(p).getRank().equals(Rank.DEVELOPER)) {
                    PunishCustomGUIDev.getInstance().openGUI(p, target, str.toString());
                } else {
                    PunishCustomGUI.getInstance().openGUI(p, target, str.toString());
                }

            }
        }

        return true;
    }
}
