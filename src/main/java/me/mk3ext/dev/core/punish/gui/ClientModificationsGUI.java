package me.mk3ext.dev.core.punish.gui;

import me.mk3ext.dev.core.Main;
import me.mk3ext.dev.core.util.ItemBuilder;
import me.mk3ext.dev.core.util.UtilColour;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class ClientModificationsGUI {

    private static ClientModificationsGUI ourInstance = new ClientModificationsGUI();
    public static ClientModificationsGUI getInstance() { return ourInstance;}

    public static void openGUI(Player staff, OfflinePlayer target) {
        String targetName = target.getName();
        String targetUUID = target.getUniqueId().toString();

        Inventory inv = Bukkit.createInventory(null, 54, "Client Modifications - ");

        ItemStack skull = new ItemStack(Material.PLAYER_HEAD , 1, (byte)3);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwningPlayer(target);
        meta.setDisplayName(UtilColour.format("&ePunish - "));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(UtilColour.format("&7You are punishing"));
        lore.add(UtilColour.format("&7" + targetName + "&7."));
        meta.setLore(lore);
        skull.setItemMeta(meta);
        inv.setItem(4, skull);

        inv.setItem(20, new ItemBuilder(Material.PAPER).displayname("&aUnapproved Mods").lore("&7Using a client modification that", "&7is not approved by test.").build());
        inv.setItem(21, new ItemBuilder(Material.PAPER).displayname("&aXray").lore("&7Any modification that allows you", "&7to easily view the locations of", "&7chests, ores or other valued items.").build());
        inv.setItem(22, new ItemBuilder(Material.PAPER).displayname("&dKill Aura").lore("&7Having the ability to hit multiple", "&7players at once, or as soon", "&7as they come within reach.").build());
        inv.setItem(23, new ItemBuilder(Material.PAPER).displayname("&dAnti Knockback").lore("&7Reducing the amount of knockback", "&7that is taken when fighting.").build());
        inv.setItem(24, new ItemBuilder(Material.PAPER).displayname("&dJesus").lore("&7The ability to walk on water", "&7when no player should be able to.").build());
        inv.setItem(30, new ItemBuilder(Material.PAPER).displayname("&dNo Slowdown").lore("&7Walking/running at full speed when", "&7the player should not be able to.").build());
        inv.setItem(31, new ItemBuilder(Material.PAPER).displayname("&cFly").lore("&7Flying (ensure that the movement", "&7is &lnot lag&r&7)").build());
        inv.setItem(32, new ItemBuilder(Material.PAPER).displayname("&cSpeed").lore("&7Overly fast movement. (Ensure that", "&7this is &lnot lag&7, and take into", "&7account the speed boosts of kits.)").build());

        inv.setItem(49, new ItemBuilder(Material.OBSIDIAN).displayname("&eGo Back").lore("&7Go back to your", "&7previous page.").build());

        new BukkitRunnable() {
            @Override
            public void run() {
                staff.openInventory(inv);
            }

        }.runTaskLater(Main.getInstance(), 1);
    }
}
