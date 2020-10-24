package me.mk3ext.dev.core.suffix.suffixs;

import me.mk3ext.dev.core.suffix.ISuffix;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class SuffixBABY implements ISuffix {

    @Override
    public String getSuffixName() {
        return "BABY";
    }

    @Override
    public ChatColor getColour() {
        return ChatColor.RED;
    }

    @Override
    public String getFriendlyName() {
        return "BABY";
    }

    @Override
    public Material getIcon() {
        return Material.ROSE_BUSH;
    }
}