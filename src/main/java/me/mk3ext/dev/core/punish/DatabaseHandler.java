package me.mk3ext.dev.core.punish;

import me.mk3ext.dev.core.Main;
import me.mk3ext.dev.core.database.createPunishment;
import me.mk3ext.dev.core.punish.reason.ChatReasons1;
import me.mk3ext.dev.core.punish.reason.ChatReasons2;
import me.mk3ext.dev.core.punish.reason.GameplayReasons;
import javafx.util.Pair;
import com.mongodb.client.MongoIterable;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class DatabaseHandler {



    public static void writeInto(String target, String punisher, String reason, String gmtdatesent, String gmtdateexpire, String punishtype, String seen) {
        int count = Math.toIntExact(Main.getInstance().mongoHandler.getPunishments().countDocuments());
        count = count + 1;
        new createPunishment(Integer.toString(count),target,punisher,reason,gmtdatesent,gmtdateexpire,punishtype,seen);
    }

    public static void setForcedRemoveTrue(String id) {
        Main.getInstance().mongoHandler.setPunishmentDoc(true,"forcedremove",id);
    }

    public static void remove(String id) {
        Main.getInstance().mongoHandler.deletePunishmentDoc(id);
    }

    public static void setSeenTrue(String id) {
        Main.getInstance().mongoHandler.setPunishmentDoc(true,"seen","uuid");
    }

    public static List<String> getGameplayPunishments(String target) {
        List<String> badIDs = new ArrayList<>();

        for (Object o : Main.getInstance().mongoHandler.getPunishments().find(eq("target", target))) {

            for (GameplayReasons r : GameplayReasons.values()) {
                String reason = r.toString();
                Document data = (Document) o;
                String re = data.getString("reason");
                if(re.equals(reason)) {
                    badIDs.add(data.getString("_id"));
                }
            }
        }
        return badIDs;
    };

    public static List<String> getSpecificPunishments(String target, String reason) {
        List<String> badIDs = new ArrayList<>();
        for (Object o : Main.getInstance().mongoHandler.getPunishments().find(and(eq("target", target),eq("reason",reason)))) {

            Document data = (Document) o;
            String re = data.getString("reason");
            if(re.equals(reason)) {
                badIDs.add(data.getString("_id"));
            }
        }
        return badIDs;
    }

    public static Pair<Boolean, String> isPerm(String target,String type) {
        List<String> badIDs = new ArrayList<String>();

        MongoIterable d = Main.getInstance().mongoHandler.getPunishments().find(and(eq("target", target), eq("punishtye", type)));
        if (d.cursor().hasNext()) {
            for (Object o : d) {
                Document data = (Document) o;
                badIDs.add(data.getString("_id"));
            }
        }
        return new Pair<>(false, "0");
    }

    public static Pair<Boolean, String> isTemp(String target,String type) {
        List<String> badIDs = new ArrayList<String>();

        MongoIterable d = Main.getInstance().mongoHandler.getPunishments().find(and(eq("target", target), eq("punishtye", type)));
        if (d.cursor().hasNext()) {
            for (Object o : d) {
                Document data = (Document) o;
                badIDs.add(data.getString("_id"));
            }
            List<String> idswherenotremoved = new ArrayList<>();
            for (String id : badIDs) {
                if (getForcedRemoved(id).equals("false")) {
                    idswherenotremoved.add(id);
                }
            }

            for (String id :idswherenotremoved) {
                String expire = getLongExpire(id);
                if (System.currentTimeMillis() < Long.parseLong(expire)) {
                    return new Pair<Boolean, String>(true, id);
                }
            }
        }
        return new Pair<>(false, "0");
    }

    public static List<String> getChatGoodPunishments(String target) {
        List<String> badIDs = new ArrayList<String>();
        for (Object o : Main.getInstance().mongoHandler.getPunishments().find(eq("target", target))) {

            for (ChatReasons1 r : ChatReasons1.values()) {
                String reason = r.toString();
                Document data = (Document) o;
                String re = data.getString("reason");
                if(re.equals(reason)) {
                    badIDs.add(data.getString("_id"));
                }
            }
        }
        return badIDs;
    }

    public static List<String> getChatBadPunishments(String target) {
        List<String> badIDs = new ArrayList<String>();
        for (Object o : Main.getInstance().mongoHandler.getPunishments().find(eq("target", target))) {

            for (ChatReasons2 r : ChatReasons2.values()) {
                String reason = r.toString();
                Document data = (Document) o;
                String re = data.getString("reason");
                if(re.equals(reason)) {
                    badIDs.add(data.getString("_id"));
                }
            }
        }
        return badIDs;
    }

    public static List<String> getAllIdsForPlayer(String target) {
        List<String> badIDs = new ArrayList<String>();
        for (Object o : Main.getInstance().mongoHandler.getPunishments().find(eq("target", target))) {

            Document data = (Document) o;
            badIDs.add(data.getString("_id"));
        }
        return badIDs;
    }

    public static String getReason(String id) {
        Document data = (Document) Main.getInstance().mongoHandler.getPunishments().find(eq("_id", id)).first();
        return data.getString("reason");
    }

    public static String getLongExpire(String id) {
        Document data = (Document) Main.getInstance().mongoHandler.getPunishments().find(eq("_id", id)).first();
        return data.getString("gmtdateexpire");
    }

    public static String getLongSent(String id) {
        Document data = (Document) Main.getInstance().mongoHandler.getPunishments().find(eq("_id", id)).first();
        return data.getString("gmtdatesent");
    }

    public static String getPunisherUUID(String id) {
        Document data = (Document) Main.getInstance().mongoHandler.getPunishments().find(eq("_id", id)).first();
        return data.getString("punisher");
    }

    public static String getTargetUUID(String id) {
        Document data = (Document) Main.getInstance().mongoHandler.getPunishments().find(eq("_id", id)).first();
        return data.getString("target");
    }

    public static String getForcedRemoved(String id) {
        Document data = (Document) Main.getInstance().mongoHandler.getPunishments().find(eq("_id", id)).first();
        return data.getString("forcedremove");
    }

    public static String getType(String id) {
        Document data = (Document) Main.getInstance().mongoHandler.getPunishments().find(eq("_id", id)).first();
        return data.getString("punishtype");
    }

    public static String getSeen(String id) {
        Document data = (Document) Main.getInstance().mongoHandler.getPunishments().find(eq("_id", id)).first();
        return data.getString("seen");
    }

    public static Boolean getRemoved(String id) {
        Document data = (Document) Main.getInstance().mongoHandler.getPunishments().find(eq("_id", id)).first();
        return data.getBoolean("forcedremove");
    }

    public static boolean isInAppeals(String uuid) {
        try {
            Document data = (Document) Main.getInstance().mongoHandler.getAppealsteam().find(eq("uuid", uuid)).first();
            return !data.isEmpty();
        } catch (NullPointerException e) {
            return false;
        }
    }

    public static String getID(String target, String expire, String reason) {
        Document data = (Document) Main.getInstance().mongoHandler.getPunishments().find(and(eq("target", target),eq("gmtdateexpire",expire),eq("reason",reason))).first();
        return data.getString("_id");
    }
//    //TOMORROWS JOB
//    private static Statement statement = DatabaseManagement.statement;
//    private static DatabaseHandler instance;
//    public static DatabaseHandler getInstance() {if (instance == null) {instance = new DatabaseHandler();} return instance;}
//
//
//    public boolean isExpired(String id) {
//        //TODO
//        return false;
//    }

}
