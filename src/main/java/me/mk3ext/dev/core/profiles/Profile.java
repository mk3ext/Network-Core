package me.mk3ext.dev.core.profiles;

import me.mk3ext.dev.core.enums.BServer;
import me.mk3ext.dev.core.listeners.Join;
import me.mk3ext.dev.core.rank.Rank;
import me.mk3ext.dev.core.rank.RankMethod;
import me.mk3ext.dev.core.util.UtilColour;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class Profile {

    public Player getOnlinePlayerObject() {
        return onlinePlayerObject;
    }

    public Rank getRank() {
        return rank;
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String rankDesc(){
        return description;
    }

    public BServer getCurrentServer() {
        return currentServer;
    }

    public Profile sendMessage(String msg) {
        onlinePlayerObject.sendMessage(UtilColour.format(msg));
        return this;
    }

    private Player onlinePlayerObject;
    private Rank rank;
    private String uuid;
    private String name;
    private String description;
    private BServer currentServer;
    private String ipAddress;

    public Profile(Player player) {
        this.onlinePlayerObject = player;
        this.uuid = player.getUniqueId().toString();
        this.rank = RankMethod.getRank(uuid);
        this.name = player.getName();
        this.ipAddress = Join.ip.get(player);
        this.description = description;
        //this.isOnlineGlobal = bungee;
        //this.currentServer = getserver;
    }

    public Profile(OfflinePlayer player) {
        this.onlinePlayerObject = null;
        this.uuid = player.getUniqueId().toString();
        this.rank = RankMethod.getRank(uuid);
        this.name = player.getName();
        this.description = RankMethod.getRank(uuid).getDescription();
        //this.isOnlineGlobal = bungee;
        //this.currentServer = getserver;
    }
}
