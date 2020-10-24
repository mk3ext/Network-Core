package me.mk3ext.dev.core.moderation;

import me.mk3ext.dev.core.rank.RankGroupsUtil;
import me.mk3ext.dev.core.util.Common;
import me.mk3ext.dev.core.profiles.Profile;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Clear implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        } else {
            Player p = (Player) sender;
            Profile prof = new Profile(p);

            if (!(RankGroupsUtil.isManagement(prof.getRank()))) {
                prof.sendMessage(Common.NoPermsAdmin);
            } else {
                if (args.length == 0) {
                    p.getInventory().clear();
                    prof.sendMessage(Common.CHATprefix + " &7Your inventory was cleared.");
                } else {
                    try {
                        Player target = Bukkit.getPlayer(args[0]);
                        target.getInventory().clear();
                        prof.sendMessage(Common.CHATprefix + " &7You cleared &6" + target.getName() + "'s &7inventory.");
                    } catch (Exception e) {
                        prof.sendMessage(Common.CHATprefix + " &7That player is not online.");
                    }
                }
            }
        }

        return true;
    }
}
