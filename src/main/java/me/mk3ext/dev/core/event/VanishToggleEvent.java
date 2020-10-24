package me.mk3ext.dev.core.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class VanishToggleEvent extends Event {

    public Player getPlayer() {
        return player;
    }
    public boolean getWhatAreTheyNow() { return whatAreTheyNow; }

    private Player player;
    private boolean whatAreTheyNow;

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    public VanishToggleEvent(Player player, boolean whatAreTheyNow) {
        this.player = player;
        this.whatAreTheyNow = whatAreTheyNow;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
}
