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

public class PunishCustomGUIDev {

    private static PunishCustomGUIDev ourInstance = new PunishCustomGUIDev();
    public static PunishCustomGUIDev getInstance() { return ourInstance;}

    public void openGUI(Player staff, OfflinePlayer target, String reason) {
        String targetName = target.getName();
        String targetUUID = target.getUniqueId().toString();
        VariableMan.addBadPlayer(staff, target);
        VariableMan.addReason(staff, reason);

        Inventory inv = Bukkit.createInventory(null, 36, "Advanced Punish - ");

        ItemStack skull = new ItemStack(Material.PLAYER_HEAD , 1, (byte)3);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwningPlayer(target);
        meta.setDisplayName(UtilColour.format("&eAdvanced Punish - " + targetName));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(UtilColour.format("&dPlayer: &7" + targetName + "&7."));
        lore.add(UtilColour.format("&dReason: &7" + reason + "&7."));
        meta.setLore(lore);
        skull.setItemMeta(meta);
        inv.setItem(4, skull);

        inv.setItem(0, new ItemBuilder(Material.ENDER_PEARL).displayname(UtilColour.format("&c&lDeveloper Notice")).addLore(UtilColour.format("&7Developers must not use the Punishment"), UtilColour.format("&7GUI to punish players without"), UtilColour.format("&7permisssion from the Management Team.")).build());
        inv.setItem(9, new ItemBuilder(Material.PAPER).displayname("&a1hr Mute").lore("&7You will mute the player for one hour.").build());
        inv.setItem(10, new ItemBuilder(Material.PAPER).displayname("&a2hr Mute").lore("&7You will mute the player for two hours.").build());
        inv.setItem(11, new ItemBuilder(Material.PAPER).displayname("&a4hr Mute").lore("&7You will mute the player for four hours.").build());
        inv.setItem(12, new ItemBuilder(Material.PAPER).displayname("&a12hr Mute").lore("&7You will mute the player for twelve hours.").build());
        inv.setItem(13, new ItemBuilder(Material.PAPER).displayname("&a24hr Mute").lore("&7You will mute the player for twenty-four hours.").build());
        inv.setItem(14, new ItemBuilder(Material.PAPER).displayname("&d3 Day Mute").lore("&7You will mute the player for three days.").build());
        inv.setItem(15, new ItemBuilder(Material.PAPER).displayname("&d7 Day Mute").lore("&7You will mute the player for seven days.").build());
        inv.setItem(16, new ItemBuilder(Material.PAPER).displayname("&d30 Day Mute").lore("&7You will mute the player for thirty days.").build());
        inv.setItem(17, new ItemBuilder(Material.PAPER).displayname("&cPermanent Mute").lore("&7You will mute the player permanently.").build());

        inv.setItem(18, new ItemBuilder(Material.FLINT_AND_STEEL).displayname("&a1hr Ban").lore("&7You will mute the player for one hour.").build());
        inv.setItem(19, new ItemBuilder(Material.FLINT_AND_STEEL).displayname("&a2hr Ban").lore("&7You will mute the player for two hours.").build());
        inv.setItem(20, new ItemBuilder(Material.FLINT_AND_STEEL).displayname("&a4hr Ban").lore("&7You will ban the player for four hours.").build());
        inv.setItem(21, new ItemBuilder(Material.FLINT_AND_STEEL).displayname("&a12hr Ban").lore("&7You will ban the player for twelve hours.").build());
        inv.setItem(22, new ItemBuilder(Material.FLINT_AND_STEEL).displayname("&a24hr Ban").lore("&7You will ban the player for twenty-four hours.").build());
        inv.setItem(23, new ItemBuilder(Material.FLINT_AND_STEEL).displayname("&d3 Day Ban").lore("&7You will ban the player for three days.").build());
        inv.setItem(24, new ItemBuilder(Material.FLINT_AND_STEEL).displayname("&d7 Day Ban").lore("&7You will ban the player for seven days.").build());
        inv.setItem(25, new ItemBuilder(Material.FLINT_AND_STEEL).displayname("&d30 Day Ban").lore("&7You will ban the player for thirty days.").build());
        inv.setItem(26, new ItemBuilder(Material.FLINT_AND_STEEL).displayname("&cPermanent Ban").lore("&7You will ban the player permanently.").build());

        inv.setItem(31, new ItemBuilder(Material.BOW).displayname("&aWarning").lore("&7You will warn the player.").build());

        staff.openInventory(inv);
    }
}
