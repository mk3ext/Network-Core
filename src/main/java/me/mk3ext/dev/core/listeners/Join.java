package me.mk3ext.dev.core.listeners;

import me.mk3ext.dev.core.Main;
import me.mk3ext.dev.core.database.createPlayer;
import me.mk3ext.dev.core.moderation.SpyManager;
import me.mk3ext.dev.core.moderation.Vanish;
import me.mk3ext.dev.core.rank.RankGroupsUtil;
import me.mk3ext.dev.core.rank.RankMethod;
import me.mk3ext.dev.core.util.Common;
import me.mk3ext.dev.core.profiles.Profile;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;

public class Join implements Listener {

    public static HashMap<Player, String> ip = new HashMap<>();

    @EventHandler
    public void join(PlayerJoinEvent e) {
        Profile prof = new Profile(e.getPlayer());
        String uuid = e.getPlayer().getUniqueId().toString();

        if (Main.getInstance().mongoHandler.getPlayers().find(new Document("uuid", uuid)).first() == null) {
            new createPlayer(uuid, 0, new ArrayList(), "", false, false);
        }

        if (RankMethod.getRank(uuid) == null) {
            RankMethod.setRank(uuid, "DEFAULT");
        }

        if (RankGroupsUtil.isStaff(RankMethod.getRank(uuid))) {

            SpyManager.getInstance().contactSpyEnabled.add(e.getPlayer());

            new BukkitRunnable() {
                public void run() {
                    String uuid = e.getPlayer().getUniqueId().toString();
                    if (Vanish.isVanished(uuid)) {
                        for (Player pl : Bukkit.getOnlinePlayers()) {
                            pl.hidePlayer(e.getPlayer());
                        }
                        new Profile(e.getPlayer()).sendMessage(Common.VANISHprefix + " &7Please be aware that you are currently &6vanished&7.");
                    }

                    e.getPlayer().performCommand("reports");
                }
            }.runTaskLater(Main.getInstance(), 5L);
        }

        e.setJoinMessage(null);


    }

    @EventHandler
    public void login(PlayerLoginEvent e) {
        if (ip.containsKey(e.getPlayer())) {
            ip.replace(e.getPlayer(), e.getAddress().getHostAddress());
        } else {
            ip.put(e.getPlayer(), e.getAddress().getHostAddress());
        }
    }

    @EventHandler
    public void quit(PlayerQuitEvent e) {
        ip.remove(e.getPlayer());
    }
}
