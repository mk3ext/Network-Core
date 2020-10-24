package me.mk3ext.dev.core.punish.gui;

import me.mk3ext.dev.core.util.ItemBuilder;
import me.mk3ext.dev.core.util.UtilColour;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

public class PunishConfirmGUI {

    private static PunishConfirmGUI ourInstance = new PunishConfirmGUI();
    public static PunishConfirmGUI getInstance() { return ourInstance; }

    public static void openGUI(Player staff, OfflinePlayer target, String reason) {
        String targetName = target.getName();
        String targetUUID = target.getUniqueId().toString();

        Inventory inv = Bukkit.createInventory(null, 54, "Confirm - ");

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

        inv.setItem(21, new ItemBuilder(Material.EMERALD_BLOCK).displayname(UtilColour.format("&a&lConfirm Punishment")).addLore("&7Confirm that you would like to punish", "&7" + targetName + " for " + reason + "&7.").build());
        inv.setItem(23, new ItemBuilder(Material.REDSTONE_BLOCK).dye(Color.GREEN).displayname(UtilColour.format("&c&lCancel Punishment")).addLore("&7Cancel your punishment. (Close this", "&7menu)").build());

        VariableMan.addReason(staff, reason);
        staff.openInventory(inv);
    }
}
