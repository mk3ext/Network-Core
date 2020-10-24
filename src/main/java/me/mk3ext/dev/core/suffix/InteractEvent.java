package me.mk3ext.dev.core.suffix;

import me.mk3ext.dev.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

import java.util.HashMap;
import java.util.List;

public class InteractEvent implements Listener {

    @EventHandler
    public void invclick(InventoryClickEvent e) {
        if (!(e.getSlotType().equals(InventoryType.SlotType.OUTSIDE))) {
            if (e.getView().getTitle() != null) {
                if (e.getCurrentItem() != null) {
                    if (e.getCurrentItem().hasItemMeta()) {
                        if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
                            String uuid = e.getWhoClicked().getUniqueId().toString();
                            if (e.getView().getTitle().equalsIgnoreCase("Active Suffix")) {
                                Material type = e.getCurrentItem().getType();
                                if (type == Material.BLAZE_ROD) {
                                    e.setCancelled(true);
                                } else if (type == Material.BARRIER) {
                                    e.setCancelled(true);
                                }

                                Suffixes active = SuffixManager.getActiveSuffix(uuid);

                                if (active != null) {
                                    if (type == active.getSuffix().getIcon()) {
                                            e.setCancelled(true);
                                            Main.getInstance().mongoHandler.setPlayerDoc(null,"activeSuffix",uuid);
                                            ((Player)e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 7F, 5F);
                                            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                                                SuffixGUI.openGUI((Player)e.getWhoClicked());
                                            }, 1L);
                                            return;
                                    }
                                }

                                e.setCancelled(true);

                                List<Suffixes> ownedList = SuffixManager.getSuffixes(e.getWhoClicked().getUniqueId().toString());
                                HashMap<Material, ISuffix> map = new HashMap<>();
                                if (ownedList != null) {
                                    for (Suffixes sf : ownedList) {
                                        map.put(sf.getSuffix().getIcon(), sf.getSuffix());
                                    }
                                }

                                if (map.containsKey(e.getCurrentItem().getType())) {
                                    SuffixManager.setActiveSuffix(e.getWhoClicked().getUniqueId().toString(), map.get(e.getCurrentItem().getType()));
                                    ((Player)e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 7F, 5F);
                                    Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                                        SuffixGUI.openGUI((Player)e.getWhoClicked());
                                    }, 1L);
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}
