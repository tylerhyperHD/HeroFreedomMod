package me.StevenLawson.TotalFreedomMod.Listener;

import me.StevenLawson.TotalFreedomMod.Config.TFM_ConfigEntry;
import me.StevenLawson.TotalFreedomMod.TFM_BanManager;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import me.StevenLawson.TotalFreedomMod.TotalFreedomMod;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class TFM_ServerListener implements Listener
{
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onServerPing(ServerListPingEvent event)
    {
        final String ip = event.getAddress().getHostAddress();

        if (TFM_BanManager.isIpBanned(ip))
        {
            event.setMotd(ChatColor.RED + TFM_Util.getPlayerFromIp(ip) + ", you are banned.");
            return;
        }

        if (TFM_ConfigEntry.ADMIN_ONLY_MODE.getBoolean())
        {
            event.setMotd(ChatColor.RED + TotalFreedomMod.SERVER_NAME + " is currently closed.");
            return;
        }

        if (Bukkit.hasWhitelist())
        {
            event.setMotd(ChatColor.RED + "Whitelist enabled.");
            return;
        }

        if (Bukkit.getOnlinePlayers().size() >= Bukkit.getMaxPlayers())
        {
            event.setMotd(ChatColor.RED + TotalFreedomMod.SERVER_NAME + " is currently full.");
            return;
        }
        
        if (TFM_ConfigEntry.ENABLE_CHAOS.getBoolean())
        {
            event.setMotd(ChatColor.RED + TotalFreedomMod.SERVER_NAME + " is currently in chaos mode, prepare for some crazy shit!");
            return;
        }
        
       if (TFM_ConfigEntry.TRAINING_SESSION.getBoolean())
        {
            event.setMotd(ChatColor.RED + TotalFreedomMod.SERVER_NAME + " is currently in a training session.");
            return;
        }
        
        if (TFM_ConfigEntry.DESTRUCTIVE_MODE.getBoolean())
        {
            event.setMotd(ChatColor.RED + TotalFreedomMod.SERVER_NAME + " is currently in destructive mode, prepare for some crazy shit!");
            return;
        }

        // Colorful MOTD

        String message = String.format("Welcome to " + TotalFreedomMod.SERVER_NAME + "%s! - Fun, Free and Easy! Running on Spigot for Minecraft 1.8.7!", TFM_Util.getPlayerFromIp(ip));

        final StringBuilder motd = new StringBuilder();

        for (String word : message.split(" "))
        {
            motd.append(TFM_Util.randomChatColor()).append(word).append(" ");
        }

        event.setMotd(TFM_Util.colorize(motd.toString()));
    }
}
