package me.mk3ext.dev.core.reports;

import me.mk3ext.dev.core.enums.CoreCommands;
import me.mk3ext.dev.core.rank.RankGroupsUtil;
import me.mk3ext.dev.core.util.Common;
import me.mk3ext.dev.core.util.ItemBuilder;
import me.mk3ext.dev.core.profiles.Profile;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class ReportInfoCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }

        Profile prof = new Profile((Player)sender);

        if (!(RankGroupsUtil.isStaff(prof.getRank()))) {
            prof.sendMessage(Common.NoPermsHelper);
            return false;
        }

        if (args.length != 1) {
            prof.sendMessage(Common.REPORTprefix + " &d" + CoreCommands.REPORT_INFO.getUsage());
            return false;
        }
        try {
            Integer.parseInt(args[0]);
        } catch (Exception e) {
            prof.sendMessage(Common.REPORTprefix + " &7That is not a number.");
            return false;
        }

        if (!(ReportsManagement.reportExists(Integer.parseInt(args[0])))) {
            prof.sendMessage(Common.REPORTprefix + " &7That report does not exist.");
            return false;
        }
        Report rep = new Report(Integer.parseInt(args[0]));

        prof.sendMessage(Common.REPORTprefix + " &7Viewing information for &dReport #" + rep.getId() + "&7...");

        Inventory inv = Bukkit.createInventory(null, 27, "Report Information - " + rep.getId());
        inv.setItem(4, new ItemBuilder(Material.CHEST).displayname("&9&lReport ID - " + rep.getId()).lore("&7You are viewing information", "&7for this report.").build());
        inv.setItem(10, new ItemBuilder(Material.IRON_PICKAXE).displayname("&e&lReporter - ").lore("&7" + Bukkit.getOfflinePlayer(UUID.fromString(rep.getFromUUID())).getName()).build());
        inv.setItem(12, new ItemBuilder(Material.IRON_PICKAXE).displayname("&e&lOffender - ").lore("&7" + Bukkit.getOfflinePlayer(UUID.fromString(rep.getOffenderUUID())).getName()).build());
        inv.setItem(14, new ItemBuilder(Material.IRON_PICKAXE).displayname("&e&lReason - ").lore("&7" + rep.getReason()).build());
        //inv.setItem(14, new ItemBuilder(Material.IRON_PICKAXE).displayname("&eServer -").lore("&7" + rep.getServer()).build());
        inv.setItem(16, new ItemBuilder(Material.IRON_PICKAXE).displayname("&e&lCurrent Status -").lore("&7" + rep.getStatus()).build());
        if (ReportOutcome.valueOf(rep.getStatus()) != ReportOutcome.NOT_HANDLED) {
            inv.setItem(0, new ItemBuilder(Material.NETHER_STAR).displayname("&c&lWarning").lore("&7This report has already", "&7been handled by another staff member.").glow().build());
        }

        prof.getOnlinePlayerObject().openInventory(inv);
        return true;
    }
}
