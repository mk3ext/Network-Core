package me.mk3ext.dev.core.suffix;

import me.mk3ext.dev.core.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

public class SuffixGUI implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;

            openGUI(p);
        } else {
            return true;
        }

        return true;
    }

    public static void openGUI(Player p) {
        Inventory inv = Bukkit.createInventory(null, 27, "Active Suffix");

        inv.setItem(4, new ItemBuilder(Material.BLAZE_ROD).displayname("&9&lActive Suffix").lore("&7Edit your active suffix.").build());

        ArrayList<Suffixes> suffixesArray = SuffixManager.getSuffixes(p.getUniqueId().toString());

        if (suffixesArray == null) {
            inv.setItem(9, new ItemBuilder(Material.BARRIER).displayname("&c&lNo Suffixes Owned").lore("&7You do not own any suffixes.").glow().build());
        } else {
            int slot = 9;
            for (Suffixes suf : suffixesArray) {
                if (SuffixManager.getActiveSuffix(p.getUniqueId().toString()) == suf) {
                    inv.setItem(slot, new ItemBuilder(suf.getSuffix().getIcon()).displayname(suf.getSuffix().getColour() + "&l" + suf.getSuffix().getFriendlyName()).lore("&7This is currently your active suffix. Press to disable.").glow().build());
                } else {
                    inv.setItem(slot, new ItemBuilder(suf.getSuffix().getIcon()).displayname(suf.getSuffix().getColour() + "&l" + suf.getSuffix().getFriendlyName()).lore("&7Make this your active suffix.").build());
                }
                slot++;
            }
        }

        p.openInventory(inv);
    }
}
