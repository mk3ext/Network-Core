package me.mk3ext.dev.core.player;

import me.mk3ext.dev.core.Main;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

public class MPrefsManager {

    public static void setSoundsOn(String uuid) {
        Main.getInstance().mongoHandler.setPlayerDoc(true,"messageSounds",uuid);
    };

    public static void setSoundsOff(String uuid) {
        Main.getInstance().mongoHandler.setPlayerDoc(false,"messageSounds",uuid);
    };

    public static boolean getSounds(String uuid) {
      Document data = (Document) Main.getInstance().mongoHandler.getPlayers().find(eq("uuid",uuid)).first();
      return data.getBoolean("messageSounds");
    };

    public static void setMessagesOn(String uuid) {
        Main.getInstance().mongoHandler.setPlayerDoc(true,"messageToggle",uuid);
    };

    public static void setMessagesOff(String uuid) {
        Main.getInstance().mongoHandler.setPlayerDoc(false,"messageToggle",uuid);
    };

    public static boolean getMessages(String uuid) {
        Document data = (Document) Main.getInstance().mongoHandler.getPlayers().find(eq("uuid",uuid)).first();
        return data.getBoolean("messageToggle");
    };
}
