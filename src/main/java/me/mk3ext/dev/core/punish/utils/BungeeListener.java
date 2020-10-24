package me.mk3ext.dev.core.punish.utils;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.HashMap;

public class BungeeListener implements PluginMessageListener {

    public static HashMap<Integer, String[]> requests = new HashMap<>();

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
        //debug.//debug("-b");
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();

        if (subchannel.equals("PlayerList")) {
            //debug.//debug("a");
            String server = in.readUTF();
            String[] playerList = in.readUTF().split(", ");
            //debug.//debug("b");
            if (requests.containsKey(1)) {
                //debug.//debug("c");
                requests.replace(1, playerList);
            } else {
                requests.put(1, playerList);
                //debug.//debug("d");
            }
        }
    }


}
