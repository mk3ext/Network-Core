package me.mk3ext.dev.core.database;

import me.mk3ext.dev.core.Main;
import org.bson.Document;

public class createReport {

    private final Main instance = Main.getInstance();
    private mongoHandler mongoConnect = instance.mongoHandler;


    public createReport(Integer id, String offenderUUID, String fromUUID, String reason, String server, String status, String handledByUUID) {

        Document document = new Document("_id",id);
        document.append("offenderUUID",offenderUUID);
        document.append("fromUUID",fromUUID);
        document.append("reason",reason);
        document.append("server",server);
        document.append("status",status);
        document.append("handledByUUID",handledByUUID);
        mongoConnect.getReports().insertOne(document);

    }
}
