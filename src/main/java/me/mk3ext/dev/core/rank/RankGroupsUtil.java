package me.mk3ext.dev.core.rank;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

public class RankGroupsUtil {

    //    DEFAULT(ChatColor.GRAY, "Default", "&7"),
    //    ALPHA(ChatColor.DARK_RED, "Alpha", "&c&lALPHA"),
    //    GAMMA(ChatColor.GREEN, "Gamma", "&a&lGAMMA"),
    //    OMEGA(ChatColor.BLUE, "Omega", "&b&lOMEGA"),
    //
    //    // SPECIAL RANKS
    //    MEDIA(ChatColor.DARK_PURPLE, "Media", "&5&lMEDIA"),


    private static final ImmutableSet<Rank> donator = Sets.immutableEnumSet(
            Rank.VIP,
            Rank.VIPPLUS,
            Rank.MVP,
            Rank.MEDIA,
            Rank.BUILDER
    );

    private static final ImmutableSet<Rank> staff = Sets.immutableEnumSet(
            Rank.TRAINEE,
            Rank.MOD,
            Rank.SRMOD,
            Rank.ADMIN,
            Rank.DEVELOPER,
            Rank.MANAGER
    );

    private static final ImmutableSet<Rank> management = Sets.immutableEnumSet(
            Rank.ADMIN,
            Rank.DEVELOPER,
            Rank.MANAGER
    );

    public static boolean isStaff(Rank r) {
        return staff.contains(r);
    }

    public static boolean isManagement(Rank r) {
        return management.contains(r);
    }

    public static boolean isModAbove(Rank r) {
        return r == Rank.SRMOD || r == Rank.ADMIN || r == Rank.DEVELOPER || r == Rank.MANAGER;
    }

    public static boolean isDonatorAbove(Rank r) {
        return donator.contains(r) || staff.contains(r);
    }
}
