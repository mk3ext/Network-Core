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

public class ChatOffencesGUI {

    private static ChatOffencesGUI ourInstance = new ChatOffencesGUI();
    public static ChatOffencesGUI getInstance() { return ourInstance;}

    public static void openGUI(Player staff, OfflinePlayer target) {
        String targetName = target.getName();
        String targetUUID = target.getUniqueId().toString();

        Inventory inv = Bukkit.createInventory(null, 54, "Chat Offences - ");

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

        inv.setItem(11, new ItemBuilder(Material.PAPER).displayname("&aExcessive Caps").lore("&7Sending an excessive amount of", "&7capitals over one or multiple lines of text.").build());
        inv.setItem(12, new ItemBuilder(Material.PAPER).displayname("&aSpamming").lore("&7Sending the same or a similar", "&7message in chat multiple times.").build());
        inv.setItem(13, new ItemBuilder(Material.PAPER).displayname("&aCharacter Spam").lore("&7Sending the same character an", "&7excessive amount of times or", "&7sending random symbols or characters", "&7many times.").build());
        inv.setItem(14, new ItemBuilder(Material.PAPER).displayname("&aChat Trolling").lore("&7Intentionally making attempts (through", "&7the sending of messages) to confuse", "&7or mislead players.").build());
        inv.setItem(15, new ItemBuilder(Material.PAPER).displayname("&aInappropriate Language").lore("&7Sending messages which include", "&7language of an inappropriate nature.").build());
        inv.setItem(20, new ItemBuilder(Material.PAPER).displayname("&aGeneral Rudeness").lore("&7Showing a significant amount of", "&7disrespect/rudeness to players or", "&7staff members.").build());
        inv.setItem(21, new ItemBuilder(Material.PAPER).displayname("&cHarassment").lore("&7Unwanted behaviour towards a user,", "&7or unwanted use of sexual language", "&7towards a user.").build());
        inv.setItem(22, new ItemBuilder(Material.PAPER).displayname("&cDiscrimination").lore("&7Discriminating towards a certain", "&7group of people. This could be", "&7a religion, race, sexuality, etc.").build());
        inv.setItem(23, new ItemBuilder(Material.PAPER).displayname("&cRacism").lore("&7Making derogatory remarks towards", "&7a certain race.").build());
        inv.setItem(24, new ItemBuilder(Material.PAPER).displayname("&cMalicious Threats &l(REAL LIFE)").lore("&7Threatening to harm another player", "&7and/or people associated with that player.", "&7Also, threatening to break hack/DDOS", "&7the player or server.").build());
        inv.setItem(30, new ItemBuilder(Material.PAPER).displayname("&cAbusive Behaviour").lore("&7Being verbally abusive to another", "&7player with the intent to", "&7emotionally harm that player.").build());
        inv.setItem(31, new ItemBuilder(Material.PAPER).displayname("&cAdvertisement").lore("&7Advertising another server or", "&7service, with the intent to", "&7draw players away from test.").build());
        inv.setItem(32, new ItemBuilder(Material.PAPER).displayname("&cUnapproved Links").lore("&7Sending links in chat that are", "&7not included on the Approved Links List.").build());

        inv.setItem(49, new ItemBuilder(Material.OBSIDIAN).displayname("&eGo Back").lore("&7Go back to your", "&7previous page.").build());

        new BukkitRunnable() {
            @Override
            public void run() {
                staff.openInventory(inv);
            }

        }.runTaskLater(Main.getInstance(), 1);
    }

}
