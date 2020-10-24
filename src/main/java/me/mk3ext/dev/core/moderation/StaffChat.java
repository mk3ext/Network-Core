package me.mk3ext.dev.core.moderation;

import me.mk3ext.dev.core.Main;
import me.mk3ext.dev.core.rank.RankGroupsUtil;
import me.mk3ext.dev.core.util.Common;
import me.mk3ext.dev.core.util.UtilColour;
import me.mk3ext.dev.core.profiles.Profile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class StaffChat implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return false;
        } else {
            Player p = (Player) sender;
            Profile prof = new Profile(p);

            if (!(RankGroupsUtil.isStaff(prof.getRank()))) {
                prof.sendMessage(Common.NoPermsHelper);
                return false;
            }

            if (args.length < 1) {
                prof.sendMessage(Common.CHATprefix + " &d/staffchat <message>");
            } else {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < args.length; i++) {
                    if (i > 0) sb.append(" ");
                    sb.append(args[i]);
                }


//                ByteArrayDataOutput out = ByteStreams.newDataOutput();
//                out.writeUTF("GetServer");
//                p.sendPluginMessage(Core.getInstance(), "BungeeCord", out.toByteArray());

                new BukkitRunnable() {
                    public void run() {
                        Main.redis.getTopic("CORE:staff").publish(UtilColour.format(Common.STAFFchat + " &b" + prof.getName() + " &8[&d"+ Main.getInstance().getConfig().getString("server-name") +"&8] -> &7" + sb.toString()));
                    }
                }.runTaskLater(Main.getInstance(), 2L);
            }
        }

        return true;
    }
}
