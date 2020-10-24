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

public class ShoutCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        } else {
            Player p = (Player) sender;
            Profile prof = new Profile(p);

            if (!(RankGroupsUtil.isModAbove(prof.getRank()))) {
                prof.sendMessage(Common.NoPermsSrMOD);
            } else {
                if (args.length < 1) {
                    prof.sendMessage(Common.SHOUTprefix + " &d/shout <message> &7- Broadcasts a message in chat to your current server.");
                    return false;
                }

                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < args.length; i++) {
                    if (i > 0) sb.append(" ");
                    sb.append(args[i]);
                }

                Bukkit.getServer().broadcastMessage(UtilColour.format(Common.SHOUTmsg)
                        .replace("%player%", prof.getName())
                        .replace("%message%", sb.toString()));
            }
        }

        return true;
    }
}
