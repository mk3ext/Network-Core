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

public class GameplayOffencesGUI {

    private static GameplayOffencesGUI ourInstance = new GameplayOffencesGUI();
    public static GameplayOffencesGUI getInstance() { return ourInstance;}

    public static void openGUI(Player staff, OfflinePlayer target) {
        String targetName = target.getName();
        String targetUUID = target.getUniqueId().toString();

        Inventory inv = Bukkit.createInventory(null, 54, "Gameplay Offences - ");

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

        inv.setItem(20, new ItemBuilder(Material.PAPER).displayname("&aTeam Killing").lore("&7Killing members of your own team.").build());
        inv.setItem(21, new ItemBuilder(Material.PAPER).displayname("&aTeam Trolling").lore("&7Intentionally confusing and/or misleading", "&7members of your own team.").build());
        inv.setItem(22, new ItemBuilder(Material.PAPER).displayname("&aCross Teaming").lore("&7Forming a team with another player/players.").build());
        inv.setItem(23, new ItemBuilder(Material.PAPER).displayname("&aMap Exploiting").lore("&7Exploiting weaknesses in maps", "&7&lresulting in an unfair advantage&r&7.").build());
        inv.setItem(24, new ItemBuilder(Material.PAPER).displayname("&aBug Exploiting").lore("&7Exploiting weaknesses in plugins", "&7&lresulting in an unfair advange&r&7.").build());

        inv.setItem(49, new ItemBuilder(Material.OBSIDIAN).displayname("&eGo Back").lore("&7Go back to your", "&7previous page.").build());

        staff.openInventory(inv);
    }

}
