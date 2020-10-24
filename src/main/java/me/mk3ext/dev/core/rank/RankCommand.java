package me.mk3ext.dev.core.rank;

import me.mk3ext.dev.core.enums.CoreCommands;
import me.mk3ext.dev.core.util.Common;
import me.mk3ext.dev.core.util.UtilColour;
import me.mk3ext.dev.core.util.UtilRankFormat;
import me.mk3ext.dev.core.profiles.Profile;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class RankCommand implements CommandExecutor {



    private final String[] ranks = {"default", "vip", "vip+", "mvp", "media", "builder", "trainee", "mod", "srmod", "admin", "developer", "manager", "owner"};

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            if (args.length != 2) {
                sender.sendMessage(UtilColour.format(Common.RANKprefix + " &d" + CoreCommands.RANK.getUsage()));
                sender.sendMessage(Common.RANKprefix + Common.AvaliableRanks);
            } else {
                String playername = args[0];
                String rank = args[1];

                ArrayList<String> ranks = new ArrayList<>();
                for (Rank r : Rank.values()) {
                    ranks.add(r.toString());
                }

                Rank rankobj = null;
                if (ranks.contains(rank.toUpperCase())) {
                    rankobj = Rank.valueOf(rank.toUpperCase());
                }
                if(rankobj == null) {
                    sender.sendMessage(Common.RANKprefix + " &dThat rank does not exist.");
                    sender.sendMessage(Common.RANKprefix + Common.AvaliableRanks);
                } else{
                        OfflinePlayer target = Bukkit.getOfflinePlayer(playername);
                        RankMethod.setRank(target.getUniqueId().toString(), rankobj.toString());
                        String rankName = UtilRankFormat.formatName(rankobj);
                        sender.sendMessage(UtilColour.format(Common.RANKprefix + " &7You changed &d" + target.getName() + "'s &7rank to " + rankName + "&7."));
                        if (target.isOnline()) {
                            target.getPlayer().sendMessage(UtilColour.format(Common.RANKprefix + " &7Your rank was updated to &d" + rankName + "&7! Please reconnect!"));
                        }
                    }
                }
            }
         else {
            Player p = (Player) sender;
            Profile prof = new Profile(p);
            if (!(RankGroupsUtil.isManagement(prof.getRank()))) {
                prof.sendMessage(Common.NoPermsAdmin);
            } else {
                if (args.length != 2) {
                    prof.sendMessage(Common.RANKprefix + " &d" + CoreCommands.RANK.getUsage());

                    int index = Arrays.asList(ranks).indexOf(prof.getRank().getFriendlyName());
                    String[] newlist = Arrays.copyOfRange(ranks,0,index);

                    StringBuilder r = new StringBuilder();
                    r.append(" &dAvailable Ranks:\n&7");
                    for (String n : newlist) {
                        r.append(", ").append(n);
                    }


                    prof.sendMessage(Common.RANKprefix + r.toString());
                } else {
                    String playername = args[0];
                    String rank = args[1];

                    int index = Arrays.asList(ranks).indexOf(prof.getRank().getFriendlyName());
                    String[] newlist = Arrays.copyOfRange(ranks,0,index);
                    ArrayList<String> ranks = new ArrayList<>();

                    Collections.addAll(ranks, newlist);
                    StringBuilder r = new StringBuilder();
                    r.append(" &dAvailable Ranks:\n&7");
                    for (String n : newlist) {
                        r.append(", ").append(n);
                    }

                    Rank rankobj = null;
                    if (ranks.contains(rank.toLowerCase())) {
                        rankobj = Rank.valueOf(rank.toUpperCase());
                    } else {
                        prof.sendMessage(Common.RANKprefix + " &cNo permission(s)");
                        return true;
                    }
                    OfflinePlayer target = Bukkit.getOfflinePlayer(playername);
                    RankMethod.setRank(target.getUniqueId().toString(), rankobj.toString());
                    String rankName = UtilRankFormat.formatName(rankobj);
                    prof.sendMessage(Common.RANKprefix + " &7You changed &d" + target.getName() + "'s &7rank to " + rankName + "&7.");
                    if(target.isOnline()){
                        target.getPlayer().sendMessage(UtilColour.format(Common.RANKprefix + " &7Your rank was updated to &d" + rankName));
                    }
                }
            }
        }
        return true;
    }
}
