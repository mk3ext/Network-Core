package me.mk3ext.dev.core.suffix.suffixs;

import me.mk3ext.dev.core.suffix.ISuffix;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class SuffixSMEXY implements ISuffix {

    @Override
    public String getSuffixName() {
        return "SMEXY";
    }

    @Override
    public ChatColor getColour() {
        return ChatColor.LIGHT_PURPLE;
    }

    @Override
    public String getFriendlyName() {
        return "SMEXY";
    }

    @Override
    public Material getIcon() {
        return Material.SUNFLOWER;
    }
}