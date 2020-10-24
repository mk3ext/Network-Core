package me.mk3ext.dev.core.database;

import me.mk3ext.dev.core.Main;
import org.bson.Document;

public class createPunishment {

    private final Main instance = Main.getInstance();
    private mongoHandler mongoConnect = instance.mongoHandler;


    public createPunishment(String id, String target, String punisher, String reason, String gmtdatesent, String gmtdateexpire, String punishtype, String seen) {

        Document document = new Document("_id",id);
        document.append("target",target);
        document.append("punisher",punisher);
        document.append("reason",reason);
        document.append("gmtdatesent",gmtdatesent);
        document.append("gmtdateexpire",gmtdateexpire);
        document.append("punishtype",punishtype);
        document.append("forcedremove",false);
        document.append("seen",seen);
        mongoConnect.getPunishments().insertOne(document);
    }


}
