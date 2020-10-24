package me.mk3ext.dev.core.punish;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import javafx.util.Pair;
import me.mk3ext.dev.core.profiles.Profile;
import me.github.wert.Core.punish.gui.*;
import me.mk3ext.dev.core.punish.reason.Reasons;
import me.mk3ext.dev.core.rank.Rank;
import me.mk3ext.dev.core.rank.RankGroupsUtil;
import me.mk3ext.dev.core.util.UtilColour;
import me.mk3ext.dev.core.util.UtilConsole;
import me.mk3ext.dev.core.punish.gui.*;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

import java.sql.SQLException;
import java.util.Objects;

public class GUIClickHandler implements Listener {

    private static final ImmutableSet<Material> Mats = Sets.immutableEnumSet(
            Material.PLAYER_HEAD,
            Material.PAPER,
            Material.DIAMOND_SWORD,
            Material.TNT,
            Material.BOOK,
            Material.ENDER_PEARL,
            Material.OBSIDIAN,
            Material.REDSTONE,
            Material.WRITABLE_BOOK,
            Material.REDSTONE_BLOCK,
            Material.EMERALD_BLOCK
    );

    private boolean isValid(final Material mat) {
        return Mats.contains(mat);
    }


    @EventHandler(priority= EventPriority.HIGHEST)
    public void onClick(InventoryClickEvent e) throws SQLException {
        Player p = (Player) e.getWhoClicked();
        if (!(e.getSlotType().equals(InventoryType.SlotType.OUTSIDE))) {
            Material type = Objects.requireNonNull(e.getCurrentItem()).getType();
            e.getView().getTitle();
            if (e.getView().getTitle().startsWith("Punishment -")) {
                //debug.//debug("2");
                if (isValid(type)) {
                    //debug.//debug("3");
                    if (!(RankGroupsUtil.isStaff(new Profile(p).getRank()))) {
                        //debug.//debug("4");
                        p.closeInventory();
                        p.sendMessage(UtilColour.format("&8&l[&d&lTEST &c&lPunish&8&l] &7Hmmm, you shouldn't be able to do that?"));
                        e.setCancelled(true);
                        return;
                    }
                }
                OfflinePlayer target = VariableMan.stafftarget.get(p);
                e.setCancelled(true);
                //debug.//debug("5");
                //debug.//debug(type.toString());
                switch (type) {
                    case PAPER:
                        ChatOffencesGUI.openGUI(p, target);
                        break;
                    case DIAMOND_SWORD:
                        ClientModificationsGUI.openGUI(p, target);
                        break;
                    case TNT:
                        GameplayOffencesGUI.openGUI(p, target);
                        break;
                    case BOOK:
                        PunishHistoryGUI.openGUI(p, target);
                        break;
                }
            } else {
                //debug.//debug("1");
            }
            if (e.getView().getTitle().startsWith("Chat Offences -") ||
                    e.getView().getTitle().startsWith("Gameplay Offences -") ||
                    e.getView().getTitle().startsWith("Client Modifications -")) {
                if (isValid(type)) {
                    if (!(RankGroupsUtil.isStaff(new Profile(p).getRank()))) {
                        //debug.//debug("4");
                        p.closeInventory();
                        p.sendMessage(UtilColour.format("&8&l[&d&lTEST &c&lPunish&8&l] &7Hmmm, you shouldn't be able to do that?"));
                        e.setCancelled(true);
                        return;
                    }
                }
                OfflinePlayer target = VariableMan.stafftarget.get(p);
                e.setCancelled(true);
                switch (type) {
                    case PAPER:
                        String itemNameTemp = e.getCurrentItem().getItemMeta().getDisplayName().replace(UtilColour.format("&a"), "");
                        String itemName1 = itemNameTemp.replace(UtilColour.format("&d"), "");
                        String itemName = itemName1.replace(UtilColour.format("&c"), "");
                        String itemName2 = itemName.replace(UtilColour.format(" &l(REAL LIFE)"), "");
                        String finalName = itemName2.replace(" ", "");

                        PunishConfirmGUI.openGUI(p, target, Reasons.valueOf(finalName).toString());
                        break;
                    case OBSIDIAN:
                        if (new Profile(p).getRank().equals(Rank.DEVELOPER)) {
                            PunishGUIMainDev.openGUI(p, target);
                        } else {
                            PunishMainGUI.openGUI(p, target);
                        }
                        break;
                    case BOOK:
                        PunishHistoryGUI.openGUI(p, target);
                }
            }

            if (e.getView().getTitle().startsWith("History -")) {
                if (isValid(type)) {
                    if (!(RankGroupsUtil.isStaff(new Profile(p).getRank()))) {
                        //debug.//debug("4");
                        p.closeInventory();
                        p.sendMessage(UtilColour.format("&8&l[&d&lTEST &c&lPunish&8&l] &7Hmmm, you shouldn't be able to do that?"));
                        e.setCancelled(true);
                        return;
                    }
                }
                OfflinePlayer target = VariableMan.stafftarget.get(p);
                UtilConsole.debug(target.getName());
                e.setCancelled(true);

                switch (type) {
                    case OBSIDIAN:
                        if (new Profile(p).getRank().equals(Rank.DEVELOPER)) {
                            PunishGUIMainDev.openGUI(p, target);
                        } else {
                            PunishMainGUI.openGUI(p, target);
                        }
                        break;
                    case REDSTONE:
                        if (DatabaseHandler.isInAppeals(p.getUniqueId().toString())) {
                            String id = e.getCurrentItem().getItemMeta().getDisplayName().replace(UtilColour.format("&c"), "").replace(UtilColour.format("&a"), "").replace("Punishment #", "");
                            //debug.//debug(id);
                            DatabaseHandler.setForcedRemoveTrue(id);
                            //debug.//debug(p.getUniqueId().toString());
                            PunishHistoryGUI.openGUI(p, target);
                        }
                        break;
                    case BOOK:
                        if (DatabaseHandler.isInAppeals(p.getUniqueId().toString())) {
                            String id = e.getCurrentItem().getItemMeta().getDisplayName().replace(UtilColour.format("&c"), "").replace(UtilColour.format("&a"), "").replace("Punishment #", "");
                            //debug.//debug(id);
                            DatabaseHandler.setForcedRemoveTrue(id);
                            //debug.//debug(p.getUniqueId().toString());
                            PunishHistoryGUI.openGUI(p, target);
                        }
                        break;
                    case WRITABLE_BOOK:
                        if (DatabaseHandler.isInAppeals(p.getUniqueId().toString())) {
                            String id = e.getCurrentItem().getItemMeta().getDisplayName().replace(UtilColour.format("&c"), "").replace(UtilColour.format("&a"), "").replace("Punishment #", "");
                            //debug.//debug(id);
                            DatabaseHandler.setForcedRemoveTrue(id);
                            //debug.//debug(p.getUniqueId().toString());
                            PunishHistoryGUI.openGUI(p, target);
                        }
                        break;
                    case DIAMOND_SWORD:
                        if (DatabaseHandler.isInAppeals(p.getUniqueId().toString())) {
                            String id = e.getCurrentItem().getItemMeta().getDisplayName().replace(UtilColour.format("&c"), "").replace(UtilColour.format("&a"), "").replace("Punishment #", "");
                            //debug.//debug(id);
                            DatabaseHandler.setForcedRemoveTrue(id);
                            //debug.//debug(p.getUniqueId().toString());
                            PunishHistoryGUI.openGUI(p, target);
                        }
                        break;
                    case TNT:
                        if (DatabaseHandler.isInAppeals(p.getUniqueId().toString())) {
                            String id = e.getCurrentItem().getItemMeta().getDisplayName().replace(UtilColour.format("&c"), "").replace(UtilColour.format("&a"), "").replace("Punishment #", "");
                            //debug.//debug(id);
                            DatabaseHandler.setForcedRemoveTrue(id);
                            //debug.//debug§(p.getUniqueId().toString());
                            PunishHistoryGUI.openGUI(p, target);
                        }
                        break;


                }

            }

            if (e.getView().getTitle().startsWith("Advanced Punish -")) {
                if (isValid(type)) {
                    if (!(RankGroupsUtil.isManagement(new Profile(p).getRank()))) {
                        //debug.//debug("4");
                        p.closeInventory();
                        p.sendMessage(UtilColour.format("&8&l[&d&lTEST &c&lPunish&8&l] &7Hmmm, you shouldn't be able to do that?"));
                        e.setCancelled(true);
                        return;
                    }
                }
                String reason = VariableMan.staffreason.get(p);
                OfflinePlayer target = VariableMan.stafftarget.get(p);
                e.setCancelled(true);

                switch (type) {
                    case BOW:
                        if (e.getSlot() == 31) {
                            PunishmentHandler.handle(target.getUniqueId().toString(), p, reason, -1, Punish.Punishments.WARN);
                        }
                        break;
                    case PAPER:
                        if (e.getSlot() == 9) {
                            PunishmentHandler.handle(target.getUniqueId().toString(), p, reason, 1, Punish.Punishments.TEMPMUTE);
                        } else if (e.getSlot() == 10) {
                            PunishmentHandler.handle(target.getUniqueId().toString(), p, reason, 2, Punish.Punishments.TEMPMUTE);
                        } else if (e.getSlot() == 11) {
                            PunishmentHandler.handle(target.getUniqueId().toString(), p, reason, 4, Punish.Punishments.TEMPMUTE);
                        } else if (e.getSlot() == 12) {
                            PunishmentHandler.handle(target.getUniqueId().toString(), p, reason, 12, Punish.Punishments.TEMPMUTE);
                        } else if (e.getSlot() == 13) {
                            PunishmentHandler.handle(target.getUniqueId().toString(), p, reason, 24, Punish.Punishments.TEMPMUTE);
                        } else if (e.getSlot() == 14) {
                            PunishmentHandler.handle(target.getUniqueId().toString(), p, reason, 72, Punish.Punishments.TEMPMUTE);
                        } else if (e.getSlot() == 15) {
                            PunishmentHandler.handle(target.getUniqueId().toString(), p, reason, 168, Punish.Punishments.TEMPMUTE);
                        } else if (e.getSlot() == 16) {
                            PunishmentHandler.handle(target.getUniqueId().toString(), p, reason, 720, Punish.Punishments.TEMPMUTE);
                        } else if (e.getSlot() == 17) {
                            PunishmentHandler.handle(target.getUniqueId().toString(), p, reason, -1, Punish.Punishments.MUTE);
                        }
                        break;
                    case FLINT_AND_STEEL:
                        if (e.getSlot() == 18) {
                            PunishmentHandler.handle(target.getUniqueId().toString(), p, reason, 1, Punish.Punishments.TEMPBAN);
                        } else if (e.getSlot() == 19) {
                            PunishmentHandler.handle(target.getUniqueId().toString(), p, reason, 2, Punish.Punishments.TEMPBAN);
                        } else if (e.getSlot() == 20) {
                            PunishmentHandler.handle(target.getUniqueId().toString(), p, reason, 4, Punish.Punishments.TEMPBAN);
                        } else if (e.getSlot() == 21) {
                            PunishmentHandler.handle(target.getUniqueId().toString(), p, reason, 12, Punish.Punishments.TEMPBAN);
                        } else if (e.getSlot() == 22) {
                            PunishmentHandler.handle(target.getUniqueId().toString(), p, reason, 24, Punish.Punishments.TEMPBAN);
                        } else if (e.getSlot() == 23) {
                            PunishmentHandler.handle(target.getUniqueId().toString(), p, reason, 72, Punish.Punishments.TEMPBAN);
                        } else if (e.getSlot() == 24) {
                            PunishmentHandler.handle(target.getUniqueId().toString(), p, reason, 168, Punish.Punishments.TEMPBAN);
                        } else if (e.getSlot() == 25) {
                            PunishmentHandler.handle(target.getUniqueId().toString(), p, reason, 720, Punish.Punishments.TEMPBAN);
                        } else if (e.getSlot() == 26) {
                            PunishmentHandler.handle(target.getUniqueId().toString(), p, reason, -1, Punish.Punishments.BAN);
                        }
                        break;
                }

            }

            if (e.getView().getTitle().startsWith("Confirm -")) {

                if (isValid(type)) {
                    if (!(RankGroupsUtil.isStaff(new Profile(p).getRank()))) {
                        //debug.//debug("4");
                        p.closeInventory();
                        p.sendMessage(UtilColour.format("&8&l[&d&lTEST &c&lPunish&8&l] &7Hmmm, you shouldn't be able to do that?"));
                        e.setCancelled(true);
                        return;
                    }
                }
                OfflinePlayer target = VariableMan.stafftarget.get(p);
                e.setCancelled(true);
                switch (type) {
                    case EMERALD_BLOCK:
                        Pair<Integer, Punish.Punishments> info = GetDuration.getDurationH(target.getUniqueId().toString(), Reasons.valueOf(VariableMan.staffreason.get(p)));

                        PunishmentHandler.handle(target.getUniqueId().toString(), p, Reasons.valueOf(VariableMan.staffreason.get(p)).toString(), info.getKey(), info.getValue());
                        break;
                    case REDSTONE_BLOCK:
                        p.getOpenInventory().close();
                        break;
                }
            }
        }
    }

}
