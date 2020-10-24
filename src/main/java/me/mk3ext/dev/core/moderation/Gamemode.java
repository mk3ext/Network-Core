package me.mk3ext.dev.core.moderation;

import me.mk3ext.dev.core.rank.RankGroupsUtil;
import me.mk3ext.dev.core.util.Common;
import me.mk3ext.dev.core.profiles.Profile;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Gamemode implements CommandExecutor {
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
                    if (p.getGameMode() != GameMode.CREATIVE) {
                        p.setGameMode(GameMode.CREATIVE);
                        prof.sendMessage(Common.GAMEMODEprefix + " &7Your gamemode has been switched to &aCreative&7.");
                    } else {
                        p.setGameMode(GameMode.SURVIVAL);
                        prof.sendMessage(Common.GAMEMODEprefix + " &7Your gamemode has been switched to &cSurvival&7.");
                    }
                } else {
                    try {
                        Player target = Bukkit.getPlayer(args[0]);
                        if (target.getGameMode() != GameMode.CREATIVE) {
                            target.setGameMode(GameMode.CREATIVE);
                            prof.sendMessage(Common.GAMEMODEprefix + " &7You switched &d" + target.getName() + "'s &7gamemode to &aCreative&7.");
                        } else {
                            target.setGameMode(GameMode.SURVIVAL);
                            prof.sendMessage(Common.GAMEMODEprefix + " &7You switched &d" + target.getName() + "'s &7gamemode to &cSurvival&7.");
                        }
                    } catch (Exception e) {
                        prof.sendMessage(Common.GAMEMODEprefix + " &7That player is not online.");
                    }
                }
            }
        }

        return true;
    }
}
