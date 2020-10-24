package me.mk3ext.dev.core.listeners;

import me.mk3ext.dev.core.rank.RankGroupsUtil;
import me.mk3ext.dev.core.util.UtilColour;
import me.mk3ext.dev.core.profiles.Profile;
import me.mk3ext.dev.core.reports.Report;
import me.mk3ext.dev.core.reports.ReportOutcome;
import me.mk3ext.dev.core.reports.ReportsManagement;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

import java.util.UUID;

public class InventoryClick implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {
        Profile prof = new Profile((Player) e.getWhoClicked());

        if (!(e.getSlotType().equals(InventoryType.SlotType.OUTSIDE))) {
            if (e.getView().getTitle() != null) {
                if (RankGroupsUtil.isStaff(prof.getRank()) && e.getView().getTitle().contains("Report Handle -")) {
                    e.setCancelled(true);
                    switch (e.getCurrentItem().getType()) {
                        case CHEST:
                            return;
                        case EMERALD:
                            int id = Integer.parseInt(e.getView().getTitle().replace("Report Handle - ", ""));
                            System.out.println(id);
                            ReportsManagement.handleReport(id, ReportOutcome.ACCEPTED, prof.getUuid());
                            prof.sendMessage("&8&l[&d&lTEST &c&lReport&8&l] &7You &aaccepted &7the report.");
                            Report rep = new Report(id);
                            if (Bukkit.getPlayer(UUID.fromString(rep.getFromUUID())) != null) {
                                Bukkit.getPlayer(UUID.fromString(rep.getFromUUID())).sendMessage(UtilColour.format("&8(&9Reports&8) &7A report you made against &6" + Bukkit.getOfflinePlayer(UUID.fromString(rep.getOffenderUUID())).getName() + " &7has been closed by a staff member."));
                            }
                            e.getWhoClicked().closeInventory();
                            return;
                        case REDSTONE:
                            int id1 = Integer.parseInt(e.getView().getTitle().replace("Report Handle - ", ""));
                            ReportsManagement.handleReport(id1, ReportOutcome.DENIED, prof.getUuid());
                            prof.sendMessage("&8&l[&d&lTEST &c&lReport&8&l] &7You &cdenied &7the report.");
                            Report rep1 = new Report(id1);
                            if (Bukkit.getPlayer(UUID.fromString(rep1.getFromUUID())) != null) {
                                Bukkit.getPlayer(UUID.fromString(rep1.getFromUUID())).sendMessage(UtilColour.format("&8(&9Reports&8) &7A report you made against &6" + Bukkit.getOfflinePlayer(UUID.fromString(rep1.getOffenderUUID())).getName() + " &7has been closed by a staff member."));
                            }
                            e.getWhoClicked().closeInventory();
                            return;
                        case LAVA_BUCKET:
                            int id2 = Integer.parseInt(e.getView().getTitle().replace("Report Handle - ", ""));
                            ReportsManagement.handleReport(id2, ReportOutcome.SYSTEM_ABUSE, prof.getUuid());
                            prof.sendMessage("&8&l[&d&lTEST &c&lReport&8&l] &7You closed the report - &6System Abuse&7.");
                            Report rep2 = new Report(id2);
                            if (Bukkit.getPlayer(UUID.fromString(rep2.getFromUUID())) != null) {
                                Bukkit.getPlayer(UUID.fromString(rep2.getFromUUID())).sendMessage(UtilColour.format("&8(&9Reports&8) &7A report you made against &6" + Bukkit.getOfflinePlayer(UUID.fromString(rep2.getOffenderUUID())).getName() + " &7has been closed by a staff member."));
                            }
                            e.getWhoClicked().closeInventory();
                            return;
                        case FEATHER:
                            int id3 = Integer.parseInt(e.getView().getTitle().replace("Report Handle - ", ""));
                            ReportsManagement.handleReport(id3, ReportOutcome.OUTCOME_NOT_REACHED, prof.getUuid());
                            prof.sendMessage("&8&l[&d&lTEST &c&lReport&8&l] &7You closed the report - &6oOutcome Not Reached&7.");
                            Report rep3 = new Report(id3);
                            if (Bukkit.getPlayer(UUID.fromString(rep3.getFromUUID())) != null) {
                                Bukkit.getPlayer(UUID.fromString(rep3.getFromUUID())).sendMessage(UtilColour.format("&8(&9Reports&8) &7A report you made against &6" + Bukkit.getOfflinePlayer(UUID.fromString(rep3.getOffenderUUID())) + " &7has been closed by a staff member."));
                            }
                            e.getWhoClicked().closeInventory();
                            return;
                    }
                }

                if (RankGroupsUtil.isStaff(prof.getRank()) && e.getView().getTitle().contains("Report Information -")) {
                    e.setCancelled(true);
                }
            }
        }
    }
}
