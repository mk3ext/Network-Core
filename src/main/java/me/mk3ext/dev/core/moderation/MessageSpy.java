package me.mk3ext.dev.core.moderation;

import me.mk3ext.dev.core.rank.RankGroupsUtil;
import me.mk3ext.dev.core.util.Common;
import me.mk3ext.dev.core.profiles.Profile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageSpy implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }

        Player p = (Player) sender;
        Profile prof = new Profile(p);
        if (RankGroupsUtil.isStaff(prof.getRank())) {

            if (SpyManager.getInstance().messageSpyEnabled.contains(p)) {
                SpyManager.getInstance().messageSpyEnabled.remove(p);
                prof.sendMessage(Common.CHATprefix + " &7You &cdisabled &7message spy.");
            } else {
                SpyManager.getInstance().messageSpyEnabled.add(p);
                prof.sendMessage(Common.CHATprefix + " &7You &aenabled &7message spy.");
            }

        } else {
            prof.sendMessage(Common.NoPermsHelper);
        }

        return true;
    }
}
