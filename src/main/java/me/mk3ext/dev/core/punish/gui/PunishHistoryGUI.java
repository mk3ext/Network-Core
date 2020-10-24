package me.mk3ext.dev.core.punish.gui;

import me.mk3ext.dev.core.punish.reason.ChatReasons1;
import me.mk3ext.dev.core.punish.reason.ChatReasons2;
import me.mk3ext.dev.core.punish.reason.ClientReasons;
import me.mk3ext.dev.core.punish.reason.GameplayReasons;
import me.mk3ext.dev.core.util.ItemBuilder;
import me.mk3ext.dev.core.util.UtilColour;
import me.mk3ext.dev.core.punish.DatabaseHandler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class PunishHistoryGUI {

    private static PunishHistoryGUI ourInstance = new PunishHistoryGUI();
    public static PunishHistoryGUI getInstance() { return ourInstance; }

    public static void openGUI(Player staff, OfflinePlayer target) {
        String targetName = target.getName();
        String targetUUID = target.getUniqueId().toString();

        Inventory inv = Bukkit.createInventory(null, 54, "History - " + targetName);

        ItemStack skull = new ItemStack(Material.PLAYER_HEAD , 1, (byte)3);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwningPlayer(target);
        meta.setDisplayName(UtilColour.format("&ePunish - " + targetName));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(UtilColour.format("&7You are punishing"));
        lore.add(UtilColour.format("&7" + targetName + "&7."));
        meta.setLore(lore);
        skull.setItemMeta(meta);
        inv.setItem(4, skull);

        int startslot = 9;
        for (String id : DatabaseHandler.getAllIdsForPlayer(targetUUID)) {
            String punisherName = Bukkit.getOfflinePlayer(UUID.fromString(DatabaseHandler.getPunisherUUID(id))).getName();
            String reason = DatabaseHandler.getReason(id);
            String seen = DatabaseHandler.getSeen(id).replace("t", "T").replace("f", "F");
            String datesent = DatabaseHandler.getLongSent(id);
            String dateexpire = DatabaseHandler.getLongExpire(id);
            Boolean removed = DatabaseHandler.getRemoved(id);
            String type = DatabaseHandler.getType(id);

            String formattedsent = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(Long.parseLong(datesent)));
            if (getCategory(reason).equalsIgnoreCase("ChatReasons1")) {
                if (!removed) {
                    if (dateexpire.equals("-1")) {
                        inv.setItem(startslot, new ItemBuilder(Material.BOOK).displayname("&aPunishment #" + id).lore("&bReason: &7" + reason, " " ,"&bPunished By: &7" + punisherName, "&bOnline Since Punishment: &7" + seen, "&bType: &7" + type, " " ,"&bDate Created: &7" + formattedsent, "&bExpiry Date: &7Permanent").addItemFlags(ItemFlag.HIDE_ENCHANTS).enchantment(Enchantment.DAMAGE_ALL, 1, false).build());
                    } else {
                        String formattedexpire = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(Long.parseLong(dateexpire)));
                        if (Long.parseLong(dateexpire) > System.currentTimeMillis()) {
                            inv.setItem(startslot, new ItemBuilder(Material.BOOK).displayname("&aPunishment #" + id).lore("&bReason: &7" + reason, " " ,"&bPunished By: &7" + punisherName, "&bOnline Since Punishment: &7" + seen, "&bType: &7" + type, " " ,"&bDate Created: &7" + formattedsent, "&bExpiry Date: &7" + formattedexpire).addItemFlags(ItemFlag.HIDE_ENCHANTS).enchantment(Enchantment.DAMAGE_ALL, 1, false).build());
                        } else {
                            inv.setItem(startslot, new ItemBuilder(Material.BOOK).displayname("&aPunishment #" + id).lore("&bReason: &7" + reason, " " ,"&bPunished By: &7" + punisherName, "&bOnline Since Punishment: &7" + seen, "&bType: &7" + type, " " ,"&bDate Created: &7" + formattedsent, "&bExpiry Date: &7" + formattedexpire).build());
                        }
                    }
                } else {
                    if (dateexpire.equals("-1")) {
                        inv.setItem(startslot, new ItemBuilder(Material.BOOK).displayname("&cPunishment #" + id).lore("&bReason: &7" + reason, " " ,"&bPunished By: &7" + punisherName, "&bOnline Since Punishment: &7" + seen, "&bType: &7" + type, " " ,"&bDate Created: &7" + formattedsent, "&bExpiry Date: &7Permanent", " ", "&c&lRemoved - Appeal Accepted").build());
                    } else {
                        String formattedexpire = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(Long.parseLong(dateexpire)));
                        inv.setItem(startslot, new ItemBuilder(Material.BOOK).displayname("&cPunishment #" + id).lore("&bReason: &7" + reason, " " ,"&bPunished By: &7" + punisherName, "&bOnline Since Punishment: &7" + seen, "&bType: &7" + type, " " ,"&bDate Created: &7" + formattedsent, "&bExpiry Date: &7" + formattedexpire, " ", "&c&lRemoved - Appeal Accepted").build());
                    }
                }
            } else if (getCategory(reason).equalsIgnoreCase("ChatReasons2")) {
                if (!removed) {
                    if (dateexpire.equals("-1")) {
                        inv.setItem(startslot, new ItemBuilder(Material.WRITABLE_BOOK).displayname("&aPunishment #" + id).lore("&bReason: &7" + reason, " " ,"&bPunished By: &7" + punisherName, "&bOnline Since Punishment: &7" + seen, "&bType: &7" + type, " " ,"&bDate Created: &7" + formattedsent, "&bExpiry Date: &7Permanent").enchantment(Enchantment.DAMAGE_ALL, 1, false).addItemFlags(ItemFlag.HIDE_ENCHANTS).build());
                    } else {
                        String formattedexpire = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(Long.parseLong(dateexpire)));
                        if (Long.parseLong(dateexpire) > System.currentTimeMillis()) {
                            inv.setItem(startslot, new ItemBuilder(Material.WRITABLE_BOOK).displayname("&aPunishment #" + id).lore("&bReason: &7" + reason, " " ,"&bPunished By: &7" + punisherName, "&bOnline Since Punishment: &7" + seen, "&bType: &7" + type, " " ,"&bDate Created: &7" + formattedsent, "&bExpiry Date: &7" + formattedexpire).enchantment(Enchantment.DAMAGE_ALL, 1, false).addItemFlags(ItemFlag.HIDE_ENCHANTS).build());
                        } else {
                            inv.setItem(startslot, new ItemBuilder(Material.WRITABLE_BOOK).displayname("&aPunishment #" + id).lore("&bReason: &7" + reason, " " ,"&bPunished By: &7" + punisherName, "&bOnline Since Punishment: &7" + seen, "&bType: &7" + type, " " ,"&bDate Created: &7" + formattedsent, "&bExpiry Date: &7" + formattedexpire).enchantment(Enchantment.DAMAGE_ALL, 1, false).build());
                        }
                    }
                } else {
                    if (dateexpire.equals("-1")) {
                        inv.setItem(startslot, new ItemBuilder(Material.WRITABLE_BOOK).displayname("&cPunishment #" + id).lore("&bReason: &7" + reason, " " ,"&bPunished By: &7" + punisherName, "&bOnline Since Punishment: &7" + seen, "&bType: &7" + type, " " ,"&bDate Created: &7" + formattedsent, "&bExpiry Date: &7Permanent", " ", "&c&lRemoved - Appeal Accepted").build());
                    } else {
                        String formattedexpire = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(Long.parseLong(dateexpire)));
                        inv.setItem(startslot, new ItemBuilder(Material.WRITABLE_BOOK).displayname("&cPunishment #" + id).lore("&bReason: &7" + reason, " " ,"&bPunished By: &7" + punisherName, "&bOnline Since Punishment: &7" + seen, "&bType: &7" + type, " " ,"&bDate Created: &7" + formattedsent, "&bExpiry Date: &7" + formattedexpire, " ", "&c&lRemoved - Appeal Accepted").build());
                    }
                }
            } else if (getCategory(reason).equalsIgnoreCase("GameplayReasons")) {
                if (type.equals("BAN") || type.equals("TEMPBAN")) {
                    if (!removed) {
                        if (dateexpire.equals("-1")) {
                            inv.setItem(startslot, new ItemBuilder(Material.TNT).displayname("&aPunishment #" + id).lore("&bReason: &7" + reason, " " ,"&bPunished By: &7" + punisherName, "&bLogin Attempted: &7" + seen, "&bType: &7" + type, " " ,"&bDate Created: &7" + formattedsent, "&bExpiry Date: &7Permanent").enchantment(Enchantment.DAMAGE_ALL, 1, false).addItemFlags(ItemFlag.HIDE_ENCHANTS).build());
                        } else {
                            String formattedexpire = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(Long.parseLong(dateexpire)));
                            if (Long.parseLong(dateexpire) > System.currentTimeMillis()) {
                                inv.setItem(startslot, new ItemBuilder(Material.TNT).displayname("&aPunishment #" + id).lore("&bReason: &7" + reason, " " ,"&bPunished By: &7" + punisherName, "&bLogin Attempted: &7" + seen, "&bType: &7" + type, " " ,"&bDate Created: &7" + formattedsent, "&bExpiry Date: &7" + formattedexpire).enchantment(Enchantment.DAMAGE_ALL, 1, false).addItemFlags(ItemFlag.HIDE_ENCHANTS).build());
                            } else {
                                inv.setItem(startslot, new ItemBuilder(Material.TNT).displayname("&aPunishment #" + id).lore("&bReason: &7" + reason, " " ,"&bPunished By: &7" + punisherName, "&bLogin Attempted: &7" + seen, "&bType: &7" + type, " " ,"&bDate Created: &7" + formattedsent, "&bExpiry Date: &7" + formattedexpire).build());
                            }
                        }
                    } else {
                        if (dateexpire.equals("-1")) {
                            inv.setItem(startslot, new ItemBuilder(Material.TNT).displayname("caPunishment #" + id).lore("&bReason: &7" + reason, " " ,"&bPunished By: &7" + punisherName, "&bLogin Attempted: &7" + seen, "&bType: &7" + type, " " ,"&bDate Created: &7" + formattedsent, "&bExpiry Date: &7Permanent", " ", "&c&lRemoved - Appeal Accepted").build());
                        } else {
                            String formattedexpire = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(Long.parseLong(dateexpire)));
                            inv.setItem(startslot, new ItemBuilder(Material.TNT).displayname("&cPunishment #" + id).lore("&bReason: &7" + reason, " " ,"&bPunished By: &7" + punisherName, "&bLogin Attempted: &7" + seen, "&bType: &7" + type, " " ,"&bDate Created: &7" + formattedsent, "&bExpiry Date: &7" + formattedexpire, " ", "&c&lRemoved - Appeal Accepted").build());
                        }
                    }
                } else {
                    if (!removed) {
                        if (dateexpire.equals("-1")) {
                            inv.setItem(startslot, new ItemBuilder(Material.TNT).displayname("&aPunishment #" + id).lore("&bReason: &7" + reason, " " ,"&bPunished By: &7" + punisherName, "&bOnline Since Punishment: &7" + seen, "&bType: &7" + type, " " ,"&bDate Created: &7" + formattedsent, "&bExpiry Date: &7Permanent").enchantment(Enchantment.DAMAGE_ALL, 1, false).addItemFlags(ItemFlag.HIDE_ENCHANTS).build());
                        } else {
                            String formattedexpire = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(Long.parseLong(dateexpire)));
                            if (Long.parseLong(dateexpire) > System.currentTimeMillis()) {
                                inv.setItem(startslot, new ItemBuilder(Material.TNT).displayname("&aPunishment #" + id).lore("&bReason: &7" + reason, " " ,"&bPunished By: &7" + punisherName, "&bOnline Since Punishment: &7" + seen, "&bType: &7" + type, " " ,"&bDate Created: &7" + formattedsent, "&bExpiry Date: &7" + formattedexpire).enchantment(Enchantment.DAMAGE_ALL, 1, false).addItemFlags(ItemFlag.HIDE_ENCHANTS).build());
                            } else {
                                inv.setItem(startslot, new ItemBuilder(Material.TNT).displayname("&aPunishment #" + id).lore("&bReason: &7" + reason, " " ,"&bPunished By: &7" + punisherName, "&bOnline Since Punishment: &7" + seen, "&bType: &7" + type, " " ,"&bDate Created: &7" + formattedsent, "&bExpiry Date: &7" + formattedexpire).build());
                            }
                        }
                    } else {
                        if (dateexpire.equals("-1")) {
                            inv.setItem(startslot, new ItemBuilder(Material.TNT).displayname("&cPunishment #" + id).lore("&bReason: &7" + reason, " " ,"&bPunished By: &7" + punisherName, "&bOnline Since Punishment: &7" + seen, "&bType: &7" + type, " " ,"&bDate Created: &7" + formattedsent, "&bExpiry Date: &7Permanent", " ", "&c&lRemoved - Appeal Accepted").build());
                        } else {
                            String formattedexpire = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(Long.parseLong(dateexpire)));
                            inv.setItem(startslot, new ItemBuilder(Material.TNT).displayname("&cPunishment #" + id).lore("&bReason: &7" + reason, " " ,"&bPunished By: &7" + punisherName, "&bOnline Since Punishment: &7" + seen, "&bType: &7" + type, " " ,"&bDate Created: &7" + formattedsent, "&bExpiry Date: &7" + formattedexpire, " ", "&c&lRemoved - Appeal Accepted").build());
                        }
                    }
                }
            } else if (getCategory(reason).equalsIgnoreCase("ClientReasons")) {
                if (type.equals("BAN") || type.equals("TEMPBAN")) {
                    if (!removed) {
                        if (dateexpire.equals("-1")) {
                            inv.setItem(startslot, new ItemBuilder(Material.DIAMOND_SWORD).displayname("&aPunishment #" + id).lore("&bReason: &7" + reason, " " ,"&bPunished By: &7" + punisherName, "&bLogin Attempted: &7" + seen, "&bType: &7" + type, " " ,"&bDate Created: &7" + formattedsent, "&bExpiry Date: &7Permanent").enchantment(Enchantment.DAMAGE_ALL, 1, false).addItemFlags(ItemFlag.HIDE_ENCHANTS).build());
                        } else {
                            String formattedexpire = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(Long.parseLong(dateexpire)));
                            if (Long.parseLong(dateexpire) > System.currentTimeMillis()) {
                                inv.setItem(startslot, new ItemBuilder(Material.DIAMOND_SWORD).displayname("&aPunishment #" + id).lore("&bReason: &7" + reason, " " ,"&bPunished By: &7" + punisherName, "&bLogin Attempted: &7" + seen, "&bType: &7" + type, " " ,"&bDate Created: &7" + formattedsent, "&bExpiry Date: &7" + formattedexpire).enchantment(Enchantment.DAMAGE_ALL, 1, false).addItemFlags(ItemFlag.HIDE_ENCHANTS).build());
                            } else {
                                inv.setItem(startslot, new ItemBuilder(Material.DIAMOND_SWORD).displayname("&aPunishment #" + id).lore("&bReason: &7" + reason, " " ,"&bPunished By: &7" + punisherName, "&bLogin Attempted: &7" + seen, "&bType: &7" + type, " " ,"&bDate Created: &7" + formattedsent, "&bExpiry Date: &7" + formattedexpire).build());
                            }

                        }
                    } else {
                        if (dateexpire.equals("-1")) {
                            inv.setItem(startslot, new ItemBuilder(Material.DIAMOND_SWORD).displayname("&cPunishment #" + id).lore("&bReason: &7" + reason, " " ,"&bPunished By: &7" + punisherName, "&bLogin Attempted: &7" + seen, "&bType: &7" + type, " " ,"&bDate Created: &7" + formattedsent, "&bExpiry Date: &7Permanent", " ", "&c&lRemoved - Appeal Accepted").build());
                        } else {
                            String formattedexpire = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(Long.parseLong(dateexpire)));
                            inv.setItem(startslot, new ItemBuilder(Material.DIAMOND_SWORD).displayname("&cPunishment #" + id).lore("&bReason: &7" + reason, " " ,"&bPunished By: &7" + punisherName, "&bLogin Attempted: &7" + seen, "&bType: &7" + type, " " ,"&bDate Created: &7" + formattedsent, "&bExpiry Date: &7" + formattedexpire, " ", "&c&lRemoved - Appeal Accepted").build());
                        }
                    }
                } else {
                    if (!removed) {
                        if (dateexpire.equals("-1")) {
                            inv.setItem(startslot, new ItemBuilder(Material.DIAMOND_SWORD).displayname("&aPunishment #" + id).lore("&bReason: &7" + reason, " " ,"&bPunished By: &7" + punisherName, "&bOnline Since Punishment: &7" + seen, "&bType: &7" + type, " " ,"&bDate Created: &7" + formattedsent, "&bExpiry Date: &7Permanent").build());
                        } else {
                            String formattedexpire = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(Long.parseLong(dateexpire)));
                            if (Long.parseLong(dateexpire) > System.currentTimeMillis()) {
                                inv.setItem(startslot, new ItemBuilder(Material.DIAMOND_SWORD).displayname("&aPunishment #" + id).lore("&bReason: &7" + reason, " " ,"&bPunished By: &7" + punisherName, "&bOnline Since Punishment: &7" + seen, "&bType: &7" + type, " " ,"&bDate Created: &7" + formattedsent, "&bExpiry Date: &7" + formattedexpire).enchantment(Enchantment.DAMAGE_ALL, 1, false).addItemFlags(ItemFlag.HIDE_ENCHANTS).build());
                            } else {
                                inv.setItem(startslot, new ItemBuilder(Material.DIAMOND_SWORD).displayname("&aPunishment #" + id).lore("&bReason: &7" + reason, " " ,"&bPunished By: &7" + punisherName, "&bOnline Since Punishment: &7" + seen, "&bType: &7" + type, " " ,"&bDate Created: &7" + formattedsent, "&bExpiry Date: &7" + formattedexpire).build());
                            }
                        }
                    } else {
                        if (dateexpire.equals("-1")) {
                            inv.setItem(startslot, new ItemBuilder(Material.DIAMOND_SWORD).displayname("&cPunishment #" + id).lore("&bReason: &7" + reason, " " ,"&bPunished By: &7" + punisherName, "&bOnline Since Punishment: &7" + seen, "&bType: &7" + type, " " ,"&bDate Created: &7" + formattedsent, "&bExpiry Date: &7Permanent", " ", "&c&lRemoved - Appeal Accepted").build());
                        } else {
                            String formattedexpire = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(Long.parseLong(dateexpire)));
                            inv.setItem(startslot, new ItemBuilder(Material.DIAMOND_SWORD).displayname("&cPunishment #" + id).lore("&bReason: &7" + reason, " " ,"&bPunished By: &7" + punisherName, "&bOnline Since Punishment: &7" + seen, "&bType: &7" + type, " " ,"&bDate Created: &7" + formattedsent, "&bExpiry Date: &7" + formattedexpire, " ", "&c&lRemoved - Appeal Accepted").build());
                        }
                    }
                }
            } else if (getCategory(reason).equalsIgnoreCase("Other")) {
                if (type.equals("BAN") || type.equals("TEMPBAN")) {
                    if (!removed) {
                        if (dateexpire.equals("-1")) {
                            inv.setItem(startslot, new ItemBuilder(Material.REDSTONE).displayname("&aPunishment #" + id).lore("&bReason: &7" + reason, " " ,"&bPunished By: &7" + punisherName, "&bLogin Attempted: &7" + seen, "&bType: &7" + type, " " ,"&bDate Created: &7" + formattedsent, "&bExpiry Date: &7Permanent").enchantment(Enchantment.DAMAGE_ALL, 1, false).addItemFlags(ItemFlag.HIDE_ENCHANTS).build());
                        } else {
                            String formattedexpire = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(Long.parseLong(dateexpire)));
                            if (Long.parseLong(dateexpire) > System.currentTimeMillis()) {
                                inv.setItem(startslot, new ItemBuilder(Material.REDSTONE).displayname("&aPunishment #" + id).lore("&bReason: &7" + reason, " " ,"&bPunished By: &7" + punisherName, "&bLogin Attempted: &7" + seen, "&bType: &7" + type, " " ,"&bDate Created: &7" + formattedsent, "&bExpiry Date: &7" + formattedexpire).enchantment(Enchantment.DAMAGE_ALL, 1, false).addItemFlags(ItemFlag.HIDE_ENCHANTS).build());
                            } else {
                                inv.setItem(startslot, new ItemBuilder(Material.REDSTONE).displayname("&aPunishment #" + id).lore("&bReason: &7" + reason, " " ,"&bPunished By: &7" + punisherName, "&bLogin Attempted: &7" + seen, "&bType: &7" + type, " " ,"&bDate Created: &7" + formattedsent, "&bExpiry Date: &7" + formattedexpire).build());
                            }
                        }
                    } else {
                        if (dateexpire.equals("-1")) {
                            inv.setItem(startslot, new ItemBuilder(Material.REDSTONE).displayname("&cPunishment #" + id).lore("&bReason: &7" + reason, " " ,"&bPunished By: &7" + punisherName, "&bLogin Attempted: &7" + seen, "&bType: &7" + type, " " ,"&bDate Created: &7" + formattedsent, "&bExpiry Date: &7Permanent", " ", "&c&lRemoved - Appeal Accepted").build());
                        } else {
                            String formattedexpire = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(Long.parseLong(dateexpire)));
                            inv.setItem(startslot, new ItemBuilder(Material.REDSTONE).displayname("&cPunishment #" + id).lore("&bReason: &7" + reason, " " ,"&bPunished By: &7" + punisherName, "&bLogin Attempted: &7" + seen, "&bType: &7" + type, " " ,"&bDate Created: &7" + formattedsent, "&bExpiry Date: &7" + formattedexpire, " ", "&c&lRemoved - Appeal Accepted").build());
                        }
                    }
                } else {
                    if (!removed) {
                        if (dateexpire.equals("-1")) {
                            inv.setItem(startslot, new ItemBuilder(Material.REDSTONE).displayname("&aPunishment #" + id).lore("&bReason: &7" + reason, " " ,"&bPunished By: &7" + punisherName, "&bOnline Since Punishment: &7" + seen, "&bType: &7" + type, " " ,"&bDate Created: &7" + formattedsent, "&bExpiry Date: &7Permanent").enchantment(Enchantment.DAMAGE_ALL, 1, false).addItemFlags(ItemFlag.HIDE_ENCHANTS).build());
                        } else {
                            String formattedexpire = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(Long.parseLong(dateexpire)));
                            if (Long.parseLong(dateexpire) > System.currentTimeMillis()) {
                                inv.setItem(startslot, new ItemBuilder(Material.REDSTONE).displayname("&aPunishment #" + id).lore("&bReason: &7" + reason, " " ,"&bPunished By: &7" + punisherName, "&bOnline Since Punishment: &7" + seen, "&bType: &7" + type, " " ,"&bDate Created: &7" + formattedsent, "&bExpiry Date: &7" + formattedexpire).enchantment(Enchantment.DAMAGE_ALL, 1, false).addItemFlags(ItemFlag.HIDE_ENCHANTS).build());
                            } else {
                                inv.setItem(startslot, new ItemBuilder(Material.REDSTONE).displayname("&aPunishment #" + id).lore("&bReason: &7" + reason, " " ,"&bPunished By: &7" + punisherName, "&bOnline Since Punishment: &7" + seen, "&bType: &7" + type, " " ,"&bDate Created: &7" + formattedsent, "&bExpiry Date: &7" + formattedexpire).build());
                            }
                        }
                    } else {
                        if (dateexpire.equals("-1")) {
                            inv.setItem(startslot, new ItemBuilder(Material.REDSTONE).displayname("&cPunishment #" + id).lore("&bReason: &7" + reason, " " ,"&bPunished By: &7" + punisherName, "&bOnline Since Punishment: &7" + seen, "&bType: &7" + type, " " ,"&bDate Created: &7" + formattedsent, "&bExpiry Date: &7Permanent", " ", "&c&lRemoved - Appeal Accepted").build());
                        } else {
                            String formattedexpire = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(Long.parseLong(dateexpire)));
                            inv.setItem(startslot, new ItemBuilder(Material.REDSTONE).displayname("&cPunishment #" + id).lore("&bReason: &7" + reason, " " ,"&bPunished By: &7" + punisherName, "&bOnline Since Punishment: &7" + seen, "&bType: &7" + type, " " ,"&bDate Created: &7" + formattedsent, "&bExpiry Date: &7" + formattedexpire, " ", "&c&lRemoved - Appeal Accepted").build());
                        }
                    }
                }
            } else {
                staff.sendMessage(UtilColour.format("&c&lPunish > &7An error has occured. Please contact a developer, quoting error ID 001."));
            }
            startslot++;

        }
        inv.setItem(49, new ItemBuilder(Material.OBSIDIAN).displayname("&eGo Back").lore("&7Go back to your", "&7previous page.").build());
        staff.openInventory(inv);

    }

    private static String getCategory(String reason) {
        String type = "Other";

        for (ChatReasons1 r : ChatReasons1.values()) {
            if (r.name().equals(reason)) {
                type = "ChatReasons1";
                break;
            }
        }
        for (ChatReasons2 r : ChatReasons2.values()) {
            if (r.name().equals(reason)) {
                type = "ChatReasons2";
                break;
            }
        }
        for (GameplayReasons r : GameplayReasons.values()) {
            if (r.name().equals(reason)) {
                type = "GameplayReasons";
                break;
            }
        }
        for (ClientReasons r : ClientReasons.values()) {
            if (r.name().equals(reason)) {
                type = "ClientReasons";
                break;
            }
        }

        return type;

    }
}
