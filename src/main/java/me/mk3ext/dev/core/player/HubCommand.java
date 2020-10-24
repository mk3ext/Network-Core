package me.mk3ext.dev.core.player;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.mk3ext.dev.core.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HubCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeUTF("Connect");
                out.writeUTF("Hub");
                p.sendPluginMessage(Main.getInstance(), "BungeeCord", out.toByteArray());
        } else {
            return true;
        }

        return true;
    }
}
