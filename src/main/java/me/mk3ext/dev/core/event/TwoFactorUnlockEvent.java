package me.mk3ext.dev.core.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TwoFactorUnlockEvent extends Event {

    //TODO CURRENTLY REDUNDANT CLASS

    public Player getPlayer() {
        return player;
    }

    private Player player;
    private static final HandlerList HANDLERS_LIST = new HandlerList();

    public TwoFactorUnlockEvent(Player player) {
        this.player = player;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
}
