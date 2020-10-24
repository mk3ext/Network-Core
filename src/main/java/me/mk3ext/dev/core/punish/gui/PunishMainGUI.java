package me.mk3ext.dev.core.punish.gui;

import me.mk3ext.dev.core.util.ItemBuilder;
import me.mk3ext.dev.core.util.UtilColour;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

public class PunishMainGUI {

    private static PunishMainGUI ourInstance = new PunishMainGUI();
    public static PunishMainGUI getInstance() { return ourInstance;}

    public static void openGUI(Player staff, OfflinePlayer target) {
        String targetName = target.getName();
        String targetUUID = target.getUniqueId().toString();
        VariableMan.addBadPlayer(staff, target);

        Inventory inv = Bukkit.createInventory(null, 54, "Punishment - ");

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

        inv.setItem(20, new ItemBuilder(Material.PAPER).displayname(UtilColour.format("&aChat Offences")).addLore(UtilColour.format("&7All chat punishments.")).build());
        inv.setItem(22, new ItemBuilder(Material.DIAMOND_SWORD).displayname(UtilColour.format("&aClient Modifications")).addLore(UtilColour.format("&7All client modification"), UtilColour.format("&7punishments.")).build());
        inv.setItem(24, new ItemBuilder(Material.TNT).displayname(UtilColour.format("&aGameplay Offences")).addLore(UtilColour.format("&7All gameplay punishments.")).build());
        inv.setItem(40, new ItemBuilder(Material.BOOK).displayname(UtilColour.format("&aPunishment History")).addLore(UtilColour.format("&7View " + targetName + "'s"), UtilColour.format("&7previous punishments.")).build());

        staff.openInventory(inv);
    }
}
