package me.mk3ext.dev.core.suffix.suffixs;

import me.mk3ext.dev.core.suffix.ISuffix;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class SuffixCAT implements ISuffix {

    @Override
    public String getSuffixName() {
        return "Cat";
    }

    @Override
    public ChatColor getColour() {
        return ChatColor.GOLD;
    }

    @Override
    public String getFriendlyName() {
        return "CAT";
    }

    @Override
    public Material getIcon() {
        return Material.OBSIDIAN;
    }
}
