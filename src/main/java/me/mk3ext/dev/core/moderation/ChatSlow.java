package me.mk3ext.dev.core.moderation;

import me.mk3ext.dev.core.rank.RankGroupsUtil;
import me.mk3ext.dev.core.util.Common;
import me.mk3ext.dev.core.util.UtilColour;
import me.mk3ext.dev.core.profiles.Profile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatSlow implements CommandExecutor {

    private ChatSlowManager plugin = ChatSlowManager.getInstance();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        } else {
            Player p = (Player) sender;
            if (!(RankGroupsUtil.isModAbove(new Profile(p).getRank()))) {
                p.sendMessage(UtilColour.format(Common.NoPermsSrMOD));
            } else {
                if (args.length != 1 && plugin.chatSlow == 0) {
                    p.sendMessage(UtilColour.format(Common.CHATprefix + " &e/chatslow <number>"));
                } else if (args.length == 0 && plugin.chatSlow != 0) {
                    long input = 0;
                    plugin.setChatSlowBackend(input);
                } else {
                    long input = 0;

                    try {
                        input = (long) (Float.parseFloat(args[0]) * 1000);
                    } catch (NumberFormatException e) {
                        p.sendMessage(UtilColour.format(Common.CHATprefix + " &7Your number format is incorrect."));
                        return true;
                    }

                    String returnMessage = (plugin.setChatSlowBackend(input));
                    if (returnMessage != null) {
                        p.sendMessage(UtilColour.format(Common.CHATprefix + " &7" + returnMessage));
                    }
                    return true;
                }

            }
        }
        return true;
    }

}
