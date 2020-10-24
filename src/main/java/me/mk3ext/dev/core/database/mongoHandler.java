package me.mk3ext.dev.core.database;

import me.mk3ext.dev.core.util.Common;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

public class mongoHandler {

    private MongoDatabase database;
    private MongoCollection players;
    private MongoCollection punishments;
    private MongoCollection reports;


    private MongoCollection appealsteam;
    private MongoCollection ranks;

    public void connect(){
        MongoClient client = MongoClients.create(Common.uri);
        setDatabase(client.getDatabase("test"));
        setAppealsteam(database.getCollection("appealsteam"));
        setPlayers(database.getCollection("players"));
        setPunishments(database.getCollection("punishments"));
        setReports(database.getCollection("reports"));
        setRanks(database.getCollection("ranks"));
    }

    public void setRankDoc(Object value, String identifier, String uuid) {
        Document document = new Document("uuid", uuid);
        Bson newValue = new Document(identifier, value);
        Bson updateOp = new Document("$set", newValue);
        ranks.updateOne(document, updateOp);
    }

    public void setPlayerDoc(Object value, String identifier, String uuid) {
        Document document = new Document("uuid", uuid);
        Bson newValue = new Document(identifier, value);
        Bson updateOp = new Document("$set", newValue);
        players.updateOne(document, updateOp);
    }

    public void setPunishmentDoc(Object value, String identifier, String id) {
        Document document = new Document("_id", id);
        Bson newValue = new Document(identifier, value);
        Bson updateOp = new Document("$set", newValue);
        punishments.updateOne(document, updateOp);
    }

    public void setReportDoc(Object value, String identifier, int id) {
        Document document = new Document("_id", id);
        Bson newValue = new Document(identifier, value);
        Bson updateOp = new Document("$set", newValue);
        reports.updateOne(document, updateOp);
    }

    public void deletePunishmentDoc(String id) {
        Document document = new Document("_id", id);
        punishments.deleteOne(document);
    }
    public void deleteAppleDoc(String uuid) {
        Document document = new Document("uuid", uuid);
        appealsteam.deleteOne(document);
    }

    public MongoCollection getPunishments() { return punishments; }
    public void setPunishments(MongoCollection punishments) { this.punishments = punishments; }

    public MongoCollection getReports() { return reports; }
    public void setReports(MongoCollection reports) { this.reports = reports; }

    public MongoCollection getAppealsteam() { return appealsteam; }
    public void setAppealsteam(MongoCollection appealsteam) { this.appealsteam = appealsteam; }


    public MongoCollection getPlayers() {
        return players;
    }
    public void setPlayers(MongoCollection players) {
        this.players = players;
    }

    public MongoCollection getRanks() { return ranks; }
    public void setRanks(MongoCollection ranks) { this.ranks = ranks; }

    public MongoDatabase getDatabase() {
        return database;
    }
    public void setDatabase(MongoDatabase database) {
        this.database = database;
    }
}
