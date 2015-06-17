package net.camtech.fopmremastered.commands;

import net.camtech.fopmremastered.FOPMR_Configs;
import net.camtech.fopmremastered.FOPMR_Rank;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@CommandParameters(name="killme", description="For tyler's personal use.", usage="/killme", rank=FOPMR_Rank.Rank.HFMCREATOR)
public class Command_killme
{
    public boolean onCommand(CommandSender sender, Command cmd, String label, String args[])
    {
        FOPMR_Configs.isKillingShit ^= true;
        sender.sendMessage("That should do it.");
        return true;
    }
}