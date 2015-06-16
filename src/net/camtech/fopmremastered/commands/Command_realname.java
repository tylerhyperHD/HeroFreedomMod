package net.camtech.fopmremastered.commands;

import net.camtech.fopmremastered.camutils.CUtils_Methods;
import net.camtech.fopmremastered.FOPMR_Configs;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@CommandParameters(name = "realname", description = "Find the real name of a player from their nickname.", usage = "/realname [player]")
public class Command_realname
{
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if(args.length != 1)
        {
            return false;
        }
        for(String uuid : FOPMR_Configs.getAdmins().getConfig().getKeys(false))
        {
            String nick = FOPMR_Configs.getAdmins().getConfig().getString(uuid + ".displayName");
            String name = FOPMR_Configs.getAdmins().getConfig().getString(uuid + ".lastName");
            String onoroff = ((Bukkit.getPlayer(name) != null) ? (ChatColor.GREEN + "online") : (ChatColor.RED + "offline"));
            if(nick.toLowerCase().contains(args[0].toLowerCase()))
            {
                sender.sendMessage(CUtils_Methods.colour(nick + ChatColor.WHITE + " is " + name + " and is " + onoroff + ChatColor.WHITE + "."));
            }
        }
        return true;
    }
}
