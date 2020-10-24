package me.mk3ext.dev.core.player;

import me.mk3ext.dev.core.profiles.Profile;
import me.mk3ext.dev.core.rank.RankGroupsUtil;
import me.mk3ext.dev.core.util.Common;
import me.mk3ext.dev.core.util.UtilColour;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageToggle implements CommandExecutor {

        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        } else {
            Player p = (Player) sender;
            Profile prof = new Profile(p);
            String uuid = p.getUniqueId().toString();
            if (!RankGroupsUtil.isDonatorAbove(prof.getRank())){
                prof.sendMessage(Common.NoPermsDonator);
                return true;
            }else {
                if (MPrefsManager.getMessages(uuid)) {
                    MPrefsManager.setMessagesOff(uuid);
                    p.sendMessage(UtilColour.format(Common.MSGprefix + " &7You &cDisabled&7 incoming messages."));
                } else {
                    MPrefsManager.setMessagesOn(uuid);
                    p.sendMessage(UtilColour.format(Common.MSGprefix + " &7You &aEnabled&7 incoming messages."));
                }
            }
        } return true;
    }
    }

