package me.mk3ext.dev.core.reports;

import me.mk3ext.dev.core.Main;
import me.mk3ext.dev.core.database.createReport;
import me.mk3ext.dev.core.util.UtilColour;
import com.mongodb.client.MongoIterable;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

public class ReportsManagement {

    private static int getNextID() {
        int number = 1;

        MongoIterable d = Main.getInstance().mongoHandler.getReports().find();
        if (d.cursor().hasNext()) {
            for (Object o : d) {
                Document data = (Document) o;
                Integer num = data.getInteger("_id");
                if(num > number) {
                    number = num;
                }
            }
        }
        return number;
    }

    public static void newReport(String offenderUUID, String fromUUID, String reason) {
        int count = Math.toIntExact(Main.getInstance().mongoHandler.getReports().countDocuments());
        count = count + 1;
        new createReport(count,offenderUUID,fromUUID,reason,"todo","NOT_HANDLED","NotYetHandled");

        int id = getNextID();

        OfflinePlayer target = Bukkit.getOfflinePlayer(UUID.fromString(offenderUUID));

        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            Main.redis.getTopic("CORE:staff").publish(UtilColour.format("&9&lNew Report >> &7A report has been filed against &d" + target.getName() + "&7. (Report ID: "+ id + ")"));
            }, 2L);

    }

    public static void handleReport(int id, ReportOutcome outcome, String handlerUUID) {
        if(outcome == ReportOutcome.NOT_HANDLED) {
            return;
        }
        switch(outcome) {
            case ACCEPTED:
                Main.getInstance().mongoHandler.setReportDoc("ACCEPTED","status",id);
                System.out.println("ACCEPTED");
                break;
            case DENIED:
                Main.getInstance().mongoHandler.setReportDoc("DENIED","status",id);
                break;
            case SYSTEM_ABUSE:
                Main.getInstance().mongoHandler.setReportDoc("SYSTEM_ABUSE","status",id);
                break;
            case OUTCOME_NOT_REACHED:
                Main.getInstance().mongoHandler.setReportDoc("OUTCOME_NOT_REACHED","status",id);
        }
        System.out.println("HANDLER CHANGED");
        Main.getInstance().mongoHandler.setReportDoc(handlerUUID,"handlerUUID",id);
    }

    public static boolean reportExists(int id) {
        Document data = (Document) Main.getInstance().mongoHandler.getReports().find(eq("_id",id)).first();
        return data != null;
    }
}
