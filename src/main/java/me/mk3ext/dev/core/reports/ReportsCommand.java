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

import static com.mongodb.client.model.Filters.eq;

public class ReportsCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return false;
        }

        Profile prof = new Profile((Player) sender);

        if (RankGroupsUtil.isStaff(prof.getRank())) {
            ArrayList<Integer> ids = new ArrayList<>();

            MongoIterable d = Main.getInstance().mongoHandler.getReports().find(eq("status", "NOT_HANDLED"));
            if (d.cursor().hasNext()) {
                for (Object o : d) {
                    Document data = (Document) o;
                    ids.add(data.getInteger("_id"));
                }
            }

            if (ids.isEmpty()) {
                prof.sendMessage(Common.REPORTprefix + " &7There are no unhandled reports.");
            } else {
                prof.sendMessage(Common.REPORTprefix + " &7There are &d" + ids.size() + " &7unhandled reports. IDs:");
                prof.sendMessage(Common.REPORTprefix + " &7" + ids.toString().replace("[", "").replace("]", ""));
            }
        } else {
            prof.sendMessage(Common.NoPermsHelper);
        }
        return true;
    }
}
