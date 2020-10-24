package me.mk3ext.dev.core.player;

import me.mk3ext.dev.core.profiles.Profile;
import me.mk3ext.dev.core.rank.RankGroupsUtil;
import me.mk3ext.dev.core.util.Common;
import me.mk3ext.dev.core.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Give implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }
        Player p = (Player) sender;
        Profile prof = new Profile(p);

        if (!(RankGroupsUtil.isManagement(prof.getRank()))) {
            prof.sendMessage(Common.NoPermsAdmin);
            return true;
        }
        if (args.length < 1) {
            listUsages(prof);
        } else if (args.length == 1) {
            if (Material.matchMaterial(args[0]) == null) {
                prof.sendMessage(Common.GIVEprefix + " &7That item does not exist.");
            } else {
                prof.getOnlinePlayerObject().getInventory().addItem(new ItemStack(Material.matchMaterial(args[0])));
                Material mat = Material.matchMaterial(args[0]);
                boolean vowel;
                if (mat.toString().replace("Material.", "").startsWith("a") ||
                        mat.toString().replace("Material.", "").startsWith("e") ||
                        mat.toString().replace("Material.", "").startsWith("i") ||
                        mat.toString().replace("Material.", "").startsWith("o") ||
                        mat.toString().replace("Material.", "").startsWith("u")) {
                    vowel = true;
                } else {
                    vowel = false;
                }
                prof.sendMessage(Common.GIVEprefix + " &7You gave yourself a" + (vowel ? "n " : " ") + mat.toString().replace("Material.", "") + ".");
            }
        } else if (args.length == 2) {
            if (args[0].equals("all")) {
                if (Material.matchMaterial(args[1]) == null) {
                    prof.sendMessage(Common.GIVEprefix + " &7That item does not exist.");
                } else {

                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        pl.getInventory().addItem(new ItemStack(Material.matchMaterial(args[1])));
                    }
                    Material mat = Material.matchMaterial(args[1]);
                    boolean vowel;
                    if (mat.toString().replace("Material.", "").startsWith("a") ||
                            mat.toString().replace("Material.", "").startsWith("e") ||
                            mat.toString().replace("Material.", "").startsWith("i") ||
                            mat.toString().replace("Material.", "").startsWith("o") ||
                            mat.toString().replace("Material.", "").startsWith("u")) {
                        vowel = true;
                    } else {
                        vowel = false;
                    }
                    prof.sendMessage(Common.GIVEprefix + " &7You gave &dALL &7a" + (vowel ? "n " : " ") + mat.toString().replace("Material.", "") + ".");
                }
                return true;
            }
            if (Bukkit.getPlayer(args[0]) != null) {
                Profile targetProf = new Profile(Bukkit.getPlayer(args[0]));
                if (Material.matchMaterial(args[1]) == null) {
                    prof.sendMessage(Common.GIVEprefix + " &7That item does not exist.");
                } else {
                    targetProf.getOnlinePlayerObject().getInventory().addItem(new ItemStack(Material.matchMaterial(args[1])));
                    Material mat = Material.matchMaterial(args[1]);
                    boolean vowel;
                    vowel = mat.toString().replace("Material.", "").startsWith("a") ||
                            mat.toString().replace("Material.", "").startsWith("e") ||
                            mat.toString().replace("Material.", "").startsWith("i") ||
                            mat.toString().replace("Material.", "").startsWith("o") ||
                            mat.toString().replace("Material.", "").startsWith("u");
                    prof.sendMessage(Common.GIVEprefix + " &7You gave &d" + targetProf.getName() + "&7 a" + (vowel ? "n " : " ") + mat.toString().replace("Material.", "") + ".");
                }
            } else {
                prof.sendMessage(Common.GIVEprefix + " &7That player is not online.");
            }
        } else if (args.length == 3) {
            if (args[0].equals("all")) {
                if (Material.matchMaterial(args[1]) == null) {
                    prof.sendMessage(Common.GIVEprefix + " &7That item does not exist.");
                } else {
                    int i;
                    try {
                        i = Integer.parseInt(args[2]);
                    } catch (Exception e) {
                        prof.sendMessage(Common.GIVEprefix + " &7That is not a number.");
                        return true;
                    }
                    Material mat = Material.matchMaterial(args[1]);

                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        pl.getInventory().addItem(new ItemBuilder(mat).amount(i).build());
                    }
                    boolean vowel;
                    vowel = mat.toString().replace("Material.", "").startsWith("a") ||
                            mat.toString().replace("Material.", "").startsWith("e") ||
                            mat.toString().replace("Material.", "").startsWith("i") ||
                            mat.toString().replace("Material.", "").startsWith("o") ||
                            mat.toString().replace("Material.", "").startsWith("u");
                    prof.sendMessage(Common.GIVEprefix + " &7You gave &dALL &7" + i + " " + mat.toString().replace("Material.", "") + ".");
                }
                return true;
            }
            if (Bukkit.getPlayer(args[0]) != null) {
                Profile targetProf = new Profile(Bukkit.getPlayer(args[0]));
                if (Material.matchMaterial(args[1]) == null) {
                    prof.sendMessage(Common.GIVEprefix + " &7That item does not exist.");
                } else {
                    int i;
                    try {
                        i = Integer.parseInt(args[2]);
                    } catch (Exception e) {
                        prof.sendMessage(Common.GIVEprefix + " &7That is not a number.");
                        return true;
                    }
                    Material mat = Material.matchMaterial(args[1]);
                    targetProf.getOnlinePlayerObject().getInventory().addItem(new ItemBuilder(mat).amount(i).build());
                    boolean vowel;
                    vowel = mat.toString().replace("Material.", "").startsWith("a") ||
                            mat.toString().replace("Material.", "").startsWith("e") ||
                            mat.toString().replace("Material.", "").startsWith("i") ||
                            mat.toString().replace("Material.", "").startsWith("o") ||
                            mat.toString().replace("Material.", "").startsWith("u");
                    prof.sendMessage(Common.GIVEprefix + " &7You gave &d" + targetProf.getName() + "&7 " + i + " " + mat.toString().replace("Material.", "") + ".");
                }
            } else {
                prof.sendMessage(Common.GIVEprefix + " &7That player is not online.");
            }
        } else if (args.length == 4) {
            if (args[0].equals("all")) {

                if (Material.matchMaterial(args[1]) == null) {
                    prof.sendMessage(Common.GIVEprefix + " &7That item does not exist.");
                } else {
                    int i;
                    try {
                        i = Integer.parseInt(args[2]);
                    } catch (Exception e) {
                        prof.sendMessage(Common.GIVEprefix + " &7That amount is not a number.");
                        return true;
                    }

                    String enchantmentarg = args[3];
                    String enchstr = enchantmentarg.split(":")[0];
                    String levelstr = enchantmentarg.split(":")[1];

                    int level;
                    try {
                        level = Integer.parseInt(levelstr);
                    } catch (Exception e) {
                        prof.sendMessage(Common.GIVEprefix + " &7That level is not a number.");
                        return true;
                    }

                    Enchantment ench = Enchantment.getByName(enchstr);
                    if (ench == null) {
                        prof.sendMessage(Common.GIVEprefix + " &7That is not an enchantment.");
                        return true;
                    }


                    Material mat = Material.matchMaterial(args[1]);
                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        pl.getInventory().addItem(new ItemBuilder(mat).amount(i).enchantment(ench,level, false).build());
                    }
                    boolean vowel;
                    vowel = mat.toString().replace("Material.", "").startsWith("a") ||
                            mat.toString().replace("Material.", "").startsWith("e") ||
                            mat.toString().replace("Material.", "").startsWith("i") ||
                            mat.toString().replace("Material.", "").startsWith("o") ||
                            mat.toString().replace("Material.", "").startsWith("u");
                    prof.sendMessage(Common.GIVEprefix + " &7You gave &dALL &7" + i + " " + mat.toString().replace("Material.", "") + ".");
                }


                return true;
            }
            if (Bukkit.getPlayer(args[0]) != null) {
                Profile targetProf = new Profile(Bukkit.getPlayer(args[0]));
                if (Material.matchMaterial(args[1]) == null) {
                    prof.sendMessage(Common.GIVEprefix + " &7That item does not exist.");
                } else {
                    int i;
                    try {
                        i = Integer.parseInt(args[2]);
                    } catch (Exception e) {
                        prof.sendMessage(Common.GIVEprefix + " &7That amount is not a number.");
                        return true;
                    }

                    String enchantmentarg = args[3];
                    String enchstr = enchantmentarg.split(":")[0];
                    String levelstr = enchantmentarg.split(":")[1];

                    int level;
                    try {
                        level = Integer.parseInt(levelstr);
                    } catch (Exception e) {
                        prof.sendMessage(Common.GIVEprefix + " &7That level is not a number.");
                        return true;
                    }

                    Enchantment ench = Enchantment.getByName(enchstr);
                    if (ench == null) {
                        prof.sendMessage(Common.GIVEprefix + " &7That is not an enchantment.");
                        return true;
                    }


                    Material mat = Material.matchMaterial(args[1]);
                    targetProf.getOnlinePlayerObject().getInventory().addItem(new ItemBuilder(mat).amount(i).enchantment(ench, level, false).build());
                    boolean vowel;
                    vowel = mat.toString().replace("Material.", "").startsWith("a") ||
                            mat.toString().replace("Material.", "").startsWith("e") ||
                            mat.toString().replace("Material.", "").startsWith("i") ||
                            mat.toString().replace("Material.", "").startsWith("o") ||
                            mat.toString().replace("Material.", "").startsWith("u");
                    prof.sendMessage(Common.GIVEprefix + " &7You gave &d" + targetProf.getName() + "&7 " + i + " " + mat.toString().replace("Material.", "") + ".");
                }
            } else {
                prof.sendMessage(Common.GIVEprefix + " &7That player is not online.");
            }
        }
        return true;
    }

    public void listUsages(Profile p) {
        String prefix = Common.GIVEprefix + " &7";
        p.sendMessage(prefix + "Listing usages...");
        p.sendMessage(prefix + "&d/give <item> &7- Gives you a specified item.");
        p.sendMessage(prefix + "&d/give <player> <item> &7- Gives a player a specified item.");
        p.sendMessage(prefix + "&d/give <player> <item> <amount> &7- Gives a player an amount of a specified item.");
        p.sendMessage(prefix + "&d/give <player> <item> <amount> <Enchantment:level> &7- Gives a player an enchanted item.");
    }
}
