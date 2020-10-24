package me.mk3ext.dev.core.database;

import me.mk3ext.dev.core.Main;
import org.bson.Document;

public class createRanks {

    private final Main instance = Main.getInstance();
    private mongoHandler mongoConnect = instance.mongoHandler;


    public createRanks(String uuid, String rank) {

        Document document = new Document("uuid", uuid);
        document.append("rank", rank);
        mongoConnect.getRanks().insertOne(document);
    }
}
