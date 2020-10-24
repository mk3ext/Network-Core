package me.mk3ext.dev.core.player;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class ReplyManager {

    private static ReplyManager instance;

    public static ReplyManager getInstance() {
        if (instance == null) {
            instance = new ReplyManager();
        }
        return instance;
    }

    public HashMap<Player, Player> playerReply = new HashMap<>();
}
