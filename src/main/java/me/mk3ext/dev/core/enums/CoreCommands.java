package me.mk3ext.dev.core.enums;


import me.github.wert.Core.moderation.*;
import me.github.wert.Core.player.*;
import me.mk3ext.dev.core.rank.RankCommand;
import me.github.wert.Core.reports.*;
import me.mk3ext.dev.core.suffix.SuffixCommand;
import me.mk3ext.dev.core.suffix.SuffixGUI;
import me.mk3ext.dev.core.support.ByteCommand;
import me.mk3ext.dev.core.moderation.*;
import me.mk3ext.dev.core.player.*;
import me.mk3ext.dev.core.reports.*;
import org.bukkit.command.CommandExecutor;

public enum CoreCommands {

    RANK("rank", "/rank <player> <rank>", "This command changes a player's rank.", new RankCommand()),
    CLEAR_CHAT("clearchat", "/clearchat", "This command clears the chat in your server.", new ClearChat()),
    CLEAR_INV("clear", "/clear [player]", "This command clears yours or another player's inventory.", new Clear()),
    GAMEMODE("gamemode", "/gamemode [player]", "This command switches yours or another player's gamemode.", new Gamemode()),
    MESSAGE("message", "/message <player> <text>", "This command privately messages another player.", new Message()),
    REPLY("reply", "/reply <text>", "This command replies to another player's private message", new Reply()),
    MESSAGE_SPY("messagespy", "/messagespy", "This command toggles message spy.", new MessageSpy()),
    GIVE("give", "/give [player] <item> [amount] [Enchantment:level]", "This command gives players items.", new Give()),
    TELEPORT("teleport", "", "This command teleports you to another player.", new Teleport()),
    BYTES("bytes", "/bytes <get/set/add/remove> <player> [amount]", "This command manages bytes.", new ByteCommand()),
    REPORT("report", "/report <player> <reason>", "This command reports another player.", new ReportCommand()),
    REPORT_INFO("reportinfo", "/reportinfo <id>", "This command gets report info.", new ReportInfoCommand()),
    REPORT_HANDLE("reporthandle", "/reporthandle <id>", "This command handles a report.", new ReportHandleCommand()),
    REPORTS("reports", "/reports", "This command shows unhandled reports.", new ReportsCommand()),
    REPORT_STATS("reportstats", "/reportstats", "This command shows report statistics", new ReportStatsCommand()),
    STAFF_CHAT("staffchat", "/staffchat <message>", "This command lets staff members talk privately.", new StaffChat()),
    CHAT_SLOW("chatslow", "/chatslow <time>", "This command lets you slow the chat.", new ChatSlow()),
    CHAT_SILENCE("chatsilence", "/chatsilence", "This command silences the chat.", new ChatSilence()),
    VANISH("vanish", "/vanish", "This command lets you disappear from other players.", new Vanish()),
    MESSAGE_TOGGLE("messagetoggle", "/messagetoggle", "This command toggles incoming messages.", new MessageToggle()),
    MESSAGE_SOUNDS("messagesounds", "/messagesounds", "This command toggles message sounds.", new MessageSounds()),
    HUB("hub", "/hub", "", new HubCommand()),
    ANNOUNCE("announce", "/announce global/local <message>", "This command announces a message.", new AnnounceCommand()),
    SHOUT("shout", "/shout <message>", "This command broadcasts a message.", new ShoutCommand()),
    SUFFIX("suffixmanage", "/suffixmanage", "Edits suffixes.", new SuffixCommand()),
    SUFFIX_GUI("suffix", "/suffix", "Change your active suffix.", new SuffixGUI());

    private String name;

    public String getName() {
        return name;
    }

    public String getUsage() {
        return usage;
    }

    public String getDescription() {
        return description;
    }

    public CommandExecutor getExecutor() {
        return executor;
    }

    private String usage;
    private String description;
    private CommandExecutor executor;

    CoreCommands(String name, String usage, String description, CommandExecutor executor) {
        this.name = name;
        this.description = description;
        this.executor = executor;
        this.usage = usage;
    }
}
