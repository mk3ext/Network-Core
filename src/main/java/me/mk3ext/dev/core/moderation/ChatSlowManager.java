package me.mk3ext.dev.core.moderation;

import me.mk3ext.dev.core.util.Common;
import me.mk3ext.dev.core.util.UtilColour;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.Bukkit;

public class ChatSlowManager {

    private static ChatSlowManager instance = new ChatSlowManager();
    public volatile long chatSlow = 0;
    public String slowStartMessage = UtilColour.format(Common.CHATprefix + " &7Chat Slow has been Enabled. &d(Delay: <time>)");
        public String slowStopMessage = UtilColour.format(Common.CHATprefix + " &7Chat Slow has been Disabled.");

    public String setChatSlowBackend(long input) {
        if (input < 0) {
            return "The number must be above 0.";
        } else if (input > 0) {
            this.chatSlow = input;
            Bukkit.getServer().broadcastMessage(this.slowStartMessage.replace("<time>", format(input)));
        } else {
            if (this.chatSlow > 0) {
                this.chatSlow = 0;
                Bukkit.getServer().broadcastMessage(this.slowStopMessage);
            } else {
                return "Chat Slow is already disabled.";
            }
        } return null;

    }

    public static String format(long time) {
        return DurationFormatUtils.formatDurationWords(time, true, true);
    }

    public static ChatSlowManager getInstance() {
        return instance;
    }
    
}
