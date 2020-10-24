package me.mk3ext.dev.core.rank;

import org.bukkit.ChatColor;

public enum Rank {

    DEFAULT(ChatColor.GRAY, "default", "&7", "Default description"),
    VIP(ChatColor.WHITE, "vip", "&dvip", "⭐ description"),
    VIPPLUS(ChatColor.WHITE, "vip+", "&dvip+", "⭐⭐ description"),
    MVP(ChatColor.WHITE, "mvp", "&dmvp", "⭐⭐⭐ description"),

    // SPECIAL RANKS
    MEDIA(ChatColor.DARK_PURPLE, "media", "&5&lMEDIA&f", "Media description"),
    BUILDER(ChatColor.AQUA, "builder", "&b&lBUILDER&f", "Builder description"),

    TRAINEE(ChatColor.YELLOW, "trainee", "&e&lTRAINEE&f", "Trainee description"),
    MOD(ChatColor.DARK_BLUE, "mod", "&9&lMOD&f", "Mod description"),
    SRMOD(ChatColor.DARK_BLUE, "srmod", "&9&lSR MOD&f", "Sr Mod description"),

    ADMIN(ChatColor.GOLD, "admin","&6&lADMIN&f", "Admin description"),
    DEVELOPER(ChatColor.RED, "developer", "&c&lDEV&f", "Developer description"),
    MANAGER(ChatColor.DARK_RED, "manager", "&4&lMANAGER&f", "Manager description"),
    OWNER(ChatColor.DARK_RED, "owner", "&4&lMANAGER&f", "Owner description");


    private ChatColor colour;
    private String name;
    private String prefix;
    private String description;

    Rank(ChatColor colour, String friendlyName, String prefix, String description) {
        this.colour = colour;
        this.name = friendlyName;
        this.prefix = prefix;
        this.description = description;
    }

    public ChatColor getColour() {
        return colour;
    }

    public String getFriendlyName() {
        return name;
    }
    public String getPrefix() {
        return prefix;
    }

    public String getDescription() {
        return description;
    }

}
