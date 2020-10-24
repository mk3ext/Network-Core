package me.mk3ext.dev.core.moderation;

import me.mk3ext.dev.core.Main;
import me.mk3ext.dev.core.event.VanishToggleEvent;
import me.mk3ext.dev.core.rank.RankGroupsUtil;
import me.mk3ext.dev.core.util.Common;
import me.mk3ext.dev.core.util.UtilColour;
import me.mk3ext.dev.core.profiles.Profile;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.mongodb.client.model.Filters.eq;

public class Vanish implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return false;
        }

        Player p = (Player) sender;
        Profile prof = new Profile(p);

        if (!(RankGroupsUtil.isStaff(prof.getRank()))) {
            prof.sendMessage(Common.NoPermsHelper);
            return false;
        }

        String uuid = prof.getUuid();
        if (isVanished(uuid)) {
            Main.getInstance().mongoHandler.setPlayerDoc(false,"vanished",uuid);
            for (Player pl : Bukkit.getOnlinePlayers()) {
                pl.showPlayer(p);
            }
            prof.sendMessage("&8&l[&d&lTEST &c&lVanish&8&l] &7You &cdisabled &7your vanish.");
            VanishToggleEvent event = new VanishToggleEvent(prof.getOnlinePlayerObject(), false);
            Bukkit.getPluginManager().callEvent(event);
            Main.redis.getTopic("CORE:staff").publish(UtilColour.format("&8&l[&d&lTEST &c&lStaff&8&l] &c" + prof.getName() + " &7has disabled vanish. &8[&d" + Main.getInstance().getConfig().getString("server-name") + "&8]"));
        } else {
            Main.getInstance().mongoHandler.setPlayerDoc(true,"vanished",uuid);
            for (Player pl : Bukkit.getOnlinePlayers()) {
                pl.hidePlayer(p);
            }
            prof.sendMessage("&8&l[&d&lTEST &c&lVanish&8&l] &7You &aenabled &7your vanish.");
            VanishToggleEvent event = new VanishToggleEvent(prof.getOnlinePlayerObject(), true);
            Bukkit.getPluginManager().callEvent(event);
            Main.redis.getTopic("CORE:staff").publish(UtilColour.format("&8&l[&d&lTEST &c&lStaff&8&l] &a" + prof.getName() + " &7has enabled vanish. &8[&d" + Main.getInstance().getConfig().getString("server-name") + "&8]"));
        }
        return true;
    }

    public static boolean isVanished(String uuid) {
        Document data = (Document) Main.getInstance().mongoHandler.getPlayers().find(eq("uuid",uuid)).first();
        return data.getBoolean("vanished");
    }
}
