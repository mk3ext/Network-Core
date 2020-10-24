package me.mk3ext.dev.core.suffix;

import me.mk3ext.dev.core.Main;
import org.bson.Document;

import java.util.ArrayList;

import static com.mongodb.client.model.Filters.eq;

public class SuffixManager {

    public static ArrayList<Suffixes> getSuffixes(String uuid) {
        Document data = (Document) Main.getInstance().mongoHandler.getPlayers().find(eq("uuid",uuid)).first();
        String suffixes = "";
        suffixes += data.get("suffixes");

        String[] temp = suffixes.split(",");
        ArrayList<Suffixes> suffixes1 = new ArrayList<>();
        for (String str : temp) {
            if(str != null) {
                try {
                    suffixes1.add(Suffixes.valueOf(str.replace("[","").replace("]","").replace(" ","")));
                } catch (IllegalArgumentException e) {
                    return null;
                }
            }
        }

        return suffixes1.isEmpty() ? null : suffixes1;
    };

    public static Suffixes getActiveSuffix(String uuid) {
        Document data = (Document) Main.getInstance().mongoHandler.getPlayers().find(eq("uuid",uuid)).first();
        String str = data.getString("activeSuffix");
        if(str != null) {
            return Suffixes.valueOf(str);
        }
        return null;
    };

    public static void setActiveSuffix(String uuid, ISuffix suffix) {
        Main.getInstance().mongoHandler.setPlayerDoc(suffix.getSuffixName(),"activeSuffix",uuid);
    };

    public static void addSuffix(String uuid, ISuffix suffix) {
        ArrayList<Suffixes> suf = new ArrayList<>();
        if(getSuffixes(uuid) != null) {
            suf.addAll(getSuffixes(uuid));
        }
        ArrayList<String> suffixes = new ArrayList<>();

        for (Suffixes suf1 : suf) {
            suffixes.add(suf1.getSuffix().getSuffixName());
        }
        suffixes.add(suffix.getSuffixName());

        Main.getInstance().mongoHandler.setPlayerDoc(suffixes,"suffixes",uuid);
    };

    public static void removeSuffix(String uuid, ISuffix suffix) {
        ArrayList<Suffixes> suf = new ArrayList<>();
        if(getSuffixes(uuid) != null) {
            suf.addAll(getSuffixes(uuid));
        }
        Suffixes suffixobj = null;

        for (Suffixes suffixes : suf) {
            if(suffixes.getSuffix() == (suffix)) {
                suffixobj = suffixes;
            }
        }
        if (suffixobj == null) {
            return;
        }

        suf.remove(suffixobj);
        String suffixRemoved = suf.toString().replace("[", "").replace("]", "").replace(" ", "");
        if(getActiveSuffix(uuid) == suffixobj) {
            Main.getInstance().mongoHandler.setPlayerDoc(null,"activeSuffix",uuid);
        }
        Main.getInstance().mongoHandler.setPlayerDoc(suffixRemoved,"suffixes",uuid);
    }
}
