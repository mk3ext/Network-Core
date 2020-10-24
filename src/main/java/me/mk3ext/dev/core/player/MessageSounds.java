package me.mk3ext.dev.core.player;

import me.mk3ext.dev.core.util.Common;
import me.mk3ext.dev.core.util.UtilColour;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageSounds implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        } else {
            Player p = (Player) sender;
            String uuid = p.getUniqueId().toString();
            if (MPrefsManager.getSounds(uuid)) {
                MPrefsManager.setSoundsOff(uuid);
                p.sendMessage(UtilColour.format(Common.MSGprefix + " &7You &cDisabled&7 message sounds."));
            } else {
                MPrefsManager.setSoundsOn(uuid);
                p.sendMessage(UtilColour.format(Common.MSGprefix + " &7You &aEnabled&7 message sounds."));
            }
        } return true;
    }
}
