package net.camtech.fopmremastered.commands;

import net.camtech.fopmremastered.camutils.CUtils_Methods;
import net.camtech.fopmremastered.FOPMR_Configs;
import net.camtech.fopmremastered.FOPMR_Rank;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@CommandParameters(name = "hfm", usage = "/hfm <reload>", description = "Check info about the plugin or reload the configuration files.")
public class Command_hfm
{
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (args.length == 1)
        {
            if (args[0].equalsIgnoreCase("reload"))
            {
                if (!FOPMR_Rank.isAdmin(sender))
                {
                    sender.sendMessage("Only admins can reload the HeroFreedomMod configuration files.");
                    return true;
                }
                FOPMR_Configs.getAdmins().reloadConfig();
                FOPMR_Configs.getBans().reloadConfig();
                FOPMR_Configs.getCommands().reloadConfig();
                FOPMR_Configs.getMainConfig().reloadConfig();
                FOPMR_Configs.getReports().reloadConfig();
                FOPMR_Configs.getAnnouncements().reloadConfig();
                sender.sendMessage(ChatColor.GREEN + "HeroFreedomMod Configs reloaded!");
                FOPMR_CommandRegistry.registerCommands();
                sender.sendMessage(ChatColor.GREEN + "HeroFreedomMod Commands reloaded!");
                return true;
            }
            return false;
        }
        else
        {
            sender.sendMessage(ChatColor.GREEN + "This is the HeroFreedomMod!");
            sender.sendMessage(CUtils_Methods.randomChatColour() + "This mod originated by Camzie99");
            sender.sendMessage(CUtils_Methods.randomChatColour() + "This mod redone by tylerhyperHD");
            sender.sendMessage(CUtils_Methods.colour("&-Created in the likes of the TFM but with more " + CUtils_Methods.randomChatColour() + "flexibility&- by " + CUtils_Methods.randomChatColour() + "Camzie99&-!"));
        }
        return true;
    }

}
