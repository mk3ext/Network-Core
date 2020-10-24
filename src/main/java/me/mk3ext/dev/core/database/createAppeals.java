package me.mk3ext.dev.core.database;

import me.mk3ext.dev.core.Main;
import org.bson.Document;

public class createAppeals {


    private final Main instance = Main.getInstance();
    private mongoHandler mongoConnect = instance.mongoHandler;


    public createAppeals(String uuid) {
        Document document = new Document("uuid", uuid);
        mongoConnect.getAppealsteam().insertOne(document);

    }
}
