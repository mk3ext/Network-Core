package me.mk3ext.dev.core.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BytesUpdateEvent extends Event {

    public Player getPlayer() {
        return player;
    }

    private Player player;
    private static final HandlerList HANDLERS_LIST = new HandlerList();

    public BytesUpdateEvent(Player appliedPlayer) {
        this.player = appliedPlayer;
    }

    @Override
    public HandlerList getHandlers() {
        return null;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
}
