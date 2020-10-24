package me.mk3ext.dev.core.reports;

import me.mk3ext.dev.core.Main;
import me.mk3ext.dev.core.rank.RankGroupsUtil;
import me.mk3ext.dev.core.util.Common;
import com.mongodb.client.MongoIterable;
import me.mk3ext.dev.core.profiles.Profile;
import org.bson.Document;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class ReportStatsCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }

        Profile prof = new Profile((Player) sender);

        ArrayList<Integer> fromIds = new ArrayList<>();

        MongoIterable d = Main.getInstance().mongoHandler.getReports().find(eq("fromUUID", prof.getUuid()));
        if (d.cursor().hasNext()) {
            for (Object o : d) {
                Document data = (Document) o;
                fromIds.add(data.getInteger("_id"));
            }
        }

        prof.sendMessage(Common.REPORTprefix + " &7You have created &d" + fromIds.size() + " &7reports.");

        if (RankGroupsUtil.isStaff(prof.getRank())) {
            ArrayList<Integer> handledByUuids = new ArrayList<>();

            MongoIterable da = Main.getInstance().mongoHandler.getReports().find(eq("handledByUUID", prof.getUuid()));
            if (da.cursor().hasNext()) {
                for (Object o : da) {
                    Document data = (Document) o;
                    handledByUuids.add(data.getInteger("_id"));
                }
            }

            prof.sendMessage(Common.REPORTprefix + " &7You have handled &d" + handledByUuids.size() + " &7reports.");
        }

        return true;

    }
}
