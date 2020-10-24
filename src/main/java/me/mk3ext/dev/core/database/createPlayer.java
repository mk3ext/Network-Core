package me.mk3ext.dev.core.database;

import me.mk3ext.dev.core.Main;
import org.bson.Document;

import java.util.ArrayList;

public class createPlayer {


    private final Main instance = Main.getInstance();
    private mongoHandler mongoConnect = instance.mongoHandler;


    public createPlayer(String uuid, int bytes, ArrayList suffixes, String activeSuffix, Boolean messageToggle, Boolean messageSounds){

        Document document = new Document("uuid",uuid);
        document.append("bytes",bytes);
        document.append("suffixes",suffixes);
        document.append("activeSuffix",activeSuffix);
        document.append("messageToggle",messageToggle);
        document.append("messageSounds",messageSounds);
        document.append("vanished",false);
        mongoConnect.getPlayers().insertOne(document);

    }
}

