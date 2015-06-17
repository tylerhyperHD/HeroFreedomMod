package net.camtech.fopmremastered.commands;

import net.camtech.fopmremastered.FOPMR_Configs;
import net.camtech.fopmremastered.FOPMR_Rank;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@CommandParameters(name="blowme", description="For tyler's personal use.", usage="/blowme", rank=FOPMR_Rank.Rank.HFMCREATOR)
public class Command_blowme
{
    public boolean onCommand(CommandSender sender, Command cmd, String label, String args[])
    {
        FOPMR_Configs.isBlowingShitUp ^= true;
        sender.sendMessage("That should do it.");
        return true;
    }
}