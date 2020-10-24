package me.mk3ext.dev.core.player;

import me.mk3ext.dev.core.moderation.SpyManager;
import me.mk3ext.dev.core.profiles.Profile;
import me.mk3ext.dev.core.enums.CoreCommands;
import me.mk3ext.dev.core.rank.RankGroupsUtil;
import me.mk3ext.dev.core.util.Common;
import me.mk3ext.dev.core.util.UtilRankFormat;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Message implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }

        Player p = (Player) sender;
        Profile prof = new Profile(p);

        if (args.length < 2) {
            prof.sendMessage("&8&l[&d&lTEST &c&lMessage&8&l] > &d" + CoreCommands.MESSAGE.getUsage());
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                if (i > 1) sb.append(" ");
                sb.append(args[i]);
            }

            String text = sb.toString();
            String playername = args[0];

            if (Bukkit.getPlayer(playername) != null) {
                if (!MPrefsManager.getMessages(Bukkit.getPlayer(playername).getUniqueId().toString())) {
                    prof.sendMessage(Common.MSGprefix + " You cannot send messages to that person.");
                    return true;
                }
                Profile targetProf = new Profile(Bukkit.getPlayer(playername));
                targetProf.sendMessage(Common.IncomingMSG + " &8(" + UtilRankFormat.formatName(prof.getRank()) + "&8) " + prof.getRank().getColour() + prof.getName() + "&r&8 -> &b" + text);

                if (MPrefsManager.getSounds(Bukkit.getPlayer(playername).getUniqueId().toString())) {
                    targetProf.getOnlinePlayerObject().playSound(targetProf.getOnlinePlayerObject().getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 7F, 4F);
                }
                prof.sendMessage(Common.OutgoingMSG + " &8(" + UtilRankFormat.formatName(targetProf.getRank()) + "&8) " + targetProf.getRank().getColour() + targetProf.getName() + "&r&8 -> &d" + text);

                HashMap reply = ReplyManager.getInstance().playerReply;

                if (reply.containsKey(prof.getOnlinePlayerObject())) {
                    reply.replace(prof.getOnlinePlayerObject(), targetProf.getOnlinePlayerObject());
                } else {
                    reply.put(prof.getOnlinePlayerObject(), targetProf.getOnlinePlayerObject());
                }

                if (reply.containsKey(targetProf.getOnlinePlayerObject())) {
                    reply.replace(targetProf.getOnlinePlayerObject(), prof.getOnlinePlayerObject());
                } else {
                    reply.put(targetProf.getOnlinePlayerObject(), prof.getOnlinePlayerObject());
                }

                for (Player pl : Bukkit.getOnlinePlayers()) {
                    Profile profpl = new Profile(pl);
                    if (RankGroupsUtil.isStaff(profpl.getRank())) {
                        if (SpyManager.getInstance().messageSpyEnabled.contains(pl)) {
                            if (pl != p && pl != targetProf.getOnlinePlayerObject()) {
                                profpl.sendMessage(Common.MSGspy + " &7" + prof.getRank().getColour() + prof.getName() + "&e to &r" + targetProf.getRank().getColour() + targetProf.getName() + " &8-> &7" + text);
                            }
                        }
                    }
                }
            } else {
                prof.sendMessage(Common.MSGprefix + " &7That player is not online.");
            }
        }

        return true;
    }
}
