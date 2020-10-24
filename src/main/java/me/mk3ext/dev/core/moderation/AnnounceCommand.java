package me.mk3ext.dev.core.moderation;

import me.mk3ext.dev.core.Main;
import me.mk3ext.dev.core.rank.RankGroupsUtil;
import me.mk3ext.dev.core.util.Common;
import me.mk3ext.dev.core.util.Decor.Title;
import me.mk3ext.dev.core.util.UtilColour;
import me.mk3ext.dev.core.profiles.Profile;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AnnounceCommand implements CommandExecutor {

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
                if (args.length < 2) {
                    prof.sendMessage("&8&l[&d&lTEST &c&lAnnounce&8&l] &d/announce global <message> &7- Announces a message to the entire network.");
                    prof.sendMessage("&8&l[&d&lTEST &c&lAnnounce&8&l] &d/announce local <message> &7- Announces a message to your current server.");
                    return false;
                }

                StringBuilder sb = new StringBuilder();
                for (int i = 1; i < args.length; i++) {
                    if (i > 1) sb.append(" ");
                    sb.append(args[i]);
                }

                if (args[0].equalsIgnoreCase("global")) {
                    Main.redis.getTopic("CORE:announcements").publish(sb.toString());
                } else if (args[0].equalsIgnoreCase("local")) {
                    Bukkit.getServer().broadcastMessage(UtilColour.format("&8(" + Common.AnnouncementLOCAL + "&8) &7" + sb.toString()));
                    Bukkit.getOnlinePlayers().forEach(player -> {

                        Title title = new Title(Common.AnnouncementLOCAL, "§7" + sb.toString(), 7, 50, 7);

                        title.send(player);

                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 7F, 5F);
                    });
                } else {
                    prof.sendMessage("&8&l[&d&lTEST &c&lAnnounce&8&l] &d/announce global <message> &7- Announces a message to the entire network.");
                    prof.sendMessage("&8&l[&d&lTEST &c&lAnnounce&8&l] &d/announce local <message> &7- Announces a message to your current server.");
                    return false;
                }
            }
        }

        return true;
    }
}
