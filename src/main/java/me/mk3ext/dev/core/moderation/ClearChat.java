package me.mk3ext.dev.core.moderation;

import me.mk3ext.dev.core.rank.RankGroupsUtil;
import me.mk3ext.dev.core.util.Common;
import me.mk3ext.dev.core.util.UtilColour;
import me.mk3ext.dev.core.util.UtilRankFormat;
import me.mk3ext.dev.core.profiles.Profile;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearChat implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            //todo console
            return true;
        }
        Player p = (Player) sender;
        Profile prof = new Profile(p);
        if (RankGroupsUtil.isModAbove(prof.getRank())) {
            for (int i = 0; i < 200; i++) {
                Bukkit.broadcastMessage(" ");
            }
            Bukkit.broadcastMessage(UtilColour.format(Common.CHATprefix + " &7The chat has been cleared by " + UtilRankFormat.getRankPrefix(prof.getRank()) + " " + p.getName() + "&7."));
        } else {
            prof.sendMessage(Common.NoPermsSrMOD);
        }

        return true;
    }
}
