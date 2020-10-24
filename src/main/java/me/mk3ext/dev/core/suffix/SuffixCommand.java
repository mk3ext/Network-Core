package me.mk3ext.dev.core.suffix;

import me.mk3ext.dev.core.rank.RankGroupsUtil;
import me.mk3ext.dev.core.util.Common;
import me.mk3ext.dev.core.profiles.Profile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SuffixCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            if (args.length != 3) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Common.SUFFIXprefix + " &d/suffixmanage <player> <add/remove> <suffix>"));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Common.SUFFIXprefix + Common.AvaliableSuffixs));
            } else {
                if (!(args[1].equalsIgnoreCase("add") || args[1].equalsIgnoreCase("remove"))) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Common.SUFFIXprefix + " &d/suffixmanage <player> <add/remove> <suffix>"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Common.SUFFIXprefix + Common.AvaliableSuffixs));
                } else {
                    String target = args[0];
                    String suffix = args[2];

                    String targetUUID = Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString();

                    boolean isIn = false;
                    for (Suffixes s : Suffixes.values()) {
                        if (s.getSuffix().getSuffixName().equalsIgnoreCase(suffix)) {
                            isIn = true;
                            break;
                        }
                    }

                    if (isIn = false) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Common.SUFFIXprefix + " &7That suffix does not exist."));
                        return true;
                    } else {

                        try {
                            Suffixes s = Suffixes.valueOf(suffix);
                        } catch (IllegalArgumentException e) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Common.SUFFIXprefix + " &7That suffix does not exist."));
                            return true;
                        }

                        if (args[1].equalsIgnoreCase("add")) {
                            if (SuffixManager.getSuffixes(targetUUID) != null) {
                                if (SuffixManager.getSuffixes(targetUUID).contains(Suffixes.valueOf(suffix))) {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Common.SUFFIXprefix + " &7That player already has that suffix."));
                                    return true;
                                }
                            }
                            SuffixManager.addSuffix(targetUUID, Suffixes.valueOf(suffix).getSuffix());
                            SuffixManager.setActiveSuffix(targetUUID, Suffixes.valueOf(suffix).getSuffix());
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Common.SUFFIXprefix + " &7You added the suffix."));
                        } else if (args[1].equalsIgnoreCase("remove")) {
                            if (SuffixManager.getSuffixes(targetUUID) != null) {
                                if (SuffixManager.getSuffixes(targetUUID).contains(Suffixes.valueOf(suffix))) {
                                    SuffixManager.removeSuffix(targetUUID, Suffixes.valueOf(suffix).getSuffix());
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Common.SUFFIXprefix + " &7You removed the suffix."));
                                    SuffixManager.setActiveSuffix(targetUUID,null);
                                } else {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Common.SUFFIXprefix + " &7That player does not have that suffix."));
                                }
                            } else {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Common.SUFFIXprefix + " &7That player does not have any suffixes."));
                            }
                        }
                    }
                }
            }
            return true;
        } else {
            Player p = (Player) sender;
            Profile prof = new Profile(p);

            if (!(RankGroupsUtil.isManagement(prof.getRank()))) {
                prof.sendMessage(Common.NoPermsAdmin);
            } else {
                if (args.length != 3) {
                    prof.sendMessage(Common.SUFFIXprefix + " &d/suffixmanage <player> <add/remove> <suffix>");
                    prof.sendMessage(Common.SUFFIXprefix + Common.AvaliableSuffixs);
                } else {
                    if (!(args[1].equalsIgnoreCase("add") || args[1].equalsIgnoreCase("remove"))) {
                        prof.sendMessage(Common.SUFFIXprefix + " &d/suffixmanage <player> <add/remove> <suffix>");
                        prof.sendMessage(Common.SUFFIXprefix + Common.AvaliableSuffixs);
                    } else {
                        String target = args[0];
                        String suffix = args[2];

                        String targetUUID = Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString();

                        boolean isIn = false;
                        for (Suffixes s : Suffixes.values()) {
                            if (s.getSuffix().getSuffixName().equals(suffix)) {
                                isIn = true;
                                break;
                            }
                        }

                        if (isIn = false) {
                            prof.sendMessage(Common.SUFFIXprefix + " &7That suffix does not exist.");
                            return true;
                        } else {

                            try {
                                Suffixes s = Suffixes.valueOf(suffix);
                            } catch (IllegalArgumentException e) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Common.SUFFIXprefix + " &7That suffix does not exist."));
                                return true;
                            }

                            if (args[1].equalsIgnoreCase("add")) {
                                if (SuffixManager.getSuffixes(targetUUID) != null) {
                                    if (SuffixManager.getSuffixes(targetUUID).contains(Suffixes.valueOf(suffix))) {
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Common.SUFFIXprefix + " &7That player already has that suffix."));
                                        return true;
                                    }
                                }
                                SuffixManager.addSuffix(targetUUID, Suffixes.valueOf(suffix).getSuffix());
                                SuffixManager.setActiveSuffix(targetUUID, Suffixes.valueOf(suffix).getSuffix());
                                prof.sendMessage(Common.SUFFIXprefix + " &7You added the suffix.");
                            } else if (args[1].equalsIgnoreCase("remove")) {
                                if (SuffixManager.getSuffixes(targetUUID) != null) {
                                    if (SuffixManager.getSuffixes(targetUUID).contains(Suffixes.valueOf(suffix))) {
                                        SuffixManager.removeSuffix(targetUUID, Suffixes.valueOf(suffix).getSuffix());
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Common.SUFFIXprefix + " &7You removed the suffix."));
                                    } else {
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Common.SUFFIXprefix + " &7That player does not have that suffix."));
                                    }
                                } else {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Common.SUFFIXprefix + " &7That player does not have any suffixes."));
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
