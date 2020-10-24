package me.mk3ext.dev.core.suffix;

import me.mk3ext.dev.core.suffix.suffixs.SuffixBABY;
import me.mk3ext.dev.core.suffix.suffixs.SuffixCAT;
import me.mk3ext.dev.core.suffix.suffixs.SuffixSMEXY;

public enum Suffixes {

    CAT(new SuffixCAT()),
    DOG(new SuffixBABY()),
    PENGUIN(new SuffixSMEXY());

    public ISuffix getSuffix() {
        return suffix;
    }

    private ISuffix suffix;

    Suffixes(ISuffix suffix) {
        this.suffix = suffix;
    }
}
