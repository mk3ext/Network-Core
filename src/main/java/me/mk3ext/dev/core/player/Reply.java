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

public class Reply implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }

        Player p = (Player) sender;
        Profile prof = new Profile(p);

        if (args.length < 1) {
            prof.sendMessage(Common.ReplyMSG + " &d" + CoreCommands.REPLY.getUsage());
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                if (i > 0) sb.append(" ");
                sb.append(args[i]);
            }

            String text = sb.toString();
            Player replyTo;


            if (ReplyManager.getInstance().playerReply.get(prof.getOnlinePlayerObject()) == null) {
                prof.sendMessage(Common.ReplyMSG + " &7You have nobody to reply to.");
                return true;
            } else {
                replyTo = ReplyManager.getInstance().playerReply.get(prof.getOnlinePlayerObject());
            }

            if (replyTo.isOnline()) {
                if (!MPrefsManager.getMessages(replyTo.getUniqueId().toString())) {
                    prof.sendMessage(Common.MSGprefix + " You cannot send messages to that person.");
                    return true;
                }
                Profile targetProf = new Profile(replyTo);
                targetProf.sendMessage(Common.IncomingMSG + " &8(" + UtilRankFormat.formatName(prof.getRank()) + "&8) " + prof.getRank().getColour() + prof.getName() + "&r&8 -> &b" + text);

                if (MPrefsManager.getSounds(targetProf.getOnlinePlayerObject().getUniqueId().toString())) {
                    targetProf.getOnlinePlayerObject().playSound(targetProf.getOnlinePlayerObject().getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 7F, 4F);
                }
                prof.sendMessage(Common.OutgoingMSG + " &8(" + UtilRankFormat.formatName(targetProf.getRank()) + "&8) " + targetProf.getRank().getColour() + targetProf.getName() + "&r&8 -> &d" + text);

                for (Player pl : Bukkit.getOnlinePlayers()) {
                    Profile profpl = new Profile(pl);
                    if (RankGroupsUtil.isStaff(profpl.getRank())) {
                        if (SpyManager.getInstance().messageSpyEnabled.contains(pl)) {
                            if (pl != p && pl != replyTo) {
                                profpl.sendMessage(Common.MSGspy + " &7" + prof.getRank().getColour() + prof.getName() + "&e to &r" + targetProf.getRank().getColour() + targetProf.getName() + " &8-> &7" + text);
                            }
                        }
                    }
                }

            } else {
                prof.sendMessage(Common.ReplyMSG + " &7That player is no longer online.");
            }
        }

        return true;
    }
}
