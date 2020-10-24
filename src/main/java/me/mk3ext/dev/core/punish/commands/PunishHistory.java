package me.mk3ext.dev.core.punish.commands;

import me.mk3ext.dev.core.rank.RankGroupsUtil;
import me.mk3ext.dev.core.rank.RankMethod;
import me.mk3ext.dev.core.util.Common;
import me.mk3ext.dev.core.util.UtilColour;
import me.mk3ext.dev.core.punish.gui.HistoryGUIWithoutGoBack;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class PunishHistory implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player p = (Player) sender;
        if (!(RankGroupsUtil.isStaff(RankMethod.getRank(p.getUniqueId().toString())))) {
            p.sendMessage(UtilColour.format(Common.NoPermsHelper));
        } else {
            if (args.length != 1) {
                p.sendMessage(UtilColour.format(Common.PUNISHprefix + " &d/ph <player>"));
            } else {
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                try {
                    HistoryGUIWithoutGoBack.getInstance().openGUI(p, target);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
}
