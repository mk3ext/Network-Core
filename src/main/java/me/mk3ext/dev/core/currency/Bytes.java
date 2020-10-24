package me.mk3ext.dev.core.currency;

import me.mk3ext.dev.core.Main;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;


public class Bytes {
    private final Main instance = Main.getInstance();

    public static void addBytes(String uuid, int toAdd) {
        Document data = (Document) Main.getInstance().mongoHandler.getPlayers().find(eq("uuid", uuid)).first();
        if(data == null) {
            return;
        }
        System.out.println(data);
        int bytes = data.getInteger("bytes",0);
        int newBytes = bytes + toAdd;
        Main.getInstance().mongoHandler.setPlayerDoc(newBytes,"bytes",uuid);
    };

    public static int getBytes(String uuid) {
        Document data = (Document) Main.getInstance().mongoHandler.getPlayers().find(eq("uuid", uuid)).first();
        return data.getInteger("bytes");
    };

    public static void setBytes(String uuid, int toSet) {
        Main.getInstance().mongoHandler.setPlayerDoc(toSet,"bytes",uuid);
    }

    public static void removeBytes(String uuid, int toRemove) {
        Document data = (Document) Main.getInstance().mongoHandler.getPlayers().find(eq("uuid", uuid)).first();
        int bytes = data.getInteger("bytes");
        int newBytes = bytes - toRemove;
        Main.getInstance().mongoHandler.setPlayerDoc(newBytes,"bytes",uuid);
    }
    public static boolean checkifhasBytes(String uuid) {
        return (Main.getInstance().mongoHandler.getPlayers().find(eq("uuid",uuid)).first() != null);
    }

}
