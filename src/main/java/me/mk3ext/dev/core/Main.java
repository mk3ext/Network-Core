package me.mk3ext.dev.core;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import lombok.Getter;
import me.mk3ext.dev.core.database.mongoHandler;
import me.mk3ext.dev.core.enums.CoreCommands;
import me.mk3ext.dev.core.listeners.InventoryClick;
import me.mk3ext.dev.core.listeners.Join;
import me.mk3ext.dev.core.listeners.TwoFactorEvents;
import me.mk3ext.dev.core.profiles.Profile;
import me.mk3ext.dev.core.punish.GUIClickHandler;
import me.mk3ext.dev.core.punish.OnChat;
import me.mk3ext.dev.core.punish.OnJoin;
import me.mk3ext.dev.core.punish.Prevent;
import me.mk3ext.dev.core.punish.commands.*;
import me.mk3ext.dev.core.punish.types.PunishBan;
import me.mk3ext.dev.core.punish.utils.BungeeListener;
import me.mk3ext.dev.core.rank.RankGroupsUtil;
import me.mk3ext.dev.core.suffix.InteractEvent;
import me.mk3ext.dev.core.util.Common;
import me.mk3ext.dev.core.util.Decor.Title;
import me.mk3ext.dev.core.util.UtilColour;
import me.mk3ext.dev.core.util.UtilConsole;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.redisson.Redisson;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class Main extends JavaPlugin implements PluginMessageListener {

    private static Main instance;
    public static Main getInstance() { return instance;}

    public mongoHandler mongoHandler;

    //TODO REDIS INFORMATION
    @Getter
    public static RedissonClient redis;
//        private String redisHost = "49.12.121.116";
//        private String redisPassword = "3z!]G[-]GtXZr5$N2,vJTZ]?f&9y[YLW";
//        private final int redisPort = 6379;

    private String redisHost = "localhost";
    private String redisPassword = "foobared";
    private int redisPort = 6379;

    private RTopic<String> alerts;

    @Override
    public void onEnable() {
        UtilConsole.send("Plugin has been enabled. Version " + getDescription().getVersion() + " has been detected.");

        registerEvents();

        instance = this;


        mongoHandler = new mongoHandler();
        mongoHandler.connect();



        registerCommands();

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new BungeeListener());
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "announcement:ann", this);
        setupRedis();
        saveDefaultConfig();
    }

    public void setupRedis() {

        Config config = new Config();
        config.useSingleServer().setAddress("127.0.0.1:6379");
        config.useSingleServer().setConnectionMinimumIdleSize(10);
        config.useSingleServer().setPassword(redisPassword);
        redis = Redisson.create(config);

        redis.getTopic("CORE:staff").addListener((channel, msg) -> {
           for (Player pl : Bukkit.getOnlinePlayers()) {
               if (RankGroupsUtil.isStaff(new Profile(pl).getRank())) {
                   if (msg.toString().contains(pl.getName())) {
                       pl.playSound(pl.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 7F, 5F);
                   }
                   pl.sendMessage(msg.toString());
               }
           }
        });

        redis.getTopic("CORE:announcements").addListener((channel, msg) -> {
            Bukkit.getServer().broadcastMessage(UtilColour.format("&8(&c&lNetwork Announcement&8) &7" + msg));
            Bukkit.getOnlinePlayers().forEach(player -> {

                Title title = new Title(Common.AnnouncementGLOBAL, "§7" + msg.toString(), 7, 50, 7);

                title.send(player);

                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 7F, 5F);
            });
        });
    }

    @Override
    public void onDisable() {
        UtilConsole.send("Plugin has been disabled.");
        instance = null;
        redis.shutdown();
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new Join(), this);
        getServer().getPluginManager().registerEvents(new InventoryClick(), this);
        getServer().getPluginManager().registerEvents(new TwoFactorEvents(), this);
        getServer().getPluginManager().registerEvents(new PunishBan(), this);
        getServer().getPluginManager().registerEvents(new OnChat(), this);
        getServer().getPluginManager().registerEvents(new OnJoin(), this);
        getServer().getPluginManager().registerEvents(new GUIClickHandler(), this);
        getServer().getPluginManager().registerEvents(new Prevent(), this);
        getServer().getPluginManager().registerEvents(new InteractEvent(), this);
    }

    public void registerCommands() {
        for (CoreCommands c : CoreCommands.values()) {
            getCommand(c.getName()).setExecutor(c.getExecutor());
        }

        getCommand("punish").setExecutor(new PunishCommand());
        getCommand("appealsteam").setExecutor(new AppealsTeamCommand());
        getCommand("punishcustom").setExecutor(new PunishCustomCommand());
        getCommand("punishhistory").setExecutor(new PunishHistory());
        getCommand("removepunishment").setExecutor(new RemovePunishment());
    }

    public static Map<UUID, String> serverMap = new HashMap<>();

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (channel.equalsIgnoreCase("announcement:ann")) {
            ByteArrayDataInput in = ByteStreams.newDataInput(message);
            String subChannel = in.readUTF();
            if (subChannel.equalsIgnoreCase("snd")) { //GLOBAL
                for (Player pl : Bukkit.getOnlinePlayers()) {
                    pl.playSound(pl.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10F, 5F);
                }
            } else if (subChannel.equalsIgnoreCase("snsd")) { //LOCAL
                if (Bukkit.getOnlinePlayers().contains(player)) {
                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        pl.playSound(pl.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10F, 5F);
                    }
                }
            }
        } else if (channel.equalsIgnoreCase("BungeeCord")) {
            UtilConsole.debug("one");
            ByteArrayDataInput in = ByteStreams.newDataInput(message);
            String subchannel = in.readUTF();
            if (subchannel.equals("GetServer")) {
                UtilConsole.debug("two");
                String serverName = in.readUTF();
                UtilConsole.debug("servername");
                serverMap.put(player.getUniqueId(), serverName);
            }
        }
    }

}
