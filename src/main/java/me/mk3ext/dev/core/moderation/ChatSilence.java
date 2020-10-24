package me.mk3ext.dev.core.moderation;

import me.mk3ext.dev.core.rank.RankGroupsUtil;
import me.mk3ext.dev.core.util.Common;
import me.mk3ext.dev.core.util.UtilColour;
import me.mk3ext.dev.core.profiles.Profile;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatSilence implements CommandExecutor {

    public static boolean chatSilence;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        } else {
            Player p = (Player) sender;
            if (RankGroupsUtil.isManagement(new Profile(p).getRank())) {
                if (chatSilence) {
                    chatSilence = false;
                    Bukkit.broadcastMessage(
                            UtilColour.format(Common.CHATprefix + " &7Chat silence has been &cDisabled&7."));
                } else {
                    chatSilence = true;
                    Bukkit.broadcastMessage(UtilColour.format(Common.CHATprefix + " &7Chat silence has been &aEnabled&7."));
                }
            } else {
                p.sendMessage(UtilColour.format(Common.NoPermsAdmin));
            }
        }
        return true;
    }

}
