package me.mk3ext.dev.core.util;

import me.mk3ext.dev.core.rank.Rank;

public class UtilRankFormat {

    public static String formatName(Rank rank) {
        return (rank.getColour() + rank.getFriendlyName());
    }

    public static String getRankPrefix(Rank rank){
        return (UtilColour.format(rank.getPrefix()));
    }

}
