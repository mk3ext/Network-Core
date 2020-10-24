package me.mk3ext.dev.core.punish;

import me.mk3ext.dev.core.util.Common;
import me.mk3ext.dev.core.util.UtilColour;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.List;

public class Prevent implements Listener {

    @EventHandler
    public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
        Player p = event.getPlayer();
        String message = event.getMessage().toLowerCase();
        List<String> cmds = new ArrayList<>();
        String uuid = p.getUniqueId().toString();
        cmds.add("msg"); cmds.add("w"); cmds.add("tell"); cmds.add("whisper"); cmds.add("message"); cmds.add("reply"); cmds.add("r");
        if (DatabaseHandler.isPerm(uuid,"MUTED").getKey() || DatabaseHandler.isTemp(uuid,"MUTED").getKey()) {
            for (String blacklist : cmds) {
                if (message.startsWith("/" + blacklist.toLowerCase() + " ") || message.equals("/" + blacklist.toLowerCase())) {
                    event.setCancelled(true);
                    event.getPlayer().sendMessage(UtilColour.format(Common.PUNISHprefix + " That command is disabled whilst muted."));
                    break;
                }
            }
        }
    }
}
