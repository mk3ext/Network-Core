package me.mk3ext.dev.core.enums;

public enum BServer {

    HUB("Hub", false, false),
    SKYWARS("Skywars", false, false);

    private String friendlyName;
    private boolean staffOnly, managementOnly;

    BServer(String friendlyName, Boolean staffOnly, Boolean managementOnly) {
        this.friendlyName = friendlyName;
        this.staffOnly = staffOnly;
        this.managementOnly = managementOnly;
    }
}
