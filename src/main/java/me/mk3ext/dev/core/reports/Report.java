package me.mk3ext.dev.core.reports;

import me.mk3ext.dev.core.Main;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

public class Report {

    private int id;
    private String offenderUUID;
    private String fromUUID;
    private String reason;
    private String status;

    public int getId() {
        return id;
    }

    public String getOffenderUUID() {
        return offenderUUID;
    }

    public String getFromUUID() {
        return fromUUID;
    }

    public String getReason() {
        return reason;
    }

    public String getStatus() {
        return status;
    }

    public String getServer() {
        return server;
    }

    private String server;

    public Report(int id) {
        this.id = id;

        Document data = (Document) Main.getInstance().mongoHandler.getReports().find(eq("_id",id)).first();
        this.offenderUUID = data.getString("offenderUUID");
        this.fromUUID = data.getString("fromUUID");
        this.reason = data.getString("reason");
        this.status = data.getString("status");
        this.server = data.getString("server");

    }
}
