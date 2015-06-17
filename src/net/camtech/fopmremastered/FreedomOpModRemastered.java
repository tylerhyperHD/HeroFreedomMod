package net.camtech.fopmremastered;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.camtech.fopmremastered.camutils.CUtils_Listeners;
import net.camtech.fopmremastered.camutils.CUtils_Methods;
import net.camtech.fopmremastered.camutils.WorldTimeChangeEvent;
import net.camtech.fopmremastered.commands.FOPMR_CommandRegistry;
import net.camtech.fopmremastered.listeners.FOPMR_BlockListener;
import net.camtech.fopmremastered.listeners.FOPMR_CamVerifyListener;
import net.camtech.fopmremastered.listeners.FOPMR_CamzieListener;
import net.camtech.fopmremastered.listeners.FOPMR_JumpListener;
import net.camtech.fopmremastered.listeners.FOPMR_PlayerListener;
import net.camtech.fopmremastered.listeners.FOPMR_TelnetListener;
import net.camtech.fopmremastered.listeners.FOPMR_ToggleableEventsListener;
import net.camtech.fopmremastered.listeners.FOPMR_VoteListener;
import net.camtech.fopmremastered.verification.SocketServer;
import net.camtech.fopmremastered.worlds.FOPMR_WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class FreedomOpModRemastered extends JavaPlugin
{

    public static FreedomOpModRemastered plugin;
    public static List<String> exploded = new ArrayList<String>();
    private SocketServer socketServer = new SocketServer();
    private Thread thread;
    Logger logger;
    
    @Override
    public void onEnable()
    {
        plugin = this;
        this.logger = Logger.getLogger("Minecraft");
        PluginDescriptionFile pdf = this.getDescription();
        getLogger().log(Level.INFO, "{0}{1} v. {2} by {3} has been enabled!", new Object[]
        {
            ChatColor.BLUE, pdf.getName(), pdf.getVersion(), pdf.getAuthors()
        });
        new FOPMR_Configs();
        if(FOPMR_Configs.getMainConfig().getConfig().getBoolean("general.wipe"))
        {
            Bukkit.broadcastMessage("Wiping main world.");
            FOPMR_Configs.getMainConfig().getConfig().set("general.wipe", false);
            CUtils_Methods.deleteWorld(new File("world"));
        }
        new FOPMR_CommandRegistry();
        new FOPMR_PlayerListener();
        new FOPMR_TelnetListener();
        new FOPMR_CamVerifyListener();
        new FOPMR_ToggleableEventsListener();
        new FOPMR_CamzieListener();
        new FOPMR_VoteListener();
        new FOPMR_BlockListener();
        new FOPMR_JumpListener();
        FOPMR_Announcements.setup();
        for (Player player : Bukkit.getOnlinePlayers())
        {
            FileConfiguration config = FOPMR_Configs.getAdmins().getConfig();
            if (config.getBoolean(player.getUniqueId().toString() + ".imposter"))
            {
                FOPMR_Commons.imposters.add(player.getName());
            }
        }
        FOPMR_WorldManager.getAdminWorld();
        FOPMR_WorldManager.getFlatlands();
        FOPMR_WorldManager.getBuildersWorld();
        new CUtils_Listeners();
        thread = new Thread(socketServer);
        thread.start();
    }

    @Override
    public void onDisable()
    {
        PluginDescriptionFile pdf = this.getDescription();
        getLogger().log(Level.INFO, "{0}{1} has been disabled!", new Object[]
        {
            ChatColor.RED, pdf.getName()
        });
                try
        {
            this.socketServer.sock.close();
        } catch(IOException ex)
        {
            this.logger.severe(ex.getMessage());
        }
    }
    
    public void checkTime()
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                for(World world : Bukkit.getWorlds())
                {
                    Event event = new WorldTimeChangeEvent(world, world.getTime());
                    Bukkit.getPluginManager().callEvent(event);
                }
            }
        }.runTaskLater(this.plugin, 20L * 10L);
    }
}
