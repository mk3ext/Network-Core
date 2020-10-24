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

public class ReportHandleCommand implements CommandExecutor {

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
            prof.sendMessage(Common.REPORTprefix + " &d" + CoreCommands.REPORT_HANDLE.getUsage());
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

        if (!(rep.getStatus().equals("NOT_HANDLED"))) {
            prof.sendMessage(Common.REPORTprefix + " &7That report has already been handled.");
            return false;
        }

        prof.sendMessage(Common.REPORTprefix + " &7Handling &dreport #" + rep.getId() + "&7...");

        Inventory inv = Bukkit.createInventory(null, 27, "Report Handle - " + rep.getId());
        inv.setItem(4, new ItemBuilder(Material.CHEST).displayname("&9&lReport ID - " + rep.getId()).lore("&7You are handling this report..").build());
        inv.setItem(10, new ItemBuilder(Material.EMERALD).displayname("&a&lAccepted").lore("&7Click to accept this report.").build());
        inv.setItem(12, new ItemBuilder(Material.REDSTONE).displayname("&c&lDenied").lore("&7Click to deny this report.").build());
        inv.setItem(14, new ItemBuilder(Material.LAVA_BUCKET).displayname("&d&lReport System Abuse").lore("&7Click to set this report as", "&7closed due to system abuse.").build());
        inv.setItem(16, new ItemBuilder(Material.FEATHER).displayname("&e&lOutcome Not Reached").lore("&7Click to set this report as", "&7outcome not reached.").build());
        if (ReportOutcome.valueOf(rep.getStatus()) != ReportOutcome.NOT_HANDLED) {
            inv.setItem(0, new ItemBuilder(Material.NETHER_STAR).displayname("&c&lWarning").lore("&7This report has already", "&7been handled by another staff member.").glow().build());
        }

        prof.getOnlinePlayerObject().openInventory(inv);

        return true;
    }
}
