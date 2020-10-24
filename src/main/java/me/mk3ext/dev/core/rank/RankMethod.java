package me.mk3ext.dev.core.rank;

import me.mk3ext.dev.core.Main;
import me.mk3ext.dev.core.database.createRanks;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

public class RankMethod {

    //testing
    public static <T extends Enum<T>> T getEnumFromString(Class<T> c, String string) {
        if( c != null && string != null ) {
            try {
                return Enum.valueOf(c, string.trim().toUpperCase());
            } catch(IllegalArgumentException ignored) {
            }
        }
        return null;
    }

    public static Rank getRank(String uuid) {
        Document data = (Document) Main.getInstance().mongoHandler.getRanks().find(eq("uuid", uuid)).first();

        if(data == null) {
            new createRanks(uuid,"DEFAULT");
            return Rank.DEFAULT;
        }
        String rank = data.getString("rank");
        return getEnumFromString(Rank.class,rank);
    }

    public static void setRank(String uuid, String rank) {
        Main.getInstance().mongoHandler.setRankDoc(rank,"rank",uuid);
    }

}
