package me.mk3ext.dev.core.moderation;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public class SpyManager {

    private static SpyManager instance;

    public static SpyManager getInstance() {
        if (instance == null) {
            instance = new SpyManager();
        }
        return instance;
    }

    public ArrayList<Player> messageSpyEnabled = new ArrayList<>();
    public ArrayList<Player> contactSpyEnabled = new ArrayList<>();
}
