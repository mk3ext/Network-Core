package me.mk3ext.dev.core.listeners;

import me.mk3ext.dev.core.moderation.ChatSilence;
import me.mk3ext.dev.core.moderation.ChatSlowManager;
import me.mk3ext.dev.core.moderation.Vanish;
import me.mk3ext.dev.core.rank.RankGroupsUtil;
import me.mk3ext.dev.core.util.Common;
import me.mk3ext.dev.core.util.UtilColour;
import me.mk3ext.dev.core.profiles.Profile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.UUID;

public class TwoFactorEvents implements Listener {

    public HashMap<UUID, Long> chatTimes = new HashMap<>();
    private ChatSlowManager plugin = ChatSlowManager.getInstance();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void chat(AsyncPlayerChatEvent e) {
        //if (TwoFactorManagement.getInstance().isAuthLocked(e.getPlayer().getUniqueId().toString())) {
         //   e.setCancelled(true);

            //TODO OLD 2FA CODE
            // try {
            //                Integer code = Integer.parseInt(e.getMessage());
            //                if (TwoFactorManagement.getInstance().playerInputCode(e.getPlayer(), code)) {
            //                    TwoFactorManagement.getInstance().removeAuthLocked(e.getPlayer().getUniqueId().toString());
            //                    TwoFactorManagement.getInstance().setLastIP(e.getPlayer().getUniqueId().toString(), Join.ip.get(e.getPlayer()));
            //                    TwoFactorManagement.getInstance().setLastAuth(e.getPlayer().getUniqueId().toString(), System.currentTimeMillis());
            //                    new Profile(e.getPlayer()).sendMessage(Common.TFAprefix + " &aAuthentication successful.");
            //                    Bukkit.getPluginManager().callEvent(new TwoFactorUnlockEvent(e.getPlayer()));
            //                } else {
            //                    new Profile(e.getPlayer()).sendMessage(Common.TFAprefix + " &7That code is incorrect.");
            //                }
            //            } catch (Exception ex) {
            //                new Profile(e.getPlayer()).sendMessage(Common.TFAprefix + " &7Codes can only contain numbers.");
            //            }
            if (RankGroupsUtil.isStaff(new Profile(e.getPlayer()).getRank())) {
                if (e.getMessage().startsWith("#")) {
                    e.setCancelled(true);
                    e.getPlayer().performCommand("sc " + e.getMessage().replace("#", ""));
                } else {
                    if (Vanish.isVanished(e.getPlayer().getUniqueId().toString())) {
                        e.setCancelled(true);
                        e.getPlayer().sendMessage(UtilColour.format(Common.VANISHprefix + " You cannot chat whilst vanished."));
                        return;
                    }
                }
            } else {
                if (ChatSilence.chatSilence) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(UtilColour.format( Common.CHATprefix + " &7The chat is currently silenced."));
                } else {
                    long chatSlow = plugin.chatSlow;
                    if (chatSlow > 0) {
                        if (!(RankGroupsUtil.isStaff(new Profile(e.getPlayer()).getRank()))) {
                            UUID uuid = e.getPlayer().getUniqueId();
                            long lastChat = this.chatTimes.getOrDefault(uuid, 0L);
                            long currentTime = System.currentTimeMillis();
                            long timeTill = lastChat + chatSlow - currentTime;
                            String formattedTimeTill = Long.toString(timeTill / 1000);
                            if (timeTill > 0) {
                                e.setCancelled(true);
                                e.getPlayer().sendMessage(UtilColour.format(Common.CHATprefix + " &7Chat Slow is enabled. Please wait &6" + formattedTimeTill + "&6 seconds&7."));
                            } else {
                                chatTimes.put(uuid, currentTime);
                            }
                        }
                    }
                }
            }
        }

    @EventHandler
    public void blockBreak(BlockBreakEvent e) {
       //  if (TwoFactorManagement.getInstance().isAuthLocked(e.getPlayer().getUniqueId().toString())) {
        //            e.setCancelled(true);
        //        }
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent e) {
        // if (TwoFactorManagement.getInstance().isAuthLocked(e.getPlayer().getUniqueId().toString())) {
        //            e.setCancelled(true);
        //        }
    }

    @EventHandler
    public void move(PlayerMoveEvent e) {
        // if (TwoFactorManagement.getInstance().isAuthLocked(e.getPlayer().getUniqueId().toString())) {
        //            e.getPlayer().teleport(e.getFrom());
        //        }
    }

    @EventHandler
    public void tab(PlayerChatTabCompleteEvent e) {
        if (e.getChatMessage().contains("bukkit") || e.getChatMessage().contains("about") || e.getChatMessage().contains("ver") ||
        e.getChatMessage().contains("version") || e.getChatMessage().contains("icanhasbukkit")) {
            e.getTabCompletions().clear();
        }
    }

    @EventHandler
    public void command(PlayerCommandPreprocessEvent e) {
        if ((e.getMessage().startsWith("/pl")) ||
                (e.getMessage().startsWith("/plugins")) ||
                (e.getMessage().startsWith("/ver")) ||
                (e.getMessage().startsWith("/version")) ||
                (e.getMessage().startsWith("/about")) ||
                (e.getMessage().startsWith("/bukkit:pl")) ||
                (e.getMessage().startsWith("/bukkit:plugins")) ||
                (e.getMessage().startsWith("/bukkit:ver")) ||
                (e.getMessage().startsWith("/icanhasbukkit")) ||
                (e.getMessage().startsWith("/bukkit:icanhasbukkit")) ||
                (e.getMessage().startsWith("/bukkit:about"))) {

            e.setCancelled(true);
            e.getPlayer().sendMessage(UtilColour.format(Common.NoSEEplugins));
        }

        if (e.getMessage().equalsIgnoreCase("/me") || e.getMessage().startsWith("/bukkit:me")) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(UtilColour.format("&cThat command is disabled."));
        }

        if (e.getMessage().startsWith("/help") ||
                e.getMessage().startsWith("/?") ||
                e.getMessage().startsWith("/bukkit:help") ||
                e.getMessage().startsWith("/bukkit:?")) {
            e.setCancelled(true);
        }
    }
}
